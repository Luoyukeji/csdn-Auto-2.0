package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.Chatbot;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.ChatbotDTO;
import com.kwan.springbootkwan.service.ChatbotService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "chatgpt问答查询api")
@RestController
@RequestMapping("chatbot")
public class ChatbotController {

    @Resource
    private ChatbotService chatbotService;

    @GetMapping
    public Result selectAll() {

        List<Chatbot> list = this.chatbotService.list();
        list = list.stream().sorted(Comparator.comparing(Chatbot::getId).reversed()).collect(Collectors.toList());
        return Result.ok(list);
    }

//    @Cacheable("chatbot-cache")
    @GetMapping("/page")
    public Result selectAll(@RequestParam Integer page
            , @RequestParam Integer pageSize
            , @RequestParam String question) {
        Page<Chatbot> pageParm = new Page<>();
        pageParm.setCurrent(page);
        pageParm.setSize(pageSize);
        QueryWrapper<Chatbot> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(question)) {
            wrapper.like("question", question);
        }
        return Result.ok(ChatbotDTO.Converter.INSTANCE.from(this.chatbotService.page(pageParm, wrapper)));
    }

    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.ok(this.chatbotService.getById(id));
    }

    @PostMapping
    public Result insert(@RequestBody Chatbot chatbot) {
        return Result.ok(this.chatbotService.save(chatbot));
    }

    @PutMapping
    public Result update(@RequestBody Chatbot chatbot) {
        return Result.ok(this.chatbotService.updateById(chatbot));
    }


    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.ok(this.chatbotService.removeByIds(idList));
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        Chatbot chatbot = new Chatbot();
        chatbot.setIsDelete(1);
        QueryWrapper<Chatbot> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(chatbotService.update(chatbot, wrapper));
    }
}