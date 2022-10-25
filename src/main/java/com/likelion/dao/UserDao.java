package com.likelion.dao;

import com.likelion.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AWSConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public void findById(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);
        System.out.println(ps);//쿼리문을 출력
        ResultSet rs = ps.executeQuery();//ResultSet이 쿼리실행문을 담아오는 객체
        rs.next();//ResultSet의 커서 초기값이 -1이므로 0에 위치
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
        //닫을때는 역순으로
        rs.close();
        ps.close();
        c.close();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("4","최승호","password"));
    }
}
