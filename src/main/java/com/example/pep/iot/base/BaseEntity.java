package com.example.pep.iot.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * entity基类
 *
 * @author LiuGang
 * @since 2022-03-01 9:38 AM
 */
@Data
public class BaseEntity {

    //数据库主键id
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建人id
     */
    @TableField(value = "createUser")
    private String createUser;


    /**
     * 最后更新人id
     */
    @TableField(value = "updateUser")
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(value = "isDeleted")
    private Boolean isDeleted = false;

    /**
     * 项目id
     */
    @TableField(value = "projectId")
    private String projectId;
}
