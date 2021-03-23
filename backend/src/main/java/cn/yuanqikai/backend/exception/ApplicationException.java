package cn.yuanqikai.backend.exception;

import cn.yuanqikai.backend.enums.CodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * @author yuanqikai
 * @date 2021-03-19 14:08
 */
public class ApplicationException extends ContextedRuntimeException {
    private CodeEnum codeEnum;

    public ApplicationException(CodeEnum codeEnum) {
        super(codeEnum.getDetail());
        this.codeEnum = codeEnum;
    }

    public ApplicationException(CodeEnum codeEnum, Throwable cause) {
        super(codeEnum.getDetail(), cause);
        this.codeEnum = codeEnum;
    }

    public CodeEnum getCodeEnum() {
        return this.codeEnum;
    }

    public String getCode() {
        return this.codeEnum != null && StringUtils.isNotBlank(this.codeEnum.getCode()) ? this.codeEnum.getCode() : "";
    }

    public String getMsg() {
        return this.codeEnum != null && StringUtils.isNotBlank(this.codeEnum.getMsg()) ? this.codeEnum.getMsg() : "";
    }
}
