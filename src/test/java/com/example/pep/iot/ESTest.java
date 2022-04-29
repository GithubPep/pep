package com.example.pep.iot;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.pep.iot.elk.index.CaseInfo;
import com.example.pep.iot.elk.index.CaseResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

import java.text.ParseException;
import java.util.Map;

/**
 * @author LiuGang
 * @since 2022-04-19 18:10
 */
@Slf4j
@SpringBootTest(classes = PepIotApplication.class,value = "dev")
public class ESTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

//    @BeforeEach
    void before() {

        // 创建索引，会根据Item类的@Document注解信息来创建
        IndexOperations newCaseIndexOps = elasticsearchTemplate.indexOps(CaseInfo.class);
        IndexOperations caseResourceIndexOps = elasticsearchTemplate.indexOps(CaseResource.class);
        newCaseIndexOps.delete();
        newCaseIndexOps.create();


        caseResourceIndexOps.delete();
        caseResourceIndexOps.create();
        log.info("case {}", newCaseIndexOps.putMapping(CaseInfo.class));
        log.info("case resource {}", caseResourceIndexOps.putMapping(CaseResource.class));

        Map<String, Object> mapping = newCaseIndexOps.getMapping();


        newCaseIndexOps.refresh();
        caseResourceIndexOps.refresh();
    }

    @Test
    public void dateTest() throws ParseException {


    }
}
