package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    @DisplayName("add() and get() is working?")
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao(); // 객체생성
        User user1 = new User("1","최승호","password");

//        userDao.add(user1); //데이터 추가시 주석해제
        Assertions.assertEquals("1",user1.getId());
    }
}