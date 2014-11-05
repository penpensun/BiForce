package biforce.log;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;

/**
 *
 * @author Administrator
 */
public class LogFile {
    static FileWriter logfw = null;
    static BufferedWriter logbw = null;
    static
    {
        try{
            logfw = new FileWriter(Setting.getLogFile(),true);
            logbw = new BufferedWriter(logfw);
        }catch(IOException e){System.out.println("Log File init error");
        }
        
    }
    
    public static void writeLog(String Log) throws IOException
    {
        logbw.write(Log);
        logbw.flush();
    }
}
