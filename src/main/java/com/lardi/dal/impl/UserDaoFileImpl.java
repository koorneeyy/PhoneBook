package com.lardi.dal.impl;

import com.lardi.PhoneBookApplication;
import com.lardi.dal.interfaces.UserDaoInterface;
import com.lardi.dal.pojo.UserPojo;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class UserDaoFileImpl implements UserDaoInterface {
    final static private Logger logger = Logger.getLogger(UserDaoFileImpl.class);
    private File USER_FILE;
    public UserDaoFileImpl() {
        if (logger.isDebugEnabled()) {   logger.debug("UserDaoFileImpl()"); }

        USER_FILE=new File( PhoneBookApplication.PROPERTIES.getProperty("files.location")+"users.csv");
        if (! USER_FILE.exists()){
            try {
                USER_FILE.createNewFile();

            } catch (IOException e) {
         logger.error(e.getMessage());
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean isUserExist(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Is exist user: "+userName); }
        boolean result = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(USER_FILE));

            String line = null;
            while ((line = br.readLine()) != null) {

                String[] split = line.split(";");
                if (split[0].equals(userName)){
                        result=true;

                    }

            }

            br.close();

        }
catch (IOException e){
System.err.println("FILE ERROR");
System.err.println(e.getMessage());
}



        return result;
    }

    @Override
    public void addNewUser(UserPojo userPojo) {
        if (logger.isDebugEnabled()) {   logger.debug("Add new user "+userPojo.getNameLogin()); }
        try(FileWriter fw = new FileWriter(USER_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.write(userPojo.toString());
       out.close();
        } catch (IOException e) {
logger.error(e.getMessage());
        }

    }

    @Override
    public UserDetails getUser(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get user "+userName); }
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = null;


        try {
            BufferedReader br = new BufferedReader(new FileReader(USER_FILE));

            String line = null;
            while ((line = br.readLine()) != null) {

                String[] split = line.split(";");
                if (split[0].equals(userName)){

                    userDetails=new User(userName,split[1],roles);
                }
            }

            br.close();

        }
        catch (IOException e){
logger.error(e.getMessage());
        }

         return userDetails;
    }

}
