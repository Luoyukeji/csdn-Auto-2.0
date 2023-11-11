package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.dto.AlgorithmicQuestionTypeDTO;

import java.util.List;

/**
 * 算法题(AlgorithmicProblem)表服务接口
 *
 * @author makejava
 * @since 2023-10-07 09:15:47
 */
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

