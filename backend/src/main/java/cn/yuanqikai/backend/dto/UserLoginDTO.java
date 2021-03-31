package cn.yuanqikai.backend.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author yuanqikai
 * @date 2021-03-22 17:16
 */
public class UserLoginDTO {

    private Integer userId;

    private String userName;

    private String password;

    private String realName;

    private Integer role;

    private String token;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
