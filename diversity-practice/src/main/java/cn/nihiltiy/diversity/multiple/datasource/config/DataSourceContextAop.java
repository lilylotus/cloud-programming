package cn.nihiltiy.diversity.multiple.datasource.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceContextAop {

    private static final Logger log = LoggerFactory.getLogger(DataSourceContextAop.class);

    @Around("@annotation(cn.nihiltiy.diversity.multiple.datasource.config.DataSourceSwitcher)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = false;
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            DataSourceSwitcher dataSourceSwitcher = method.getAnnotation(DataSourceSwitcher.class);
            if (null != dataSourceSwitcher) {
                clear = dataSourceSwitcher.clear();
                DataSourceContextHolder.set(dataSourceSwitcher.value().getDatasourceName());
                log.info("数据源切换至：{}", dataSourceSwitcher.value().getDatasourceName());
            }
            return pjp.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

}
