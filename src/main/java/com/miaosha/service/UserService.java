package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.UserModel;

public interface UserService {

    //通过用户id获取用户对象的方法
    UserModel getUserById(Integer id);

    //用户注册
    void register(UserModel userModel) throws BusinessException;

    /*
    用户登录服务
    telphone:用户注册手机
    encrptPassword:用户加密后密码
     */
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
