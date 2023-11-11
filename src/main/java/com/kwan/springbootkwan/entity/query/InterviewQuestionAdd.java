package com.kwan.springbootkwan.entity.query;

import lombok.Data;

@Data
public class InterviewQuestionAdd {
    private Integer addType;
    private String question;
    private Integer questionType;
}
