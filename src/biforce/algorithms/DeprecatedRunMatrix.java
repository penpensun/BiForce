/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import biforce.matrices.BipartiteGraphMatrix;
/**
 * This class is the main class for biforce on matrix.
 * @author Peng Sun
 * 
 */
public class DeprecatedRunMatrix {
    
    /**
     * The main entrance
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException
    {
        String input = null;
        String paramfile="./parameters.ini";
        String clustermode=null;
        String clusterout=null;
        int sizerank = 1;
        boolean hasHeader  = true; 
        double thresh = -Double.MAX_VALUE;
        
        for(String arg: args)
        {
            String prefix = arg.substring(0,arg.indexOf("="));
            String value = arg.substring(arg.indexOf("=")+1);
            switch(prefix){
                case "-i":
                    input=value;
                    break;
                case "-input":
                    input = value;
                    break;
                //cluster mode
                case "-clustermode":
                    clustermode = value;
                    break;
                case "-m":
                    clustermode=value;
                    break;
                case "-o":
                    clusterout = value;
                    break;
                case "-output":
                    clusterout = value;
                    break;
                case "-param":
                    paramfile =value;
                    break;
                case "-p":
                    paramfile = value;
                    break;
                case "-sizerank":
                    sizerank = Integer.parseInt(value);
                    break;
                case "-h":
                    hasHeader = Boolean.parseBoolean(value);
                    break;
                case "-thresh":
                    thresh = Double.parseDouble(value);
                    break;
                case "-t":
                    thresh = Double.parseDouble(value);
            }
        }
        
         if(input == null || paramfile == null || clusterout== null ||clustermode == null)
                throw new IllegalArgumentException("Not all the parameters are initialized");
        
         runMatrix(input,clustermode,clusterout, sizerank,hasHeader,paramfile, thresh);
    }
    
    public static void runMatrix(String input, String clusterMode, String clusterout, 
            int sizerank, boolean hasHeader,
            String paramfile, double thresh) throws IOException
    {
       Param p = Param.readParams(paramfile);
       if(thresh != -Double.MAX_VALUE)
           p.setThresh((float)thresh);
       
       BufferedWriter bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<\\output> "+input+"\r\n");
       bw.close();
       new BiForceOnMatrix2().runMatrix(new BipartiteGraphMatrix(input,hasHeader), clusterMode, p).writeClusters(clusterout);
       //write the file ends
       bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<\\output>\r\n");
       bw.flush();
       bw.close();
    }
    
    
}
