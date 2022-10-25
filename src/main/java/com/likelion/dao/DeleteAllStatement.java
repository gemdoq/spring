package com.likelion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{
    PreparedStatement ps = null;

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        ps = c.prepareStatement("delete from users");
        return ps;
    }
}
