package com.hlh.hlhoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.exception.ThrowUtils;
import com.hlh.hlhoj.model.dto.question.DeleteResourceRequest;
import com.hlh.hlhoj.model.dto.question.GetResourceRequest;
import com.hlh.hlhoj.model.dto.question.ResourceCreateRequest;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.ResourceVO;
import com.hlh.hlhoj.service.ResourceService;
import com.hlh.hlhoj.service.UserService;
import com.hlh.hlhoj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/resource")
@Slf4j
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
public class ResourceController {
    @Resource
    private UserService userService;
    @Resource
    private ResourceService resourceService;
    
    @PostMapping("/add")
    //老师管理员创建资源
    public BaseResponse<Boolean> addResource(@RequestBody ResourceCreateRequest createRequest, HttpServletRequest request,@RequestPart("file") MultipartFile file){
        if(createRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()== UserConstant.DEFAULT_ROLE||loginUser.getUserRole()==UserConstant.USER_LOGIN_STATE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        String filename=new String();
        if(!file.isEmpty()){
             filename = FileUtils.UplodeResource(file, loginUser);
        }
        com.hlh.hlhoj.model.entity.Resource resource = new com.hlh.hlhoj.model.entity.Resource();
        resource.setTitle(createRequest.getTitle());
        resource.setTextpath(filename);
        resource.setUserid(loginUser.getId());
        resource.setCrratetime(new Date());
        boolean save = resourceService.save(resource);
        ThrowUtils.throwIf(!save,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(save);
    }

    @PostMapping("/getall")
    public BaseResponse<Page<ResourceVO>> getResource(@RequestBody GetResourceRequest getResourceRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        long current = getResourceRequest.getCurrent();
        long size = getResourceRequest.getPageSize();
        //限制爬虫
        ThrowUtils.throwIf(size>20,ErrorCode.PARAMS_ERROR);
        QueryWrapper<com.hlh.hlhoj.model.entity.Resource> queryWrapper=new QueryWrapper<>();
        if(getResourceRequest!=null){
            queryWrapper.eq(ObjectUtils.isNotEmpty(getResourceRequest.getUserid()),"userid",getResourceRequest.getUserid());
            queryWrapper.gt(ObjectUtils.isNotEmpty(getResourceRequest.getCrratetime()),"crratetime",getResourceRequest.getCrratetime());
        }
        Page<com.hlh.hlhoj.model.entity.Resource> resourcePage = resourceService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(resourceService.getResourceVOpage(resourcePage,loginUser,request));
    }

    /**
     * 删除对应的资源
     * @param getResourceRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteResource(@RequestBody DeleteResourceRequest getResourceRequest, HttpServletRequest request){
        if(getResourceRequest==null||getResourceRequest.getId()<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()== UserConstant.DEFAULT_ROLE||loginUser.getUserRole()==UserConstant.USER_LOGIN_STATE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        com.hlh.hlhoj.model.entity.Resource resource = resourceService.getById(getResourceRequest.getId());
        if(resource==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean delete = resourceService.removeById(getResourceRequest.getId());
        if(!delete){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(delete);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateResource(@RequestBody ResourceCreateRequest createRequest, HttpServletRequest request,@RequestPart("file") MultipartFile file){
        if(createRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()== UserConstant.DEFAULT_ROLE||loginUser.getUserRole()==UserConstant.USER_LOGIN_STATE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        com.hlh.hlhoj.model.entity.Resource resource = resourceService.getById(createRequest.getId());
        if(resource==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        String filename=new String();
        if(!file.isEmpty()){
            filename = FileUtils.UplodeResource(file, loginUser);
        }
        com.hlh.hlhoj.model.entity.Resource newresource = new com.hlh.hlhoj.model.entity.Resource();
        BeanUtils.copyProperties(resource,newresource);
        newresource.setCrratetime(new Date());
        if(filename!=null){
            newresource.setTextpath(filename);
        }
        boolean result = resourceService.updateById(newresource);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }
}
