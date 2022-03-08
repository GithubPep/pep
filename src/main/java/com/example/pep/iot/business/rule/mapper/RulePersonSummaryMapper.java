package com.example.pep.iot.business.rule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pep.iot.business.rule.entity.PassRule;
import com.example.pep.iot.business.rule.entity.RulePersonSummary;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiuGang
 * @since 2022-03-01 5:55 PM
 */
@Mapper
public interface RulePersonSummaryMapper extends BaseMapper<RulePersonSummary> {
}
