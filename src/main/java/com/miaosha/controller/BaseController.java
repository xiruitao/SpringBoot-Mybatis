package com.miaosha.controller;

import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义exceptionhandler解决未被controller层吸收的exception（为业务逻辑处理上的问题或业务逻辑错误而并非服务端不能处理的错误）
    @ExceptionHandler(Exception.class)//需要指明收到什么样的exception之后才会进入它的处理环节，此处定义为根类
    @ResponseStatus(HttpStatus.OK)//捕获到controller抛出的exception，并返回HttpStatus.OK,即status=200
    @ResponseBody //handler exception使用这种方式（Object会寻找本地页面文件）仅仅只能返回页面路径，无法处理viewobject类对应的@ResponseBody形式，加上@ResponseBody注解即可解决
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else {
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
