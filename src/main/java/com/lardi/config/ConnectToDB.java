package com.lardi.config;

        import com.lardi.PhoneBookApplication;
        import org.apache.log4j.Logger;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class ConnectToDB {
    final static private Logger logger = Logger.getLogger(ConnectToDB.class);
    private static Connection instance;
    private static Connection ConnectToDB(){
        if (logger.isDebugEnabled()) {   logger.debug("Create instance ConnectToDB()" ); }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        String url= PhoneBookApplication.PROPERTIES.getProperty("db.url");
        String user= PhoneBookApplication.PROPERTIES.getProperty("db.user");
        String password= PhoneBookApplication.PROPERTIES.getProperty("db.password");

        try {
            instance = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        if (instance == null) {

            logger.error("Connection to DB error ");

            System.exit(1);
        }
        return instance;
    }
    public static synchronized Connection getInstance()  {
        if(instance==null){
            instance=  ConnectToDB();
        }
        return instance;
    }
}

