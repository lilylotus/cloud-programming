package cn.nihility.cloud.feign.hystrix;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * 一段简单的使用 HystrixCommand 封装服务隔离调用的实例
 */
public class QueryOrderIdCommand extends HystrixCommand<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(QueryOrderIdCommand.class);
    private String orderId;

    public QueryOrderIdCommand(String orderId) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryByOrderId"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(10)//至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)//熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                        .withCircuitBreakerErrorThresholdPercentage(50)//错误率达到50开启熔断保护
                )
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderServicePool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)));
        this.orderId = orderId;
    }

    /**
     * 执行逻辑的地方
     */
    @Override
    protected Integer run() throws Exception {
        return 10;
    }

    @Override
    public Integer execute() {
        Thread t = Thread.currentThread();
        System.out.println("thread " + t.getName() + ": now " + orderId + " execute execute()...");
        return super.execute();
    }

    @Override
    public Future<Integer> queue() {
        Thread t = Thread.currentThread();
        System.out.println("thread " + t.getName() + ": now " + orderId + " execute queue()...");
        return super.queue();
    }

    @Override
    public Observable<Integer> observe() {
        Thread t = Thread.currentThread();
        System.out.println("thread " + t.getName() + ": now " + orderId + " execute observe()...");
        return super.observe();
    }

    @Override
    public Observable<Integer> toObservable() {
        Thread t = Thread.currentThread();
        System.out.println("thread " + t.getName() + ": now " + orderId + " execute toObservable()...");
        return super.toObservable();
    }

    /**
     * 如果发生失败在这里写回调的逻辑
     */
    @Override
    protected Integer getFallback() {
        return -1;
    }

    /**
     * 这里是简单的模拟调用
     */
    public static void main(String[] args) {
        QueryOrderIdCommand command = new QueryOrderIdCommand("1");
        logger.info("result:{}", command.execute());
    }

}
