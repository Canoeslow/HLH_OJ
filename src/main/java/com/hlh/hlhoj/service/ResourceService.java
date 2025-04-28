package com.hlh.hlhoj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.model.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.ResourceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 12261
* @description 针对表【resource(资源表)】的数据库操作Service
* @createDate 2025-04-27 13:53:59
*/
public interface ResourceService extends IService<Resource> {

    Page<ResourceVO> getResourceVOpage(Page<Resource> questionPage, User loginUser, HttpServletRequest request);
}
