package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.entity.DictionaryManagement;
import com.kwan.springbootkwan.entity.dto.AlgorithmicQuestionTypeDTO;
import com.kwan.springbootkwan.mapper.AlgorithmicProblemMapper;
import com.kwan.springbootkwan.service.AlgorithmicProblemService;
import com.kwan.springbootkwan.service.DictionaryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlgorithmicProblemServiceImpl extends ServiceImpl<AlgorithmicProblemMapper, AlgorithmicProblem> implements AlgorithmicProblemService {

    @Autowired
    private AlgorithmicProblemMapper interviewQuestionMapper;
    @Autowired
    private DictionaryManagementService dictionaryManagementService;

    @Override
    public List<AlgorithmicQuestionTypeDTO> questionType() {
        final List<AlgorithmicQuestionTypeDTO> algorithmicQuestionTypeDTOS = this.allQuestionType();
        //获取种类,并按数量排序
        LinkedList<AlgorithmicQuestionTypeDTO> types = interviewQuestionMapper.questionType();
        types.addFirst(new AlgorithmicQuestionTypeDTO(0, "全部", 0));
        for (AlgorithmicQuestionTypeDTO algorithmicQuestionTypeDTO : types) {
            //数据库存的是问题类型的编码
            final AlgorithmicQuestionTypeDTO item = algorithmicQuestionTypeDTOS.stream().filter(x -> x.getQuestionType().equals(algorithmicQuestionTypeDTO.getQuestionType())).findFirst().get();
            algorithmicQuestionTypeDTO.setName(item.getName());
        }
        return types;
    }

    @Override
    public List<AlgorithmicQuestionTypeDTO> allQuestionType() {
        QueryWrapper<DictionaryManagement> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", 2);
        wrapper.eq("is_delete", 0);
        final List<DictionaryManagement> list = dictionaryManagementService.list(wrapper);
        return list.stream()
                .map(item -> {
                    AlgorithmicQuestionTypeDTO algorithmicQuestionTypeDTO = new AlgorithmicQuestionTypeDTO();
                    algorithmicQuestionTypeDTO.setQuestionType(item.getCode());
                    algorithmicQuestionTypeDTO.setName(item.getName());
                    return algorithmicQuestionTypeDTO;
                }).collect(Collectors.toList());
    }
}

