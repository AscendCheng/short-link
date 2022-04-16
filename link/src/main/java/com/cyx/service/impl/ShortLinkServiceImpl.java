package com.cyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.component.LoginInterceptor;
import com.cyx.component.ShortLinkComponent;
import com.cyx.config.RabbitMqConfig;
import com.cyx.enums.DomainTypeEnum;
import com.cyx.enums.EventMessageTypeEnum;
import com.cyx.enums.ShortLinkStateEnum;
import com.cyx.manager.DomainManager;
import com.cyx.manager.GroupCodeMappingManager;
import com.cyx.manager.LinkGroupManager;
import com.cyx.manager.ShortLinkManager;
import com.cyx.mapper.ShortLinkMapper;
import com.cyx.model.DomainDO;
import com.cyx.model.EventMessage;
import com.cyx.model.GroupCodeMappingDO;
import com.cyx.model.LinkGroupDO;
import com.cyx.model.PageVo;
import com.cyx.model.ShortLinkDO;
import com.cyx.model.request.ShortLinkAddRequest;
import com.cyx.model.request.ShortLinkDelRequest;
import com.cyx.model.request.ShortLinkPageRequest;
import com.cyx.model.request.ShortLinkUpdateRequest;
import com.cyx.service.ShortLinkService;
import com.cyx.utils.CommonUtil;
import com.cyx.utils.IdUtil;
import com.cyx.utils.JsonData;
import com.cyx.utils.JsonUtil;
import com.cyx.vo.ShortLinkVO;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cyx
 * @since 2022-02-15
 */
