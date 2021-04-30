package cn.yuanqikai.backend.response;

import cn.yuanqikai.backend.enums.CodeEnum;
import cn.yuanqikai.backend.enums.StatusEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanqikai
 * @date 2021-03-19 15:54
 */
public class BaseResponse implements Serializable {
    public String code;
    private String msg;

    public BaseResponse() {
    }

    public BaseResponse(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public BaseResponse(CodeEnum codeEnum, String msg) {
        this.code = codeEnum.getCode();
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean ok() {
        return StatusEnum.success(this.code);
    }

    public static BaseResponse success() {
        return new BaseResponse(StatusEnum.SUCCESS);
    }

    public static BaseResponse fail() {
        return new BaseResponse(StatusEnum.FAIL);
    }

    public BaseResponse code(CodeEnum code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        return this;
    }

    public BaseResponse msg(String msg) {
        this.msg = msg;
        return this;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
