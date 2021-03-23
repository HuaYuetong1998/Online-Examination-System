package cn.yuanqikai.backend.vo;

import cn.yuanqikai.backend.exception.UserException;
import cn.yuanqikai.backend.exception.enums.UserEnum;
import cn.yuanqikai.backend.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * @author yuanqikai
 * @date 2021-03-19 14:00
 */
public class RegisterVO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机电话
     */
    private String tel;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户角色，0-管理员，1-教师，2-学生
     */
    private Integer role;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void valid() {
        if (StringUtils.isBlank(userName)) {
            throw new UserException(UserEnum.USER_NAME_BLANK);
        }

        if(StringUtils.isBlank(password)) {
            throw new UserException(UserEnum.PASSWORD_BLANK);
        }

        if(StringUtils.isBlank(realName)) {
            throw new UserException(UserEnum.REAL_NAME_BLANK);
        }

        if(StringUtils.isBlank(tel)) {
            throw new UserException(UserEnum.TEL_BLANK);
        }

        if(StringUtils.isBlank(email)) {
            throw new UserException(UserEnum.EMAIL_BLANK);
        }

        if (StringUtils.isBlank(gender)) {
            throw new UserException(UserEnum.GENDER_BLANK);
        }

        if(Objects.equals(role,null)) {
            throw new UserException(UserEnum.ROLE_BLANK);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
