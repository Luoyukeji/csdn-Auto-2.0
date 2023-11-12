package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.csdn.RedPackageResponse;
import com.kwan.springbootkwan.service.CsdnRedPackageService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CsdnRedPackageServiceImpl implements CsdnRedPackageService {

    @Value("${csdn.cookie}")
    private String csdnCookie;

    @Override
    public Object haveRedPackage() {
        Integer type = 2;
        String host = "https://bizapi.csdn.net";
        String path = "/community-cloud/v1/new/home/recent?pageNum=1&type=" + type;
        OkHttpClient client = new OkHttpClient();
        final String nonce = "86520cbe-2e44-40c9-ae10-d19cfdf08526";
        final String signature = "HRVCjNq+6T52hz7eF4SDcnlNTJauIMbrF63X31AJ6wI=";
        Request request = new Request.Builder()
                .url(host + path)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("origin", "https://bbs.csdn.net")
                .addHeader("referer", "https://bbs.csdn.net/?type=4&header=0&utm_source=wwwtab")
                .addHeader("cookie", csdnCookie)
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                .addHeader("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("X-Ca-Nonce", nonce)
                .addHeader("X-Ca-Signature", signature)
                .addHeader("X-Ca-Key", "203899271")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("Sec-Fetch-Site", "same-site")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("host", "bizapi.csdn.net")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            response.header("content-type", "application/json;charset=utf-8");
            String responseData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            RedPackageResponse collectResponse = objectMapper.readValue(responseData, RedPackageResponse.class);
            collectResponse.getData();
            System.out.println(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Integer type = 2;
        String host = "https://bizapi.csdn.net";
        String path = "/community-cloud/v1/new/home/recent?pageNum=1&type=" + type;
        OkHttpClient client = new OkHttpClient();
        final String nonce = "86520cbe-2e44-40c9-ae10-d19cfdf08526";
        final String signature = "HRVCjNq+6T52hz7eF4SDcnlNTJauIMbrF63X31AJ6wI=";
        Request request = new Request.Builder()
                .url(host + path)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("origin", "https://bbs.csdn.net")
                .addHeader("referer", "https://bbs.csdn.net/?type=4&header=0&utm_source=wwwtab")
                .addHeader("cookie", "uuid_tt_dd=10_20285116700-1699412958190-182091; c_adb=1; loginbox_strategy=%7B%22taskId%22%3A308%2C%22abCheckTime%22%3A1699412959325%2C%22version%22%3A%22exp1%22%2C%22blog-threeH-dialogtipShowTimes%22%3A1%7D; UserName=qyj19920704; UserInfo=3a23ff2c7452466ab71cc5d4606d2aeb; UserToken=3a23ff2c7452466ab71cc5d4606d2aeb; UserNick=%E6%AA%80%E8%B6%8A%E5%89%91%E6%8C%87%E5%A4%A7%E5%8E%82; AU=769; UN=qyj19920704; BT=1699412972470; p_uid=U010000; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22qyj19920704%22%2C%22scope%22%3A1%7D%7D; management_ques=1699426171980; historyList-new=%5B%5D; ssxmod_itna=iqRx270QG=DtN0Lhx8FeRDWT4Curd3xDI3D/QnGDnqD=GFDK40oAyhIbDOKccKkn2nXeaY0qP0rAtUjxtT/Qa+4GIDeKG2DmeDyDi5GRD0KmKiDenQD5xGoDPxDeDAGKDC955KDd2XvNjBfRGBDD3DmqGySqGvjdjUCdUxD0PqDYPTFd2exBQZ+uLjAmUVnMbYnOGAKD+1tTqWb0GKR7eK8c+Kn7xtKg934fhPb7GrRcc3C6i1tk5xD=; ssxmod_itna2=iqRx270QG=DtN0Lhx8FeRDWT4Curd3xDKG93=KkDBw72D7PUPBWFGF+5hxm281HU8LUpD9tqlYfEFXre5Ce9D+=88G8U1YciQccvg+KuiH6H=B=KXzO=UujrROOupYk9o06U9TmcSSXRlS3feQ=1I80er7PKMSr5pf2Yy4KZ3xG2z4GcDiQVb2GKeD==; dp_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MjA4Nzc0LCJleHAiOjE3MDAxMTg5NjEsImlhdCI6MTY5OTUxNDE2MSwidXNlcm5hbWUiOiJxeWoxOTkyMDcwNCJ9.aExmuc4tgKqtI4Pf6p2jMluYXV4vwUqiuK6As8_FF9I; blog_details_nps=1699613550551; is_advert=1; qyj19920704comment_new=1699675229375; creative_btn_mp=3; c_hasSub=true; SidecHatdocDescBoxNum=true; dc_session_id=10_1699751595149.901437; c_segment=11; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1699703402,1699706314,1699711243,1699751596; dc_sid=9a39ee11e4f1a7e482f5e151a419a52e; blog_details_recommend_nps=1699751971622; c_first_ref=default; c_first_page=https%3A//www.csdn.net/qc; fe_request_id=1699753350384_0058_3053081; c_dsid=11_1699752505945.092715; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230920034826.png%22%2C%22articleNum%22%3A624%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22qyj19920704%22%7D; log_Id_click=14; c_pref=https%3A//bbs.csdn.net/%3Ftype%3D4%26header%3D0%26utm_source%3Dwwwtab; c_ref=https%3A//www.csdn.net/%3Fspm%3D1011.2124.3001.4476; c_utm_source=wwwtab; utm_source=wwwtab; c_page_id=default; log_Id_pv=156; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1699753667; log_Id_view=2283; dc_tos=s3zme5")
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                .addHeader("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("X-Ca-Nonce", nonce)
                .addHeader("X-Ca-Signature", signature)
                .addHeader("X-Ca-Key", "203899271")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("Sec-Fetch-Site", "same-site")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("host", "bizapi.csdn.net")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            response.header("content-type", "application/json;charset=utf-8");
            String responseData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            RedPackageResponse collectResponse = objectMapper.readValue(responseData, RedPackageResponse.class);
            RedPackageResponse.RedPackageData data = collectResponse.getData();
            List<RedPackageResponse.RedPackageData.RedPackageList> list = data.getList();
            if (CollectionUtil.isNotEmpty(list)) {
                list = list.stream().filter(x -> Objects.nonNull(x.getContent().getCheckRedPacket())).collect(Collectors.toList());
                System.out.println(list);
            }
            System.out.println(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
