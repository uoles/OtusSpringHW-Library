package ru.otus.mkulikov;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.otus.mkulikov.dao.BookDao;
import ru.otus.mkulikov.domain.Book;


/**
 * Created by IntelliJ IDEA.
 * Developer: Maksim Kulikov
 * Date: 2019-03-14
 * Time: 15:27
 */

@SpringBootApplication
@PropertySource("classpath:application.yml")
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class);
        BookDao dao = context.getBean(BookDao.class);

        Book book = dao.getBookById(1);

        Console.main(args);
    }
}
