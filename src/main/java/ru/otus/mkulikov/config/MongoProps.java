package ru.otus.mkulikov.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 19.08.2019
 * Time: 23:55
 */

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoProps {
    private int port;
    private String database;
    private String uri;
}
