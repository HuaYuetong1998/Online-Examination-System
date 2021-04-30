package cn.yuanqikai.backend.exception;

import cn.yuanqikai.backend.enums.CodeEnum;

/**
 * @author yuanqikai
 * @date 2021-03-19 14:16
 */
public class UserException extends ApplicationException{

    public UserException(CodeEnum codeEnum) {
        super(codeEnum);
    }

    public UserException(CodeEnum codeEnum, Throwable cause) {
        super(codeEnum, cause);
    }
}
