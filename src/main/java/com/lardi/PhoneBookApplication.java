package com.lardi;

import com.lardi.config.ConnectToDB;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class PhoneBookApplication {
    final static private Logger logger = Logger.getLogger(PhoneBookApplication.class);
    private static String KEY_ARGUMENT = "-Dlardi.conf=";
    public static Properties PROPERTIES = new Properties();

    public static void main(String[] args) {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        Iterator<String> argumentsIterator = arguments.iterator();
        while (argumentsIterator.hasNext()) {
            String nextArgument = argumentsIterator.next();
            if (nextArgument.startsWith(KEY_ARGUMENT)) {
                FileInputStream input = null;
                String strFilePath = nextArgument.substring(KEY_ARGUMENT.length());
                if (logger.isInfoEnabled()) {
                    logger.info("Load config file: " + strFilePath);
                }
                try {
                    input = new FileInputStream(strFilePath);
                    PROPERTIES.load(input);
                } catch (IOException ex) {
                    logger.error("error open file: " + strFilePath + "  " + ex.getMessage());
                    ex.printStackTrace();
                    System.exit(1);
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

                break;
            }

        }


        if (PROPERTIES.getProperty("datastore").equals("files")) {
            File datastoreDir = new File(PROPERTIES.getProperty("files.location"));
            if (logger.isInfoEnabled()) {
                logger.info("FS chooised as datastore, test path: " + datastoreDir);
            }

            boolean dirExist = datastoreDir.exists();

            if (!dirExist) {

                if (logger.isInfoEnabled()) {
                    logger.info("Try create dir " + datastoreDir);
                }
                dirExist = datastoreDir.mkdirs();
            }
            if (!dirExist) {
                logger.error("Can not find or create " + datastoreDir);
                System.exit(1);
            }

        }


        if (PROPERTIES.getProperty("datastore").equals("db")) {
            if (logger.isInfoEnabled()) {   logger.info("DB chooised as datastore, test connection settings"); }
            ConnectToDB.getInstance();

        }


        SpringApplication.run(PhoneBookApplication.class, args);


    }
}

