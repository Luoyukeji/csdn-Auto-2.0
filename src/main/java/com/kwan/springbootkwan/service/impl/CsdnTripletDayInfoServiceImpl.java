package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.mapper.CsdnTripletDayInfoMapper;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class CsdnTripletDayInfoServiceImpl extends ServiceImpl<CsdnTripletDayInfoMapper, CsdnTripletDayInfo> implements CsdnTripletDayInfoService {
    @Override
    public CsdnTripletDayInfo todayInfo() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String formattedDate = sdf.format(currentDate);
        log.info("当前日期是:{}", formattedDate);
        QueryWrapper<CsdnTripletDayInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("triplet_date", formattedDate);
        final CsdnTripletDayInfo one = this.getOne(wrapper);
        if (Objects.isNull(one)) {
            CsdnTripletDayInfo csdnTripletDayInfo = new CsdnTripletDayInfo();
            csdnTripletDayInfo.setTripletDate(new Date());
            csdnTripletDayInfo.setUpdateTime(new Date());
            this.save(csdnTripletDayInfo);
        }
        return one;
    }
}

