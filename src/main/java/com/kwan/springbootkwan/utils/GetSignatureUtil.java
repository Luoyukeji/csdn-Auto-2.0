package com.kwan.springbootkwan.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * Signature生成工具类
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/22 13:52
 */
public class GetSignatureUtil {


    public static String sign(String fullUrl, String method, String onceKey, String xcakey, String ekey) throws Exception {
        String[] wholdUrl = fullUrl.split("\\?");
        String url, params = "";
        if ("get".equals(method)) {
            url = wholdUrl[0];
            params = wholdUrl[1];
        } else {
            url = wholdUrl[0];
        }
        String _url = url + (!"".equals(params) ? "?" + params : "");
        String to_enc;
        if ("get".equals(method)) {
            to_enc = String.format("GET\napplication/json, text/plain, */*\n\n\n\nx-ca-key:%s\nx-ca-nonce:%s\n%s", xcakey, onceKey, _url);
        } else {
            to_enc = String.format("POST\n%s\n\n%s\n\nx-ca-key:%s\nx-ca-nonce:%s\n%s"
                    , "application/json, text/plain, */*", "application/json;", xcakey, onceKey, _url);
        }
        return getSHA256StrJava(to_enc, ekey);
    }

    private static String getSHA256StrJava(String content, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secret_key);
        byte[] binaryData = mac.doFinal(content.getBytes());
        return Base64.encodeBase64String(binaryData);
    }

}
