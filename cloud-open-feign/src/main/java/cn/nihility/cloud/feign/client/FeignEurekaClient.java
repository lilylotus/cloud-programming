package cn.nihility.cloud.feign.client;

import cn.nihility.cloud.common.result.CommonResult;
import cn.nihility.cloud.feign.hystrix.FeignEurekaClientHsytrixFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eurekaClient", fallbackFactory = FeignEurekaClientHsytrixFallBackFactory.class)
public interface FeignEurekaClient {

    @GetMapping(value = "/hei/echo/{msg}")
    CommonResult echo(@PathVariable("msg") String msg);

}
