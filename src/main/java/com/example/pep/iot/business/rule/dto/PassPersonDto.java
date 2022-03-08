package com.example.pep.iot.business.rule.dto;

import lombok.Data;

/**
 * @author LiuGang
 * @since 2022-03-02 3:09 PM
 */
@Data
public class PassPersonDto {

    private String personId;

    private String personName;

    private Integer personSex = 2;

    private Integer personType;

    private String personCode;

    private String personImageUrl;
    
    private Long timestamp;
}
