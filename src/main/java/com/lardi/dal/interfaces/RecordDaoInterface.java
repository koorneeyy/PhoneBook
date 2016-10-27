package com.lardi.dal.interfaces;

import com.lardi.dal.pojo.RecordPojo;
import java.util.List;

public interface RecordDaoInterface {

    List<RecordPojo> getAllRecords(String userName);
    List<RecordPojo> getFiltered(String keyword,String userName);
    void addRecord(RecordPojo recordPojo,String userName);
    void deleteRecord(int recordId,String userName);
    void editRecord(RecordPojo recordPojo,String userName);
    RecordPojo getRecordById(int recordId,String userName);


}
