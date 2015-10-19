package com.gys.kitten.core.vo;

import java.io.Serializable;

/**
 * Author: kitten
 * Date: 11-3-15
 * Time: 下午2:55
 * Des: response 操作相应容器
 */
public class ResponseObject implements Serializable {

    protected String message;
    protected Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}