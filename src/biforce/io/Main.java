/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.io;
import biforce.algorithms.BiForceOnGraph2;
import biforce.algorithms.BiForceOnMatrix2;
import biforce.algorithms.Param;
import biforce.graphs.BipartiteGraph;
import biforce.matrices.BipartiteGraphMatrix;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
/**
 *
 * @author Peng
 */
public class Main {
    
    /**
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException
    {
        String input=null;
        String paramfile=null;
        String type=null;
        String clustermode=null;
        String output=null;
        String graphout = null;
        int sizerank = 1;
        boolean hasHeader  = true;
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
                case "-type":
                    type = value;
                    break;
                case "-t":
                    type =value;
                    break;
                //cluster mode
                case "-clustermode":
                    clustermode = value;
                    break;
                case "-m":
                    clustermode=value;
                    break;
                case "-o":
                    output = value;
                    break;
                case "-output":
                    output = value;
                    break;
                case "-param":
                    paramfile =value;
                    break;
                case "-p":
                    paramfile = value;
                    break;
                case "-go":
                    graphout = value;
                    break;
                case "-sizerank":
                    sizerank = Integer.parseInt(value);
                    break;
                case "-h":
                    hasHeader = Boolean.parseBoolean(value);
                    break;
            }
            
            //check if all parameters are initialized
            if(input == null || paramfile == null || type == null|| clustermode == null ||
                    output == null)
                throw new IllegalArgumentException("Not all the parameters are initialized");
            
            
            type = type.trim();
            //graph input
            if(type.equals("g")|| type.equals("graph"))
                runGraph(input,output, graphout, paramfile);
            else if(type.equals("m") || type.equals("matrix"))
                runMatrix(input,clustermode,output,sizerank,hasHeader, paramfile);
            
        }
    }
    
    
    public static void runGraph(String input, String clusterout, String graphout, String paramfile) throws IOException
    {
       Param p = Param.readParams(paramfile);
       BipartiteGraph graph = new BipartiteGraph(input);
       BiForceOnGraph2.run(graph,p);
       BufferedWriter bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<output>"+input+"\r\n");
       bw.close();
       graph.writeResult(clusterout);
       bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<\\output>\r\n");
       bw.flush();
       
       if(graphout!= null)
           graph.writeGraph(graphout);
    }
    
    public static void runMatrix(String input, String clusterMode, String clusterout, 
            int sizerank, boolean hasHeader,
            String paramfile) throws IOException
    {
       Param p = Param.readParams(paramfile);
       BufferedWriter bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<input> "+input+"<\\input>\r\n");
       bw.write("<output>\r\n");
       bw.close();
       BiForceOnMatrix2.runMatrix(new BipartiteGraphMatrix(input,hasHeader), clusterMode, p).writeClusters(clusterout);
       //write the file ends
       bw = new BufferedWriter(new FileWriter(clusterout,true));
       bw.write("<\\output>\r\n");
       bw.flush();
       bw.close();
    }
    
    
    /**
     * Test methods
     * This test method run biforce on "almost-bicluster-graphs" which are used to 
     */
    public static void testgraphs() throws IOException
    {
        String inputfolder = "D:\\MPI\\projects\\BiClustering\\bicluster_editing\\editing_costs\\inputs";
        String outputfile = "D:\\MPI\\projects\\BiClustering\\bicluster_editing\\editing_costs\\biforceV2";
        String paramfile = "./parameters.ini";
        String[] files = new File(inputfolder).list();
        for(String file:files)
        {
            runGraph(inputfolder+"\\"+file, outputfile,null,paramfile);
        }
        
    }
    
    public static void testmatrices() throws IOException
    {
        String inputfolder = "D:\\MPI\\projects\\BiClustering\\art_matrix\\inputs\\";
        String outputfile = "D:\\MPI\\projects\\BiClustering\\art_matrix\\biforce_out";
        String paramfile = "./parameters.ini";
        String[] files = new File(inputfolder).list();
        for(String file: files)
        {
            runMatrix(inputfolder+file,"o",
                    outputfile,1,false,
                    paramfile);
        }
    }
    
}
