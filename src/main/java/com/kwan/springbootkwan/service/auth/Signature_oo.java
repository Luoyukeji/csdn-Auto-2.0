package com.kwan.springbootkwan.service.auth;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * 删除文章
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/8 09:04
 */
public class Signature_oo {
    public static void main(String[] args) {
        String url = "https://bizapi.csdn.net/blog/phoenix/console/v1/article/del";
        String paramUrl = "/blog/phoenix/console/v1/article/del";
        String nonce = getNonce();
        final String xCaKey = "203803574";
        String signature = getSignature(nonce, xCaKey, paramUrl);
        HttpRequest request = HttpRequest.get(url);
        // 添加自定义头部
        request.header("authority", "bizapi.csdn.net");
        request.header("pragma", "no-cache");
        request.header("cache-control", "no-cache");
        request.header("sec-ch-ua", "\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"");
        request.header("dnt", "1");
        request.header("x-ca-signature-headers", "x-ca-key,x-ca-nonce");
        request.header("x-ca-signature", signature);
        request.header("x-ca-nonce", nonce);
        request.header("sec-ch-ua-mobile", "?0");
        request.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
        request.header("uber-trace-id", "50d7304716310079669041001c6a0ab7:50d7304716310089:0:0");
        request.header("accept", "application/json, text/plain, */*");
        request.header("x-ca-key", "203871397");
        request.header("origin", "https://ask.csdn.net");
        request.header("sec-fetch-site", "same-site");
        request.header("sec-fetch-mode", "cors");
        request.header("sec-fetch-dest", "empty");
        request.header("referer", "https://ask.csdn.net/?spm=1005.2025.3001.4492&rewardType=2&stateType=5&sortBy=1&quick=3");
        request.header("accept-language", "zh-CN,zh;q=0.9");
        // 发送请求并获取响应
        HttpResponse response = request.execute();
        // 处理响应
        int statusCode = response.getStatus();
        String responseBody = response.body();
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        System.out.println(response);
    }

    public static String getNonce() {
        StringBuilder text = new StringBuilder();
        char[] charList = new char[16];
        for (int c = 0; c < 6; c++) {
            charList[c] = (char) (97 + c);
        }
        for (int c = 0; c < 10; c++) {
            charList[c + 6] = (char) (48 + c);
        }
        String template = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx";
        Random random = new Random();
        for (char i : template.toCharArray()) {
            if (i == '4') {
                text.append('4');
            } else if (i == '-') {
                text.append('-');
            } else {
                text.append(charList[random.nextInt(16)]);
            }
        }
        return text.toString();
    }

    public static String getSignature(String nonce, String xCaKey, String url) {
        String appSecret = "OTGHZy1hLh1HFWbLnpG68OwZGc2TQwld";
        byte[] appSecretBytes = appSecret.getBytes(StandardCharsets.UTF_8);
        String toEnc = "GET\napplication/json, text/plain, */*\n\n\n\nx-ca-key:" + xCaKey + "\nx-ca-nonce:" + nonce + "\n" + url;
        byte[] toEncBytes = toEnc.getBytes(StandardCharsets.UTF_8);
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(appSecretBytes, "HmacSHA256");
            hmacSha256.init(secretKey);
            byte[] signatureBytes = hmacSha256.doFinal(toEncBytes);
            String signature = Base64.getEncoder().encodeToString(signatureBytes);
            return signature;
        } catch (NoSuchAlgorithmException | java.security.InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}