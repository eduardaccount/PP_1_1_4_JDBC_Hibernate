package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {

    private static final String url = "jdbc:mysql://localhost:3306/ppschema";
    private static final String user = "root";
    private static final String password = "12345678";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dialect = "org.hibernate.dialect.MySQL5Dialect";
    private static final String UPDATE = "update";

    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;


    public static SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, driver);
            properties.put(Environment.URL, url);
            properties.put(Environment.USER, user);
            properties.put(Environment.PASS, password);
            properties.put(Environment.DIALECT, dialect);
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sessionFactory;
    }


    public static Connection getConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void closeAll() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
