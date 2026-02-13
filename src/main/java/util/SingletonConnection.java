package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnection {
    private static SingletonConnection instance = null;
    private static final String url ="jdbc:mysql://localhost:3306/livechat";
    private static final String user="root";
    private static final String password="";
    private Connection connection;

    private SingletonConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("connection done");
        }catch (Exception e){
            System.out.println("database connection error" + e.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public static SingletonConnection getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new SingletonConnection();
        }
        return instance;
    }
}
