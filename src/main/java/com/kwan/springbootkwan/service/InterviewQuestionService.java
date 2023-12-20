package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.InterviewQuestion;
import com.kwan.springbootkwan.entity.dto.InterviewQuestionTypeDTO;

import java.util.List;

/**
 * 面试题(InterviewQuestion)表服务接口
 *
 * @author makejava
 * @since 2023-09-08 16:31:53
 */
public interface InterviewQuestionService extends IService<InterviewQuestion> {
    /**
     * 上传面试题
     *
     * @param path
     * @return
     */
    boolean uploadFile(String path);
    /**
     * 获取所有的类型
     *
     * @return
     */
    List<InterviewQuestionTypeDTO> allQuestionType();
}