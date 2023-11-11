package com.kwan.springbootkwan.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmicQuestionTypeDTO {
    /**
     * 问题类型编码
     */
    private Integer questionType;
    /**
     * 问题类型名称
     */
    private String name;
    /**
     * 问题类型数量
     */
    private Integer typeSize;
}

