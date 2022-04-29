package com.example.pep.iot.elk.repository;

import com.example.pep.iot.elk.index.CaseInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LiuGang
 * @since 2022-04-22 15:27
 */
@Repository
public interface CaseRepository extends ElasticsearchRepository<CaseInfo, String> {

}
