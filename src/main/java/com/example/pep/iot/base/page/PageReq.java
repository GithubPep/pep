package com.example.pep.iot.base.page;

import lombok.Data;

/**
 * 分页查询req
 *
 * @author LiuGang
 * @since 2022-03-26 21:31
 */
@Data
public class PageReq {

    //当前页码
    private long currentPage;

    //当前数据页大小
    private long pageSize;

    //搜索字段
    private String searchFieldName;

    //搜索词
    private String searchWord;

    //排序字段
    private String sortFieldName;

    //排序方向 是否正序
    private boolean asc;
}
