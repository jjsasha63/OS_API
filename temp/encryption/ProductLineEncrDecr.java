package com.red.os_api.encryption;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.stream.Collectors;

public class ProductLineEncrDecr implements ProductLineEncrDecrInterface {

    @Value("${key.product.line}")
    private String key;


    @Override
    public String encrypt(Map<String, Integer> product) {
        return AES.encrypt(mapToString(product),key);
    }

    @Override
    public Map<String, Integer> decrypt(String e) {
        return null;
    }

    String mapToString(Map<String,Integer> map){
        return map.keySet().stream()
                .map(key -> key + "-" + map.get(key))
                .collect(Collectors.joining(", "));

    }
}
