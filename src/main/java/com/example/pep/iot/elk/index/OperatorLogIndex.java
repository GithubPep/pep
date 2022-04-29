package com.example.pep.iot.elk.index;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 操作日志文件的索引
 *
 * @author LiuGang
 * @since 2022-03-09 3:47 PM
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@Document(indexName = OperatorLogIndex.LogIndexName, shards = 1, replicas = 0)
public class OperatorLogIndex {

    public final static String LogIndexName = "operator_log_index";

    public final static String LogIndexAlias = "operator_log_index_alias";

    @Id
    private String id;

    /**
     * 操作人员姓名
     */
    @Field(name = "requestUserName", type = FieldType.Keyword)
    private String requestUserName;

    /**
     * 操作动作/行为 eg: 张三添加了一条规则,根据注解自定义
     */
    @Field(name = "requestAction", type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String requestAction;

    /**
     * 请求时间
     */
    @Field(name = "requestTime", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private String requestTime;

    /**
     * 请求参数
     */
    @Field(name = "requestParam", type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String requestParam;

    /**
     * 请求响应结果
     */
    @Field(name = "requestResult", type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String requestResult;


}
