package com.lardi.dal.interfaces;

import com.lardi.dal.pojo.UserPojo;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDaoInterface {


     boolean isUserExist(String userName);
     void addNewUser(UserPojo userPojo);
     UserDetails getUser(String userName);

}


