package com.example.pep.iot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author LiuGang
 * @since 2022-03-04 1:48 PM
 */
@Slf4j
public class CertificateUtils {

    private final static Charset CHARSET = StandardCharsets.UTF_8;

    private CertificateUtils() {
    }

    /**
     * 创建签名
     *
     * @param params    请求参数
     * @param accessKey ak
     * @param secretKey sk
     * @param timestamp 时间戳
     * @return 签名字符串
     */
    public static String createSign(HashMap<String, String> params, String accessKey, String secretKey, String timestamp) {
        String stringSignTemp = getStringSignTemp(params, accessKey, secretKey, timestamp);
        log.info("accessKey is {} ,sign stringSignTemp is {}", accessKey, stringSignTemp);
        String sign = DigestUtils.md5Hex(stringSignTemp.getBytes(CHARSET)).toUpperCase();
        log.info("sign is {}", sign);
        return sign;
    }

    public static String getStringSignTemp(HashMap<String, String> params, String accessKey, String secretKey, String timestamp) {
        HashMap<String, String> newRequestParams = removeEmptyParam(params);
        newRequestParams.put("AccessKey", accessKey);
        newRequestParams.put("Timestamp", timestamp);
        String strUrlParams = parseUrlString(newRequestParams);
        return MessageFormat.format("{0}&SecretKey={1}", strUrlParams, secretKey);
    }

    private static HashMap<String, String> removeEmptyParam(HashMap<String, String> params) {
        HashMap<String, String> newParams = new HashMap<>(10);
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (value != null && !"".equals(value)) {
                    newParams.put(key, value);
                }
            }
        }
        return newParams;
    }

    private static String parseUrlString(HashMap<String, String> requestMap) {
        List<String> keyList = new ArrayList<>(requestMap.keySet());
        Collections.sort(keyList);
        List<String> entryList = new ArrayList<>();
        String key;
        String value;
        for (Iterator<String> var3 = keyList.iterator(); var3.hasNext(); entryList.add(MessageFormat.format("{0}={1}", key, value))) {
            key = var3.next();
            value = requestMap.get(key);
            try {
                value = URLEncoder.encode(value, CHARSET.name());
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }
        }
        return String.join("&", entryList);
    }


}
