package cn.nihiltiy.diversity.multiple.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {

    /**
     * 最终的 determineCurrentLookupKey 返回的是从 DataSourceContextHolder 中拿到的
     * 因此在动态切换数据源的时候注解
     * 应该给 DataSourceContextHolder 设值
     *
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }

}
