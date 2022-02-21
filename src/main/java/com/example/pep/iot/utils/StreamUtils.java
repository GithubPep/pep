package com.example.pep.iot.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author LiuGang
 * @since 2022-02-17 11:07 AM
 */
public class StreamUtils {

    /**
     * 使用stream时,直接使用,减少判断空
     *
     * @param collection 集合
     * @param <T>        类型
     * @return 返回流
     */
    public static <T> Stream<T> stream(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            collection = new ArrayList<>();
        }
        return collection.stream();
    }
}
