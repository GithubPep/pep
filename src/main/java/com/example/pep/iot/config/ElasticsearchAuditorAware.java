package com.example.pep.iot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * es 审计功能配置
 *
 * @author LiuGang
 * @since 2022-04-20 18:47
 */
@Configuration
public class ElasticsearchAuditorAware {

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of("pep");
    }

}
