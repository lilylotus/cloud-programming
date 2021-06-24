package cn.nihiltiy.diversity.multiple.datasource.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSwitcher {

    /**
     * 默认数据源
     */
    DataSourceEnum value() default DataSourceEnum.WRITE;

    /**
     * 清除
     */
    boolean clear() default true;

}
