package com.example.pep.iot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.example.pep.iot.elk.repository")
@SpringBootApplication
@MapperScan(basePackages = {"com.example.pep.iot.business.**", "com.example.pep.iot.emq.mapper"})
public class PepIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PepIotApplication.class, args);
    }

}
