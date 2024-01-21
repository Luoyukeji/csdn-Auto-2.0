package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import lombok.Data;

import java.util.Set;

@Data
public class AlgorithmicProblemQuery extends BasePage {
    private Integer id;
    private Integer addType;
    private String questionName;
    private String questionType;
    private Integer degreeOfImportance;
    private Integer degreeOfDifficulty;
    private Integer difficultyOfScore;
    private Integer leetcodeNumber;
    private String leetcodeLink;
    private Set<String> tag;
}