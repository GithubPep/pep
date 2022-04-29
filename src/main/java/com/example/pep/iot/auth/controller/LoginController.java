package com.example.pep.iot.auth.controller;

import com.example.pep.iot.auth.entity.AdminUser;
import com.example.pep.iot.auth.req.LoginReq;
import com.example.pep.iot.auth.service.LoginService;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 登录管理
 *
 * @author LiuGang
 * @since 2022-03-22 15:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public ResultVO<String> login(@RequestBody LoginReq loginReq, HttpServletResponse response) {
        String token = loginService.login(loginReq);
        //String domain,String path, int maxAge,boolean httpOnly
        Cookie cookie = new Cookie("token", token);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        cookie.setHttpOnly(false);
        redisTemplate.opsForValue().set("", token);
        Objects.requireNonNull(response).addCookie(cookie);
        return ResultVO.ok();
    }

    @GetMapping("/logout")
    public ResultVO<Void> logout() {

        return ResultVO.ok();
    }

    @GetMapping("/getInfo")
    public ResultVO<AdminUser> userInfo(@RequestParam("token") String token) {
        AdminUser adminUser = loginService.parseJWT(token);
        return ResultVO.ok(adminUser);
    }
}
