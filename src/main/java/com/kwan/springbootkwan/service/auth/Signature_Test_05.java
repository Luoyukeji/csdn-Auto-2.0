package com.kwan.springbootkwan.service.auth;

import com.kwan.springbootkwan.utils.GetNonceUtil;
import com.kwan.springbootkwan.utils.GetSignatureUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 删除文章
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/8 13:47
 */
public class Signature_Test_05 {

    public static void main(String[] args) throws Exception {
        del();
    }

    public static void del() throws Exception {
        String host = "https://bizapi.csdn.net";
        String path = "/blog/phoenix/console/v1/article/del";
        String onceKey = GetNonceUtil.generateUUID();
        String sign = GetSignatureUtil.sign(path, "post", onceKey);
        MediaType JSON = MediaType.parse("application/json;");
        String jsonBody = "{\"articleId\": \"122517817\",\"deep\": false}";
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(host + path)
                .addHeader("host", "bizapi.csdn.net")
                .addHeader("connection", "keep-alive")
                .addHeader("content-length", "38")
                .addHeader("pragma", "no-cache")
                .addHeader("cache-control", "no-cache")
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                .addHeader("x-ca-signature-headers", "x-ca-key,x-ca-nonce")
                .addHeader("x-ca-signature", sign)
                .addHeader("x-ca-nonce", onceKey)
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                .addHeader("content-type", "application/json;")
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("x-ca-Key", "203803574")
                .addHeader("sec-ch-ua-platform", "macOS")
                .addHeader("origin", "https://mp.csdn.net")
                .addHeader("sec-fetch-site", "same-site")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("referer", "https://mp.csdn.net/mp_blog/manage/article?spm=1011.2419.3001.5298")
                .addHeader("accept-encoding", "gzip, deflate, br")
                .addHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("cookie", "uuid_tt_dd=10_20285116700-1699412958190-182091; c_segment=11; c_adb=1; dc_sid=b267fc500aea1c7d7c276ef7d4a9a84e; loginbox_strategy=%7B%22taskId%22%3A308%2C%22abCheckTime%22%3A1699412959325%2C%22version%22%3A%22exp1%22%2C%22blog-threeH-dialogtipShowTimes%22%3A1%7D; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1699412959; hide_login=1; SESSION=0a742a93-93a3-488f-87df-fd28453706b0; UserName=qyj19920704; UserInfo=3a23ff2c7452466ab71cc5d4606d2aeb; UserToken=3a23ff2c7452466ab71cc5d4606d2aeb; UserNick=%E6%AA%80%E8%B6%8A%E5%89%91%E6%8C%87%E5%A4%A7%E5%8E%82; AU=769; UN=qyj19920704; BT=1699412972470; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22qyj19920704%22%2C%22scope%22%3A1%7D%7D; c_hasSub=true; creative_btn_mp=3; dc_session_id=10_1699422321429.127910; c_page_id=default; c_pref=default; c_ref=default; c_first_ref=default; management_ques=1699426171980; c_first_page=https%3A//mp.csdn.net/mp_blog/manage/article%3Fspm%3D1011.2124.3001.5298; c_dsid=11_1699426699457.574838; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1699426700; log_Id_pv=29; log_Id_click=11; log_Id_view=191; dc_tos=s3soio")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        response.header("content-type", "application/json;charset=utf-8");
//        出现错误可以将此打开获取报错内容
        System.out.println(response);
        System.out.println(response.header("X-Ca-Error-Message"));
        String responseData = response.body().string();
        System.out.println(responseData);
    }
}
