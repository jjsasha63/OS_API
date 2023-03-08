package com.red.os_api.encryption;

import java.util.Map;

public interface ProductLineEncrDecrInterface {

    String encrypt(Map<String,Integer> product);

    Map<String,Integer> decrypt(String e);

}
