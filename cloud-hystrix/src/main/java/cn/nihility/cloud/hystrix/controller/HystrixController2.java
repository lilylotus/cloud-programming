package cn.nihility.cloud.hystrix.controller;

import cn.nihility.cloud.hystrix.service.HystrixCommandService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixController2 {

    private RestTemplate restTemplate;

    public HystrixController2(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/hystrix2/command/{command}")
    public String commandService(@PathVariable("command") String command) {
        HystrixCommandService service = new HystrixCommandService(command);
        return service.execute();
    }

    @HystrixCommand(fallbackMethod = "requestNormalFallbackMethod")
    @RequestMapping("/hystrix2/requestNormal")
    public String requestNormal() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestNormal", String.class);
    }

    @HystrixCommand(fallbackMethod = "requestTimeOutFallbackMethod")
    @RequestMapping("/hystrix2/requestTimeOut")
    public String requestTimeOut() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestTimeOut", String.class);
    }

    @HystrixCommand(fallbackMethod = "requestErrorFallbackMethod")
    @RequestMapping("/hystrix2/requestError")
    public String requestError() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestError", String.class);
    }

    @HystrixCommand(fallbackMethod = "requestCircuitBreakerFallbackMethod")
    @RequestMapping("/hystrix2/requestCircuitBreaker/{random}")
    public String requestCircuitBreaker(@PathVariable("random") Integer random) {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestCircuitBreaker/{random}", String.class, random);
    }

    public String requestTimeOutFallbackMethod() {
        return "requestTimeOutFallbackMethod";
    }

    public String requestErrorFallbackMethod() {
        return "requestErrorFallbackMethod";
    }

    public String requestNormalFallbackMethod() {
        return "requestNormalFallbackMethod";
    }

    public String requestCircuitBreakerFallbackMethod(Integer random) {
        return "requestCircuitBreakerFallbackMethod [" + random + "]";
    }

}
