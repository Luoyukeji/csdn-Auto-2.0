package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CsdnUserSearch;
import com.kwan.springbootkwan.entity.csdn.DeleteQuery;
import com.kwan.springbootkwan.entity.csdn.ScoreResponse;
import com.kwan.springbootkwan.enums.CollectStatus;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.mapper.CsdnArticleInfoMapper;
import com.kwan.springbootkwan.service.CsdnArticleCommentService;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnArticleLikeService;
import com.kwan.springbootkwan.service.CsdnCollectService;
import com.kwan.springbootkwan.utils.GetNonceUtil;
import com.kwan.springbootkwan.utils.GetSignatureUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.math.BigDecimal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnArticleInfoServiceImpl extends ServiceImpl<CsdnArticleInfoMapper, CsdnArticleInfo> implements CsdnArticleInfoService {
    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.self_user_nick_name}")
    private String selfUserNickName;
    @Value("${csdn.url.get_article_score_url}")
    private String getArticleScoreUrl;
    @Resource
    private CsdnArticleLikeService csdnArticleLikeService;
    @Resource
    private CsdnCollectService csdnCollectService;
    @Resource
    private CsdnArticleCommentService csdnArticleCommentService;

    @Override
    public CsdnArticleInfo getArticleByArticleId(String articleId) {
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("article_id", articleId)
                .last("limit 1");
        return this.getOne(wrapper);
    }


    @Override
    public CsdnArticleInfo getArticle(String nickName, String userName) {

        String url = "https://so.csdn.net/api/v3/search?q=" + nickName + "&t=blog&p=1&s=new&tm=7&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&urw=&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
        HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
            if (Objects.nonNull(csdnUserSearch)) {
                final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                if (CollectionUtil.isNotEmpty(result_vos)) {
                    for (CsdnUserSearch.CsdnUserSearchInner searchInner : result_vos) {
                        final String username = searchInner.getUsername();
                        if (StringUtils.equalsIgnoreCase(username, userName)) {
                            return this.getArticleBySearchAll(searchInner);
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CsdnArticleInfo> getArticles10(String nickName, String userName) {
        List<CsdnArticleInfo> res = new ArrayList<>();
        String url = "https://so.csdn.net/api/v3/search?q=" + nickName + "&t=blog&p=1&s=new&tm=10&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&urw=&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
        HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
            if (Objects.nonNull(csdnUserSearch)) {
                final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                if (CollectionUtil.isNotEmpty(result_vos)) {
                    for (CsdnUserSearch.CsdnUserSearchInner searchInner : result_vos) {
                        final String username = searchInner.getUsername();
                        if (StringUtils.equalsIgnoreCase(username, userName)) {
                            if (res.size() >= 10) {
                                return res;
                            }
                            res.add(this.getArticleBySearchAll(searchInner));
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<CsdnArticleInfo> getArticles100(String nickName, String userName) {
        List<CsdnArticleInfo> res = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String url = "https://so.csdn.net/api/v3/search?q=" + nickName + "&t=blog&p=" + i + "&s=new&tm=30&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&urw=&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
            HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).execute();
            final String body = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
                if (Objects.nonNull(csdnUserSearch)) {
                    final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                    if (CollectionUtil.isNotEmpty(result_vos)) {
                        for (CsdnUserSearch.CsdnUserSearchInner searchInner : result_vos) {
                            final String username = searchInner.getUsername();
                            if (StringUtils.equalsIgnoreCase(username, userName)) {
                                if (res.size() >= 100) {
                                    return res;
                                }
                                res.add(this.getArticleBySearchAll(searchInner));
                            }
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public void saveArticle(CsdnArticleInfo add) {
        final String articleId = add.getArticleId();
        CsdnArticleInfo csdnArticleInfo = this.getArticleByArticleId(articleId);
        if (Objects.isNull(csdnArticleInfo)) {
            csdnArticleInfo = new CsdnArticleInfo();
            csdnArticleInfo.setArticleId(articleId);
            csdnArticleInfo.setUserName(add.getUserName());
            csdnArticleInfo.setArticleTitle(add.getArticleTitle());
            csdnArticleInfo.setArticleDescription(add.getArticleDescription());
            csdnArticleInfo.setArticleUrl(add.getArticleUrl());
            csdnArticleInfo.setArticleScore(add.getArticleScore());
            csdnArticleInfo.setNickName(add.getNickName());
            csdnArticleInfo.setIsMyself(add.getIsMyself());
            this.save(csdnArticleInfo);
        }
    }

    @Override
    public Integer getArticleScore(String url) {
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
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("url", url)
                    .build();
            Request request = new Request.Builder()
                    .url(getArticleScoreUrl)
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
                ObjectMapper objectMapper = new ObjectMapper();
                ScoreResponse scoreResponse;
                try {
                    scoreResponse = objectMapper.readValue(responseBody, ScoreResponse.class);
                    final ScoreResponse.ScoreData data = scoreResponse.getData();
                    if (Objects.nonNull(data)) {
                        return data.getScore();
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Request failed with response code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void getBlogScore() {
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("article_score", 0);
        final List<CsdnArticleInfo> list = this.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnArticleInfo csdnArticleInfo : list) {
                final String articleUrl = csdnArticleInfo.getArticleUrl();
                final Integer articleScore;
                try {
                    articleScore = this.getArticleScore(articleUrl);
                    csdnArticleInfo.setArticleScore(articleScore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.updateById(csdnArticleInfo);
            }
        }
    }


    @Override
    public Integer getViewCount(String userName, String articleId) {
        String url = "https://blog.csdn.net/" + userName + "/article/details/" + articleId;
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("articleId", articleId)
                .execute();
        final String body = response.body();
        final int pid = body.indexOf("\"pid\":\"404\"");
        if (pid != -1) {
            return 0;
        }
        final int index = body.indexOf("阅读量");
        String str = body.substring(index, index + 7);
        for (int i = str.length() - 1; i >= 0; i--) {
            char lastChar = str.charAt(i);
            if (lastChar == 'k') {
                // 字符不是数字，舍去最后一位字符
                str = str.substring(3, i);
                return new BigDecimal(str).multiply(new BigDecimal(1000)).intValue();
            } else if (lastChar == 'w') {
                // 字符不是数字，舍去最后一位字符
                str = str.substring(3, i);
                return new BigDecimal(str).multiply(new BigDecimal(10000)).intValue();
            } else if (Character.isDigit(lastChar)) {
                // 字符不是数字，舍去最后一位字符
                return Integer.valueOf(str.substring(3, i + 1));
            }
        }
        return 0;
    }

    @Override
    public void syncMyBlog() {
        for (int i = 1; i <= 100; i++) {
            String url = "https://so.csdn.net/api/v3/search?q=" + selfUserNickName + "&t=blog&p=" + i + "&s=new&tm=30&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&urw=&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
            HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).execute();
            final String body = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
                if (Objects.nonNull(csdnUserSearch)) {
                    final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                    if (CollectionUtil.isNotEmpty(result_vos)) {
                        for (CsdnUserSearch.CsdnUserSearchInner searchInner : result_vos) {
                            final String username = searchInner.getUsername();
                            if (StringUtils.equalsIgnoreCase(username, selfUserName)) {
                                this.getArticleBySearchAll(searchInner);
                            }
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteLowBlog() {
        try {
            QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("is_delete", 0);
            wrapper.le("article_score", 79);
            wrapper.eq("is_myself", 1);
            final List<CsdnArticleInfo> list = list(wrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (CsdnArticleInfo csdnArticleInfo : list) {
                    final String articleId = csdnArticleInfo.getArticleId();
                    String host = "https://bizapi.csdn.net";
                    String path = "/blog/phoenix/console/v1/article/del";
                    final String xcakey = "203803574";
                    final String ekey = "9znpamsyl2c7cdrr9sas0le9vbc3r6ba";
                    String onceKey = GetNonceUtil.onceKey();
                    String signature = GetSignatureUtil.sign(path, "post", onceKey, xcakey, ekey);
                    DeleteQuery deleteQuery = new DeleteQuery();
                    deleteQuery.setArticleId(articleId);
                    deleteQuery.setDeep(false);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonCollectInfo = objectMapper.writeValueAsString(deleteQuery);
                    HttpResponse response = HttpUtil.createPost(host + path)
                            .header("Cookie", "uuid_tt_dd=10_20285116700-1699412958190-182091; c_adb=1; UN=qyj19920704; p_uid=U010000; management_ques=1699426171980; historyList-new=%5B%5D; dp_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MjA4Nzc0LCJleHAiOjE3MDAxMTg5NjEsImlhdCI6MTY5OTUxNDE2MSwidXNlcm5hbWUiOiJxeWoxOTkyMDcwNCJ9.aExmuc4tgKqtI4Pf6p2jMluYXV4vwUqiuK6As8_FF9I; blog_details_nps=1699613550551; blog_details_recommend_nps=1699751971622; c_ins_prid=-; c_ins_rid=1699884736255_252401; c_ins_fref=https://blog.csdn.net/nav/java; c_ins_fpage=/?utm_source=260232576&spm=1001.2100.3001.8290; c_ins_um=-; ins_first_time=1699884741141; hide_login=1; loginbox_strategy=%7B%22taskId%22%3A317%2C%22abCheckTime%22%3A1699886580644%2C%22version%22%3A%22ExpA%22%2C%22nickName%22%3A%22%E6%AA%80%E8%B6%8A%E5%89%91%E6%8C%87%E5%A4%A7%E5%8E%82%22%7D; UserName=qyj19920704; UserInfo=b98083c8819e4e53b63b0a57853c25a6; UserToken=b98083c8819e4e53b63b0a57853c25a6; UserNick=%E6%AA%80%E8%B6%8A%E5%89%91%E6%8C%87%E5%A4%A7%E5%8E%82; AU=769; BT=1699887483429; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%2C%22uid_%22%3A%7B%22value%22%3A%22qyj19920704%22%2C%22scope%22%3A1%7D%7D; c_segment=11; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1699765069,1699856983,1699881521,1699895347; dc_sid=1cbfec7af2f0d0a6026dd26ad15838f9; is_advert=1; SidecHatdocDescBoxNum=true; fe_request_id=1699943329457_0936_2289941; creative_btn_mp=3; c_utm_source=wwwtab; c_hasSub=true; qyj19920704comment_new=1699959325478; c_dl_prid=1699949672197_794895; c_dl_rid=1699962909502_780632; c_dl_fref=https://blog.csdn.net/MrZhangTS/article/details/124969234; c_dl_fpage=/download/yhsbzl/22403877; c_dl_um=distribute.pc_relevant.none-task-blog-2%7Edefault%7Ebaidujs_baidulandingword%7Edefault-1-124969234-blog-128776276.235%5Ev38%5Epc_relevant_anti_vip_base; SESSION=9633036a-58e7-4068-b7e9-dc2bafe27a0a; dc_session_id=11_1699967846228.286064; c_pref=default; c_ref=default; c_first_ref=43.139.90.182; log_Id_pv=182; log_Id_view=2428; c_first_page=https%3A//blog.csdn.net/m0_46376834/article/details/134366635; c_dsid=11_1699968481254.621155; c_page_id=default; ssxmod_itna=QqRxnDBQKWuDyQDzOD2YLDkYlQRiWiDYwnWPDsaQbDSxGKidDqxBmWC4DQbwSwQnhBxfGvYETpXmai++0KWDr2M4GIDeKG2DmeDyDi5GRD0IeebDeW=D5xGoDPxDeDAmKDCyodKDdR2Lv7ROaPRxDWDY5DXg5DN1v7zZICKDGdaDi3A0I+340OMoyH8aO1DU5IZ7GWKYxNwGKqfQiPYCGtqmrL8GDqOBGtB+2iYm24SjLL7BfhHlfDD=; ssxmod_itna2=QqRxnDBQKWuDyQDzOD2YLDkYlQRiWiDYwnDikvYwDlP/Dj+2qqlSo07D004jKG7lnhiHKxD=; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230920034826.png%22%2C%22articleNum%22%3A595%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22qyj19920704%22%7D; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1699968708; log_Id_click=22; dc_tos=s4489e")
                            .header("Content-Type", "application/json;")
                            .header("x-ca-Key", "203803574")
                            .header("x-ca-nonce", onceKey)
                            .header("x-ca-signature", signature)
                            .header("x-ca-signature-headers", "x-ca-key,x-ca-nonce")
                            .header("host", "bizapi.csdn.net")
                            .header("connection", "keep-alive")
                            .header("pragma", "no-cache")
                            .header("cache-control", "no-cache")
                            .header("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                            .header("sec-ch-ua-mobile", "?0")
                            .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                            .header("accept", "application/json, text/plain, */*")
                            .header("sec-ch-ua-platform", "macOS")
                            .header("origin", "https://mp.csdn.net")
                            .header("sec-fetch-site", "same-site")
                            .header("sec-fetch-mode", "cors")
                            .header("sec-fetch-dest", "empty")
                            .header("referer", "https://mp.csdn.net/mp_blog/manage/article?spm=1011.2419.3001.5298")
                            .header("accept-encoding", "gzip, deflate, br")
                            .header("accept-language", "zh-CN,zh;q=0.9,en;q=0.8")
                            .body(jsonCollectInfo)
                            .execute();
                    this.removeById(csdnArticleInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CsdnArticleInfo getArticleBySearchAll(CsdnUserSearch.CsdnUserSearchInner searchInner) {
        final String articleId = searchInner.getArticleid();
        CsdnArticleInfo csdnArticleInfo = this.getArticleByArticleId(articleId);
        if (Objects.isNull(csdnArticleInfo)) {
            final String username = searchInner.getUsername();
            final String nickname = searchInner.getNickname().replace("<em>", "").replace("</em>", "").trim();
            final String url = "https://blog.csdn.net/" + username + "/article/details/" + articleId;
            csdnArticleInfo = new CsdnArticleInfo();
            csdnArticleInfo.setArticleId(articleId);
            csdnArticleInfo.setArticleUrl(url);
            csdnArticleInfo.setArticleTitle(searchInner.getTitle());
            csdnArticleInfo.setArticleDescription(searchInner.getDescription());
            csdnArticleInfo.setUserName(username);
            csdnArticleInfo.setNickName(nickname);
            csdnArticleInfo.setArticleType(searchInner.getType());
            csdnArticleInfo.setArticleScore(this.getScore(url));
            if (StringUtils.equalsIgnoreCase(selfUserName, username)) {
                csdnArticleInfo.setIsMyself(1);
            }
            this.saveArticle(csdnArticleInfo);
        }
        return csdnArticleInfo;
    }

    @Override
    public Integer getScore(String articleUrl) {
        return this.getArticleScore(articleUrl);
    }

    @Override
    public void checkBlogStatus(CsdnTripletDayInfo csdnTripletDayInfo, CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo) {
        final Integer likeStatusQuery = csdnArticleInfo.getLikeStatus();
        final Integer likeStatus = csdnTripletDayInfo.getLikeStatus();
        final Integer commentStatus = csdnTripletDayInfo.getCommentStatus();
        final String articleId = csdnArticleInfo.getArticleId();
        // 处理点赞的情况
        if (LikeStatus.LIKE_IS_FULL.getCode().equals(likeStatusQuery)) {
            final Boolean like = csdnArticleLikeService.isLike(articleId, csdnUserInfo);
            if (like) {
                csdnArticleInfo.setLikeStatus(LikeStatus.HAVE_ALREADY_LIKED.getCode());
            } else if (LikeStatus.LIKE_IS_FULL.getCode().equals(likeStatus)) {
                csdnArticleInfo.setLikeStatus(LikeStatus.LIKE_IS_FULL.getCode());
            } else {
                // 如果当天是点赞成功状态,则可以点赞,文章点赞状态是未处理状态
                csdnArticleInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
            }
        }
        // 收藏
        final Boolean collect = csdnCollectService.isCollect(articleId, csdnUserInfo);
        if (collect) {
            csdnArticleInfo.setCollectStatus(CollectStatus.HAVE_ALREADY_COLLECT.getCode());
        } else if (CollectStatus.COLLECT_IS_FULL.getCode().equals(likeStatus)) {
            csdnArticleInfo.setCollectStatus(CollectStatus.COLLECT_IS_FULL.getCode());
        } else {
            csdnArticleInfo.setCollectStatus(CollectStatus.UN_PROCESSED.getCode());
        }
        // 处理评论的情况
        final Integer commentStatusQuery = csdnArticleInfo.getCommentStatus();
        if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatusQuery)
                || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatusQuery)
                || CommentStatus.COMMENT_TOO_FAST.getCode().equals(commentStatusQuery)
                || CommentStatus.COMMENT_NUM_40.getCode().equals(commentStatusQuery)) {
            // 查询是否评论过
            final Boolean comment = csdnArticleCommentService.isComment(csdnArticleInfo, csdnUserInfo);
            if (comment) {
                csdnArticleInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
            } else if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatus)
                    || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatus)
                    || CommentStatus.COMMENT_NUM_40.getCode().equals(commentStatus)) {
                csdnArticleInfo.setCommentStatus(commentStatus);
            } else {
                csdnArticleInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
            }
        }
        this.updateById(csdnArticleInfo);
    }
}