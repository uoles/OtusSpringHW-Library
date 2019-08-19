package ru.otus.mkulikov.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 19.08.2019
 * Time: 23:56
 */

@Configuration
public class ApplicationConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.mkulikov.changelog";

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }
}