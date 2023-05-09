package com.idle.entity.auth;

import lombok.Data;

@Data
public class Account {
    Long id;
    String email;
    String username;
    String password;
    String avatar;
    String address;
}
