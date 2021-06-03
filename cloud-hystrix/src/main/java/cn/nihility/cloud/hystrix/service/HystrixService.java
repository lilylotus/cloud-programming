package cn.nihility.cloud.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HystrixService {

    private RestTemplate restTemplate;

    public HystrixService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
        }, fallbackMethod = "requestNormalFallbackMethod"
    )
    public String requestNormal() {
        //return restTemplate.getForObject("http://eurekaClient/hystrix/requestNormal", String.class);
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestTimeOut", String.class);
    }

    @HystrixCommand(
        commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "6"),
            @HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "5000"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "3000")
        },  fallbackMethod = "requestCircuitBreakerFallbackMethod"
    )
    public String requestCircuitBreaker(Integer random) {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestCircuitBreaker/{random}", String.class, random);
    }

    @HystrixCommand(threadPoolKey = "requestTimeOut",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "2"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2")
        },
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }, fallbackMethod = "requestTimeOutFallbackMethod")
    public String requestTimeOut() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestTimeOut", String.class);
    }

    @HystrixCommand(threadPoolKey = "requestError",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "5"),
                @HystrixProperty(name = "maxQueueSize", value = "10"),
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10")
        }, fallbackMethod = "requestErrorFallbackMethod")
    public String requestError() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestError", String.class);
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
