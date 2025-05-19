package org.isu_std.dao.jdbc_helper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper <T> {
    T map(ResultSet resultSet) throws SQLException, IOException;
}
