package com.example.pep.iot.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * 统一打印输入参数,输出返回值
 *
 * @author LiuGang
 * @since 2022-03-31 16:55
 */
@Slf4j
@Configuration
public class TraceLogAutoConfiguration {


    @Value("${config.log.trace-enabled:true}")
    private Boolean traceEnabled;

    @Value("${config.log.max-payload-body:2000}")
    private Integer maxPayLoadBody;

    @Value("${config.log.json-format:true}")
    private Boolean jsonFormat;


    @Bean
    @ConditionalOnMissingBean({CommonsRequestLoggingFilter.class})
    CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new TraceLogAutoConfiguration.RequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        commonsRequestLoggingFilter.setBeforeMessagePrefix("request info -> ");
        commonsRequestLoggingFilter.setBeforeMessageSuffix("");
        commonsRequestLoggingFilter.setAfterMessagePrefix("request body -> ");
        commonsRequestLoggingFilter.setAfterMessageSuffix("");
        commonsRequestLoggingFilter.setMaxPayloadLength(this.maxPayLoadBody);
        return commonsRequestLoggingFilter;
    }

    @Bean(
            name = {"commonsRequestLogHandlerInterceptor"}
    )
    @ConditionalOnMissingBean({TraceLogAutoConfiguration.CommonsRequestLogHandlerInterceptor.class})
    TraceLogAutoConfiguration.CommonsRequestLogHandlerInterceptor commonsRequestLogHandlerInterceptor() {
        return new TraceLogAutoConfiguration.CommonsRequestLogHandlerInterceptor();
    }


    @ControllerAdvice(
            basePackages = {"com.example.pep.iot"}
    )
    public class LogAdvice extends RequestBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
        public LogAdvice() {
        }

        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return TraceLogAutoConfiguration.this.traceEnabled;
        }

        public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
            return TraceLogAutoConfiguration.this.traceEnabled;
        }

        public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
            String jsonResult = JSON.toJSONString(body, TraceLogAutoConfiguration.this.jsonFormat);
            TraceLogAutoConfiguration.log.info(body.toString().length() > TraceLogAutoConfiguration.this.maxPayLoadBody ? "request body -> {} ..." : "request body -> {}", StrUtil.sub(jsonResult, 0, TraceLogAutoConfiguration.this.maxPayLoadBody));
            return body;
        }

        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            String requestURI = request.getURI().getPath();
            if (!requestURI.contains("actuator") && !requestURI.contains("webjars")) {
                if (MediaType.APPLICATION_JSON.equalsTypeAndSubtype(selectedContentType)) {
                    String jsonResult = JSON.toJSONString(body, TraceLogAutoConfiguration.this.jsonFormat);
                    TraceLogAutoConfiguration.log.info(jsonResult.length() > TraceLogAutoConfiguration.this.maxPayLoadBody ? "response -> {} ..." : "response -> {}", StrUtil.sub(jsonResult, 0, TraceLogAutoConfiguration.this.maxPayLoadBody));
                }

            }
            return body;
        }
    }

    public class CommonsRequestLogHandlerInterceptor extends HandlerInterceptorAdapter {
        public CommonsRequestLogHandlerInterceptor() {
        }

        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (!TraceLogAutoConfiguration.this.traceEnabled) {
                return true;
            } else {
                String contentType = request.getContentType() == null ? "application/x-www-form-urlencoded" : request.getContentType();
                if (MediaType.APPLICATION_JSON.includes(MediaType.parseMediaType(contentType))) {
                    return true;
                } else {
                    String result = this.getMessagePayload(request);
                    if (StringUtils.isNotBlank(result)) {
                        TraceLogAutoConfiguration.log.info("request param -> {}", result);
                    }

                    return super.preHandle(request, response, handler);
                }
            }
        }

        protected String getMessagePayload(HttpServletRequest request) {
            ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper == null) {
                return "";
            } else {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length <= 0) {
                    return "";
                } else {
                    try {
                        return new String(buf, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException var5) {
                        TraceLogAutoConfiguration.log.error("error convert string", var5);
                        return "[unknown]";
                    }
                }
            }
        }
    }

    private class RequestLoggingFilter extends CommonsRequestLoggingFilter implements OrderedFilter {

        private RequestLoggingFilter() {
        }

        protected boolean shouldLog(HttpServletRequest request) {
            return TraceLogAutoConfiguration.this.traceEnabled;
        }

        protected void beforeRequest(HttpServletRequest request, String message) {
            String requestURI = request.getRequestURI();
            if (!requestURI.contains("actuator") && !requestURI.contains("webjars")) {
                TraceLogAutoConfiguration.log.info(message);
            }
        }

        public int getOrder() {
            return -106;
        }
    }


}
