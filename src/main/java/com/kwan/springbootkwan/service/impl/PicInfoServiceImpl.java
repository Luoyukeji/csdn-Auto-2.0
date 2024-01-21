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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


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
    public boolean insertByPath(String path, String type) {
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
    private void insertPic(String type, String picPath, String picName) {
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
    public boolean insertByBaiduUrl(String url, String type) {
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
                    pic.setPicUrl("http://43.139.90.182/img/" + file.getOriginalFilename());
                    pic.setType("宝宝");
                    this.save(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {

    }

    private static long getUUIDRandomPart(UUID uuid) {
        // 获取UUID的128位数值中的高64位作为随机数部分
        return uuid.getMostSignificantBits();
    }

    private MultipartFile convert(String imageUrl) throws IOException {
        // 使用RestTemplate获取图片字节数组
        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
        // 将字节数组封装为ByteArrayResource
        ByteArrayResource byteArrayResource = new ByteArrayResource(Objects.requireNonNull(imageBytes));
        // 创建MockMultipartFile对象
        // 生成UUID
        UUID uuid = UUID.randomUUID();
        // 提取UUID中的随机数部分
        long randomPart = getUUIDRandomPart(uuid);
        return new MockMultipartFile("file", randomPart + ".jpg", "image/jpeg", byteArrayResource.getInputStream());
    }

    @Override
    public String uploadByUrl(String imgUrl) {
        try {
            final MultipartFile file = this.convert(imgUrl);
            log.info("handleFileUpload() called with: file= {}", file.getOriginalFilename());
            if (file.isEmpty()) {
                return null;
            }
            String fileNameWithoutExtension = this.getFileNameWithoutExtension(file.getOriginalFilename());
            PicInfo pic = this.getPicByName(fileNameWithoutExtension);
            if (Objects.isNull(pic)) {
                try {
                    String uploadDir = "/kwan/img/";
//                    String uploadDir = "/Users/qinyingjie/Downloads/";
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File serverFile = new File(uploadDir + file.getOriginalFilename());
                    file.transferTo(serverFile);
                    return "http://43.139.90.182/img/" + file.getOriginalFilename();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

