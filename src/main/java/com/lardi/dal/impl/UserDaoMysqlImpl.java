package com.lardi.dal.impl;

import com.lardi.config.ConnectToDB;
import com.lardi.dal.interfaces.UserDaoInterface;
import com.lardi.dal.pojo.UserPojo;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDaoMysqlImpl implements UserDaoInterface {
    final static private Logger logger = Logger.getLogger(UserDaoMysqlImpl.class);
    @Override
    public boolean isUserExist(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Is exist user: "+userName); }
        Connection connection = ConnectToDB.getInstance();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT id FROM users WHERE login='" + userName + "'");
            while (resultSet.next()){
               return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addNewUser(UserPojo userPojo) {
        if (logger.isDebugEnabled()) {   logger.debug("Add new user "+userPojo.getNameLogin()); }
        Connection connection = ConnectToDB.getInstance();
        try {
            Statement statement = connection.createStatement();
            statement.execute("insert into users  (login,fullName,password)values ('" + userPojo.getNameLogin() + "','" + userPojo.getFullName()+"','"+userPojo.getPassword()+"')");

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public UserDetails getUser(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get user "+userName); }
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = null;
        Connection connection = ConnectToDB.getInstance();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  password from  users where login='" + userName + "'");
            while (resultSet.next()){
                String password = resultSet.getString("password");
                userDetails=new User(userName,password,roles);
                logger.info(userDetails+"  "+password);

            }


        } catch (SQLException e) {
logger.error(e.getMessage());
            e.printStackTrace();
        }

        return userDetails;
    }
}
