package cn.nihility.cloud.feign.hystrix;

import cn.nihility.cloud.common.result.CommonResult;
import cn.nihility.cloud.feign.client.FeignEurekaClient;
import org.springframework.stereotype.Component;

/**
 * @author intel
 * @date 2021/05/07 11:45
 */
@Component
public class FeignEurekaClientHsytrixFallBack implements FeignEurekaClient {

    @Override
    public CommonResult echo(String msg) {
        return CommonResult.failure(50000, "FeignEurekaClientHsytrixFallBack [" + msg + "]");
    }

}
