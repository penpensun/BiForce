/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.io;
import java.io.IOException;
import java.io.*;
import biforce.algorithms.*;
import biforce.graphs.*;
/**
 * Main class version 3. 
 * The release 1.0 version.
 * 
 * @author Peng Sun
 */
public class Main3 {
    public static void main(String args[]){
        FileReader fr =null;
        String nullfile = null;
        try{
            fr = new FileReader(nullfile);
        }catch(IOException e){
            System.out.println("Exception catched");
        }
    }
    
    public static void runGraph(int graphType,
            float thresh,
            String graphIn,
            String graphOut,
            String clusterOut,
            String editingOut,
            boolean isHeader,
            boolean isInXmlFile,
            boolean isOutXmlFile,
            String paramFile,
            int slType
            )throws IOException{
        /* Read the parameters. */
        Param p  =new Param(paramFile);
        p.setThresh(thresh);
        runGraph(graphType, thresh, graphIn, graphOut, clusterOut, editingOut,
                isHeader, isInXmlFile, isOutXmlFile, p, slType);
        
    }
    
    /**
     * This method runs nforce on different types of graphs.
     * @param graphType The type of the input graph.
     * @param thresh The threshold.
     * @param threshArray The threshold array for multi-threshold computing.
     * @param graphIn The graph input file.
     * @param graphOut The graph output file.
     * @param clusterOut The cluster output file.
     * @param editingOut The editing output file.
     * @param isHeader Does the input file have a header.
     * @param isInXmlFile
     * @param isOutXmlFile
     * @param p 
     * @param slType 
     * @param isMultipleThresh  If a multiple threshold is used in computing.
     */
    public static void runGraph(int graphType,
            String graphIn,
            String graphOut,
            String clusterOut,
            String editingOut,
            boolean isHeader,
            boolean isInXmlFile,
            boolean isOutXmlFile,
            Param p,
            int slType,
            boolean isMultipleThresh){
        /* Check the parameters. */
        if(graphType <=0 || graphType >=5)
            throw new IllegalArgumentException ("(runGraph) The graph type can only be [1-4]. ");
        if(isMultipleThresh && p.getThreshArray() == null)
            throw new IllegalArgumentException("(runGraph) Multiple thresh array is null. ");
        if(!isMultipleThresh && Float.isNaN(p.getThresh()))
            throw new IllegalArgumentException("(runGraph) The threshold must be a number.");
        if(p.getLowerth()<0)
            throw new IllegalArgumentException("(runGraph) The lower-bound of the threshold cannot be smaller than 0.");
        if(p.getUpperth() <0)
            throw new IllegalArgumentException("(runGraph) The upper-bound of the threshold cannot be smaller than 0.");
        if(p.getStep()<0)
            throw new IllegalArgumentException("(runGraph) The step of the threshold cannot be smaller than 0.");
        
        if(graphIn == null)
            throw new IllegalArgumentException("(runGraph) The input graph cannot be null.");
       
        if(clusterOut==null)
            throw new IllegalArgumentException("(runGraph) The graph output cannotbe null.");
        
        
        if(slType != 1 && slType != 2)
            throw new IllegalArgumentException("(runGraph) The type of single linkage clustering error:  "+slType);
        /* Check if the file can be openend. */
        FileReader fr = null;
        FileWriter fw = null;
        try{
        fr = new FileReader(graphIn);
        }catch(IOException e){
            System.err.println("(runGraph) The input graph cannot be read. ");
            return;
        }
        
        try{
            fw = new FileWriter(clusterOut);
        }catch(IOException e){
            System.err.println("(runGraph) The cluster output file writing error. ");
        }
        
        if(editingOut != null){
            try{
                fw.close();
                fw = new FileWriter(editingOut);
            }catch(IOException e){
                System.err.println("(runGraph) The editing details output file writing error.");
            }
        }
        if(graphOut != null){
            try{
                fw = new FileWriter(graphOut);
            }catch(IOException e){
                System.err.println("(runGrpah) The graph output file writing error.");
            }
        }
        
        try{
            fw.close();
        }catch(IOException e){};
        
        /* Create the graph according to different graph type. */
        Graph2 inputGraph = null;
        switch(graphType){
            case 1: /* bipartite graph. */
                inputGraph = new MatrixBipartiteGraph2(graphIn,isHeader); 
                break;
            case 2:/* hierarchy npartite graph. */
                inputGraph = new MatrixHierNpartiteGraph(graphIn, isHeader);
                break;
            case 3: /* hierarchy general graph. */
                inputGraph = new MatrixHierGeneralGraph(graphIn,isHeader,isInXmlFile);
                break;
            case 4: /* General graph. */
                inputGraph = new MatrixGraph(graphIn,isHeader);
                break;
            default: 
                throw new IllegalArgumentException("(runGraph) The given graph type is illegal.");
                
        }
        /* Run the algorithm. */
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            boolean isMultipleThresh = (p.getThreshArray() == null);
            inputGraph = algorithm.run(inputGraph, p, slType); /* The main entrace. */
        }catch(IOException e){
            System.err.println("(runGraph) The algorithm error.");
            return;
        }
        /* Output the graph. */
        System.out.println("Start writing the results.");
        if(graphOut != null)
            inputGraph.writeGraphTo(graphOut, isOutXmlFile);
        System.out.println("The resulted graph is written to:  "+graphOut);
        /* Output the cluster. */
        inputGraph.writeClusterTo(clusterOut,isOutXmlFile);
        System.out.println("The resulted cluster is written to:  "+clusterOut);
        /* Output the editing details. */
        if(editingOut != null)
            inputGraph.writeResultInfoTo(editingOut);
        System.out.println("The editing information is written to:  "+editingOut);
    }
    
    /**
     * This method runs the algorithm on different types of graphs. 
     * @param graphType The type of the graphs: from 1 to 4.
     * 1 bipartite graph
     * 2 hierarchy npartite graph.
     * 3 hierarchy general graph.
     * 4 general graph
     * @param thresh The threshold for an edge.
     * @param threshArray The array of thresholds, used only in npartite hierarchy general graph or npartite hierarchy graph.
     * @param fatt The fatt coefficient.
     * @param frep The frep coefficient. 
     * @param maxIter The maximum iteration.
     * @param M0 The M0.
     * @param dim The number of dimensions.
     * @param graphIn The path of the input graph.
     * @param graphOut The path to output the graph.
     * @param lowerth The lower-bound for the single linkage search
     * @param step The step of increment of single linkage search.
     * @param isHeader
     * @param radius The radius for the circle in the first step of random layout.
     * @param upperth The upper-bound for the single linkage search.
     * @param clusterOut The path to output the cluster result.
     * @param editingOut The path to output the details of the editing cost.
     * @param isInXmlFile  This is the input format: false, plain format; true, xml format.
     * @param isOutXmlFile This is the output format: false, plain foramt; true, xml format.
     * @param slType This is the type of clustering: 1. Classic single-linkage clustering. 2. Single-linkage chain clustering.
     */
    public static void runGraph(int graphType,
            float thresh,
            float[] threshArray,
            float fatt, float frep,
            int maxIter, float M0, int dim,
            float radius, 
            float upperth,
            float lowerth,
            float step,
            boolean isHeader,
            boolean isInXmlFile,
            boolean isOutXmlFile,
            String graphIn,
            String graphOut,
            String clusterOut,
            String editingOut,
            int slType
            ){
        
        /* Create the parameter object. */
        Param p = new Param(maxIter,
                fatt, frep,M0,dim,radius,thresh, threshArray, upperth,lowerth,step);
        runGraph(graphType, thresh,graphIn,graphOut,clusterOut,editingOut, isHeader, isInXmlFile, isOutXmlFile, p,slType);
    }
    
    
    
}
