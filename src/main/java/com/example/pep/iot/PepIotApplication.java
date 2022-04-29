package com.example.pep.iot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.retry.annotation.EnableRetry;

@EnableElasticsearchAuditing
@EnableElasticsearchRepositories(basePackages = "com.example.pep.iot.elk.repository")
@SpringBootApplication
@MapperScan(basePackages = {
        "com.example.pep.iot.business.**",
        "com.example.pep.iot.emq.mapper",
        "com.example.pep.iot.auth.mapper"
})
@EnableRetry
public class PepIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PepIotApplication.class, args);
    }

}
