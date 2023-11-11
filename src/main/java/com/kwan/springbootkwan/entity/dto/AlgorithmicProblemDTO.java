package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.AlgorithmicProblem;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * 面试题(InterviewQuestion)表实体类
 *
 * @author makejava
 * @since 2023-09-08 16:31:53
 */
@Data
@SuppressWarnings("serial")
public class AlgorithmicProblemDTO extends Model<AlgorithmicProblemDTO> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 面试问题
     */
    private String questionName;
    /**
     * 类型
     */
    private Integer questionType;
    /**
     * 1~10的分值
     */
    private Integer degreeOfImportance;
    /**
     * 1:简单;2:中等;3:困难
     */
    private Integer degreeOfDifficulty;
    /**
     * 困难指数
     */
    private Integer difficultyOfScore;
    /**
     * 力扣的问题号
     */
    private Integer leetcodeNumber;
    /**
     * 力扣的问题链接
     */
    private String leetcodeLink;
    /**
     * 创建时间
     */
    private Date createTime;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<AlgorithmicProblemDTO, AlgorithmicProblem> {
        AlgorithmicProblemDTO.Converter INSTANCE = Mappers.getMapper(AlgorithmicProblemDTO.Converter.class);
    }
}