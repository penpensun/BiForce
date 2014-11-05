/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import biforce.graphs.MatrixBipartiteGraph;
import biforce.graphs.MatrixBipartiteGraph2;
import biforce.graphs.MatrixHierNpartiteGraph;
import biforce.graphs.MatrixHierGeneralGraph;
import biforce.algorithms.BiForceOnGraph4;
import java.io.FileWriter;
import java.io.BufferedWriter;
import biforce.sim.SimGraphGen;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class analyzes the performance of BiForceOnGraph4on, including:
 * (1) Cost deviations from the standard costs.
 * (2) The running times on various sizes of the input graphs.
 * (3) The memory consumption of various sizes of input graphs.
 * on
 * (1) GeneralHierGraph
 * (2) GeneralGraph
 * (3) NpartiteGraph
 * (4) BipartiteGraph
 * @author Peng Sun
 */
public class PerformanceAnalyzer {
    /**
     * This method generates the artificial graphs for performance analysis.
     * @param type
     * @param size
     * @param setSizes
     * @param number
     * @param mean
     * @param dev 
     * @param outputPrefix 
     * @return  
     */
    public static ArrayList<Double> graphGenerator(String type, int size,int[] setSizes, int number, double mean, double dev, String outputPrefix){
        /* The ArrayList<Double> contains the standard costs. */
        ArrayList<Double> stdCosts = new ArrayList<Double>(number);
        /* Generate graphs according to types. */
        if(type.equals("npartite")){
            /* Check if the set sizes are null.*/
            if(setSizes == null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for npartite graph generation.");
            /* Generate n-partite graphs.*/
            for(int i=0;i<number;i++){
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(SimGraphGen.generatorHierNpartiteGraph(setSizes, outputPrefix+"_"+i+".txt", mean, dev));
            }
        }
        else if(type.equals("hiergeneral")){
            /* Check if the set sizes are null.*/
            if(setSizes== null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for hiergeneral graph generation.");
            /* Generate hiergeneral graphs. */
            for(int i=0;i<number;i++){
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(SimGraphGen.generatorHierGeneralGraph(setSizes, outputPrefix+"_"+i+".txt", mean, dev));
            }
        }
        else if(type.equals("bipartite")){
            System.out.println("Not available at the current stage.");
            /* Check if set sizes are null. */
            if(setSizes == null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for bipartite graph generation.");
            
        }
        else if(type.equals("general")){
            /* Check if the number of nodes is larger than 0. */
            if(size <= 0)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal size for general graph generation.");
            /* Generate general graphs. */
            for(int i=0;i<number;i++)
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(SimGraphGen.generatorGeneralGraph1(size, outputPrefix+"_"+i+".txt", mean, dev));
        }
        else 
            throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal type.");
        
        return stdCosts;
    }
    
    /**
     * This method runs BiForceOnGraph4 on all files in inputFolder and output the standard costs
     * as well as the resulting editing costs into resultOutput.
     * @param inputFolder
     * @param inputPrefix
     * @param resultOutput
     * @param stdCosts
     * @param append
     * @param paramFile 
     * @param number 
     */
    public static void runAlgorithm(String inputPrefix, String resultOutput,String paramFile, boolean append,
            ArrayList<Double> stdCosts, int number){
        /* Create the output file. */
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(resultOutput,append);
            bw = new BufferedWriter(fw);
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) Result file error.");
        }
        
        try{
        bw.write("Input prefix\tStandard costs\tEditing costs\r\n");
        }catch(IOException e){}
        /* Run BiforceOnGraph4 on the graph. */
        for(int i=0;i<number;i++){
            /* Read the input graph.*/
            MatrixHierGeneralGraph hierGeneralInput =
                    new MatrixHierGeneralGraph(inputPrefix+"_"+i+".txt",false,0);
            BiForceOnGraph4 biforce = new BiForceOnGraph4();
            Param p = Param.readParams(paramFile);
            try{
            biforce.run(hierGeneralInput,p);
            }catch(IOException e){
                System.out.println("(PerformanceAnalyzer.runAlgorithm) biforce running error.");
            }
            /* Output the result into the resultOutput file. */
            try{
            bw.write(inputPrefix+"\t"+stdCosts.get(i)+"\t"+hierGeneralInput.getCost()+"\r\n");
            bw.flush();
            }catch(IOException e){
                System.out.println("(PerformanceAnalyzer.runAlgorithm) biforce result outputting error.");
            }
        }
        try{
        bw.close();
        fw.close();
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) file closing error.");
        }
    }
    
    /**
     * In this method, we generate
     */
    public static void performanceAnalysisOnHierGeneralGraph() {
        String resultOutput = "../../data/testdata/performance_profile/costs_comparision.txt";
        String inputFolder = "../../data/testdata/performance_profile/inputs/";
        String paramFile = "./parameters.ini"; /* This is the parameter file for biforce. */
        String inputPrefix=  "../../data/testdata/performance_profile/inputs/hiergeneral_50nodes";
        int number = 10; /* This is the number of repeats. */
        double mean = 20;  /* The mean of the normal distribution. */
        double dev = 10; /* The deviation of the normal distribution .*/
        
        /* Each graph is with four levels. The vertices are evenly distributed. */
        /* For the graphs with 1600 nodes. */
        int setSizes[] = {400,400,400,400}; /* The 50 nodes are distributed to each level as 14,12,12,12 nodes. */
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneral",-1,setSizes,number,mean,dev,inputPrefix);
        
        /* Run BiforceOnGraph4 on the graphs with 50 nodes. */
        runAlgorithm(inputPrefix, resultOutput,paramFile,true, stdCosts,number);
    }
    
    public static void main(String[] args) throws IOException{
        performanceAnalysisOnHierGeneralGraph();
    }
}
