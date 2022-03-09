package com.example.pep.iot;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author LiuGang
 * @since 2022-02-25 2:40 PM
 */
@Slf4j
public class JSONTest {


    @Test
    public void eval(){
        JSONObject data = JSONObject.parseObject("{\"other\":{\"music\":\"海王\",\"movie\":\"神奇女侠\"},\"address\":\"中国最美丽的地方\",\"gender\":1,\"mobile\":\"9090980\",\"name\":\"张三\",\"age\":[23,24]}");
        System.out.println(JSONPath.paths(data));
        Object name = JSONPath.eval(data, "$.name");
        System.out.println("张三".equals(name));
        List<String> movie = (List<String>) JSONPath.eval(data, "$.age");
        System.out.println(movie);
        String music = JSONPath.eval(data, "$.other.music").toString();
        System.out.println(music);
    }
}
