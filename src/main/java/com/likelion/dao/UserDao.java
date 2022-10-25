package com.likelion.dao;

import com.likelion.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.Class.forName;

public class UserDao {

    /*
    환경변수에서 값을 읽어와서 맵에 담고 각 변수를 선언하여 맵에 담은 자료에서 키에 해당하는 값들을 각각 담아준다
    클래스 이름으로 드라이버를 가져오는 코드(class.forname(드라이브명))
    Connection타입 변수에 드라이브매니저클래스의 getconnection(개인정보)메서드로 연결을 담기
    PreparedStatement타입 변수에 접속한 객체 내의 prepareStatement()메서드로 쿼리문을 담기
    해당되는 PreparedStatement타입 객체의 키에 해당하는 문자열 값을 변경한다.
     */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(dbHost, dbUser, dbPassword);

        PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }
    /*
    add()처럼 findById()도 구현
    연결은 똑같고, 쿼리문의 내용이 select문으로 변경
    ResultSet은 select문의 결과를 받는 객체,
     */
    public void findById(String id) throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(dbHost, dbUser, dbPassword);

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
