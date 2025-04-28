package com.hlh.hlhoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.entity.Whchat;
import com.hlh.hlhoj.model.vo.WhchatVO;
import com.hlh.hlhoj.service.UserService;
import com.hlh.hlhoj.service.WhchatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        queryWrapper.orderByAsc("createTime");
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
}
