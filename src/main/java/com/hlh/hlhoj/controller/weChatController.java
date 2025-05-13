package com.hlh.hlhoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.dto.question.WhchatRequest;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.entity.Whchat;
import com.hlh.hlhoj.model.vo.WhchatVO;
import com.hlh.hlhoj.service.UserService;
import com.hlh.hlhoj.service.WhchatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wechat")
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
@Slf4j
public class weChatController {
    @Resource
    private WhchatService whchatService;
    @Resource
    private UserService userService;
    @GetMapping("/all")
    public BaseResponse<List<WhchatVO>> allWechat(HttpServletRequest request){
        QueryWrapper<Whchat> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("crratetime");
        List<Whchat> whchats = whchatService.list(queryWrapper);
        List<WhchatVO> whchatVOS=new ArrayList<>();
        for(Whchat whchat:whchats){
            WhchatVO whchatVO=new WhchatVO();
            Long userid = whchat.getUserid();
            User user = userService.getById(userid);
            String userName = user.getUserName();
            BeanUtils.copyProperties(whchat,whchatVO);
            whchatVO.setUserName(userName);
            whchatVOS.add(whchatVO);
        }
        return ResultUtils.success(whchatVOS);
    }

    /**
     * 添加消息
     * @param whchatRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addWechat(@RequestBody WhchatRequest whchatRequest, HttpServletRequest request){
        if(whchatRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Whchat whchat=new Whchat();
        BeanUtils.copyProperties(whchatRequest,whchat);
        whchat.setUserid(loginUser.getId());
        whchat.setCrratetime(new Date());
        boolean save = whchatService.save(whchat);
        if(!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(save);
    }
}
