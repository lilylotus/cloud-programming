package cn.nihiltiy.diversity.multiple.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "cn.nihiltiy.diversity.multiple.datasource.mapper",
    sqlSessionTemplateRef = "sqlSessionTemplate")
public class MultiDatasourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.read")
    public DataSource readDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DataSourceRouter dataSourceRouter() {
        DataSourceRouter dynamicDataSource = new DataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.WRITE.getDatasourceName(), writeDataSource());
        targetDataSources.put(DataSourceEnum.READ.getDatasourceName(), readDataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(writeDataSource());
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper.xml"));
        factoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        factoryBean.setDataSource(dataSourceRouter());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSourceRouter());
    }

}
