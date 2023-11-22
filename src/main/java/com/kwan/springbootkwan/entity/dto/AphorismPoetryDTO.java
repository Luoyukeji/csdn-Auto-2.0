package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.AphorismPoetry;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;


/**
 * 诗词实体类
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/10/9 11:19
 */
@Data
@SuppressWarnings("serial")
public class AphorismPoetryDTO extends Model<AphorismPoetryDTO> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 诗词内容
     */
    private String poetryText;
    /**
     * 创建时间
     */
    private Date createTime;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<AphorismPoetryDTO, AphorismPoetry> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}