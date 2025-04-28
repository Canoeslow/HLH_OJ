package com.hlh.hlhoj.controller;

import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.dto.question.CreateFeedBackRequest;
import com.hlh.hlhoj.model.dto.question.deleteFeedbackRequest;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.entity.Userfeedback;
import com.hlh.hlhoj.service.UserService;
import com.hlh.hlhoj.service.UserfeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/userfeedback")
@Slf4j
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
public class UserfeedbackController {

    @Resource
    private UserfeedbackService userfeedbackService;
    @Resource
    private UserService userService;
    private UserController userController;

    /**
     * 创建问题
     * @param createFeedBackRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addFeedback(@RequestBody CreateFeedBackRequest createFeedBackRequest, HttpServletRequest request){
        if(createFeedBackRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!= UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Userfeedback userfeedback = new Userfeedback();
        userfeedback.setQuestiontext(createFeedBackRequest.getQuestiontext());
        userfeedback.setFeedbacktext(createFeedBackRequest.getFeedbacktext());
        userfeedback.setCrratetime(new Date());
        boolean result = userfeedbackService.save(userfeedback);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFeedback(@RequestBody deleteFeedbackRequest deleteFeedbackRequest, HttpServletRequest request){
        if(deleteFeedbackRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!= UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Userfeedback userfeedbackServiceById = userfeedbackService.getById(deleteFeedbackRequest.getId());
        if(userfeedbackServiceById==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = userfeedbackService.removeById(deleteFeedbackRequest.getId());
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateFeedback(@RequestBody CreateFeedBackRequest createFeedBackRequest, HttpServletRequest request){
        if(createFeedBackRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!= UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Userfeedback userfeedback = new Userfeedback();
        userfeedback.setId(createFeedBackRequest.getId());
        userfeedback.setQuestiontext(createFeedBackRequest.getQuestiontext());
        userfeedback.setFeedbacktext(createFeedBackRequest.getFeedbacktext());
        boolean result = userfeedbackService.updateById(userfeedback);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(result);
    }

    @GetMapping("/list")
    public BaseResponse<List<Userfeedback>> getAllFeedback(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!= UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        List<Userfeedback> list = userfeedbackService.list();
        return ResultUtils.success(list);
    }
}
