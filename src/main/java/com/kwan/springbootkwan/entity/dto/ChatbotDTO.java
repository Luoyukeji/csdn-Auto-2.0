package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.Chatbot;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;


@Data
public class ChatbotDTO {

    private Integer id;
    private String question;
    private String response;
    private Date createTime;
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<ChatbotDTO, Chatbot> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}