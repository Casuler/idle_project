package com.idle.entity.user;

import lombok.Data;

@Data
public class AccountUser {
    Long id;
    String username;
    String nickname;
    String email;
    String avatar;
    String address;
}
