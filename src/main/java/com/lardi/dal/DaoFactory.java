package com.lardi.dal;

import com.lardi.PhoneBookApplication;
import com.lardi.dal.impl.RecordDaoFileImpl;
import com.lardi.dal.impl.RecordDaoMysqlImpl;
import com.lardi.dal.impl.UserDaoFileImpl;
import com.lardi.dal.impl.UserDaoMysqlImpl;
import com.lardi.dal.interfaces.RecordDaoInterface;
import com.lardi.dal.interfaces.UserDaoInterface;
import org.apache.log4j.Logger;

public class DaoFactory {
    final static private Logger logger = Logger.getLogger(DaoFactory.class);



    public static UserDaoInterface  getUserDao() {
        if (logger.isDebugEnabled()) {   logger.debug("getUserDao() "); }
        UserDaoInterface userDao = null;
        if(PhoneBookApplication.PROPERTIES.getProperty("datastore").equals("db")){
            userDao=new UserDaoMysqlImpl();
        }
        if(PhoneBookApplication.PROPERTIES.getProperty("datastore").equals("files")){
            userDao=new UserDaoFileImpl();
        }

return userDao;
    }

    public static RecordDaoInterface getRecordDao() {
        if (logger.isDebugEnabled()) {   logger.debug("getRecordDao() "); }
        RecordDaoInterface recordDao=null;
        if(PhoneBookApplication.PROPERTIES.getProperty("datastore").equals("db")){
            recordDao=new RecordDaoMysqlImpl();
        }

        if(PhoneBookApplication.PROPERTIES.getProperty("datastore").equals("files")){
            recordDao=new RecordDaoFileImpl();
        }
        return recordDao;
    }
}
