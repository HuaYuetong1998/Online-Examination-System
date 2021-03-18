package cn.yuanqikai.backend.vo;

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

    }
}
