package cn.nihility.cloud.feign.controller;

import cn.nihility.cloud.common.result.CommonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author intel
 * @date 2021/05/07 15:37
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    private static final Logger log = LoggerFactory.getLogger(HystrixController.class);

    private RestTemplate restTemplate;

    public HystrixController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallBackEcho",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    @GetMapping("/echo/{msg}")
    public CommonResult hei(@PathVariable("msg") String msg) {
        log.info("receive echo msg [{}]", msg);
        return restTemplate.getForObject("http://127.0.0.1:30011/hei/echo/{msg}", CommonResult.class, msg);
    }

    public CommonResult fallBackEcho(String msg) {
        return CommonResult.failure(50000, "HystrixController Hsytrix FallBack [" + msg + "]");
    }

}
