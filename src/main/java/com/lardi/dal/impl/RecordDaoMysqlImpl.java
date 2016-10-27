package com.lardi.dal.impl;

import com.lardi.config.ConnectToDB;
import com.lardi.dal.interfaces.RecordDaoInterface;
import com.lardi.dal.pojo.RecordPojo;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoMysqlImpl implements RecordDaoInterface {
    final static private Logger logger = Logger.getLogger(RecordDaoMysqlImpl.class);
    @Override
    public List<RecordPojo> getAllRecords(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get all records for user "+userName); }
        Connection connection = ConnectToDB.getInstance();
        ArrayList<RecordPojo>  recordPojos=new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from  records where user_id=(select id from users where login ='"+userName+"')");
            while (resultSet.next()){
            RecordPojo record=new RecordPojo();

                record.setfName(resultSet.getString("fName"));
                record.setsName(resultSet.getString("sName"));
                record.setmName(resultSet.getString("mName"));
                record.setAdress(resultSet.getString("adress"));
                record.setMail(resultSet.getString("mail"));


                record.setmPhone(resultSet.getString("mPhone"));
                record.sethPhone(resultSet.getString("hPhone"));
                record.setId(resultSet.getInt("id"));

                recordPojos.add(record);
            }


        } catch (SQLException e) {
logger.error(e.getMessage());
            e.printStackTrace();
        }
        return recordPojos;
    }

    @Override
    public List<RecordPojo> getFiltered(String keyword, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get filter records for user "+userName+" and filter keyword "+keyword); }
        Connection connection = ConnectToDB.getInstance();
        ArrayList<RecordPojo>  recordPojos=new ArrayList<>();
        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * from  records where user_id=(select id from users where login =?) and (fName like ? or sName like ? or mPhone like ?)");
            stmt.setString(1, userName);
            stmt.setString(2,"%"+keyword+"%");
            stmt.setString(3,"%"+keyword+"%");
            stmt.setString(4,"%"+keyword+"%");

            ResultSet resultSet =stmt.executeQuery();
            while (resultSet.next()){
                RecordPojo record=new RecordPojo();

                record.setfName(resultSet.getString("fName"));
                record.setsName(resultSet.getString("sName"));
                record.setmName(resultSet.getString("mName"));
                record.setAdress(resultSet.getString("adress"));
                record.setMail(resultSet.getString("mail"));


                record.setmPhone(resultSet.getString("mPhone"));
                record.sethPhone(resultSet.getString("hPhone"));
                record.setId(resultSet.getInt("id"));

                recordPojos.add(record);
            }


        } catch (SQLException e) {
logger.error(e.getMessage());
            e.printStackTrace();

        }
        return recordPojos;

    }

    @Override
    public void addRecord(RecordPojo recordPojo, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Add record for user "+userName); }
        Connection connection = ConnectToDB.getInstance();
        try {
            PreparedStatement stmt = connection.prepareStatement("insert into records (user_id,fName,sName,mName,mPhone,hPhone,adress,mail)values((select id from users where login =?),?,?,?,?,?,?,?)");
            stmt.setString(1, userName);
            stmt.setString(2, recordPojo.getfName());
            stmt.setString(3, recordPojo.getsName());
            stmt.setString(4, recordPojo.getmName());
            stmt.setString(5, recordPojo.getmPhone());
            stmt.setString(6, recordPojo.gethPhone());
            stmt.setString(7, recordPojo.getAdress());
            stmt.setString(8, recordPojo.getMail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void deleteRecord(int recordId, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Delete record for user "+userName+" and record id"+recordId); }
        Connection connection = ConnectToDB.getInstance();
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete from records where id='"+recordId+"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editRecord(RecordPojo recordPojo, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Edit record for user "+userName+" and record id"+recordPojo.getId()); }
        Connection connection = ConnectToDB.getInstance();
        try {
            PreparedStatement stmt = connection.prepareStatement("update records set fName=?,sName=?,mName=?,mPhone=?,hPhone=?,adress=?,mail=? where id=?");
            stmt.setString(1, recordPojo.getfName());
            stmt.setString(2, recordPojo.getsName());
            stmt.setString(3, recordPojo.getmName());
            stmt.setString(4, recordPojo.getmPhone());
            stmt.setString(5, recordPojo.gethPhone());
            stmt.setString(6, recordPojo.getAdress());
            stmt.setString(7, recordPojo.getMail());
            stmt.setInt(8, recordPojo.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    @Override
    public RecordPojo getRecordById(int recordId, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get record by id for user "+userName+" and record id"+recordId); }
        Connection connection = ConnectToDB.getInstance();
        RecordPojo record=new RecordPojo();
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from  records where id='"+recordId+"'");
            while (resultSet.next()){

                record.setfName(resultSet.getString("fName"));
                record.setsName(resultSet.getString("sName"));
                record.setmName(resultSet.getString("mName"));
                record.setAdress(resultSet.getString("adress"));
                record.setMail(resultSet.getString("mail"));

                record.setmPhone(resultSet.getString("mPhone"));
                record.sethPhone(resultSet.getString("hPhone"));
                record.setId(resultSet.getInt("id"));

            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


return  record;
    }

}
