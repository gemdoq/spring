package com.likelion.dao;

import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class UserDaoFactory {

    @Bean
    UserDao awsUserDao() {
        return new UserDao(awsDataSource());
    }

    @Bean
    DataSource awsDataSource() {
        Map<String, String> env = System.getenv();

        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        SimpleDriverDataSource c = new SimpleDriverDataSource();
        c.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        c.setUrl(dbHost);
        c.setUsername(dbUser);
        c.setPassword(dbPassword);

        return c;
    }
}
