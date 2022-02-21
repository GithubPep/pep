package com.example.pep.iot;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.id.NanoId;
import com.example.pep.iot.utils.StreamUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
class PepIotApplicationTests {

    @Test
    void contextLoads() {
        String s = NanoId.randomNanoId(20);
        log.info("s:{}", s);
    }

    @Test
    public void testExpireMap() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .maxSize(100)
                .expiration(1, TimeUnit.SECONDS)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .variableExpiration()
                .build();
        map.put("test", "test123");
        Thread.sleep(500);
        log.info("get:1:{}", map.get("test"));
        map.put("test1", "test1234");
        Thread.sleep(600);
        log.info("get:2:{}", map.get("test"));
        log.info("get:1:{}", map.get("test1"));
    }

    @Test
    public void testStream() {
        List<User> list = new ArrayList<>();
        list.add(new User().setAge(1).setName("1"));
        list.add(new User().setAge(2).setName("2"));
        list.add(new User().setAge(3).setName("3"));
        list.add(new User().setAge(3).setName("3"));
        list.add(new User().setAge(1).setName("2"));
        //只根据age分组
//        Map<Object, List<User>> ageMap = StreamUtils.stream(list).collect(Collectors.groupingBy(user -> new User().setAge(user.getAge())));
//        System.out.println(ageMap);
        //根据user的全部属性分组
        Map<Object, List<User>> map = StreamUtils.stream(list).collect(Collectors.groupingBy(user -> user));
        System.out.println(map);
    }

    @Test
    public void testImgType() {
        File file = FileUtil.file("/Users/pep/Desktop/screen.png");
        String type = FileTypeUtil.getType(file);
        log.info("type:{}", type);
    }


    @Data
    @Accessors(chain = true)
    static class User {

        private String name;

        private Integer age;

        private Address address;
    }

    @Data
    @Accessors(chain = true)
    static class Address {

        private String city;

        private String country;
    }

}
