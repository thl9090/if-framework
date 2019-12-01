package com.yx.user.model;

import java.io.Serializable;

/**
 * @author YanBingHao
 * @since 2019/1/17
 */
public class RangeUserModel implements Serializable {

    private static final long serialVersionUID = 4820608558755610282L;
    
    private Long userId;
    private String phone;
    private String userName;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