@Service
@Slf4j
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Autowired
    private ShortLinkManager shortLinkManager;

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DomainManager domainManager;

    @Autowired
    private LinkGroupManager linkGroupManager;

    @Autowired
    private ShortLinkComponent shortLinkComponent;

    @Autowired
    private GroupCodeMappingManager groupCodeMappingManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ShortLinkVO parseShortLinkCode(String shortLinkCode) {
        ShortLinkDO shortLinkDO = shortLinkManager.findByShortLinkCode(shortLinkCode);
        if (shortLinkDO == null) {
            return null;
        }
        ShortLinkVO shortLinkVO = new ShortLinkVO();
        BeanUtils.copyProperties(shortLinkDO, shortLinkVO);
        return shortLinkVO;
    }

    @Override
    public JsonData createShortLink(ShortLinkAddRequest request) {
        String newOriginal = CommonUtil.addUrlPrefix(request.getOriginalUrl());
        request.setOriginalUrl(newOriginal);
        EventMessage eventMsg = EventMessage.builder().account(LoginInterceptor.threadLocal.get().getAccountNo())
                .content(JsonUtil.obj2Json(request))
                .messageId(IdUtil.geneSnowFlakeID().toString())
                .eventMessageType(EventMessageTypeEnum.SHORT_LINK_ADD.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMqConfig.getShortLinkEventExchange(),
                rabbitMqConfig.getShortLinkAddRoutingKey(),
                eventMsg);
        // 如果请求不为空

        return JsonData.buildSuccess();
    }

    /**
     * 处理短链新增逻辑.
     *
     * @param eventMessage 消息
     * @return boolean
     */
    @Override
    public boolean handleAddShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccount();
        String eventMessageType = eventMessage.getEventMessageType();
        ShortLinkAddRequest shortLinkAddRequest = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkAddRequest.class);
        // 短链域名校验
        DomainDO domainDO = this.checkDomain(shortLinkAddRequest.getDomainType(), shortLinkAddRequest.getDomainId(), accountNo);
        // 校验组是否合法
        LinkGroupDO linkGroupDO = this.checkLinkGroup(shortLinkAddRequest.getGroupId(), accountNo);

        // 长链摘要
        String originalUrlDigest = CommonUtil.MD5(shortLinkAddRequest.getOriginalUrl());

        // 生成短链码
        String shortLinkCode = shortLinkComponent.createShortLink(originalUrlDigest);

        // 加锁
        //key1是短链码，ARGV[1]是accountNo,ARGV[2]是过期时间
        String script = "if redis.call('EXISTS',KEYS[1])==0 then redis.call('set',KEYS[1],ARGV[1]); redis.call('expire',KEYS[1],ARGV[2]); return 1;" +
                " elseif redis.call('get',KEYS[1]) == ARGV[1] then return 2;" +
                " else return 0; end;";

        Long result = (Long) redisTemplate.execute(new
                DefaultRedisScript<>(script, Long.class), Arrays.asList(shortLinkCode), accountNo, 100);
        int row = 0;
        boolean duplicateCodeFlag = false;
        if (result > 0) {
            if (EventMessageTypeEnum.SHORT_LINK_ADD_LINK.name().equalsIgnoreCase(eventMessageType)) {
                // 判断是否已存在
                ShortLinkDO shortLinkDOInDb = shortLinkManager.findByShortLinkCode(shortLinkCode);
                if (shortLinkDOInDb == null) {
                    ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                            .accountNo(accountNo)
                            .code(shortLinkCode)
                            .title(shortLinkAddRequest.getTitle())
                            .originalUrl(shortLinkAddRequest.getOriginalUrl())
                            .domain(domainDO.getValue())
                            .groupId(linkGroupDO.getId())
                            .expired(shortLinkAddRequest.getExpired())
                            .sign(originalUrlDigest)
                            .state(ShortLinkStateEnum.ACTIVE.name())
                            .del(0)
                            .build();
                    row = shortLinkManager.addShortLink(shortLinkDO);
                } else {
                    log.error("SHORT_LINK_ADD_LINK短链码重复");
                    duplicateCodeFlag = true;
                }
            } else if (EventMessageTypeEnum.SHORT_LINK_ADD_MAPPING.name().equalsIgnoreCase(eventMessageType)) {
                GroupCodeMappingDO groupCodeMappingDOInDB = groupCodeMappingManager.findByCodeAndMappingId(shortLinkCode, linkGroupDO.getId(), accountNo);
                if (groupCodeMappingDOInDB == null) {
                    GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                            .accountNo(accountNo)
                            .code(shortLinkCode)
                            .title(shortLinkAddRequest.getTitle())
                            .originalUrl(shortLinkAddRequest.getOriginalUrl())
                            .domain(domainDO.getValue())
                            .groupId(linkGroupDO.getId())
                            .expired(shortLinkAddRequest.getExpired())
                            .sign(originalUrlDigest)
                            .state(ShortLinkStateEnum.ACTIVE.name())
                            .del(0)
                            .build();
                    row = groupCodeMappingManager.add(groupCodeMappingDO);
                } else {
                    log.error("SHORT_LINK_ADD_MAPPING短链码重复");
                    duplicateCodeFlag = true;
                }

            }
        } else {
            // 加锁失败,自旋100ms。原因：短链码已被占用,需要重新生成
            log.error("短链码重复,加锁失败");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.error("短链码重复,sleep失败");
            }
            duplicateCodeFlag = true;
        }
        if (duplicateCodeFlag) {
            String originalUrl = CommonUtil.addUrlPrefixVersion(shortLinkAddRequest.getOriginalUrl());
            shortLinkAddRequest.setOriginalUrl(originalUrl);
            eventMessage.setContent(JsonUtil.obj2Json(shortLinkAddRequest));
            log.warn("短链码报错失败，重新生成:{}", eventMessage);
            handleAddShortLink(eventMessage);
        }
        return row > 0;
    }

    @Override
    public PageVo pageShortLinkByGroupId(ShortLinkPageRequest request) {
        PageVo pageVo = groupCodeMappingManager.pageShortLinkByGroupId(request.getPage(), request.getSize(),
                LoginInterceptor.threadLocal.get().getAccountNo(), request.getGroupId());
        return pageVo;
    }

    @Override
    public JsonData deleteShortLink(ShortLinkAddRequest request) {
        EventMessage eventMsg = EventMessage.builder().account(LoginInterceptor.threadLocal.get().getAccountNo())
                .content(JsonUtil.obj2Json(request))
                .messageId(IdUtil.geneSnowFlakeID().toString())
                .eventMessageType(EventMessageTypeEnum.SHORT_LINK_DEL.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMqConfig.getShortLinkEventExchange(),
                rabbitMqConfig.getShortLinkDeleteRoutingKey(),
                eventMsg);
        // 如果请求不为空

        return JsonData.buildSuccess();
    }

    /**
     * 处理短链新增逻辑.
     *
     * @param eventMessage 消息
     * @return boolean
     */
    @Override
    public boolean handleUpdateShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccount();
        String eventMessageType = eventMessage.getEventMessageType();
        ShortLinkUpdateRequest shortLinkUpdateRequest = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkUpdateRequest.class);
        // 短链域名校验
        DomainDO domainDO = this.checkDomain(shortLinkUpdateRequest.getDomainType(), shortLinkUpdateRequest.getDomainId(), accountNo);

        int rows = 0;
        if (EventMessageTypeEnum.SHORT_LINK_UPDATE_LINK.name().equalsIgnoreCase(eventMessageType)) {
            // 判断是否已存在
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .code(shortLinkUpdateRequest.getCode())
                    .title(shortLinkUpdateRequest.getTitle())
                    .domain(domainDO.getValue())
                    .build();
            rows = shortLinkManager.update(shortLinkDO);
            log.info("更新C端,rows:{}", rows);
        } else if (EventMessageTypeEnum.SHORT_LINK_ADD_MAPPING.name().equalsIgnoreCase(eventMessageType)) {
            GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                    .id(shortLinkUpdateRequest.getMappingId())
                    .groupId(shortLinkUpdateRequest.getGroupId())
                    .accountNo(accountNo)
                    .domain(domainDO.getValue())
                    .build();
            rows = groupCodeMappingManager.update(groupCodeMappingDO);
            log.info("更新B端,rows:{}", rows);
        }
        return rows > 0;
    }

    @Override
    public boolean handleDeleteShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccount();
        String eventMessageType = eventMessage.getEventMessageType();
        ShortLinkDelRequest shortLinkDelRequest = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkDelRequest.class);
        int rows = 0;
        if (EventMessageTypeEnum.SHORT_LINK_DEL_LINK.name().equalsIgnoreCase(eventMessageType)) {
            // 判断是否已存在
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .code(shortLinkDelRequest.getCode())
                    .accountNo(accountNo)
                    .build();
            rows = shortLinkManager.del(shortLinkDO);
            log.info("删除C端,rows:{}", rows);
        } else if (EventMessageTypeEnum.SHORT_LINK_DEL_MAPPING.name().equalsIgnoreCase(eventMessageType)) {
            GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                    .id(shortLinkDelRequest.getMappingId())
                    .groupId(shortLinkDelRequest.getGroupId())
                    .accountNo(accountNo)
                    .build();
            rows = groupCodeMappingManager.delete(groupCodeMappingDO);
            log.info("删除B端,rows:{}", rows);
        }
        return false;
    }

    @Override
    public JsonData updateShortLink(ShortLinkUpdateRequest request) {
        EventMessage eventMsg = EventMessage.builder().account(LoginInterceptor.threadLocal.get().getAccountNo())
                .content(JsonUtil.obj2Json(request))
                .messageId(IdUtil.geneSnowFlakeID().toString())
                .eventMessageType(EventMessageTypeEnum.SHORT_LINK_UPDATE.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMqConfig.getShortLinkEventExchange(),
                rabbitMqConfig.getShortLinkUpdateRoutingKey(),
                eventMsg);
        // 如果请求不为空

        return JsonData.buildSuccess();
    }

    /**
     * 校验域名.
     *
     * @param domainType
     * @param domainId
     * @param accountNo
     * @return
     */
    private DomainDO checkDomain(String domainType, Long domainId, Long accountNo) {
        DomainDO domainDo;
        if (DomainTypeEnum.CUSTOM.name().equals(domainType)) {
            domainDo = domainManager.findById(domainId, accountNo);
        } else {
            domainDo = domainManager.findByDomainTypeAndId(DomainTypeEnum.OFFICIAL, domainId);
        }
        Assert.notNull(domainDo, "短链域名不合法");
        return domainDo;
    }

    private LinkGroupDO checkLinkGroup(Long groupId, Long accountNo) {
        LinkGroupDO detail = linkGroupManager.detail(groupId, accountNo);
        Assert.notNull(detail, "短链组名不合法");
        return detail;
    }

}
