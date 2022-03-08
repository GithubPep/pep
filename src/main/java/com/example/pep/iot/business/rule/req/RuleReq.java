package com.example.pep.iot.business.rule.req;

import com.example.pep.iot.base.ValidGroup;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 添加rule的VO对象
 *
 * @author LiuGang
 * @since 2022-03-01 9:56 AM
 */
@Data
public class RuleReq {

    @Null(groups = ValidGroup.ValidAdd.class, message = "新增rule时,不能设置id值")
    @NotBlank(groups = ValidGroup.ValidEdit.class, message = "编辑rule时,id不能为空")
    private String id;

    @Size(groups = {ValidGroup.ValidAdd.class, ValidGroup.ValidEdit.class}, min = 1, max = 20, message = "规则名称长度应该在1~20之前")
    private String ruleName;

    @Size(groups = {ValidGroup.ValidAdd.class, ValidGroup.ValidEdit.class}, max = 50, message = "规则描述长度应该少于50")
    private String ruleDesc;

    @NotEmpty(groups = {ValidGroup.ValidAdd.class, ValidGroup.ValidEdit.class}, message = "识别方式不能为空")
    private List<String> recognizeTypes;

    private List<String> rulePersons;

    private List<String> rulePersonsGroup;

    //device信息
    @NotEmpty(groups = {ValidGroup.ValidAdd.class, ValidGroup.ValidEdit.class}, message = "规则下设备不能为空")
    private List<RuleDeviceReq> ruleDevices;

    //时间规则
    @NotEmpty(groups = {ValidGroup.ValidAdd.class, ValidGroup.ValidEdit.class}, message = "规则下时间规则不能为空")
    private List<RuleTimeReq> passTimeRules;

}
