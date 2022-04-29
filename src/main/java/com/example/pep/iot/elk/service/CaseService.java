package com.example.pep.iot.elk.service;

import com.example.pep.iot.elk.index.CaseInfo;
import com.example.pep.iot.elk.index.CaseResource;
import com.example.pep.iot.elk.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 案件主逻辑
 *
 * @author LiuGang
 * @since 2022-04-22 15:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository caseRepository;

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    public String saveCase() {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setName("name");
        caseInfo.setCaseTime(LocalDateTime.now());
        caseInfo.setCaseLocation("caseLocation");
        caseRepository.save(caseInfo);
        String caseId = caseInfo.getId();
        for (int i = 0; i < 2; i++) {
            CaseResource caseResource = new CaseResource();
            String resource = caseId + "Resource" + i;
            caseResource.setUri(resource);
            caseResource.setName(resource);
            caseResource.setSource(CaseResource.Source.STREET);
            caseResource.setLocation(resource);
            caseResource.setRelation(new JoinField<>(CaseResource.TYPE, caseInfo.getId()));
            elasticsearchTemplate.save(caseResource);
        }
        return caseInfo.getId();
    }
}
