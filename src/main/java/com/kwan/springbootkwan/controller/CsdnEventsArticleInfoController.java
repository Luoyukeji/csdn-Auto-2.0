package com.kwan.springbootkwan.controller;


import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.service.CsdnEventsArticleInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * csdn2023活动文章详细信息(CsdnEventsArticleInfo)表控制层
 *
 * @author makejava
 * @since 2024-01-16 16:43:11
 */
@RestController
@RequestMapping("csdnEventsArticleInfo")
public class CsdnEventsArticleInfoController {

    @Resource
    private CsdnEventsArticleInfoService csdnEventsArticleInfoService;

    @ApiOperation(value = "保存文章", nickname = "保存文章")
    @GetMapping("/savaArticle")
    public Result savaArticle() {
        csdnEventsArticleInfoService.savaArticle();
        return Result.ok("保存文章完成");
    }

}

