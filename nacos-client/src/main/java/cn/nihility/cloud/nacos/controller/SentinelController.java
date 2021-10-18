package cn.nihility.cloud.nacos.controller;

import cn.nihility.cloud.nacos.service.SentinelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelController {

    private SentinelService sentinelService;

    public SentinelController(SentinelService sentinelService) {
        this.sentinelService = sentinelService;
    }

    @GetMapping("/test1")
    public String test1() {
        return "this is test1 -----------";
    }

    @GetMapping("/test2")
    public String test2() {
        return "this is test2 -----------";
    }

    @GetMapping("/limit")
    public String limit() {
        return sentinelService.hello("测试QPS限流");
    }

    @GetMapping("/thread")
    public String thread(){
        return sentinelService.threadTest("线程测试");
    }

}
