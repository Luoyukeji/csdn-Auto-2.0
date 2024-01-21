package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.InterviewQuestion;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.InterviewQuestionDTO;
import com.kwan.springbootkwan.entity.query.InterviewQuestionAdd;
import com.kwan.springbootkwan.entity.query.InterviewQuestionUpdate;
import com.kwan.springbootkwan.service.InterviewQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "面试题api")
@RestController
@RequestMapping("interviewQuestion")
public class InterviewQuestionController {

    @Resource
    private InterviewQuestionService interviewQuestionService;

    @ApiOperation(value = "获取面试题", nickname = "获取面试题")
    @GetMapping("/page")
    public Result selectAll(@RequestParam Integer page
            , @RequestParam Integer pageSize
            , @RequestParam String question
            , @RequestParam String questionType) {
        Page<InterviewQuestion> pageParm = new Page<>();
        pageParm.setCurrent(page);
        pageParm.setSize(pageSize);
        QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        if (StringUtils.isNotEmpty(questionType)) {
            wrapper.eq("question_type", questionType);
        }
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(question)) {
            wrapper.like("question", question);
        }
        return Result.ok(InterviewQuestionDTO.Converter.INSTANCE.from(this.interviewQuestionService.page(pageParm, wrapper)));
    }

    /**
     * 新增问题
     *
     * @return 所有数据
     */
    @PostMapping("/add")
    public Result add(@RequestBody InterviewQuestionAdd addInfo) {
        final Integer addType = addInfo.getAddType();
        final String question = addInfo.getQuestion();
        if (StringUtils.isEmpty(question)) {
            return Result.error("问题不能为空");
        }
        //批量添加
        if (addType == 1) {
            final String[] split = question.split("\n");
            for (String str : split) {
                str = str.trim().replace("- ", "");
                if (StringUtils.isEmpty(str)) {
                    continue;
                }
                InterviewQuestion interviewQuestion = new InterviewQuestion();
                QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
                wrapper.eq("question", str);
                wrapper.eq("is_delete", 0);
                final InterviewQuestion one = this.interviewQuestionService.getOne(wrapper);
                if (one == null) {
                    interviewQuestion.setQuestion(str);
                    interviewQuestion.setQuestionType(addInfo.getQuestionType());
                    this.interviewQuestionService.save(interviewQuestion);
                }
            }
        } else {
            InterviewQuestion interviewQuestion = new InterviewQuestion();
            QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
            wrapper.eq("question", question);
            wrapper.eq("is_delete", 0);
            final InterviewQuestion one = this.interviewQuestionService.getOne(wrapper);
            if (one == null) {
                interviewQuestion.setQuestion(question);
                interviewQuestion.setQuestionType(addInfo.getQuestionType());
                this.interviewQuestionService.save(interviewQuestion);
                return Result.ok();
            } else {
                return Result.error("该面试问题已存在");
            }
        }
        return Result.ok();
    }
    @ApiOperation(value = "导入问题", nickname = "导入问题")
    @GetMapping("/upload")
    public Result uploadFile(@RequestParam String path) {
        return Result.ok(this.interviewQuestionService.uploadFile(path));
    }
    /**
     * 更新面试题
     *
     * @param addInfo
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody InterviewQuestionUpdate addInfo) {
        InterviewQuestion interviewQuestion = new InterviewQuestion();
        interviewQuestion.setId(addInfo.getId());
        interviewQuestion.setQuestion(addInfo.getQuestion());
        interviewQuestion.setQuestionType(addInfo.getQuestionType());
        return Result.ok(this.interviewQuestionService.updateById(interviewQuestion));
    }


    /**
     * 删除面试题
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        InterviewQuestion interviewQuestion = new InterviewQuestion();
        interviewQuestion.setIsDelete(1);
        QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(this.interviewQuestionService.update(interviewQuestion, wrapper));
    }
}

