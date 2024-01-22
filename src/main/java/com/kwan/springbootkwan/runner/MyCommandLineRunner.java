package com.kwan.springbootkwan.runner;

import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
import com.kwan.springbootkwan.service.CsdnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnAutoReplyService csdnAutoReplyService;
    @Autowired
    private CsdnLikeCollectService csdnLikeCollectService;

    @Override
    public void run(String... args) {
        log.info("CommandLineRunner run() called start");
        csdnAutoReplyService.commentSelf();
        csdnService.dealTriplet();
        csdnLikeCollectService.dealLikeCollect(csdnLikeCollectService.acquireLikeCollect());
        log.info("CommandLineRunner run() called finished");
    }
}

