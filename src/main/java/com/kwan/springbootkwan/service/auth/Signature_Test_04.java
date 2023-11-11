package com.kwan.springbootkwan.service.auth;

import cn.hutool.json.JSONObject;
import com.kwan.springbootkwan.utils.GetNonceUtil;
import com.kwan.springbootkwan.utils.GetSignatureUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文章列表页获取文章信息
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/8 13:47
 */
public class Signature_Test_04 {

    public static void main(String[] args) throws Exception {
        getAllMyArticle();
    }

    public static JSONObject getAllMyArticle() throws Exception {
        String host = "https://bizapi.csdn.net";
        String path = "/blog/phoenix/console/v1/data/single-article-list?action=down&page=1&size=40&type=date";
        String onceKey = GetNonceUtil.onceKey();
        String sign = GetSignatureUtil.sign(path, "get", onceKey);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(host + path)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Connection", "keep-alive")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36")
                .addHeader("origin", "https://mp.csdn.net")
                .addHeader("referer", "https://mp.csdn.net/mp_blog/analysis/article/single")
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"105\", \"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"105\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-site")
                .addHeader("uri-name", "feige")
                //登录后的cokie
                .addHeader("cookie", "uuid_tt_dd=10_20285116700-1697522872601-604163; loginbox_strategy=%7B%22taskId%22%3A308%2C%22abCheckTime%22%3A1697522874474%2C%22version%22%3A%22control%22%7D; UserName=qyj19920704; UserInfo=a7d3b88c53a841ebb5792202cb43c84f; UserToken=a7d3b88c53a841ebb5792202cb43c84f; UserNick=%E6%AA%80%E8%B6%8A%E5%89%91%E6%8C%87%E5%A4%A7%E5%8E%82; AU=769; UN=qyj19920704; BT=1697522886100; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22qyj19920704%22%2C%22scope%22%3A1%7D%7D; management_ques=1697592152734; blog_details_recommend_nps=1697616147423; 404_page_nps=1697764955523; blog_details_nps=1698023190651; c_dl_um=-; c_ins_um=-; c_ins_prid=1698062777818_482125; c_ins_fref=https://blog.csdn.net/Why_does_it_work/article/details/132379261; c_ins_rid=1698065720471_137784; c_ins_fpage=/@why_does_it_work/HTML-CSS-JS_38/embed; ins_first_time=1698065722171; ssxmod_itna=eqjOq0hGOGkD8zDXzqOxmq4YuKh58DGxk88DGXxG8mDnqD=GFDK40oog5erE=2=LehAx3hdUGgvfs+WEnoK4YiPeaX=bDneG0DQKGmDBKDSDWKD9mGi4GGjxBYDQxAYDGDDp9DGkN8uD0+1lgvO9EcD9xDWDY5DXoYDN6vO+=v1NDGdqDi3ADvwt40OVOy7zpOze/5v/liaTGPkLAR4oW+Ydi0oT64xLjGYgb2K=Q0q=Grqe1heZ7hY3+nu4D===; ssxmod_itna2=eqjOq0hGOGkD8zDXzqOxmq4YuKh58DGxkSxA6u5beD/DfEOx7P2ga69=S6yghP6CQk2wsYtx7Qr6DjKD2eoD; c_dl_prid=1698078817876_322700; c_dl_rid=1698394990416_489823; c_dl_fref=https://blog.csdn.net/apple_53311083; c_dl_fpage=/download/apple_53311083/88478024; dp_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MjA4Nzc0LCJleHAiOjE2OTkxNTc3NDYsImlhdCI6MTY5ODU1Mjk0NiwidXNlcm5hbWUiOiJxeWoxOTkyMDcwNCJ9.-k9TQsMrYHwvZuiD_83OhzwXmtJrtWMnemn4ciqg2zw; c_adb=1; c_hasSub=true; creative_btn_mp=3; write_guide_show=3; SidecHatdocDescBoxNum=true; c_page_id=default; c_utm_source=14998968; dc_session_id=11_1698764408010.601160; dc_sid=d60b6a188cb40d765ed51642ee7e2bc4; c_pref=default; c_ref=default; c_first_ref=default; c_first_page=https%3A//www.csdn.net/qc; c_dsid=11_1698766698997.024373; c_segment=3; log_Id_pv=304; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1698719672,1698751467,1698764408,1698766699; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1698766699; get_latest_comment=%257B%2522list%2522%253A%255B%257B%2522url%2522%253A%2522https%253A%252F%252Fqinyingjie.blog.csdn.net%252Farticle%252Fdetails%252F134142148%2523comments_29544088%2522%252C%2522myUrl%2522%253A%2522https%253A%252F%252Fblog.csdn.net%252Fm0_63951142%2522%252C%2522userName%2522%253A%2522%25E7%2583%25AD%25E7%2588%25B1%25E7%25BC%2596%25E7%25A8%258B%25E7%259A%2584%25E6%259E%2597%25E5%2585%25AE%2522%252C%2522content%2522%253A%2522%25E5%258D%259A%25E4%25B8%25BB%25E7%259A%2584%25E6%2596%2587%25E7%25AB%25A0%25E7%25BB%2586%25E8%258A%2582%25E5%25BE%2588%25E5%2588%25B0%25E4%25BD%258D%25EF%25BC%258C%25E5%2585%25BC%25E9%25A1%25BE%25E5%25AE%259E%25E7%2594%25A8%25E6%2580%25A7%25E5%2592%258C%25E5%258F%25AF%25E6%2593%258D%25E4%25BD%259C%25E6%2580%25A7%25EF%25BC%258C%25E6%2584%259F%25E8%25B0%25A2%25E5%258D%259A%25E4%25B8%25BB%25E7%259A%2584%25E5%2588%2586%25E4%25BA%25AB%25EF%25BC%258C%25E6%259C%259F%25E5%25BE%2585%25E5%258D%259A%25E4%25B8%25BB%25E6%258C%2581%25E7%25BB%25AD%25E5%25B8%25A6%25E6%259D%25A5%25E6%259B%25B4%25E5%25A4%259A%25E5%25A5%25BD%25E6%2596%2587%25EF%25BC%258C%25E5%2590%258C%25E6%2597%25B6%25E4%25B9%259F%25E5%25B8%258C%25E6%259C%259B%25E5%258F%25AF%25E4%25BB%25A5%25E6%259D%25A5%25E6%2588%2591%25E5%258D%259A%25E5%25AE%25A2%25E6%258C%2587%25E5%25AF%25BC%25E6%2588%2591%25E4%25B8%2580%25E7%2595%25AA%25EF%25BC%2581%2522%252C%2522title%2522%253A%2522%25E3%2580%2590%25E9%25AB%2598%25E6%2595%2588%25E5%25BC%2580%25E5%258F%2591%25E5%25B7%25A5%25E5%2585%25B7%25E7%25B3%25BB%25E5%2588%2597%25E3%2580%2591%25E4%25BD%25A0%25E7%259C%259F%25E7%259A%2584%25E4%25BC%259A%25E4%25BD%25BF%25E7%2594%25A8Mac%25E5%2590%2597%253F%2522%252C%2522child%2522%253Afalse%252C%2522createTime%2522%253A1698766518708%257D%252C%257B%2522url%2522%253A%2522https%253A%252F%252Fqinyingjie.blog.csdn.net%252Farticle%252Fdetails%252F134142148%2523comments_29543832%2522%252C%2522myUrl%2522%253A%2522https%253A%252F%252Fblog.csdn.net%252Fweixin_64133130%2522%252C%2522userName%2522%253A%2522%25E6%2598%258E%25E7%259F%25BEjava%2522%252C%2522content%2522%253A%2522%25E4%25BC%2598%25E8%25B4%25A8%25E5%25A5%25BD%25E6%2596%2587%25EF%25BC%258C%25E5%258D%259A%25E4%25B8%25BB%25E7%259A%2584%25E6%2596%2587%25E7%25AB%25A0%25E7%25BB%2586%25E8%258A%2582%25E5%25BE%2588%25E5%2588%25B0%25E4%25BD%258D%25EF%25BC%258C%25E5%2585%25BC%25E9%25A1%25BE%25E5%25AE%259E%25E7%2594%25A8%25E6%2580%25A7%25E5%2592%258C%25E5%258F%25AF%25E6%2593%258D%25E4%25BD%259C%25E6%2580%25A7%25EF%25BC%258C%25E6%2584%259F%25E8%25B0%25A2%25E5%258D%259A%25E4%25B8%25BB%25E7%259A%2584%25E5%2588%2586%25E4%25BA%25AB%25EF%25BC%258C%25E6%259C%259F%25E5%25BE%2585%25E5%258D%259A%25E4%25B8%25BB%25E6%258C%2581%25E7%25BB%25AD%25E5%25B8%25A6%25E6%259D%25A5%25E6%259B%25B4%25E5%25A4%259A%25E5%25A5%25BD%25E6%2596%2587%2522%252C%2522title%2522%253A%2522%25E3%2580%2590%25E9%25AB%2598%25E6%2595%2588%25E5%25BC%2580%25E5%258F%2591%25E5%25B7%25A5%25E5%2585%25B7%25E7%25B3%25BB%25E5%2588%2597%25E3%2580%2591%25E4%25BD%25A0%25E7%259C%259F%25E7%259A%2584%25E4%25BC%259A%25E4%25BD%25BF%25E7%2594%25A8Mac%25E5%2590%2597%253F%2522%252C%2522child%2522%253Afalse%252C%2522createTime%2522%253A1698765659922%257D%255D%252C%2522timestamp%2522%253A1698766518708%257D; creativeSetApiNew=%7B%22toolbarImg%22%3Anull%2C%22publishSuccessImg%22%3Anull%2C%22articleNum%22%3A899%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Afalse%2C%22userName%22%3A%22qyj19920704%22%7D; log_Id_view=1909; log_Id_click=278; dc_tos=s3egtp")
                .addHeader("x-ca-key", "203803574")
                .addHeader("x-ca-nonce", onceKey)
                .addHeader("x-ca-signature", sign)
                .addHeader("x-ca-signature-headers", "x-ca-key,x-ca-nonce")
                .build();
        Response response = client.newCall(request).execute();
        response.header("content-type", "application/json;charset=utf-8");
//        出现错误可以将此打开获取报错内容
//        System.out.println(response);
//        System.out.println(response.header("X-Ca-Error-Message"));
        String responseData = response.body().string();
        System.out.println(responseData);
        return null;
    }
}
