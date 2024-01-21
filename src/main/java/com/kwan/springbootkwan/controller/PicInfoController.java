package com.kwan.springbootkwan.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.PicInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.query.PicInfoQuery;
import com.kwan.springbootkwan.entity.query.PicInfoUpdate;
import com.kwan.springbootkwan.service.PicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Api(tags = "图片信息api")
@RestController
@RequestMapping("/picInfo")
public class PicInfoController {

    @Resource
    private PicInfoService picInfoService;

    @ApiOperation(value = "获取所有图片", nickname = "获取所有图片")
    @GetMapping(value = "/getAll")
    public Result getAll() {
        return Result.ok(this.picInfoService.list());
    }


    @ApiOperation(value = "根据百度图片路径获取图片", nickname = "根据百度图片路径获取图片")
    @GetMapping(value = "/insertByBaiduUrl")
    public Result insertByBaiduUrl(@RequestParam("url") String url, @RequestParam("type") String type) {
        this.picInfoService.insertByBaiduUrl(url, type);
        return Result.ok("根据百度图片路径获取图片完成");
    }

    @ApiOperation(value = "分页查询图片", nickname = "分页查询图片")
    @PostMapping("/page")
    public Result selectAll(@RequestBody PicInfoQuery query) {
        Page<PicInfo> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<PicInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.eq("is_delete", 0);
        final String type = query.getType();
        if (StringUtils.isNotEmpty(type)) {
            wrapper.eq("type", type);
        }
        final String picName = query.getPicName();
        if (StringUtils.isNotEmpty(picName)) {
            wrapper.like("pic_name", picName);
        }
        return Result.ok(this.picInfoService.page(pageParm, wrapper));
    }

    @ApiOperation(value = "更新图片", nickname = "更新图片")
    @PostMapping("/update")
    public Result update(@RequestBody PicInfoQuery query) {
        final PicInfo picInfo = this.picInfoService.getById(query.getId());
        if (Objects.nonNull(picInfo)) {
            picInfo.setType(query.getType());
            picInfo.setPicName(query.getPicName());
            this.picInfoService.updateById(picInfo);
        }
        return Result.ok("更新图片成功");
    }


    @ApiOperation(value = "上传图片", nickname = "上传图片")
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        this.picInfoService.handleFileUpload(files);
        return Result.ok("上传图片成功");
    }

    @ApiOperation(value = "批量修改图片", nickname = "批量修改图片")
    @PostMapping("/batchUpdatePic")
    public Result batchUpdatePic(@RequestBody PicInfoUpdate update) {
        final List<Integer> ids = update.getIds();
        final String type = update.getType();
        if (CollectionUtil.isNotEmpty(ids) && StringUtils.isNotEmpty(type)) {
            List<PicInfo> picInfoList = ids.stream()
                    .map(id -> picInfoService.getById(id))
                    .collect(Collectors.toList());
            picInfoList.forEach(picInfo -> picInfo.setType(type));
            picInfoService.updateBatchById(picInfoList);
        }
        return Result.ok("批量修改图片成功");
    }
}

