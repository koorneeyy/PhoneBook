package com.lardi;

import com.lardi.dal.DaoFactory;
import com.lardi.dal.interfaces.UserDaoInterface;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DaoUserDetailsService implements UserDetailsService {
    final static private Logger logger = Logger.getLogger(DaoUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (logger.isDebugEnabled()) {   logger.debug("Get UserDetails for user "+s); }

        UserDaoInterface userDao = DaoFactory.getUserDao();
        UserDetails user = userDao.getUser(s);
        if(user==null){
            if (logger.isDebugEnabled()) {   logger.debug("No user "+s+" found"); }
            throw new UsernameNotFoundException("No user "+s+" found");
        }
        return user ;
    }
}
