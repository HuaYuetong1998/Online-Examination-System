package cn.yuanqikai.backend.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author yuanqikai
 * @date 2021-03-18 11:50
 */
public class UserLoginDTO {

    private UserDTO user;

    private String token;

    public UserLoginDTO() {

    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
