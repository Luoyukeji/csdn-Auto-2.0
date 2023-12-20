package com.kwan.springbootkwan.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.PicInfo;
import com.kwan.springbootkwan.entity.pic.BaiduPic;
import com.kwan.springbootkwan.entity.pic.PicDataInfo;
import com.kwan.springbootkwan.mapper.PicInfoMapper;
import com.kwan.springbootkwan.service.PicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * 图片信息表(PicInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-08-09 12:44:03
 */
@Slf4j
@Service
public class PicInfoServiceImpl extends ServiceImpl<PicInfoMapper, PicInfo> implements PicInfoService {

    @Autowired
    private PicInfoMapper picInfoMapper;
    /**
     * 图片路径前缀
     */
    private static final String PRE_FIX = "https://gitcode.net/qyj19920704/baby-images/-/raw/main/";

    @Override
    public boolean insertByPath(String path, Integer type) {
        Path directoryPath = Paths.get(path);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    final String picNameOrigin = filePath.getFileName().toString();
                    String picPath = PRE_FIX + picNameOrigin;
                    final String[] split = picNameOrigin.split(".");
                    String picName = picNameOrigin;
                    if (Objects.nonNull(split) && split.length > 0) {
                        picName = split[0];
                    }
                    this.insertPic(type, picPath, picName);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 插入图片
     *
     * @param picPath
     * @param picName
     */
    private void insertPic(Integer type, String picPath, String picName) {
        QueryWrapper<PicInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("pic_url", picPath);
        wrapper.eq("is_delete", 0);
        final PicInfo pic = picInfoMapper.selectOne(wrapper);
        if (Objects.nonNull(pic)) {
            return;
        }
        PicInfo picInfo = new PicInfo();
        picInfo.setPicName(picName);
        picInfo.setPicUrl(picPath);
        picInfo.setType(type);
        picInfoMapper.insert(picInfo);
    }

    @Override
    public boolean insertByBaiduUrl(String url, Integer type) {
        HttpResponse response;
        try {
            response = HttpUtil.createGet(url).execute();
            String responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            BaiduPic baiduPic = objectMapper.readValue(responseBody, BaiduPic.class);
            final List<PicDataInfo> data = baiduPic.getData();
            for (PicDataInfo datum : data) {
                final String hoverURL = datum.getHoverURL();
                final String fromPageTitle = datum.getFromPageTitle();
                if (StringUtils.isNotEmpty(fromPageTitle)) {
                    String[] split = fromPageTitle.split(".");
                    if (Objects.isNull(split) || split.length == 0) {
                        split = fromPageTitle.split(",");
                    }
                    this.insertPic(type, hoverURL, Objects.isNull(split) || split.length == 0 ? fromPageTitle : split[0]);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void handleFileUpload(MultipartFile[] files) {
        for (MultipartFile file : files) {
            log.info("handleFileUpload() called with: file= {}", file.getOriginalFilename());
            if (file.isEmpty()) {
                return;
            }
            String fileNameWithoutExtension = this.getFileNameWithoutExtension(file.getOriginalFilename());
            PicInfo pic = this.getPicByName(fileNameWithoutExtension);
            if (Objects.isNull(pic)) {
                try {
                    String uploadDir = "/kwan/img/";
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File serverFile = new File(uploadDir + file.getOriginalFilename());
                    file.transferTo(serverFile);
                    pic = new PicInfo();
                    pic.setPicName(fileNameWithoutExtension);
                    pic.setPicUrl("https://www.qinyingjie.top/img/" + file.getOriginalFilename());
                    pic.setType(0);
                    this.save(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        }
    }

    private String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        } else {
            return fileName;
        }
    }

    @Override
    public PicInfo getPicByName(String picName) {
        QueryWrapper<PicInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("pic_name", picName);
        wrapper.eq("is_delete", 0);
        return picInfoMapper.selectOne(wrapper);
    }
}

