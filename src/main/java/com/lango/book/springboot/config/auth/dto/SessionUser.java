package com.lango.book.springboot.config.auth.dto;

import com.lango.book.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User uuer) {
        this.name = getName();
        this.email = getEmail();
        this.picture = getPicture();
    }
}
