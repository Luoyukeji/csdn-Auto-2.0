package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.Chatbot;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.ChatbotDTO;
import com.kwan.springbootkwan.service.ChatbotService;
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


/**
 * (Chatbot)表控制层
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/7/11 18:04
 */
@RestController
@RequestMapping("chatbot")
public class ChatbotController {
    /**
     * 服务对象
     */
    @Resource
    private ChatbotService chatbotService;

    /**
     * 获取所有数据
     *
     * @return
     */
    @GetMapping
    public Result selectAll() {

        List<Chatbot> list = this.chatbotService.list();
        list = list.stream().sorted(Comparator.comparing(Chatbot::getId).reversed()).collect(Collectors.toList());
        return Result.ok(list);
    }


    /**
     * 分页查询所有数据,本地缓存的使用
     *
     * @return 所有数据
     */
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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.ok(this.chatbotService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param chatbot 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody Chatbot chatbot) {
        return Result.ok(this.chatbotService.save(chatbot));
    }

    /**
     * 修改数据
     *
     * @param chatbot 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody Chatbot chatbot) {
        return Result.ok(this.chatbotService.updateById(chatbot));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
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

