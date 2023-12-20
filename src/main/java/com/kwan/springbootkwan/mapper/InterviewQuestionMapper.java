package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.springbootkwan.entity.InterviewQuestion;
import com.kwan.springbootkwan.entity.dto.InterviewQuestionTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;

@Mapper
public interface InterviewQuestionMapper extends BaseMapper<InterviewQuestion> {

    /**
     * 获取面试题的种类
     *
     * @return
     */
    LinkedList<InterviewQuestionTypeDTO> questionType();

    /**
     * 获取面试题的种类
     *
     * @return
     */
    LinkedList<InterviewQuestionTypeDTO> questionType(@Param("id") String id);
}

