package com.lis.listest.tools;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;


public class Log {
    public static void l(String msg){
        l(msg,LogType.INFO);
    }
    public static void l(String msg,LogType logType){
        String s = "+-+-+-+-+-+-+-   " ;
        if(logType == LogType.INFO)
            s+="INFO:  ";
        else if(logType==LogType.ERR)
            s+="ERROR:  ";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss a");
        s+=simpleDateFormat.format(new Date())+":  ";
        s+=msg;

        System.out.println(s);
    }
}