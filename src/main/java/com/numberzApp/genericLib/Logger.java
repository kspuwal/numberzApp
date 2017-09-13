package com.numberzApp.genericLib;

import org.slf4j.LoggerFactory;


public class Logger {

    private static org.slf4j.Logger LOGGER;
    //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
   // System.


    public Logger(Class<?> clazz) {
        LOGGER = LoggerFactory.getLogger(clazz);
    }

    public void logUseCase(String description) {
        LOGGER.debug("*****************************");
        LOGGER.debug(description);
        LOGGER.debug("*****************************");
    }

    public void debug(String description) {
        LOGGER.debug(description);
        //System.out.println(description);
    }
}

