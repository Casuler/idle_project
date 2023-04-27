package com.idle.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {

    String sendValidateEmail(String email, String sessionId, boolean hasAccount);
    String validateAndRegister(String username, String password, String email, String code, String sessionId);
    boolean upgradeInfo(String nickname, String password, String email);
    boolean upgradeAvatar(String avatar, Integer id);
    boolean upgradeAddress(String address, Integer id);
    String validateOnly(String email, String code, String sessionId);
    boolean resetPassword(String password, String email);

}
