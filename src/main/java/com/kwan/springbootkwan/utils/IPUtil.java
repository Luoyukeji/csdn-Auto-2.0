package com.kwan.springbootkwan.utils;

import java.util.Random;


/**
 * IP工具类
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/7 10:11
 */
public class IPUtil {

    /**
     * 获取随机ip
     *
     * @return
     */
    public static String generateRandomIP() {
        Random random = new Random();
        int part3 = random.nextInt(256);
        int part4 = random.nextInt(256);
        String ip = "123.64." + part3 + "." + part4;
        String ip1 = "110.127." + part3 + "." + part4;
        String ip2 = "115.168." + part3 + "." + part4;
        String ip3 = "119.18." + part3 + "." + part4;
        String ip4 = "123.67." + part3 + "." + part4;
        int i = (new Random()).nextInt(5);
        if (i == 1) {
            return ip1;
        } else if (i == 2) {
            return ip2;
        } else if (i == 3) {
            return ip3;
        } else if (i == 4) {
            return ip4;
        } else {
            return i == 5 ? ip : ip;
        }
    }
}
