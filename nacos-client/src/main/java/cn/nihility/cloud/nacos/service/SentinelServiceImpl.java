package cn.nihility.cloud.nacos.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class SentinelServiceImpl implements SentinelService {

    @SentinelResource(value = "limit", defaultFallback = "defaultFallback",
        blockHandler = "handlerException", blockHandlerClass = {BlockException.class})
    @Override
    public String hello(String limit) {
        return "阈值类型 QPS 流控模式直接";
    }

    @SentinelResource(value = "thread", defaultFallback = "defaultFallback",
        blockHandler = "handlerException", blockHandlerClass = {BlockException.class})
    @Override
    public String threadTest(String thread) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "阈值类型 线程数、流控模式 直接";
    }

    /**
     * 自定义简单的 服务限流或者降级回调方法
     */
    public String defaultFallback() {
        return "太拥挤了 ~ 请稍后重试 ";
    }

    public String handlerException() {
        return "测试超出流量限制的部分是否会进入到 blockHandler 的方法。";
    }

}
