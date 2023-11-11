package com.kwan.springbootkwan.controller;

import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "csdn其他用户给我的点赞和收藏")
@RestController
@RequestMapping("/csdn/likeCollect")
public class CsdnLikeCollectController {

    @Autowired
    private CsdnLikeCollectService csdnLikeCollectService;

    @ApiOperation(value = "获取其他用户给我的点赞和收藏信息", nickname = "获取其他用户给我的点赞和收藏信息")
    @GetMapping("/acquireLikeCollect")
    public Result acquireLikeCollect() {
        return Result.ok(csdnLikeCollectService.acquireLikeCollect());
    }

    @ApiOperation(value = "处理其他用户给我的点赞和收藏三连", nickname = "处理其他用户给我的点赞和收藏三连")
    @GetMapping("/dealLikeCollect")
    public Result dealLikeCollect() {
        csdnLikeCollectService.dealLikeCollect(csdnLikeCollectService.acquireLikeCollect());
        return Result.ok("处理其他用户给我的点赞和收藏三连完成");
    }
}