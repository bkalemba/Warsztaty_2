package pl.coderslab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Warsztaty_2?useSSL=false" + "&characterEncoding=utf8" + "&useUnicode=true", "root", "coderslab");
    }

}
