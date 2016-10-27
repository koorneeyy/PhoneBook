package com.lardi.dal.impl;

import com.lardi.PhoneBookApplication;
import com.lardi.dal.interfaces.RecordDaoInterface;
import com.lardi.dal.pojo.RecordPojo;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class RecordDaoFileImpl implements RecordDaoInterface {
    final static private Logger logger = Logger.getLogger(RecordDaoFileImpl.class);
    static private String RECORD_FOLDER="records";
    static private String EXTENTION=".csv";
     static private String FILES_ROOT = PhoneBookApplication.PROPERTIES.getProperty("files.location");
    @Override
    public List<RecordPojo> getAllRecords(String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get all records for user "+userName); }
        ArrayList<RecordPojo> recordPojos=new ArrayList<>();
        File recordsFile=new File(FILES_ROOT+File.separatorChar+RECORD_FOLDER+File.separatorChar+userName+EXTENTION);
        if(! recordsFile.exists()){
                  if (logger.isInfoEnabled()) {   logger.info("Records file  " + recordsFile.toString() + " not exist"); }
            return null;
        }
        else {


            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(recordsFile.toString()));
            String line = null;
                while ((line = reader.readLine()) != null && line.split(";").length==8){
                    RecordPojo record=new RecordPojo();
                    String[] split = line.split(";");
                    record.setId(Integer.parseInt(split[0]));
                    record.setfName(split[1]);
                    record.setsName(split[2]);
                    record.setmName(split[3]);
                    record.setmPhone(split[4]);
                    record.sethPhone(split[5]);
                    record.setAdress(split[6]);
                    record.setMail(split[7]);
                    recordPojos.add(record);
                }
                reader.close();


            } catch (FileNotFoundException e) {
                     logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            }

        }
        return recordPojos;
    }

    @Override
    public List<RecordPojo> getFiltered(String keyword, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get filter records for user "+userName+" and filter keyword "+keyword); }
        ArrayList<RecordPojo> recordPojos=new ArrayList<>();
        File recordsFile=new File(FILES_ROOT+File.separatorChar+RECORD_FOLDER+File.separatorChar+userName+EXTENTION);
        if(! recordsFile.exists()){
            if (logger.isInfoEnabled()) {   logger.info("Records file  " + recordsFile.toString() + " not exist"); }
            return null;
        }
        else {


            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(recordsFile.toString()));
                String line;
                while ((line = reader.readLine()) != null&&line.split(";").length==8){
                    String[] split = line.split(";");

                    if(split[1].toLowerCase().contains(keyword.toLowerCase())||split[2].toLowerCase().contains(keyword.toLowerCase())||split[4].contains(keyword)){
                        RecordPojo record=new RecordPojo();
                        record.setId(Integer.parseInt(split[0]));
                        record.setfName(split[1]);
                        record.setsName(split[2]);
                        record.setmName(split[3]);
                        record.setmPhone(split[4]);
                        record.sethPhone(split[5]);
                        record.setAdress(split[6]);
                        record.setMail(split[7]);
                        recordPojos.add(record);

                    }


                }
                reader.close();


            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return null;
            }

        }
        return recordPojos;
    }

    @Override
    public void addRecord(RecordPojo recordPojo, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Add record for user "+userName); }
        File recordsFile=new File(FILES_ROOT+File.separatorChar+RECORD_FOLDER+File.separatorChar+userName+EXTENTION);
        if(! recordsFile.exists()){
            try {
                recordsFile.getParentFile().mkdirs();
                recordsFile.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();

            }
        }
        writeLine(recordsFile,recordPojo.toString());

    }

    @Override
    public void deleteRecord(int recordId, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Delete record for user "+userName+" and record id"+recordId); }
        File recordsFile=new File(FILES_ROOT+File.separatorChar+RECORD_FOLDER+File.separatorChar+userName+EXTENTION);
        List<RecordPojo> allRecords = getAllRecords(userName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(recordsFile);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFound " +e.getMessage());
            e.printStackTrace();
        }
        writer.print("");
        writer.close();
        Iterator<RecordPojo> readIterator = allRecords .iterator();
        while (readIterator.hasNext()){
            RecordPojo recordPojo = readIterator.next();

            if(recordPojo.getId()==recordId)
            {
            continue;
            }
            writeLine(recordsFile,recordPojo.toString());
        }

    }

    @Override
    public void editRecord(RecordPojo recordPojo, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Edit record for user "+userName+" and record id"+recordPojo.getId()); }
        File recordsFile=new File(FILES_ROOT+File.separatorChar+RECORD_FOLDER+File.separatorChar+userName+EXTENTION);
        List<RecordPojo> allRecords = getAllRecords(userName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(recordsFile);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        writer.print("");
        writer.close();
        Iterator<RecordPojo> readIterator = allRecords .iterator();
        while (readIterator.hasNext()){
            RecordPojo nextRecordPojo = readIterator.next();

            if(nextRecordPojo.getId()==recordPojo.getId())
            {
                 nextRecordPojo.setfName(recordPojo.getfName());
                 nextRecordPojo.setsName(recordPojo.getsName());
                 nextRecordPojo.setmName(recordPojo.getmName());
                 nextRecordPojo.setmPhone(recordPojo.getmPhone());
                 nextRecordPojo.sethPhone(recordPojo.gethPhone());
                 nextRecordPojo.setAdress(recordPojo.getAdress());
                 nextRecordPojo.setMail(recordPojo.getMail());
            }
            writeLine(recordsFile,nextRecordPojo.toString());
        }
    }

    @Override
    public RecordPojo getRecordById(int recordId, String userName) {
        if (logger.isDebugEnabled()) {   logger.debug("Get record by id for user "+userName+" and record id"+recordId); }
        List<RecordPojo> allRecords = getAllRecords(userName);
        Iterator<RecordPojo> readIterator = allRecords .iterator();
        while (readIterator.hasNext()){
            RecordPojo recordPojo = readIterator.next();

            if(recordPojo.getId()==recordId)
            {
                return recordPojo;
            }


        }
        logger.warn("Record not found:  for user "+userName+" and record id"+recordId);
        return null;
    }

    private  void writeLine(File file,String lineToWrite){
        Writer output;
        int id=countLines(file)+1;
        try {
            output = new BufferedWriter(new FileWriter(file, true));
            output.append(id+";"+lineToWrite);
            output.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }


    private  int countLines(File file){
        BufferedReader reader = null;
        int linesCount = 0;
        try {
            reader = new BufferedReader(new FileReader(file.toString()));

            while (reader.readLine() != null){
                linesCount++;
            }
            reader.close();

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return linesCount;
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return linesCount;
        }
return linesCount;
    }

    private List<String> fileLines(File file){
        Scanner sc = null;
        try {
            sc = new Scanner(file);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }


    return  lines;
    }
}
