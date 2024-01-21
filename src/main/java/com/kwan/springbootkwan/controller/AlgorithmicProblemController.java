package com.kwan.springbootkwan.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.AlgorithmicProblemDTO;
import com.kwan.springbootkwan.entity.query.AlgorithmicProblemQuery;
import com.kwan.springbootkwan.service.AlgorithmicProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

@Api(tags = "算法题api")
@RestController
@RequestMapping("algorithmicProblem")
public class AlgorithmicProblemController {

    @Resource
    private AlgorithmicProblemService algorithmicProblemService;

    @ApiOperation(value = "获取算法题", nickname = "获取算法题")
    @PostMapping("/page")
    public Result selectAll(@RequestBody AlgorithmicProblemQuery query) {
        Page<AlgorithmicProblem> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("id");
        final String questionType = query.getQuestionType();
        if (StringUtils.isNotEmpty(questionType)) {
            wrapper.eq("question_type", questionType);
        }
        final String questionName = query.getQuestionName();
        if (StringUtils.isNotEmpty(questionName)) {
            wrapper.like("question_name", questionName);
        }
        final Set<String> tag = query.getTag();
        if (CollectionUtil.isNotEmpty(tag)) {
            wrapper.and(w -> {
                for (String tagItem : tag) {
                    w.or().like("tag", tagItem);
                }
            });
        }
        return Result.ok(AlgorithmicProblemDTO.Converter.INSTANCE.from(this.algorithmicProblemService.page(pageParm, wrapper)));
    }

    @ApiOperation(value = "随机一题", nickname = "随机一题")
    @GetMapping("/random")
    public Result random() {
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByAsc("rand()").last("limit 1");
        return Result.ok(AlgorithmicProblemDTO.Converter.INSTANCE.from(this.algorithmicProblemService.getOne(wrapper)));
    }

    @PostMapping("/add")
    public Result add(@RequestBody AlgorithmicProblemQuery query) {
        final Integer addType = query.getAddType();
        final String questionName = query.getQuestionName();
        if (StringUtils.isEmpty(questionName)) {
            return Result.error("问题不能为空");
        }
        //批量添加
        if (addType == 1) {
            final String[] split = questionName.split("\n");
            for (String str : split) {
                str = str.trim().replace("- ", "");
                if (StringUtils.isEmpty(str)) {
                    continue;
                }
                AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
                QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
                wrapper.eq("question_name", str);
                wrapper.eq("is_delete", 0);
                final AlgorithmicProblem one = this.algorithmicProblemService.getOne(wrapper);
                if (one == null) {
                    BeanUtils.copyProperties(query, algorithmicProblem);
                    algorithmicProblem.setQuestionName(str);
                    this.algorithmicProblemService.save(algorithmicProblem);
                }
            }
        } else {
            AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
            QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
            wrapper.eq("question_name", questionName);
            wrapper.eq("is_delete", 0);
            final AlgorithmicProblem one = this.algorithmicProblemService.getOne(wrapper);
            if (one == null) {
                BeanUtils.copyProperties(query, algorithmicProblem);
                final Set<String> tag = query.getTag();
                if (CollectionUtil.isNotEmpty(tag)) {
                    algorithmicProblem.setTag(StringUtils.join(tag, ","));
                }
                this.algorithmicProblemService.save(algorithmicProblem);
                return Result.ok();
            } else {
                return Result.error("该面试问题已存在");
            }
        }
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody AlgorithmicProblemQuery query) {
        AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
        BeanUtils.copyProperties(query, algorithmicProblem);
        final Set<String> tag = query.getTag();
        if (CollectionUtil.isNotEmpty(tag)) {
            algorithmicProblem.setTag(StringUtils.join(tag, ","));
        }
        return Result.ok(this.algorithmicProblemService.updateById(algorithmicProblem));
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
        algorithmicProblem.setIsDelete(1);
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(this.algorithmicProblemService.update(algorithmicProblem, wrapper));
    }
}

