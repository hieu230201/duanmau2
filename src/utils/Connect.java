package utils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class Connect {
    public Connection con() throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("123");
        ds.setDatabaseName("EduSys");
        ds.setServerName("LAPTOP-8LABNS8Q\\SQLEXPRESS");
        ds.setPortNumber(1433);
        Connection con = ds.getConnection();
        return con;
    }
}
