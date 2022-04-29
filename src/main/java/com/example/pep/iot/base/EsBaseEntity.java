package com.example.pep.iot.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * es基类
 *
 * @author LiuGang
 * @since 2022-04-20 18:46
 */
@Getter
@Setter
public abstract class EsBaseEntity implements Persistable<String> {
    /**
     * 创建人ID
     */
    @CreatedBy
    @Field(type = FieldType.Text)
    private String createUserId;

    /**
     * 创建时间
     */
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @LastModifiedBy
    @Field(type = FieldType.Text)
    private String modifyUserId;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return createTime == null;
    }
}
