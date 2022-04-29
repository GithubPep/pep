package com.example.pep.iot.elk.index;

import com.example.pep.iot.base.EsBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.join.JoinField;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件资源
 *
 * @author LiuGang
 * @since 2022-04-22 15:19
 */
@Getter
@Setter
@TypeAlias(CaseResource.TYPE)
@Document(indexName = CaseInfo.TYPE, replicas = 1, shards = 3)
public class CaseResource extends EsBaseEntity {

    public static final String TYPE = "case-resource";

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 文件名称
     */
    @Field(type = FieldType.Text)
    private String name;


    @Field(type = FieldType.Nested)
    private FieldInfo fieldInfo = new FieldInfo();

    @JsonIgnore
    @JoinTypeRelations(relations = {
            @JoinTypeRelation(parent = CaseInfo.TYPE, children = {CaseResource.TYPE})

    })
    private JoinField<String> relation;

    public void setUri(String uri) {
        this.fieldInfo.uri = uri;
    }

    public void setLocation(String location) {
        this.fieldInfo.location = location;
    }

    public void setSource(Source source) {
        this.fieldInfo.videoInfo.source = source;
    }

    public void setDuration(Long duration) {
        this.fieldInfo.videoInfo.duration = duration;
    }

    /**
     * 数据来源
     */
    public enum Source {
        /* 执法记录仪 */
        RECORDER,
        /* 街面 */
        STREET,
        /* 审讯 */
        TRIAL,
        /* 外部上传 */
        EXTERNAL,
        /* 剪辑 */
        CLIP
    }

    @Getter
    @Setter
    public static class FieldInfo {
        /**
         * 文件真实的时间
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime fileTime;

        /**
         * 资源描述
         */
        @Field(type = FieldType.Text)
        private String describe;

        /**
         * 警员编号
         */
        @Field(type = FieldType.Text)
        private String policeCode;

        /**
         * 文件路径，可以为本地文件，也可以是网络文件
         */
        private String uri;

        /**
         * 文件生成地点
         */
        @Field(type = FieldType.Text)
        private String location;

        @Field(type = FieldType.Nested)
        private VideoInfo videoInfo = new VideoInfo();
    }

    @Getter
    @Setter
    public static class VideoInfo {
        /**
         * 数据来源
         */
        private Source source;

        private Long duration;

        /**
         * 剪辑来源文件，当{@link VideoInfo#source}  为 {@link Source#CLIP} 时不为空
         */
        private String resourcePid;

        /**
         * 用于剪辑视频获取开始时间，单位：毫秒，剪辑视频才有该字段
         */
        private long subStartTime;

        /**
         * 用于剪辑视频获取结束时间，单位：毫秒，剪辑视频才有该字段
         */
        private long subEndTime;

        /**
         * 地点信息
         */
        @Field(type = FieldType.Nested)
        private List<PointInfo> pointInfoList;
    }

    /**
     * 案件资源的位置信息
     */
    @Getter
    @Setter
    public static class PointInfo {
        /**
         * 位置
         */
        private GeoPoint point;
        /**
         * 地点名称
         */
        private String location;
        /**
         * 对应视频位置
         */
        private long position;
    }
}
