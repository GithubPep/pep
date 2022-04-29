package com.example.pep.iot.elk.index;

import com.example.pep.iot.base.EsBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 文章
 *
 * @author LiuGang
 * @since 2022-04-19 14:55
 */
@Getter
@Setter
@Document(indexName = Article.TYPE, shards = 3, replicas = 0)
public class Article extends EsBaseEntity {

    /**
     * 关联名称，固定不变
     */
    public static final String TYPE = "article";

    /**
     * 文章id
     */
    @Id
    private String id;

    /**
     * 文章标题
     */
//    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 文章内容
     */
//    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    @Field(type = FieldType.Text)
    private String content;

    /**
     * 发表人id
     */
    @Field(type = FieldType.Keyword)
    private String userId;

    /**
     * 发表时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss.SSSz")
    private LocalDateTime pubTime;

    /**
     * 是否公开
     */
    @Field(type = FieldType.Boolean)
    private Boolean hidden;


}
