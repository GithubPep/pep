package com.example.pep.iot.elk.index;

import com.example.pep.iot.base.EsBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.join.JoinField;

import java.time.LocalDateTime;

/**
 * 案件集合，es 管理
 *
 * @author LiuGang
 * @since 2022-04-22 15:00
 */
@Getter
@Setter
@TypeAlias(CaseInfo.TYPE)
@Document(indexName = CaseInfo.TYPE, replicas = 1,shards = 3)
public class CaseInfo extends EsBaseEntity {

    /**
     * 关联名称，固定不变
     */
    public static final String TYPE = "case";

    /**
     * 记录ID，也是案件编号
     */
    @Id
    private String id;

    /**
     * 案件名称
     */
    @Field(type = FieldType.Text)
    private String name;

    /**
     * 案件地点
     */
    @Field(index = false, type = FieldType.Keyword)
    private String caseLocation;

    /**
     * 案件发生时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime caseTime;

    /**
     * 关联关系，关联媒体文件使用
     */
    @JsonIgnore
    @JoinTypeRelations(relations = {
            @JoinTypeRelation(parent = CaseInfo.TYPE, children = {CaseResource.TYPE})

    })
    private JoinField<String> relation = new JoinField<>(TYPE);

}
