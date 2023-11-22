package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.PicInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.query.PicInfoQuery;
import com.kwan.springbootkwan.service.PicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;


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


    @ApiOperation(value = "分页查询图片", nickname = "分页查询图片")
    @GetMapping("/page")
    public Result selectAll(@RequestParam Integer page, @RequestParam Integer pageSize
            , @RequestParam Integer picType) {
        Page<PicInfo> pageParm = new Page<>();
        pageParm.setCurrent(page);
        pageParm.setSize(pageSize);
        QueryWrapper<PicInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.eq("is_delete", 0);
        wrapper.eq("type", picType);
        return Result.ok(this.picInfoService.page(pageParm, wrapper));
    }

    @ApiOperation(value = "更新图片", nickname = "更新图片")
    @PostMapping("/update")
    public Result update(@RequestBody PicInfoQuery query) {
        final PicInfo picInfo = this.picInfoService.getById(query.getId());
        if (Objects.nonNull(picInfo)) {
            picInfo.setType(query.getType());
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

}

