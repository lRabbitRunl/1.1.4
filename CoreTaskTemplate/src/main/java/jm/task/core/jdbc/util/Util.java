package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    //jdbc:mysql://localhost:3306/Peoples?useSSL=false
    private static final String URL = "jdbc:mysql://" + "localhost" + ":3306/" + "test" + "?verifyServerCertificate=false" + "&useSSL=false" + "&requireSSL=false" + "&useLegacyDatetimeCode=false" + "&amp" + "&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "6154";

    private static Connection connection = null;
    private static SessionFactory sessionfactory = null;

    public static Connection getConnection() {


        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    public static SessionFactory getSessionFactory() {
        //SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration()
                    .setProperty("connection.driver_class","com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url",URL)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql", "true")
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionfactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Исключение!" + e);
        }
        return sessionfactory;
    }

    public static void shutdown() {
        if(connection != null){
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sessionfactory!= null){
            getSessionFactory().close();
        }
    }
}
