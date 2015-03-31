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
    
    public static String VERTEX_LOG = "./vertex_log";
    public static String DIST_LOG = "./dist_log";
    public static String INTRA_EW_LOG = "./intra_log_";
    public static String INTER_EW_LOG = "./inter_log_";
    
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
    
    
    public static String getParameterFile()
    {
        return ParameterFile;
    }
}
