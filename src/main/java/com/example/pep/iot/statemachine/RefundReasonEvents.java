package com.example.pep.iot.statemachine;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author LiuGang
 * @since 2022-03-10 2:47 PM
 */
@AllArgsConstructor
public enum RefundReasonEvents implements IEnum<Integer> {

    APPROVE(1, "审核通过"),
    REJECT(2, "审批拒绝"),
    TIME_OUT(3, "审批超时");

    @EnumValue
    private final int value;

    @Getter
    private final String msg;

    public static RefundReasonEvents valueOf(int val) {
        Optional<RefundReasonEvents> optional = Arrays.stream(values())
                .filter(instance -> instance.value == val).limit(1)
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalArgumentException("未知退款理由审批事件类型: " + val);
    }

    @Override
    public Integer getValue() {
        return value;
    }
}

