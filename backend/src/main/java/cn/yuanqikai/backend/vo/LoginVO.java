package cn.yuanqikai.backend.vo;

import cn.yuanqikai.backend.exception.UserException;
import cn.yuanqikai.backend.exception.enums.UserEnum;
import cn.yuanqikai.backend.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author yuanqikai
 * @date 2021-03-18 11:55
 */
public class LoginVO {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void valid() {
        if (StringUtils.isBlank(userName)) {
            throw new UserException(UserEnum.USER_NAME_BLANK);
        }

        if(StringUtils.isBlank(password)) {
            throw new UserException(UserEnum.PASSWORD_BLANK);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
