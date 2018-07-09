package com.xinyuan.ms.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author liang
 */
@Data
@Configuration
public class DataSourceConfig {

    private static String url;

    private static String username;

    private static String password;

    private static String driverClassName;

//    @Bean
//    public DataSource getDataSource() {
//        return buildDataSource();
//    }

    @Bean
    public IdGenerator getIdGenerator() {
        return new CommonSelfIdGenerator();
    }

    private DataSource buildDataSource() {
        //设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库ds_0,ds_1到map里
        dataSourceMap.put("yirui_circle", createDataSource());
        //设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);

        //设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
        //0和1两个表是真实的表，t_order是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from t_order就能查完0和1表的

        List<String> orderNameList = new ArrayList<>(10);

        for (int i = 0; i < 9; i++) {
            orderNameList.add("t_order_" + i);
        }

        TableRule orderTableRule = TableRule.builder("t_order")
                .actualTables(orderNameList)
                .dataSourceRule(dataSourceRule)
                .build();

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm())).build();

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);

        return dataSource;
    }

    private static DataSource createDataSource() {
        //使用druid连接数据库
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(driverClassName);
        result.setUrl(url);
        result.setUsername(username);
        result.setPassword(password);
        return result;
    }

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        DataSourceConfig.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        DataSourceConfig.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        DataSourceConfig.password = password;
    }

    @Value("${spring.datasource.driver-class-name}")
    public void setDriverClassName(String driverClassName) {
        DataSourceConfig.driverClassName = driverClassName;
    }
}
