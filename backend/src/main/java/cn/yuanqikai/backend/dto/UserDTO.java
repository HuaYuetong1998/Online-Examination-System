package cn.yuanqikai.backend.dto;

/**
 * @author yuanqikai
 * @date 2021-03-18 11:45
 */
public class UserDTO {

    private Integer userId;

    private String username;

    private String password;

    private String realName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
