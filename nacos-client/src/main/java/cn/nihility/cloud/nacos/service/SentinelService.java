package cn.nihility.cloud.nacos.service;

public interface SentinelService {

    /**
     * 阈值类型 QPS  流控模式直接
     */
    String hello(String limit);


    /**
     * 阈值类型 线程数、流控模式 直接
     */
    String threadTest(String thread);

}
