package com.kwan.springbootkwan.entity.query;

import lombok.Data;

@Data
public class AlgorithmicProblemQuery {
    private Integer id;
    private Integer addType;
    private String questionName;
    private Integer questionType;
    private Integer degreeOfImportance;
    private Integer degreeOfDifficulty;
    private Integer difficultyOfScore;
    private Integer leetcodeNumber;
    private String leetcodeLink;
}