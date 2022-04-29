package com.example.pep.iot;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.pep.iot.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LiuGang
 * @since 2022-03-17 6:04 PM
 */
@Slf4j
public class TestClass {


    @Test
    public void listTest() {

        DateTime now = DateTime.now();
        LocalDateTime localDateTime = LocalDateTimeUtil.ofUTC(Instant.EPOCH);
        log.info("now:{}", localDateTime);
        log.info("now:{}", now);

    }

    @Test
    public void md5Test() {
        String content = "123456";
        String salt = "1234567812345678";

        String s = AESUtils.encryptHex(content, salt);
        System.out.println(s);
        System.out.println(AESUtils.decryptStr(s, salt));
    }

    @Test
    public void jwtTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zs");
        map.put("age", "25");
        String token = JWTUtil.createToken(map, "pep".getBytes(StandardCharsets.UTF_8));
        System.out.println(token);
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject claimsJson = jwt.getPayload().getClaimsJson();
        System.out.println(claimsJson);
    }

    @Test
    public void test() {
        com.alibaba.fastjson.JSONObject parseObject = new com.alibaba.fastjson.JSONObject();
        String s = new com.alibaba.fastjson.JSONObject().fluentPut("value", "绑定").toJSONString();
        parseObject.fluentPut("carOperate", s);
        String carOperate = JSONPath.eval(parseObject.getString("carOperate"), "$.value").toString();
        log.info("carOperate:{}", carOperate);
    }


    @Test
    public void testTool() {

        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> other = new ArrayList<>();
        first.add("1");
        first.add("2");
        other.add("2");
        other.add("1");
        boolean b = first.containsAll(other) && other.containsAll(first);
        System.out.printf(String.valueOf(b));


        HashMap<String, String> firstMap = new HashMap<>();
        HashMap<String, String> secondMap = new HashMap<>();
        firstMap.put("1", "1");
        firstMap.put("2", "2");
        firstMap.put("4", "4");
        secondMap.put("2", "2");
        log.info("firstMap:{}", firstMap);
        log.info("secondMap:{}", secondMap);

    }


    @Test
    public void testShape() {
        String shape = "[{\"x\":192.9314793577982,\"y\":6.023150802752294,\"z\":0},{\"x\":-150.37808199541286,\"y\":6.023150802752294,\"z\":0}]";
        String ss = "[[{\"x\":-21.260357420006667,\"y\":10.824713277415725,\"z\":0},{\"x\":-21.27,\"y\":0.79,\"z\":0},{\"x\":-31.311065182072937,\"y\":0.7704690355282713,\"z\":0}],[{\"x\":-31.289988145651325,\"y\":-13.225397624262591,\"z\":0},{\"x\":-21.29,\"y\":-13.21,\"z\":0},{\"x\":-21.304285699708476,\"y\":-23.209989795933986,\"z\":0}],[{\"x\":-7.296146821898832,\"y\":-23.22471695260611,\"z\":0},{\"x\":-7.29,\"y\":-13.19,\"z\":0},{\"x\":2.7510769044936865,\"y\":-13.178251051580306,\"z\":0}],[{\"x\":2.7299883144512265,\"y\":0.8252876040941348,\"z\":0},{\"x\":-7.27,\"y\":0.81,\"z\":0},{\"x\":-7.2557143002915225,\"y\":10.809989795933985,\"z\":0}]]";
        String s = buildShape(shape);
        System.out.println(
                s
        );

        StringBuilder shapeBuilder = new StringBuilder();
        JSONArray jsonArray = JSONArray.parseArray(ss);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray array = jsonArray.getJSONArray(i);
                String shapeItem = buildShape(com.alibaba.fastjson.JSONObject.toJSONString(array));
                shapeBuilder.append(shapeItem).append(" ");
            }
        }
        System.out.println(shapeBuilder.substring(0, shapeBuilder.length() - 1));

    }

    /**
     * 根据shape JSON字符串转成 x,y,z x,y,z 格式
     */
    public String buildShape(String shape) {
        if (Objects.nonNull(shape) && !"null".equals(shape)) {
            StringBuilder shapeBuilder = new StringBuilder();
            JSONArray jsonArray = JSONArray.parseArray(shape);
            for (Object obj : jsonArray) {
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(obj.toString());
                shapeBuilder
                        .append(new BigDecimal(jsonObject.get("x").toString()).toPlainString())
                        .append(",")
                        .append(new BigDecimal(jsonObject.get("y").toString()).toPlainString())
                        .append(",")
                        .append(new BigDecimal(jsonObject.get("z").toString()).toPlainString()).append(" ");
            }
            return shapeBuilder.substring(0, shapeBuilder.length() - 1);
        }
        return "";
    }

    @Test
    public void sort() {
        Action a = new Action();
        a.type = "delete";

        Action b = new Action();
        b.type = "delete";

        Action c = new Action();
        c.type = "update";

        Action d = new Action();
        d.type = "update";
        List<Action> list = new ArrayList<>();
        list.add(c);
        list.add(a);
        list.add(b);
        list.add(d);

        System.out.println(list);

        list.sort(new Comparator<Action>() {
            @Override
            public int compare(Action o1, Action o2) {
                return o1.type.equals("delete") ? 1 : -1;
            }
        });

        System.out.println(list);

        list.sort(new Comparator<Action>() {
            @Override
            public int compare(Action o1, Action o2) {
                return o2.type.equals("delete") ? 1 : -1;
            }
        });

        System.out.println(list);
    }

    public static class Action {

        public String type;

        @Override
        public String toString() {
            return this.type;
        }
    }


}
