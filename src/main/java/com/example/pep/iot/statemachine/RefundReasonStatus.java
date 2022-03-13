package com.example.pep.iot.statemachine;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author LiuGang
 * @since 2022-03-10 2:46 PM
 */
@AllArgsConstructor
public enum RefundReasonStatus implements IEnum<Integer> {

    READY_TO_APPROVE(1, "待审批"),
    APPROVED(2, "审核通过"),
    REJECT(3, "审核拒绝"),
    AUTO_REJECT(4, "超时自动拒绝");

    @EnumValue
    private final int value;
    @Getter
    private final String msg;

    public static RefundReasonStatus valueOf(int val) {
        Optional<RefundReasonStatus> optional = Arrays.stream(values())
                .filter(instance -> instance.value == val).limit(1)
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalArgumentException("未知退款理由审批状态类型: " + val);
    }

    @Override
    public Integer getValue() {
        return value;
    }
}


