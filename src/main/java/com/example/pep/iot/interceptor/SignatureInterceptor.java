package com.example.pep.iot.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.pep.iot.Consistent;
import com.example.pep.iot.exception.BizException;
import com.example.pep.iot.response.ResultEnum;
import com.example.pep.iot.utils.CertificateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 网关过滤器
 *
 * @author LiuGang
 * @since 2022-03-04 2:22 PM
 */
@Slf4j
public class SignatureInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //todo check login ? by token ...

        String accessKey = "";
        String secretKey = "";
        String requestBody = readParams(request);
        HashMap<String, String> params = JSON.parseObject(Objects.requireNonNull(requestBody), new TypeReference<HashMap<String, String>>() {
        });
        String sign = request.getHeader(Consistent.Headers.SIGNATURE);
        String timestamp = request.getHeader(Consistent.Headers.TIMESTAMP);
        String brand = request.getHeader(Consistent.Headers.BRAND);
        if (StrUtil.hasBlank(sign, brand, timestamp)) {
            throw new BizException(ResultEnum.SIGNATURE_ERROR, "签名参数缺少");
        }
        //check timestamp
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 60 * 30 * 1000) {
            throw new BizException(ResultEnum.SIGNATURE_ERROR, "请求时间戳已过期");
        }
        //check sign
        if (!sign.equalsIgnoreCase(CertificateUtils.createSign(params, accessKey, secretKey, timestamp))) {
            throw new BizException(ResultEnum.SIGNATURE_ERROR);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //从request中拿到body请求对象
    private String readParams(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        String method = request.getMethod();
        if (method.equals("GET")) {
            Map<String, String> parameterMap = new HashMap<>();
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String value = request.getParameter(name);
                parameterMap.put(name, value);
            }
            return JSONObject.toJSONString(parameterMap);
        } else if (method.equals("POST")) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, request.getCharacterEncoding()))) {
                String line;
                boolean firstLine = true;
                StringBuilder builder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    if (!firstLine) {
                        builder.append(System.getProperty("line.separator"));
                    } else {
                        firstLine = false;
                    }
                    builder.append(line);
                }
                return builder.toString();
            }
        }
        return "";
    }
}
