package cn.nihility.cloud.hystrix.controller;

import cn.nihility.cloud.hystrix.service.HystrixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixController {

    private HystrixService hystrixService;

    public HystrixController(HystrixService hystrixService) {
        this.hystrixService = hystrixService;
    }

    @RequestMapping("/hystrix/requestNormal")
    public String requestNormal() {
        return hystrixService.requestNormal();
    }

    @RequestMapping("/hystrix/requestTimeOut")
    public String requestTimeOut() {
        return hystrixService.requestTimeOut();
    }

    @RequestMapping("/hystrix/requestError")
    public String requestError() {
        return hystrixService.requestError();
    }

    @RequestMapping("/hystrix/requestCircuitBreaker/{random}")
    public String requestCircuitBreaker(@PathVariable("random") Integer random) {
        return hystrixService.requestCircuitBreaker(random);
    }

    @RequestMapping("/hystrix/requestNormalSemaphore")
    public ResponseEntity<String> requestNormalSemaphore() {
        return ResponseEntity.ok(hystrixService.requestNormalSemaphore());
    }

}
