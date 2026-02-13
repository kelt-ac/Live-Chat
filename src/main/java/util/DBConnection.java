package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String url ="jdbc:mysql://localhost:3306/livechat";
    private static String user="root";
    private static String password="";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
