package com.kwan.springbootkwan.controller;

import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.service.CsdnRedPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "csdn红包管理")
@RequestMapping("/csdn/redPackage")
public class CsdnRedPackageController {

    @Autowired
    private CsdnRedPackageService csdnRedPackageService;

    @ApiOperation(value = "获取有红包的文章", nickname = "获取有红包的文章")
    @GetMapping("/haveRedPackage")
    public Result haveRedPackage() {
        return Result.ok(csdnRedPackageService.haveRedPackage());
    }
}
