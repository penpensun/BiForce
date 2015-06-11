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
            float threshArray[],
            String graphIn,
            String graphOut,
            String clusterOut,
            String editingOut,
            boolean isHeader,
            boolean isInXmlFile,
            boolean isOutXmlFile,
            String paramFile,
            int slType,
            boolean isMultipleThresh
            )throws IOException{
        /* Read the parameters. */
        Param p  =new Param(paramFile);
        runGraph(graphType, thresh, threshArray,graphIn, graphOut, clusterOut, editingOut,
                isHeader, isInXmlFile, isOutXmlFile, p, slType,isMultipleThresh);
        
    }
    
    /**
     * This method runs nforce on different types of graphs.
     * @param graphType The type of the input graph.
     * @param thresh The threshold.
     * @param threshArray The threshold array for multiple thresholds.
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
            float thresh,
            float[] threshArray,
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
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
        /* Check the parameters. */
        if(graphType <=0 || graphType >=6)
            throw new IllegalArgumentException ("(runGraph) The graph type can only be [1-5]. ");
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
        // Set the thresholds
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
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
                inputGraph = new MatrixGraph(graphIn,isHeader, isInXmlFile);
                break;
            case 5: /* General npartite graph. */
                inputGraph = new MatrixGeneralNpartiteGraph(graphIn, isHeader, isInXmlFile);
                break;
            default: 
                throw new IllegalArgumentException("(runGraph) The given graph type is illegal.");   
        }
        /* Run the algorithm. */
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            inputGraph = algorithm.run(inputGraph, p, slType, isMultipleThresh); /* The main entrace. */
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
        if(clusterOut != null)
            inputGraph.writeClusterTo(clusterOut,isOutXmlFile);
        System.out.println("The resulted cluster is written to:  "+clusterOut);
        /* Output the editing details. */
        if(editingOut != null)
            inputGraph.writeResultInfoTo(editingOut);
        System.out.println("The editing information is written to:  "+editingOut);
    }
    
    public static MatrixGeneralNpartiteGraph runDrugRepos(float thresh,
            float[] threshArray,
            String graphIn,
            String paramFile,
            String editingOutput,
            boolean isMultipleThresh
            ){
        Param p = new Param(paramFile);
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
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
        /* Check if the file can be openend. */
        FileReader fr = null;
        FileWriter fw = null;
        try{
        fr = new FileReader(graphIn);
        }catch(IOException e){
            System.err.println("(runGraph) The input graph cannot be read. ");
            return null;
        }
        
        Graph2 inputGraph = new MatrixGeneralNpartiteGraph(graphIn,false, true); // With no header, with xml input.
        // Run nforce
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            inputGraph = algorithm.run(inputGraph, p, 1, isMultipleThresh);
        }catch(IOException e){
            System.err.println("(runGraphDrugReposPreClust) Algorithm running error.");
        }
        inputGraph.writeResultInfoTo(editingOutput);
        return (MatrixGeneralNpartiteGraph)inputGraph;
    }
    
    
    /**
     * This method is designed for drug repositiong preclustering.
     * In this method the vertex-- preclusters mapping, 
     * the preclusters -- index mapping will be outputted.
     * @param thresh
     * @param threshArray
     * @param graphIn
     * @param clusterOut
     * @param paramFile
     * @param slType
     * @param indexMappingOut
     * @param vertexMappingOut
     * @param assocOut
     * @param clusterPrefix
     * @param isMultipleThresh 
     * @return  
     */
    public static MatrixGraph runGraphDrugReposPreClust(float thresh,
            float[] threshArray,
            String graphIn,
            String clusterOut,
            String paramFile,
            int slType,
            String indexMappingOut,
            String vertexMappingOut,
            String clusterPrefix,
            boolean isMultipleThresh){
        Param p = new Param(paramFile);
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
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
        
        if(vertexMappingOut == null)
            throw new IllegalArgumentException("(runGraphDrugReposPreCluster) Vertex mapping output is null.");
        if(indexMappingOut == null)
            throw new IllegalArgumentException("(runGraphDrugReposPreCluster) Index mapping output is null.");
        
        /* Check if the file can be openend. */
        FileReader fr = null;
        FileWriter fw = null;
        try{
        fr = new FileReader(graphIn);
        }catch(IOException e){
            System.err.println("(runGraph) The input graph cannot be read. ");
            return null;
        }
        try{
            fw = new FileWriter(clusterOut);
        }catch(IOException e){
            System.err.println("(runGraph) The cluster output file writing error. ");
        }
        
        try{
            fw = new FileWriter(vertexMappingOut);
        }catch(IOException e){
            System.err.println("(runGraph) The ver mapping output file writing error:  "+vertexMappingOut);
        }
        
        try{
            fw = new FileWriter(indexMappingOut);
        }catch(IOException e){
            System.err.println("(runGraphDrugReposPreClust) Index mapping output file error:  "+indexMappingOut);
        }
        
        
        Graph2 inputGraph = new MatrixGraph(graphIn,false, true); // With no header, with xml input.
        // Run nforce
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            inputGraph = algorithm.run(inputGraph, p, slType, isMultipleThresh);
        }catch(IOException e){
            System.err.println("(runGraphDrugReposPreClust) Algorithm running error.");
        }
        // Output the cluster
        inputGraph.writeClusterTo(clusterOut, true);
        System.out.println("The resulted cluster is written to:  "+clusterOut);
        // Output the precluster -- index mapping.
        MatrixGraph mg   = (MatrixGraph)inputGraph;
        mg.writeXmlPreClusterIndexMapping(indexMappingOut);
        System.out.println("The preclusters -- index mapping is written to :  "+indexMappingOut);
        mg.writeVertexPreClusterMapping(vertexMappingOut,clusterPrefix);
        System.out.println("The vertex -- precluster mapping is written to:  "+vertexMappingOut);
        
        return mg;
    }
    /**
     * This method runs the algorithm on different types of graphs. 
     * @param graphType The type of the graphs: from 1 to 5.
     * 1 bipartite graph
     * 2 hierarchy npartite graph.
     * 3 hierarchy general graph.
     * 4 general graph
     * 5 general npartite graph.
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
     * @param isMultipleThresh Whether the input uses multiple threshold or not
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
            int slType,
            boolean isMultipleThresh
            ){
        
        /* Create the parameter object. */
        Param p = new Param(maxIter,
                fatt, frep,M0,dim,radius,thresh, threshArray, upperth,lowerth,step);
        runGraph(graphType,thresh, threshArray,graphIn,graphOut,clusterOut,editingOut, isHeader, isInXmlFile, isOutXmlFile, p,slType, isMultipleThresh);
    }
    
    /**
     * This method runs the algorithm on a given Graph2 object.
     * @param thresh
     * @param threshArray
     * @param inputGraph
     * @param graphOut
     * @param clusterOut
     * @param editingOut
     * @param isHeader
     * @param isInXmlFile
     * @param isOutXmlFile
     * @param paramFile
     * @param slType
     * @param isMultipleThresh 
     */
    public static void runGraph(
            float thresh,
            float[] threshArray,
            Graph2 inputGraph,
            String graphOut,
            String clusterOut,
            String editingOut,
            boolean isHeader,
            boolean isInXmlFile,
            boolean isOutXmlFile,
            String paramFile,
            int slType,
            boolean isMultipleThresh
            ){
        Param p = new Param(paramFile);
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
        /* Check the parameters. */
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
        
        if(inputGraph == null)
            throw new IllegalArgumentException("(runGraph) The input graph cannot be null.");
        if(clusterOut==null)
            throw new IllegalArgumentException("(runGraph) The graph output cannotbe null.");
        
        
        if(slType != 1 && slType != 2)
            throw new IllegalArgumentException("(runGraph) The type of single linkage clustering error:  "+slType);
        // Set the thresholds
        p.setThresh(thresh);
        p.setThreshArray(threshArray);
        /* Check if the file can be openend. */
        FileWriter fw = null;
        
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
        
        /* Run the algorithm. */
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            inputGraph = algorithm.run(inputGraph, p, slType, isMultipleThresh); /* The main entrace. */
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
}
