package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnAccountManagement;
import com.kwan.springbootkwan.entity.csdn.CsdnAccountInfoResponse;
import com.kwan.springbootkwan.entity.csdn.CurrentAmount;
import com.kwan.springbootkwan.entity.dto.CsdnAccountTotalDTO;
import com.kwan.springbootkwan.mapper.CsdnAccountManagementMapper;
import com.kwan.springbootkwan.service.CsdnAccountManagementService;
import com.kwan.springbootkwan.utils.GetNonceUtil;
import com.kwan.springbootkwan.utils.GetSignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class CsdnAccountManagementServiceImpl extends ServiceImpl<CsdnAccountManagementMapper, CsdnAccountManagement> implements CsdnAccountManagementService {

    @Value("${csdn.cookie}")
    private String csdnCookie;

    @Autowired
    private CsdnAccountManagementMapper csdnAccountManagementMapper;

    @Override
    public CsdnAccountManagement getAccountInfo(String orderNo) {
        QueryWrapper<CsdnAccountManagement> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("order_no", orderNo)
                .last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public void addAccountInfo() {
        for (int pageNum = 100; pageNum >= 1; pageNum--) {
            this.dealCsdnAccountInfo(pageNum);
        }
    }

    @Override
    public void add5AccountInfo() {
        for (int pageNum = 5; pageNum >= 1; pageNum--) {
            this.dealCsdnAccountInfo(pageNum);
        }
    }

    @Override
    public CurrentAmount.CurrentAmountData.CurrentAmountDetail currentAmount() {
        try {
            String host = "https://bizapi.csdn.net";
            String path = "/mall/mp/mallorder/api/internal/wallet/polymerize";
            final String onceKey = GetNonceUtil.onceKey();
            HttpResponse httpResponse = HttpUtil.createGet(host + path)
                    .header("Cookie", csdnCookie)
                    .header("X-Ca-Nonce", onceKey)
                    .header("X-Ca-Signature", GetSignatureUtil.sign(path, "get", onceKey, CommonConstant.AccountInfo.X_CA_KEY, CommonConstant.AccountInfo.E_KEY))
                    .header("X-Ca-Key", CommonConstant.AccountInfo.X_CA_KEY)
                    .header("sec-ch-ua-platform", "\"macOS\"")
                    .header("Sec-Fetch-Site", "same-site")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("host", "bizapi.csdn.net")
                    .header("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .header("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                    .header("Connection", "keep-alive")
                    .header("origin", "https://blink.csdn.net")
                    .header("referer", "https://i.csdn.net/")
                    .header("sec-ch-ua-mobile", "?0")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                    .header("Accept", "application/json, text/plain, */*")
                    .execute();
            final String receiveListBody = httpResponse.body();
            log.info("currentAmount() called {}", receiveListBody);
            ObjectMapper objectMapper = new ObjectMapper();
            final CurrentAmount.CurrentAmountData data = objectMapper.readValue(receiveListBody, CurrentAmount.class).getData();
            return data.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CsdnAccountTotalDTO> totalInfo() {
        return csdnAccountManagementMapper.totalInfo();
    }

    /**
     * 处理系统的余额信息
     *
     * @param pageNum
     */
    private void dealCsdnAccountInfo(int pageNum) {
        try {
            String host = "https://bizapi.csdn.net";
            String path = "/mall/mp/mallorder/api/internal/wallet/balance/history?endMonthTime&pageNum=" + pageNum + "&pageSize=10&startMonthTime&type=0";
            final String onceKey = GetNonceUtil.onceKey();
            HttpResponse httpResponse = HttpUtil.createGet(host + path)
                    .header("Cookie", csdnCookie)
                    .header("X-Ca-Nonce", onceKey)
                    .header("X-Ca-Signature", GetSignatureUtil.sign(path, "get", onceKey, CommonConstant.AccountInfo.X_CA_KEY, CommonConstant.AccountInfo.E_KEY))
                    .header("X-Ca-Key", CommonConstant.AccountInfo.X_CA_KEY)
                    .header("sec-ch-ua-platform", "\"macOS\"")
                    .header("Sec-Fetch-Site", "same-site")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("host", "bizapi.csdn.net")
                    .header("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .header("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"")
                    .header("Connection", "keep-alive")
                    .header("origin", "https://blink.csdn.net")
                    .header("referer", "https://i.csdn.net/")
                    .header("sec-ch-ua-mobile", "?0")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36")
                    .header("Accept", "application/json, text/plain, */*")
                    .execute();
            final String receiveListBody = httpResponse.body();
            ObjectMapper objectMapper = new ObjectMapper();
            final CsdnAccountInfoResponse.CsdnAccountInfoData data = objectMapper.readValue(receiveListBody, CsdnAccountInfoResponse.class).getData();
            final List<CsdnAccountInfoResponse.CsdnAccountInfoData.DetailData> detailData = data.getData();
            if (CollectionUtil.isNotEmpty(detailData)) {
                for (int i = detailData.size() - 1; i >= 0; i--) {
                    CsdnAccountInfoResponse.CsdnAccountInfoData.DetailData detailDatum = detailData.get(i);
                    final String orderNo = detailDatum.getOrderNo();
                    if (StringUtils.isNotEmpty(orderNo)) {
                        CsdnAccountManagement accountInfo = this.getAccountInfo(orderNo);
                        if (Objects.isNull(accountInfo)) {
                            accountInfo = new CsdnAccountManagement();
                            BeanUtils.copyProperties(detailDatum, accountInfo);
                            accountInfo.setAmount(new BigDecimal(detailDatum.getAmount()));
                            this.save(accountInfo);
                        } else {
                            BeanUtils.copyProperties(detailDatum, accountInfo);
                            accountInfo.setAmount(new BigDecimal(detailDatum.getAmount()));
                            this.updateById(accountInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

