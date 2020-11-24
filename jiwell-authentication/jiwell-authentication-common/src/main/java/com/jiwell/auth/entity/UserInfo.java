package com.jiwell.auth.entity;

/**
 * 用户信息
 */
public class UserInfo {

    private Long id;

    private String account;

    private String phone;

    private String gender;

    private String nickname;

    public UserInfo() {
    }

    public UserInfo(Long id, String account, String phone, String gender, String nickname) {
        this.id = id;
        this.account = account;
        this.phone = phone;
        this.gender = gender;
        this.nickname = nickname;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
