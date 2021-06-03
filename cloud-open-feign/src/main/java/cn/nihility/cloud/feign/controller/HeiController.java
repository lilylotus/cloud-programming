package cn.nihility.cloud.feign.controller;

import cn.nihility.cloud.common.result.CommonResult;
import cn.nihility.cloud.feign.client.FeignEurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hei")
public class HeiController {

    private static final Logger log = LoggerFactory.getLogger(HeiController.class);

    private final FeignEurekaClient feignEurekaClient;

    public HeiController(FeignEurekaClient feignEurekaClient) {
        this.feignEurekaClient = feignEurekaClient;
    }

    @GetMapping("/echo/{msg}")
    public CommonResult hei(@PathVariable("msg") String msg) {
        log.info("receive echo msg [{}]", msg);
        return feignEurekaClient.echo(msg);
    }

}
