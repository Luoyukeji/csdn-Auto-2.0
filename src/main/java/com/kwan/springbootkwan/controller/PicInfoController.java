package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.PicInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.service.PicInfoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 图片信息表(PicInfo)表控制层
 *
 * @author makejava
 * @since 2023-08-09 12:44:02
 */
@RestController
@RequestMapping("picInfo")
public class PicInfoController {
    /**
     * 服务对象
     */
    @Resource
    private PicInfoService picInfoService;

    @GetMapping(value = "/getAll")
    public Result getAll() {
        return Result.ok(this.picInfoService.list());
    }

    /**
     * 分页查询图片
     *
     * @return 所有数据
     */
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

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param picInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<PicInfo> page, PicInfo picInfo) {
        return Result.ok(this.picInfoService.page(page, new QueryWrapper<>(picInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.ok(this.picInfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param picInfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody PicInfo picInfo) {
        return Result.ok(this.picInfoService.save(picInfo));
    }


    /**
     * 新增图片
     *
     * @param path
     * @return
     */
    @PostMapping(value = "/insertByPath")
    public Result insertByPath(@RequestParam String path, @RequestParam Integer type) {
        return Result.ok(this.picInfoService.insertByPath(path, type));
    }


    /**
     * 通过url新增图片
     *
     * @param url
     * @return
     */
    @PostMapping(value = "/insertByBaiduUrl")
    public Result insertByBaiduUrl(@RequestParam String url, @RequestParam Integer type) {
        return Result.ok(this.picInfoService.insertByBaiduUrl(url, type));
    }


    /**
     * 修改数据
     *
     * @param picInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody PicInfo picInfo) {
        return Result.ok(this.picInfoService.updateById(picInfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.ok(this.picInfoService.removeByIds(idList));
    }
}

