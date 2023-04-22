package com.idle.service.impl;

import com.idle.entity.auth.Account;
import com.idle.mapper.UserMapper;
import com.idle.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;
    @Resource
    UserMapper mapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空！");
        Account account = mapper.findAccountByNameOrEmail(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误！");
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }


    /**
     * 1.先生成对应的验证码
     * 2.把邮箱和对应的验证码放在Redis中（过期时间3分钟。如果此时重新要求发送邮件，
     * 只要剩余时间低于2分钟，即可重新发送一次邮件，重复此流程）
     * 3.发送验证码到指定邮箱
     * 4.如果发送失败，把Redis中刚插入的键值对删除
     * 5.用户在注册时，从Redis中取出对应键值对，对比验证码是否一致
     */
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;
        if(Boolean.TRUE.equals(template.hasKey(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if(expire > 120) return "请求频繁，请稍后再试！";
        }
        Account account = mapper.findAccountByNameOrEmail(email);
        if( hasAccount &&  account == null) return "该邮箱未注册账户";
        if (!hasAccount && account != null) return "该邮箱已注册";
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Idle验证邮件");
        message.setText("验证码是：" + code);
        try{
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败，请检查邮箱地址是否有效！";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":false";
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s == null) return "验证码已失效，请重新接收验证码！";
            if(s.equals(code)) {
                Account account = mapper.findAccountByNameOrEmail(username);
                if(account != null) return "该用户名已被注册";
                password = encoder.encode(password);
                template.delete(key);
                if (mapper.createAccount(username, password, email) > 0) {
                    return null;
                } else {
                    return "发生错误，请联系管理员";
                }
            } else {
                return "验证码错误，请检查后再提交！";
            }
        } else {
            return "请先获取验证码邮件！";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":true";
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s == null) return "验证码已失效，请重新接收验证码！";
            if(s.equals(code)) {
                template.delete(key);
                return null;
            } else {
                return "验证码错误，请检查后再提交！";
            }
        } else {
            return "请先获取验证码邮件！";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
        password = encoder.encode(password);
        return mapper.resetPasswordByEmail(password, email) > 0;
    }
}
