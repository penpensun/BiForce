package biforce.log;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Peng
 * This class stores some static variables: the paths of the temp files, path of the log file, etc.
 */
public class Setting {
    private static String tempMatrixFile = "./temp/tempParsedMatrix.txt";
    private static String tempResultFile ="./temp/tempMatrixResult.txt";
    private static String tempGraphFile = "./temp/tempGraph.txt";
    private static String ParameterFile = "./parameters/parameters.txt";
    
    private static String LogFile = "./log/BiForce.log";
    
    public static String getTempMatrixFile()
    {
        return tempMatrixFile;
    }
    
    public static String getTempResultFile()
    {
        return tempResultFile;
    }
    
    public static String getTempGraphFile()
    {
        return tempGraphFile;
    }
    
    public static String getLogFile()
    {
        return LogFile;
    }
    
    public static String getParameterFile()
    {
        return ParameterFile;
    }
}
