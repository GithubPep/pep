package com.example.pep.iot.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.pep.iot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 发布快照
 *
 * @author LiuGang
 * @since 2022-03-02 3:29 PM
 */
@Data
@TableName(value = "rule_publish_snapshot")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RulePublishSnapshot extends BaseEntity {

    private String ruleId;

    private String deviceCodes;

    private String persons;

    private String flows;
}
