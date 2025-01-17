package com.nakji.myapp.login.model;

import com.nakji.myapp.user.model.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String email;
    private String profileImg;

    public SessionUser(User user) {
        this.nickname = user.getName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImage();
    }
}
