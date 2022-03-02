package com.cyx.strategy;

import com.cyx.enums.BizCodeEnum;
import com.cyx.exception.BizException;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * CustomDBPreciseShardingAlgorithm.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/3/1
 */
public class CustomDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 获取分片后的库或表
     *
     * @param availableTargetNames 分库时->分片库集合databaseNames，分表时->分片表集合tableNames
     * @param preciseShardingValue 分片属性：logicTableName 逻辑表名称, String columnName 分片键（字段）, value 从sql中解析的分片键的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        // 获取分片键的值上的库信息
        String codePrefix = preciseShardingValue.getValue().substring(0, 1);

        for (String availableTargetName : availableTargetNames) {
            String targetNameSuffix = availableTargetName.substring(availableTargetName.length() - 1);
            if (codePrefix.equals(targetNameSuffix)) {
                return availableTargetName;
            }
        }
        throw new BizException(BizCodeEnum.DB_ROUTE_NOT_FOUND);
    }
}
