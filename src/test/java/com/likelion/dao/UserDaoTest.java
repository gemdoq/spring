package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("deleteAll() is working?")
    void getCount() throws SQLException, ClassNotFoundException {

        User user1 = new User("1","name1","password1");
        User user2 = new User("2","name2","password2");
        User user3 = new User("3","name3","password3");

        UserDao userDao = (UserDao) context.getBean("awsConnectedUserDao");
        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());

        userDao.add(user1);
        Assertions.assertEquals(1, userDao.getCount());
        userDao.add(user2);
        Assertions.assertEquals(2, userDao.getCount());
        userDao.add(user3);
        Assertions.assertEquals(3, userDao.getCount());

    }

    @Test
    @DisplayName("add() and get() is working?")
    void addAndSelect() throws SQLException, ClassNotFoundException {
        User user1 = new User("1","name1","password1");

//        ConnectionMaker connectionMaker = new AWSConnectionMaker();//(1-1)인터페이스를 구현체로 바꾸고
//        UserDao userDao = new UserDao(connectionMaker); //(1-2)의존성 주입한 객체생성
//        UserDao userDao = new UserDaoFactory().awsConnectedUserDao(); //(2-1)인터페이스를 구현체로 바꾸고, 의존성 주입한 객체생성을 factory에서 대신함
        UserDao userDao = (UserDao) context.getBean("awsConnectedUserDao");//(3-1)스프링을 이용해서 조립
        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());

        userDao.add(user1);
        Assertions.assertEquals(1, userDao.getCount());
        User selectedUser = userDao.findById(user1.getId());

        Assertions.assertEquals("name1",user1.getName());
        Assertions.assertEquals("password1",user1.getPassword());
    }
}