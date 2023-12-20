package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.DictionaryManagement;
import com.kwan.springbootkwan.entity.InterviewQuestion;
import com.kwan.springbootkwan.entity.dto.InterviewQuestionTypeDTO;
import com.kwan.springbootkwan.mapper.InterviewQuestionMapper;
import com.kwan.springbootkwan.service.DictionaryManagementService;
import com.kwan.springbootkwan.service.InterviewQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 面试题(InterviewQuestion)表服务实现类
 *
 * @author makejava
 * @since 2023-09-08 16:31:53
 */
@Slf4j
@Service
public class InterviewQuestionServiceImpl extends ServiceImpl<InterviewQuestionMapper, InterviewQuestion> implements InterviewQuestionService {

    @Resource
    private InterviewQuestionMapper interviewQuestionMapper;
    @Resource
    private DictionaryManagementService dictionaryManagementService;

    @Override
    public boolean uploadFile(String path) {
        log.info("uploadFile() called with: path= {}", path);
        // 定义文件路径
        Path filePath = Paths.get(path);
        try {
            // 读取文件的所有行到List对象
            List<String> lines = Files.readAllLines(filePath);
            // 遍历List对象并输出每一行
            for (String line : lines) {
                QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
                wrapper.eq("question", line);// 按照 age 字段降序排列
                final InterviewQuestion one = this.getOne(wrapper);
                if (one == null) {
                    log.info("uploadFile() called with: question= {}", line);
                    InterviewQuestion interviewQuestion = new InterviewQuestion();
                    interviewQuestion.setQuestion(line);
                    interviewQuestion.setResponse("");
                    this.save(interviewQuestion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<InterviewQuestionTypeDTO> allQuestionType() {
        QueryWrapper<DictionaryManagement> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", 1);
        wrapper.eq("is_delete", 0);
        final List<DictionaryManagement> list = dictionaryManagementService.list(wrapper);
        return list.stream()
                .map(item -> {
                    InterviewQuestionTypeDTO interviewQuestionTypeDTO = new InterviewQuestionTypeDTO();
                    interviewQuestionTypeDTO.setQuestionType(item.getCode());
                    interviewQuestionTypeDTO.setName(item.getName());
                    return interviewQuestionTypeDTO;
                }).collect(Collectors.toList());
    }
}