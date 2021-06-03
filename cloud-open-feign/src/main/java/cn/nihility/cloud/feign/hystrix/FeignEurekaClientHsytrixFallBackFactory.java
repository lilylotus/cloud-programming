package cn.nihility.cloud.feign.hystrix;

import cn.nihility.cloud.common.result.CommonResult;
import cn.nihility.cloud.feign.client.FeignEurekaClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author intel
 * @date 2021/05/07 18:01
 */
@Component
public class FeignEurekaClientHsytrixFallBackFactory implements FallbackFactory<FeignEurekaClient> {

    private static final Logger log = LoggerFactory.getLogger(FeignEurekaClientHsytrixFallBackFactory.class);

    @Override
    public FeignEurekaClient create(Throwable cause) {
        return new FeignEurekaClient() {
            @Override
            public CommonResult echo(String msg) {
                log.error("HsytrixFallBackFactory echo", cause);
                return CommonResult.failure(500, msg + " [" + cause.getMessage() + "]");
            }
        };
    }

}
