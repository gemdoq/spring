package com.likelion.dao;

import com.likelion.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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
        String sql = "delete from users";
        jdbcTemplate.update(sql);
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
        String sql = "insert into users(id,name,password) values(?,?,?);";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() throws SQLException, ClassNotFoundException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//
//        try {
//            c = dataSource.getConnection();
//            ps = c.prepareStatement("select count(*) from users");
//            rs = ps.executeQuery();
//
//            if(rs.next()) {
//                count = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if(rs!=null) try {
//                rs.close();
//            } catch (SQLException ignore) {
//            }
//            if(ps!=null) try {
//                ps.close();
//            } catch (SQLException ignore) {
//            }
//            if(c!=null) try {
//                c.close();
//            } catch (SQLException ignore) {
//            }
//        }
//        return count;
        String sql = "select count(*) from users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }
    };
    public User findById(String id) throws ClassNotFoundException, SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        User user = null;
//
//        try {
//            c = dataSource.getConnection();
//            ps = c.prepareStatement("select * from users where id = ?");
//            ps.setString(1, id);
//            System.out.println(ps);//쿼리문을 출력
//            rs = ps.executeQuery();//ResultSet이 쿼리실행문을 담아오는 객체
//
//            if (rs.next()) {
//                System.out.println(rs.getString(2));
//                System.out.println(rs.getString(3));
//                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            //사용한 객체를 닫을 때는 역순으로
//            if(rs!=null) try {
//                rs.close();
//            } catch (SQLException ignore) {
//            }
//            if(ps!=null) try {
//                ps.close();
//            } catch (SQLException ignore) {
//            }
//            if(c!=null) try {
//                c.close();
//            } catch (SQLException ignore) {
//            }
//        }
//        return user;
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<User> getAll() {
        String sql = "select * from users order by id";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
