/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import biforce.graphs.BipartiteGraph;

/**
 *
 * @author Peng
 */
public class NoUsageRunGraph {
    public static void main(String args[]) throws IOException
    {
        String input = null;
        String paramfile="./parameters.ini";
        String graphout = null;
        String clusterout = null;
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
                case "-out":
                    clusterout = value;
                    break;
                case "-o":
                    clusterout = value;
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
                case "-graphout":
                    graphout = value;
                    break;
                case "-t":
                    thresh= Double.parseDouble(value);
                    break;
                case "-thresh":
                    thresh =Double.parseDouble(value);
                    break;
            }
        }
        
        if(input == null || paramfile == null || clusterout== null )
                throw new IllegalArgumentException("Not all the parameters are initialized");
        if(graphout== null)
            graphout = new StringBuilder(input).append(".out").toString();
        
        
        runGraph(input,clusterout,graphout,paramfile,thresh);
    }
    
    public static void runGraph(String input, String clusterout, String graphout, String paramfile, double thresh) throws IOException
    {
            
        Param p = Param.readParams(paramfile);
        
        if(thresh != -Double.MAX_VALUE)
            p.setThresh(thresh);
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
        
}
