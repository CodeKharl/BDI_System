package org.isu_std.dao.mysqldao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlHelper {
    public static void setPreparedStatementStr(PreparedStatement preparedStatement, String[] contents) throws SQLException {
        for(int i = 0; i < contents.length; i++){
            preparedStatement.setString(i + 1, contents[i]);
        }
    }
}
