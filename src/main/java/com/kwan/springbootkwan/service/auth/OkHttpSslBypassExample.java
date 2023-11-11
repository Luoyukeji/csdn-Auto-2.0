package com.kwan.springbootkwan.service.auth;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class OkHttpSslBypassExample {
    public static void main(String[] args) {
        try {
            // 创建自定义TrustManager来绕过SSL验证
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // 创建SSL上下文并初始化
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            // 创建OkHttpClient并设置自定义的SSL上下文
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true) // 禁用主机名验证
                    .build();

            MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundarySNjHB5BC1gTFn3PG");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("url", "https://qinyingjie.blog.csdn.net/article/details/134094686")
                    .build();
            Request request = new Request.Builder()
                    .url("https://bizapi.csdn.net/trends/api/v1/get-article-score")
                    .method("POST", body)
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-site")
                    .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"118\", \"Google Chrome\";v=\"118\", \"Not=A?Brand\";v=\"99\"")
                    .addHeader("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                    .addHeader("X-Ca-Signature", "kYkS7PV3x5M1TsCI22Kn3MqiA/V+OZnIGtdHOxQQ1TI=")
                    .addHeader("X-Ca-Nonce", "d6bedcac-4ab6-4a17-a06d-77c0f2ee3c37")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("X-Ca-Signed-Content-Type", "multipart/form-data")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("X-Ca-Key", "203930474")
                    .addHeader("sec-ch-ua-platform", "\"macOS\"")
                    .addHeader("host", "bizapi.csdn.net")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarySNjHB5BC1gTFn3PG")
                    .build();

            Response response = client.newCall(request).execute();

            // 处理响应
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);
            } else {
                System.out.println("Request failed with response code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
