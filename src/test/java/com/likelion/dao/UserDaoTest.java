package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1", "name1", "password1");
        user2 = new User("2", "name2", "password2");
        user3 = new User("3", "name3", "password3");
        userDao.deleteAll();
    }

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        userDao.add(user1);
        assertEquals("name1",userDao.findById("1").getName());

        userDao.deleteAll();
    }

    @Test
    void getCount() throws SQLException, ClassNotFoundException {
        userDao.getCount();

        userDao.add(user1);
        assertEquals(1,userDao.getCount());

        userDao.add(user2);
        assertEquals(2,userDao.getCount());

        userDao.add(user3);
        assertEquals(3,userDao.getCount());

        userDao.deleteAll();
    }
}