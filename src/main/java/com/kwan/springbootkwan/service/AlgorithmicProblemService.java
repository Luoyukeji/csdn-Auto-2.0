package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.dto.AlgorithmicQuestionTypeDTO;

import java.util.List;


public interface AlgorithmicProblemService extends IService<AlgorithmicProblem> {

    /**
     * 根据已有数据查询类型
     *
     * @return
     */
    List<AlgorithmicQuestionTypeDTO> questionType();


    /**
     * 问题类型字典
     *
     * @return
     */
    List<AlgorithmicQuestionTypeDTO> allQuestionType();
}

