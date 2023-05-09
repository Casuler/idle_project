package com.idle.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {

    String sendValidateEmail(String email, String sessionId, boolean hasAccount);
    String validateAndRegister(String username, String password, String email, String code, String sessionId);
    boolean upgradeInfo(String nickname, String email, String address, String username);
    boolean upgradeAvatar(String avatar, Long id);
    boolean upgradeAddress(String address, Long id);
    String validateOnly(String email, String code, String sessionId);
    boolean resetPassword(String password, String email);

}
