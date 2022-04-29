package com.example.pep.iot.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pep.iot.auth.entity.AdminUser;
import com.example.pep.iot.auth.mapper.AdminMapper;
import com.example.pep.iot.auth.req.LoginReq;
import com.example.pep.iot.exception.BizException;
import com.example.pep.iot.response.ResultEnum;
import com.example.pep.iot.utils.AESUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 登录主逻辑类
 *
 * @author LiuGang
 * @since 2022-03-22 15:27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService extends ServiceImpl<AdminMapper, AdminUser> {


    @Value("${spring.application.name:}")
    private String applicationName;


    /**
     * value: 抛出指定异常
     * maxAttempts: 最大次数
     * backoff: 等待策略: delay 延时时间 multiplier 延时倍数
     */
    @Retryable(value = BizException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1))
    public String login(LoginReq loginReq) {
        int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
        log.info("retryCount:{}", retryCount);
        //userCode
        LambdaQueryWrapper<AdminUser> queryWrapper = Wrappers.lambdaQuery(AdminUser.class)
                .eq(AdminUser::getUserCode, loginReq.getUserCode());
        AdminUser adminUser = this.getOne(queryWrapper);
        if (Objects.nonNull(adminUser)) {
            //password
            String password = adminUser.getPassword();
            String encryptHex = AESUtils.encryptHex(loginReq.getPassword(), adminUser.getSalt());
            if (password.equals(encryptHex)) {
                //valid->ok build token
                Map<String, Object> bean = BeanUtil.beanToMap(adminUser.setPassword(null));
                return JWTUtil.createToken(bean, applicationName.getBytes(StandardCharsets.UTF_8));
            }
        }
        throw new BizException(ResultEnum.LOGIN_SIGN_ERROR);
    }

    @Recover
    public String recover(BizException e, LoginReq loginReq) {
        log.error("loginReq:{},e:", loginReq, e);
        return "";
    }

    public AdminUser parseJWT(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject claimsJson = jwt.getPayload().getClaimsJson();
        return JSONUtil.toBean(claimsJson, AdminUser.class);
    }
}
