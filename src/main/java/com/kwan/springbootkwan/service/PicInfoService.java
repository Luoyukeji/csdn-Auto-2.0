package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.PicInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片信息表(PicInfo)表服务接口
 *
 * @author makejava
 * @since 2023-08-09 12:44:03
 */
public interface PicInfoService extends IService<PicInfo> {
    /**
     * 根据名称查询图片
     *
     * @param picName
     * @return
     */
    PicInfo getPicByName(String picName);

    /**
     * 根据目录插入图片
     *
     * @param path
     * @param type
     * @return
     */
    boolean insertByPath(String path, Integer type);

    /**
     * 根据百度图片路径获取图片
     *
     * @param url
     * @param type
     * @return
     */
    boolean insertByBaiduUrl(String url, Integer type);

    /**
     * 上传图片
     *
     * @param file
     */
    void handleFileUpload(MultipartFile[] file);
}

