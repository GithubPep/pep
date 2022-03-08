package com.example.pep.iot.business.rule.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pep.iot.business.rule.entity.RuleTimeSummary;
import com.example.pep.iot.business.rule.mapper.RuleTimeSummaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 规则与人员关联 主逻辑
 *
 * @author LiuGang
 * @since 2022-03-01 10:14 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleTimeService extends ServiceImpl<RuleTimeSummaryMapper, RuleTimeSummary> {


}
