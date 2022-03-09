package com.example.pep.iot.elk.repository;

import com.example.pep.iot.elk.index.OperatorLogIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 操作类
 *
 * @author LiuGang
 * @since 2022-03-09 4:32 PM
 */
@Repository
public interface OperatorLogRepository extends ElasticsearchRepository<OperatorLogIndex, String> {

}
