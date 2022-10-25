package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    @DisplayName("add() and get() is working?")
    void addAndSelect() throws SQLException, ClassNotFoundException {
//        ConnectionMaker connectionMaker = new AWSConnectionMaker();//(1-1)인터페이스를 구현체로 바꾸고
//        UserDao userDao = new UserDao(connectionMaker); //(1-2)의존성 주입한 객체생성
//        UserDao userDao = new UserDaoFactory().awsConnectedUserDao(); //(2-1)인터페이스를 구현체로 바꾸고, 의존성 주입한 객체생성을 factory에서 대신함
        UserDao userDao = (UserDao) context.getBean("awsConnectedUserDao");
        User user1 = new User("1","최승호","password");

//        userDao.add(user1); //데이터 추가시 주석해제
        Assertions.assertEquals("1",user1.getId());
    }
}