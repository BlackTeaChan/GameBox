package com.blackteachan.gamebox.utils;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * @author blackteachan
 */
public class LogUtil {

    private static boolean print = true;

    private Logger log;

    public LogUtil(Class clazz){
        log = Logger.getLogger(clazz);
    }

    public void d(Object message){
        if(print) {
            log.debug(message);
        }
    }

    public void i(Object message){
        if(print) {
            log.info(message);
        }
    }

    public void e(Object message){
        if(print) {
            log.error(message);
        }
    }

    public static void enablePrint(){
        print = true;
    }

    public static void disablePrint(){
        print = false;
    }

}
