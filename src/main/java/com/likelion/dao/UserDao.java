package com.likelion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {

    /* 환경변수에서 값을 읽어와서 맵에 담고 각 변수를 선언하여 맵에 담은 자료에서 키에 해당하는 값들을 각각 담아준다
       클래스 이름으로 드라이버를 가져오는 코드(class.forname(드라이브명))
       Connection타입 변수에 드라이브매니저클래스의 getconnection(개인정보)메서드로 연결을 담기
       PreparedStatement타입 변수에 접속한 객체 내의 prepareStatement()메서드로 쿼리문을 담기
       해당되는 PreparedStatement타입 객체의 키에 해당하는 문자열 값을 변경한다.
     */
    public void add() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);

        PreparedStatement ps = conn.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, "0");
        ps.setString(2, "최승호");
        ps.setString(3, "password");

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add();
    }
}
