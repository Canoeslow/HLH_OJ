package com.hlh.hlhoj.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hlh.hlhoj.model.entity.Resource;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.ResourceVO;
import com.hlh.hlhoj.service.ResourceService;
import com.hlh.hlhoj.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 12261
* @description 针对表【resource(资源表)】的数据库操作Service实现
* @createDate 2025-04-27 13:53:59
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

    @Override
    public Page<ResourceVO> getResourceVOpage(Page<Resource> questionPage, User loginUser, HttpServletRequest request) {
        List<Resource> records = questionPage.getRecords();
        Page<ResourceVO> resourceVos = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if(CollectionUtils.isEmpty(records)){
            return resourceVos;
        }
        List<ResourceVO> resourceVOS = records.stream().map(Resource -> {
            ResourceVO resourceVO = ResourceVO.objToVo(Resource);
            if (Resource.getTextpath() != null) {
                String downloadurl = TeacherQuestionServiceImpl.generateDownloadUrlResource(request, Resource.getId(), Resource.getTextpath());
                resourceVO.setTextpath(downloadurl);
            }
            return resourceVO;
        }).collect(Collectors.toList());
        resourceVos.setRecords(resourceVOS);
        return resourceVos;
    }
}




