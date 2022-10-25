package com.likelion.dao;

import com.likelion.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(c!=null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?);");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
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
