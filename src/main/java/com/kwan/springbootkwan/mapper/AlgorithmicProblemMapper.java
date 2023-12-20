package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.dto.AlgorithmicQuestionTypeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedList;

@Mapper
public interface AlgorithmicProblemMapper extends BaseMapper<AlgorithmicProblem> {

    LinkedList<AlgorithmicQuestionTypeDTO> questionType();
}

