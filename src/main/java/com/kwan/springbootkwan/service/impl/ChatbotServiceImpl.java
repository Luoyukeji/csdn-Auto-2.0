package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.mapper.ChatbotMapper;
import com.kwan.springbootkwan.entity.Chatbot;
import com.kwan.springbootkwan.service.ChatbotService;
import org.springframework.stereotype.Service;


@Service
public class ChatbotServiceImpl extends ServiceImpl<ChatbotMapper, Chatbot> implements ChatbotService {

}

