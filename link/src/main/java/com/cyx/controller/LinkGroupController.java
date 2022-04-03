package com.cyx.controller;

import com.cyx.enums.BizCodeEnum;
import com.cyx.model.LinkGroupVO;
import com.cyx.model.request.LinkGroupRequest;
import com.cyx.service.LinkGroupService;
import com.cyx.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LinkGroupController.
 *
 * @author ChengYuXin N0041237
 * @version 1.0.0
 * @date 2022/2/20
 */
@RestController
@RequestMapping("/api/group/v1")
public class LinkGroupController {
    @Autowired
    private LinkGroupService linkGroupService;

    @PostMapping("/add")
    public JsonData add(@RequestBody LinkGroupRequest addRequest) {
        int row = linkGroupService.add(addRequest);
        return row == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_OPER_FAIL);
    }

    @DeleteMapping("/{groupId}")
    public JsonData delete(@PathVariable("groupId") Long groupId){
        int row = linkGroupService.delete(groupId);
        return row == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_OPER_FAIL);
    }

    @GetMapping("/{groupId}")
    public JsonData detail(@PathVariable("groupId") Long groupId){
        LinkGroupVO lingGroupVo =  linkGroupService.detail(groupId);
        return lingGroupVo == null ? JsonData.buildSuccess(lingGroupVo) : JsonData.buildResult(BizCodeEnum.GROUP_NOT_EXIST);
    }

    @GetMapping("/list/all")
    public JsonData listAll(){
        List<LinkGroupVO> list =  linkGroupService.listAll();
        return JsonData.buildSuccess(list);
    }

    @PutMapping("/update")
    public JsonData update(@RequestBody LinkGroupRequest request){
        int row = linkGroupService.update(request);
        return row == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_OPER_FAIL);
    }
}
