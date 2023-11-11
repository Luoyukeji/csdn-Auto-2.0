package com.kwan.springbootkwan.controller;

import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.csdn.MessageResponse;
import com.kwan.springbootkwan.service.CsdnMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "csdn私信")
@RestController
@RequestMapping("/csdn/message")
public class CsdnMessageController {

    @Autowired
    private CsdnMessageService csdnMessageService;

    @ApiOperation(value = "获取私信信息", nickname = "获取私信信息")
    @GetMapping("/acquireMessage")
    public Result acquireMessage() {
        final List<MessageResponse.MessageData.Sessions> sessions = csdnMessageService.acquireMessage();
        return Result.ok(sessions);
    }

    @ApiOperation(value = "处理私信三连", nickname = "处理私信三连")
    @GetMapping("/dealMessage")
    public Result dealMessage() {
        csdnMessageService.dealMessage(csdnMessageService.acquireMessage());
        return Result.ok("处理私信三连完成");
    }

    @ApiOperation(value = "回复私信", nickname = "回复私信")
    @GetMapping("/replyMessage")
    public Result replyMessage() {
        String toUsername = "2301_79035870";
        Integer messageType = 0;
        String messageBody = "谢谢";
        String fromClientType = "WEB";
        String fromDeviceId = "10_20285116700–1699412958190–182091";
        String appId = "CSDN-PC";
        csdnMessageService.replyMessage(toUsername, messageType, messageBody, fromClientType, fromDeviceId, appId);
        return Result.ok("回复私信完成");
    }

    @ApiOperation(value = "设置私信已读", nickname = "设置私信已读")
    @GetMapping("/messageRead")
    public Result messageRead() {
        String toUsername = "2301_79035870";
        csdnMessageService.messageRead(toUsername);
        return Result.ok("设置私信已读完成");
    }

    @ApiOperation(value = "是否回复过私信", nickname = "是否回复过私信")
    @GetMapping("/haveRepliedMessage")
    public Result haveRepliedMessage() {
        String fromUsername = "", blogUrl = "";
        csdnMessageService.haveRepliedMessage(fromUsername, blogUrl);
        return Result.ok("是否回复过私信完成");
    }
}