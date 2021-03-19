package com.jiwell.fcm.pojo;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:42
 * @Feature: 用戶實體類
 */
@Table(name = "tb_jwfirebase")
public class JwFirebase implements Serializable {

    @Id
    private Long userId;

    /**
     * firebase token
     */
    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long useId) {
        this.userId = useId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwFirebase{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
