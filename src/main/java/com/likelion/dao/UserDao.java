package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
//        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                return c.prepareStatement("delete from users");
//            }
//        });

//        jdbcContext.excuteSql("delete from users");

        jdbcTemplate.update("delete from users");
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
//        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?);");
//                ps.setString(1, user.getId());
//                ps.setString(2, user.getName());
//                ps.setString(3, user.getPassword());
//
//                return ps;
//            }
//        });

        jdbcTemplate.update("insert into users(id,name,password) values(?,?,?);", user.getId(), user.getName(), user.getPassword());
    }

    public User findById(String id) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select * from users where id = ?");
            ps.setString(1, id);
            System.out.println(ps);//쿼리문을 출력
            rs = ps.executeQuery();//ResultSet이 쿼리실행문을 담아오는 객체

            if (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            //사용한 객체를 닫을 때는 역순으로
            if(rs!=null) try {
                rs.close();
            } catch (SQLException ignore) {
            }
            if(ps!=null) try {
                ps.close();
            } catch (SQLException ignore) {
            }
            if(c!=null) try {
                c.close();
            } catch (SQLException ignore) {
            }
        }
        return user;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();

            if(rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs!=null) try {
                rs.close();
            } catch (SQLException ignore) {
            }
            if(ps!=null) try {
                ps.close();
            } catch (SQLException ignore) {
            }
            if(c!=null) try {
                c.close();
            } catch (SQLException ignore) {
            }
        }
        return count;
    }
}
