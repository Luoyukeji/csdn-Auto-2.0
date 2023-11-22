package com.kwan.springbootkwan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Nonce生成工具类
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/22 13:52
 */
public class GetNonceUtil {
    private static List<String> chats = new ArrayList<>();

    static {
        //生成 [a, b, c, d, e, f, g, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        for (int i = 0; i < 7; i++) {
            char c = (char) (i + 97);
            chats.add(String.valueOf(c));
        }
        for (int i = 0; i < 10; i++) {
            char c = (char) (i + 48);
            chats.add(String.valueOf(c));
        }
    }

    /**
     * 获取一次性访问key
     *
     * @return
     */
    public static String onceKey() {
        List<String> strs = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < 30; i++) {
            strs.add(chats.get(rd.nextInt(chats.size())));
        }
        return String.format("%s%s%s%s%s%s%s%s-%s%s%s%s-4%s%s%s-9%s%s%s-%s%s%s%s%s%s%s%s%s%s%s%s", strs.toArray());
    }
}
