package com.kwan.springbootkwan.service.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * 抽取参数并验证
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/8 13:47
 */
public class Signature_Test_02 {

    /**
     * acf69aa9-e4b7-429b-a7a7-e986a394e6d1
     * 5FH8KglKPcCyevvEtS669R0N2rOZM35nD+JI30RHNw8=
     *
     * @param args
     */
    public static void main(String[] args) {
//        String url = "/mp/ask/v1/questions/list?communityInfo=1&pageNo=1&pageSize=30&quick=3&rewardType=2&sortBy=1&type=5";
        String url = "/mp/ask/v1/questions/list?pageNo=1&pageSize=30&communityInfo=1&rewardType=2&sortBy=1&type=5";
        final String xCaKey = "203871397";
        final String nonce = "acf69aa9-e4b7-429b-a7a7-e986a394e6d1";
        System.out.println(getSignature(nonce, xCaKey, url));
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
