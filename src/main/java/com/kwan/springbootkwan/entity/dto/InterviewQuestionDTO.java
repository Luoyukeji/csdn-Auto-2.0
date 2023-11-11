package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.InterviewQuestion;
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
public class InterviewQuestionDTO extends Model<InterviewQuestionDTO> {
    //主键id
    private Integer id;
    //面试问题
    private String question;
    //问题回答
    private String response;
    //知识类型,先默认0,后面再区分
    private Integer questionType;
    //创建时间
    private Date createTime;
    //逻辑删除,0未删除,1已删除
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<InterviewQuestionDTO, InterviewQuestion> {
        InterviewQuestionDTO.Converter INSTANCE = Mappers.getMapper(InterviewQuestionDTO.Converter.class);
    }
}

