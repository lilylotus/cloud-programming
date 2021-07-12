package cn.nihility.cloud.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HystrixService {

    private static final Logger log = LoggerFactory.getLogger(HystrixService.class);
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

    @HystrixCommand(
        // 设置一个隔离的线程池
        threadPoolKey = "requestTimeOut",
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

    /**
     * 信号量隔离，配置当并发高的时候服务降级，并返回拖底数据，防止服务雪崩
     */
    @HystrixCommand(fallbackMethod = "requestNormalSemaphoreFallbackMethod",
        commandProperties = {
            // 信号量隔离，默认线程 THREAD
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"),
            // 信号量最大并发度
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "30")
        })
    public String requestNormalSemaphore() {
        return restTemplate.getForObject("http://eurekaClient/hystrix/requestNormal", String.class);
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

    public String requestNormalSemaphoreFallbackMethod() {
        log.error("requestNormalSemaphoreFallbackMethod");
        return "requestNormalSemaphoreFallbackMethod";
    }

    public String requestCircuitBreakerFallbackMethod(Integer random) {
        return "requestCircuitBreakerFallbackMethod [" + random + "]";
    }

}
