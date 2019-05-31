package ru.otus.mkulikov;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.mkulikov.app.dao.BookDaoJdbc;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 28.05.2019
 * Time: 9:47
 */

@SpringBootConfiguration
@ComponentScan("ru.otus.mkulikov.app")
@PropertySource({"classpath:application.yml"})
@EnableConfigurationProperties
public class AppTestConfig {

    @Bean
    public NamedParameterJdbcOperations getNamedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    //@Bean
    //public DataSource getDataSource() {
    //    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    //    dataSourceBuilder.driverClassName("org.h2.Driver");
    //    dataSourceBuilder.url("jdbc:h2:mem:test");
    //    dataSourceBuilder.username("SA");
    //    dataSourceBuilder.password("");
    //    return dataSourceBuilder.build();
    //}

    @Bean
    @Primary
    @ConfigurationProperties("application.yml")
    public DataSourceProperties getDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    //@ConfigurationProperties("datasource.oracle")
    public DataSource getDatasource() {
        return getDatasourceProperties().initializeDataSourceBuilder()
                .build();
    }
}
