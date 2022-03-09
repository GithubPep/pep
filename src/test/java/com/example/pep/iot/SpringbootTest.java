package com.example.pep.iot;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.pep.iot.elk.index.OperatorLogIndex;
import com.example.pep.iot.elk.repository.OperatorLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author LiuGang
 * @since 2022-03-09 4:34 PM
 */
@Slf4j
@SpringBootTest
public class SpringbootTest {

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private OperatorLogRepository operatorLogRepository;

    @Test
    public void createIndex() {
        elasticsearchOperations.createIndex(OperatorLogIndex.class);
        elasticsearchOperations.putMapping(OperatorLogIndex.class);
    }

    @Test
    public void addDocument() {
        OperatorLogIndex operatorLogIndex = new OperatorLogIndex()
                .setId(IdUtil.getSnowflakeNextIdStr())
                .setRequestAction("add")
                .setRequestTime(DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                .setRequestParam("{\"id\":\"13513965533\"}");
        operatorLogRepository.save(operatorLogIndex);
    }

    @Test
    public void testDateQuery() {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("requestTime")
                .lt(DateUtil.format(new Date(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        Iterable<OperatorLogIndex> search = operatorLogRepository.search(rangeQueryBuilder);
        for (OperatorLogIndex operatorLogIndex : search) {
            System.out.println(operatorLogIndex);
        }
    }
}
