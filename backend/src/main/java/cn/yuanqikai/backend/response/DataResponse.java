package cn.yuanqikai.backend.response;

import cn.yuanqikai.backend.enums.CodeEnum;
import cn.yuanqikai.backend.enums.StatusEnum;

/**
 * @author yuanqikai
 * @date 2021-03-22 15:30
 */
public class DataResponse <T> extends BaseResponse{
    private T data;

    public DataResponse() {
    }

    public DataResponse(CodeEnum codeEnum) {
        super(codeEnum);
    }

    public DataResponse(T data, CodeEnum codeEnum) {
        this(codeEnum);
        this.data = data;
    }

    public DataResponse(CodeEnum codeEnum, String msg) {
        super(codeEnum, msg);
    }

    public DataResponse(T data, CodeEnum codeEnum, String msg) {
        this(codeEnum, msg);
        this.data = data;
    }

    public static DataResponse fail() {
        return new DataResponse(StatusEnum.FAIL);
    }

    public static DataResponse fail(String errorMsg) {
        DataResponse response = new DataResponse(StatusEnum.FAIL);
        response.setMsg(errorMsg);
        return response;
    }

    public static DataResponse success() {
        return new DataResponse(StatusEnum.SUCCESS);
    }

    public DataResponse code(CodeEnum code) {
        super.code(code);
        return this;
    }

    public DataResponse msg(String msg) {
        super.msg(msg);
        return this;
    }

    public DataResponse data(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
