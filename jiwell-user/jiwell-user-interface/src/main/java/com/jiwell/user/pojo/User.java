package com.jiwell.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:42
 * @Feature: 用戶實體類
 */
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用戶名
     */
    //@Length(min = 4,max = 15,message = "用戶名只能在4~15位之間")
    private String account;

    /**
     * 密碼
     */
    //@JsonIgnore
    //@Length(min = 6,max = 25,message = "密碼只能在6~25位之間")
    private String password;

    /**
     * 電話
     */
    //@Pattern(regexp = "^1[35678]\\d{9}$", message = "手機號格式不正確")
    private String phone;
    //female
    /**
     * 性別 預設為 male
     */
    private String gender;

    private String nickname ;
    /**
     * 創建時間
     */
    private Date created;

    public Long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +'\'' +
                ", nickname=" + nickname +'\'' +
                ", created=" + created +
                '}';
    }
}
