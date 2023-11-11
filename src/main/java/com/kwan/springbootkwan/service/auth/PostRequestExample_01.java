package com.kwan.springbootkwan.service.auth;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class PostRequestExample_01 {
    public static void main(String[] args) {
        try {
            // 设置 URL
            URL url = new URL("https://bizapi.csdn.net/blog/phoenix/console/v1/article/del");

            // 创建一个信任所有证书的 TrustManager
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // 初始化 SSL 上下文，绕过 SSL 校验
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // 设置请求头
            connection.setRequestProperty("Host", "bizapi.csdn.net");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Length", "38");
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Sec-Ch-Ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
            connection.setRequestProperty("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce");
            connection.setRequestProperty("X-Ca-Signature", "7FP+pT4aB7sATGCCh6eSGEeY/MB+Le3K08n7jgqW/MQ=");
            connection.setRequestProperty("X-Ca-Nonce", "800a0e71-4dea-43a1-aa62-d7e152aee9f6");
            connection.setRequestProperty("Sec-Ch-Ua-Mobile", "?0");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("X-Ca-Key", "203803574");
            connection.setRequestProperty("Sec-Ch-Ua-Platform", "macOS");
            connection.setRequestProperty("Origin", "https://mp.csdn.net");
            connection.setRequestProperty("Sec-Fetch-Site", "same-site");
            connection.setRequestProperty("Sec-Fetch-Mode", "cors");
            connection.setRequestProperty("Sec-Fetch-Dest", "empty");
            connection.setRequestProperty("Referer", "https://mp.csdn.net/mp_blog/manage/article?spm=1011.2419.3001.5298");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("Cookie", "your-cookie-values-here");
            connection.setRequestProperty("X-Postman-Captr", "7793363");

            // 允许输入和输出流
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 设置请求体
            String requestBody = "{\"articleId\": \"124029992\", \"deep\": false}";
            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes("UTF-8"));
            os.close();

            // 发起请求
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 处理响应
            // ...

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
