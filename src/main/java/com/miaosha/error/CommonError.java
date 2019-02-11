package com.miaosha.error;

public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError serErrMsg(String errMsg);
}
