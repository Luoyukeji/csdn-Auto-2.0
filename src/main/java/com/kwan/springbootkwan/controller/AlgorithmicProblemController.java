package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.AlgorithmicProblemDTO;
import com.kwan.springbootkwan.entity.query.AlgorithmicProblemQuery;
import com.kwan.springbootkwan.service.AlgorithmicProblemService;
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
 * 算法题(AlgorithmicProblem)表控制层
 *
 * @author makejava
 * @since 2023-10-07 09:15:45
 */
@RestController
@RequestMapping("algorithmicProblem")
public class AlgorithmicProblemController {

    @Resource
    private AlgorithmicProblemService algorithmicProblemService;

    /**
     * 获取面试题的种类的数量
     *
     * @return
     */
    @GetMapping("/questionType")
    public Result questionType() {
        return Result.ok(this.algorithmicProblemService.questionType());
    }

    /**
     * 获取面试题的种类的数量
     *
     * @return
     */
    @GetMapping("/allQuestionType")
    public Result allQuestionType() {
        return Result.ok(this.algorithmicProblemService.allQuestionType());
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/page")
    public Result selectAll(@RequestParam Integer page
            , @RequestParam Integer pageSize
            , @RequestParam String questionName
            , @RequestParam Integer questionType) {
        Page<AlgorithmicProblem> pageParm = new Page<>();
        pageParm.setCurrent(page);
        pageParm.setSize(pageSize);
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        if (questionType != 0) {
            wrapper.eq("question_type", questionType);
        }
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(questionName)) {
            wrapper.like("question_name", questionName);
        }
        return Result.ok(AlgorithmicProblemDTO.Converter.INSTANCE.from(this.algorithmicProblemService.page(pageParm, wrapper)));
    }

    /**
     * 随机一题
     */
    @GetMapping("/random")
    public Result random() {
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByAsc("rand()").last("limit 1");
        return Result.ok(AlgorithmicProblemDTO.Converter.INSTANCE.from(this.algorithmicProblemService.getOne(wrapper)));
    }

    /**
     * 新增问题
     *
     * @return 所有数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody AlgorithmicProblemQuery addInfo) {
        final Integer addType = addInfo.getAddType();
        final String questionName = addInfo.getQuestionName();
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
                    BeanUtils.copyProperties(addInfo, algorithmicProblem);
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
                BeanUtils.copyProperties(addInfo, algorithmicProblem);
                this.algorithmicProblemService.save(algorithmicProblem);
                return Result.ok();
            } else {
                return Result.error("该面试问题已存在");
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
    public Result update(@RequestBody AlgorithmicProblemQuery query) {
        AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
        BeanUtils.copyProperties(query, algorithmicProblem);
        return Result.ok(this.algorithmicProblemService.updateById(algorithmicProblem));
    }

    /**
     * 删除面试题
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        AlgorithmicProblem algorithmicProblem = new AlgorithmicProblem();
        algorithmicProblem.setIsDelete(1);
        QueryWrapper<AlgorithmicProblem> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(this.algorithmicProblemService.update(algorithmicProblem, wrapper));
    }
}

