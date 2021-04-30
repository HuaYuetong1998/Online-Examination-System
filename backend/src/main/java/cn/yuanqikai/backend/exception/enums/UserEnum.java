package cn.yuanqikai.backend.exception.enums;

import cn.yuanqikai.backend.enums.CodeEnum;

/**
 * @author yuanqikai
 * @date 2021-03-19 14:17
 */
public enum UserEnum implements CodeEnum<UserEnum> {

    USER_NOT_EXIST("100000","用户不存在"),
    USER_NAME_BLANK("100001","用户名不能为空"),
    PASSWORD_BLANK("100002","用户密码不能为空"),
    REAL_NAME_BLANK("100003","用户真实姓名不能为空"),
    TEL_BLANK("100004","用户手机号不能为空"),
    EMAIL_BLANK("100005","用户电子邮箱不能为空"),
    GENDER_BLANK("100006","用户性别不能为空"),
    ROLE_BLANK("100007","用户权限不能为空"),
    USER_HAS_EXISTED("100008","用户已存在"),

    ;
    private String code, msg;

    UserEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public UserEnum msg(String msg) {
        this.msg = msg;
        return this;
    }
}
