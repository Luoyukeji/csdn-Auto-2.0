package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.mapper.CsdnTripletDayInfoMapper;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        QueryWrapper<CsdnTripletDayInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("triplet_date", formattedDate);
        CsdnTripletDayInfo csdnTripletDayInfo = this.getOne(wrapper);
        if (Objects.isNull(csdnTripletDayInfo)) {
            csdnTripletDayInfo = new CsdnTripletDayInfo();
            csdnTripletDayInfo.setTripletDate(new Date());
            // 获取今天的星期
            Week dayOfWeek = DateUtil.dayOfWeekEnum(DateUtil.date());
            // 获取大写的星期几
            String uppercaseDayOfWeek = dayOfWeek.toChinese("星期");
            csdnTripletDayInfo.setWeekInfo(uppercaseDayOfWeek);
            this.save(csdnTripletDayInfo);
        }
        return csdnTripletDayInfo;
    }

    private List<String> days() {
        List<String> dates = new ArrayList<>();
        final DateTime yesterdayDate = DateUtil.yesterday();
        final String yesterday = DateUtil.formatDate(yesterdayDate);
        dates.add(yesterday);
        for (int i = 1; i < 29; i++) {
            final DateTime dateTime = DateUtil.offsetDay(yesterdayDate, -i);
            dates.add(DateUtil.formatDate(dateTime));
        }
        return dates;
    }


    @Override
    public void addAll() {
        final List<String> days = this.days();
        for (int i = days.size() - 1; i >= 0; i--) {
            final String dateStr = days.get(i);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateStr);
                QueryWrapper<CsdnTripletDayInfo> wrapper = new QueryWrapper<>();
                wrapper.eq("is_delete", 0);
                wrapper.eq("triplet_date", dateStr);
                final CsdnTripletDayInfo one = this.getOne(wrapper);
                if (Objects.isNull(one)) {
                    CsdnTripletDayInfo csdnTripletDayInfo = new CsdnTripletDayInfo();
                    csdnTripletDayInfo.setTripletDate(date);
                    Week dayOfWeek = DateUtil.dayOfWeekEnum(date);
                    String uppercaseDayOfWeek = dayOfWeek.toChinese("星期");
                    csdnTripletDayInfo.setWeekInfo(uppercaseDayOfWeek);
                    this.save(csdnTripletDayInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resetWeekInfo() {
        QueryWrapper<CsdnTripletDayInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        final List<CsdnTripletDayInfo> list = this.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnTripletDayInfo csdnTripletDayInfo : list) {
                final Date tripletDate = csdnTripletDayInfo.getTripletDate();
                // 获取今天的星期
                Week dayOfWeek = DateUtil.dayOfWeekEnum(tripletDate);
                // 获取大写的星期几
                String week = dayOfWeek.toChinese("星期");
                csdnTripletDayInfo.setWeekInfo(week);
                this.updateById(csdnTripletDayInfo);
            }
        }
    }
}

