package com.example.pep.iot.base.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pep.iot.utils.StreamUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页查询返回对象
 *
 * @author LiuGang
 * @since 2022-03-26 21:35
 */
@Data
@Accessors(chain = true)
public class PageResponse<S, T> {

    //当前页码
    private long currentPage;

    //当前数据页大小
    private long pageSize;

    //总数
    private long count;

    //列表
    private List<T> pageList;

    public PageResponse(Page<S> page, Function<S, T> transfer) {
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.count = page.getTotal();
        this.pageList = StreamUtils.stream(page.getRecords())
                .map(transfer)
                .collect(Collectors.toList());
    }


}
