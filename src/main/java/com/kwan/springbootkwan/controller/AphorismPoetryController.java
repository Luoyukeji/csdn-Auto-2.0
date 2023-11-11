package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.AphorismPoetry;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.AphorismPoetryDTO;
import com.kwan.springbootkwan.entity.query.AphorismPoetryQuery;
import com.kwan.springbootkwan.service.AphorismPoetryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 名言警句(AphorismPoetry)表控制层
 *
 * @author makejava
 * @since 2023-10-09 11:13:12
 */
@RestController
@RequestMapping("aphorismPoetry")
public class AphorismPoetryController {
    /**
     * 服务对象
     */
    @Resource
    private AphorismPoetryService aphorismPoetryService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/page")
    public Result selectAll(@RequestParam Integer page
            , @RequestParam Integer pageSize
            , @RequestParam String poetryText) {
        Page<AphorismPoetry> pageParm = new Page<>();
        pageParm.setCurrent(page);
        pageParm.setSize(pageSize);
        QueryWrapper<AphorismPoetry> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(poetryText)) {
            wrapper.like("poetry_text", poetryText);
        }
        return Result.ok(AphorismPoetryDTO.Converter.INSTANCE.from(this.aphorismPoetryService.page(pageParm, wrapper)));
    }

    /**
     * 随机一题
     */
    @GetMapping("/random")
    public Result random() {
        QueryWrapper<AphorismPoetry> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByAsc("rand()").last("limit 1");
        return Result.ok(AphorismPoetryDTO.Converter.INSTANCE.from(this.aphorismPoetryService.getOne(wrapper)));
    }

    /**
     * 新增问题
     *
     * @return 所有数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody AphorismPoetryQuery poetryQuery) {
        final Integer addType = poetryQuery.getAddType();
        final String poetryText = poetryQuery.getPoetryText();
        if (StringUtils.isEmpty(poetryText)) {
            return Result.error("内容不能为空");
        }
        //批量添加
        if (addType == 1) {
            final String[] split = poetryText.split("\n");
            for (String str : split) {
                str = str.trim().replace("- ", "");
                if (StringUtils.isEmpty(str)) {
                    continue;
                }
                AphorismPoetry algorithmicProblem = new AphorismPoetry();
                QueryWrapper<AphorismPoetry> wrapper = new QueryWrapper<>();
                wrapper.eq("poetry_text", str);
                wrapper.eq("is_delete", 0);
                final AphorismPoetry one = this.aphorismPoetryService.getOne(wrapper);
                if (one == null) {
                    BeanUtils.copyProperties(poetryQuery, algorithmicProblem);
                    algorithmicProblem.setPoetryText(str);
                    this.aphorismPoetryService.save(algorithmicProblem);
                }
            }
        } else {
            AphorismPoetry aphorismPoetry = new AphorismPoetry();
            QueryWrapper<AphorismPoetry> wrapper = new QueryWrapper<>();
            wrapper.eq("poetry_text", poetryText);
            wrapper.eq("is_delete", 0);
            final AphorismPoetry one = this.aphorismPoetryService.getOne(wrapper);
            if (one == null) {
                BeanUtils.copyProperties(poetryQuery, aphorismPoetry);
                this.aphorismPoetryService.save(aphorismPoetry);
                return Result.ok();
            } else {
                return Result.error("该诗词已存在");
            }
        }
        return Result.ok();
    }

    /**
     * 更新面试题
     *
     * @param query
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody AphorismPoetryQuery query) {
        AphorismPoetry aphorismPoetry = new AphorismPoetry();
        BeanUtils.copyProperties(query, aphorismPoetry);
        return Result.ok(this.aphorismPoetryService.updateById(aphorismPoetry));
    }

    /**
     * 删除面试题
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        AphorismPoetry aphorismPoetry = new AphorismPoetry();
        aphorismPoetry.setIsDelete(1);
        QueryWrapper<AphorismPoetry> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(this.aphorismPoetryService.update(aphorismPoetry, wrapper));
    }
}

