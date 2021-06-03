package cn.nihility.cloud.hystrix.service;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class HystrixCommandService extends HystrixCommand<String> {

    private String secretKey;

    public HystrixCommandService(String secretKey) {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("HystrixCommandService"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(2000)
                ).andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(20)
                                .withMaxQueueSize(5)
                )
        );
        this.secretKey = secretKey;
    }

    @Override
    protected String run() throws Exception {
        return "HystrixCommandService run [" + secretKey + "]";
    }

    @Override
    protected String getFallback() {
        return "HystrixCommandService Fallback [" + secretKey + "]";
    }

}
