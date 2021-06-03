package cn.nihility.cloud.eureka.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    private static final Logger log = LoggerFactory.getLogger(HystrixController.class);

    @RequestMapping("/requestNormal")
    public String requestNormal() {
        log.info("requestNormal");
//        throw new RuntimeException("requestNormal");
        return "requestNormal success";
    }

    @RequestMapping("/requestTimeOut")
    public String requestTimeOut() {
        log.info("requestTimeOut");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // do nothing
        }
        return "requestTimeOut success";
    }

    @RequestMapping("/requestError")
    public String requestError() {
        log.info("requestError");
        throw new RuntimeException("requestError");
    }

    @RequestMapping("/requestCircuitBreaker/{random}")
    public String requestCircuitBreaker(@PathVariable("random") Integer random) {
        log.info("requestCircuitBreaker random [{}]", random);
        if (random % 2 == 0) {
            throw new RuntimeException("requestCircuitBreaker");
        }
        return "requestCircuitBreaker";
    }

}
