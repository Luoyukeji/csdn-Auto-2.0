package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.dto.AlgorithmicQuestionTypeDTO;

import java.util.LinkedList;

/**
 * 算法题(AlgorithmicProblem)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-07 09:15:45
 */
public interface AlgorithmicProblemMapper extends BaseMapper<AlgorithmicProblem> {

    LinkedList<AlgorithmicQuestionTypeDTO> questionType();
}

