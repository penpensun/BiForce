/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package performance;
import biforce.graphs.MatrixBipartiteGraph;
import biforce.graphs.MatrixBipartiteGraph2;
import biforce.graphs.MatrixHierNpartiteGraph;
import biforce.graphs.MatrixHierGeneralGraph;
import biforce.algorithms.BiForceOnGraph4;
import biforce.algorithms.BiForceOnGraph4;
import biforce.algorithms.BiForceOnGraph4;
import biforce.algorithms.Param;
import biforce.algorithms.Param;
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
    int repeat = 5; /* How many repeats of artificial graphs to generate . */
    double mean = 20;
    double dev = 10;
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
    public ArrayList<Double> graphGenerator(String type, 
            int size,int[] setSizes, int number, double mean, double dev, 
            String outputPrefix, String costsOutput){
        SimGraphGen gen = new SimGraphGen();
        /* The ArrayList<Double> contains the standard costs. */
        ArrayList<Double> stdCosts = new ArrayList<>(number);
        /* Generate graphs according to types. */
        if(type.equals("npartite")){
            /* Check if the set sizes are null.*/
            if(setSizes == null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for npartite graph generation.");
            /* Generate n-partite graphs.*/
            for(int i=0;i<number;i++){
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(gen.generatorHierNpartiteGraph(setSizes, outputPrefix+"_"+i+".txt", mean, dev));
            }
        }
        else if(type.equals("hiergeneral")){
            /* Check if the set sizes are null.*/
            if(setSizes== null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for hiergeneral graph generation.");
            /* Generate hiergeneral graphs. */
            for(int i=0;i<number;i++){
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(gen.generateHierGeneralGraph(setSizes, outputPrefix+"_"+i+".txt", mean, dev));
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
                stdCosts.add(gen.generatorGeneralGraph1(size, outputPrefix+"_"+i+".txt", mean, dev));
        }
        else if (type.equals("hiergeneralxml")){
             /* Check if the set sizes are null.*/
            if(setSizes== null)
                throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal setSizes for hiergeneral graph generation.");
            /* Generate hiergeneral graphs. */
            for(int i=0;i<number;i++){
                /* Generate the graph and add the cost into the stdCosts. */
                stdCosts.add(gen.generateHierGeneralGraphXml(setSizes, outputPrefix+"_"+i+".txt", mean, dev));
            }
        }
        else 
            throw new IllegalArgumentException("(PerformanceAnalyzer.graphGenerator) Illegal type.");
        
        /* Output the costs into the file costsOutput.*/
        try{
        FileWriter costsFw= new FileWriter(costsOutput);
        BufferedWriter costsBw = new BufferedWriter(costsFw);
        /* Output the file names and the costs. */
        for(int i=0;i<number;i++){
            costsBw.write(stdCosts.get(i)+"\t"+outputPrefix+"_"+i+".txt\n");
        }
        costsBw.flush();
        costsBw.close();
        costsFw.close();
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.graphGenerator) Illegal costs output: "+costsOutput);
            e.printStackTrace();
        }
        
        return stdCosts;
    }
    /**
     * This method generates artificial graphs with 400 nodes, evenly distributed in 4 levels, with 5 repeats.
     */
    public void generateGraphs400Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/400nodes/xml_hier_general_400nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_400nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {100,100,100,100};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    /**
     * This method generates artificial graphs with 800 nodes, evenly distributed in 4 levels, with 5 repeats.
     */
    public void generateGraphs800Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/800nodes/xml_hier_general_800nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_800nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {200,200,200,200};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    
    /**
     * This method generates artifical graphs with 1600 nodes, evenly distributed in 4 levels, with 5 repeats.
     */
    public void generateGraphs1600Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/1600nodes/xml_hier_general_1600nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_1600nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {400,400,400,400};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    
    /**
     * This method generates artificial graphs with 3200 nodes, evenly distributed in 4 levels, with 5 repeats.
     */
    public void generateGraphs3200Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/3200nodes/xml_hier_general_3200nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_3200nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {800,800,800,800};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    /**
     * This method generates sim graphs of 6400 nodes.
     */
    public void generateGraphs6400Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/6400nodes/xml_hier_general_6400nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_6400nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {1600,1600,1600,1600};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    
    /**
     * This method generates sim graphs of 9600 nodes.
     */
    public void generateGraphs9600Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/9600nodes/xml_hier_general_9600nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_9600nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {2400,2400,2400,2400};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,3,mean,dev,
                inputPrefix,costsOutput);
    }
    
    /**
     * This method generates sim graphs of 12800 nodes.
     */
    public void generateGraphs12800Nodes(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/12800nodes/xml_hier_general_12800nodes";
        String costsOutput ="../../data/testdata/performance_profile/standard_costs/xml_hier_general_12800nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {3200,3200,3200,3200};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    
    
    /**
     * 
     */
    public void generateSingleGraph(){
        String inputPrefix="../../data/testdata/performance_profile/inputs/xml_hier_general_800nodes";
        String costsOutput="../../data/testdata/performance_profile/standard_costs/xml_hier_general_800nodes_costs.txt";
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {4200,4200,4200,4200};
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
    }
    
    
    
    public void runAlgorithmOnSingleFile(String input, String resultOutput,
            String paramFile, boolean isXml){
        
        /* Create the output file. */
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(resultOutput);
            bw = new BufferedWriter(fw);
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) Result file error.");
            return;
        }
        
        try{
            bw.write("Input prefix\tStandard costs\tEditing costs\r\n");
        }catch(IOException e){}
        /* Read the input graph.*/
        MatrixHierGeneralGraph hierGeneralInput =
                new MatrixHierGeneralGraph(input,false,isXml,0);
        BiForceOnGraph4 biforce = new BiForceOnGraph4();
        Param p = Param.readParams(paramFile);
        try{
            biforce.run(hierGeneralInput,p);
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) biforce running error.");
        }
        /* Output the result into the resultOutput file. */
        try{
            bw.write(input+"\t"+hierGeneralInput.getCost()+"\r\n");
            bw.flush();
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) biforce result outputting error.");
        }

        try{
            bw.close();
            fw.close();
        }catch(IOException e){
            System.out.println("(PerformanceAnalyzer.runAlgorithm) file closing error.");
        }
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
    public void runAlgorithm(String inputPrefix, String resultOutput,String paramFile, boolean append,
            ArrayList<Double> stdCosts, int number, boolean isXml){
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
                    new MatrixHierGeneralGraph(inputPrefix+"_"+i+".txt",false,isXml,0);
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
    public void performanceAnalysisOnHierGeneralGraph() {
        String resultOutput = "../../data/testdata/performance_profile/costs_comparision.txt";
        String inputFolder = "../../data/testdata/performance_profile/inputs/";
        String costsOutput = "../../data/testdata/performance_profile/standard_costs.txt";
        String paramFile = "./parameters.ini"; /* This is the parameter file for biforce. */
        String inputPrefix=  "../../data/testdata/performance_profile/inputs/hiergeneral_50nodes";
        int number = 10; /* This is the number of repeats. */
        double mean = 20;  /* The mean of the normal distribution. */
        double dev = 10; /* The deviation of the normal distribution .*/
        
        /* Each graph is with four levels. The vertices are evenly distributed. */
        /* For the graphs with 1600 nodes. */
        int setSizes[] = {400,400,400,400}; /* The 50 nodes are distributed to each level as 14,12,12,12 nodes. */
        ArrayList<Double> stdCosts=  graphGenerator("hiergeneral",-1,setSizes,number,mean,dev,inputPrefix,costsOutput);
        /* Run BiforceOnGraph4 on the graphs with 50 nodes. */
        runAlgorithm(inputPrefix, resultOutput,paramFile,true, stdCosts,number,false);
    }
    
    
    public void performanceAnalysisOnXmlHierGeneralGraph(){
        String resultOutput = "../../data/testdata/performance_profile/costs_comparison.txt";
        String inputFolder = "../../data/testdata/performance_profile/inputs/";
        String costsOutput = "../../data/testdata/performance_profile/standard_costs.txt";
        String paramFile="./parameters.ini"; /* This is the parameter file for biforce. */
        String inputPrefix="../../data/testdata/performance_profile/inputs/xml_hier_general_1600nodes";
        int repeat = 1;
        double mean = 20;
        double dev=10;
        /* Each graph is with four levels. The verices are evenly distributed. */
        int setSizes[] = {1600,1600,1600,1600};
        ArrayList<Double> stdCosts = graphGenerator("hiergeneralxml",-1,setSizes,repeat,mean,dev,
                inputPrefix,costsOutput);
        /* Run BiforceGraph4 on the graphs with 1600 nodes.*/
        runAlgorithm(inputPrefix, resultOutput,paramFile,true,stdCosts,repeat,true);
    }
    
    public void generateExternIntraMatrix(String outputFile, int manyRows){
        SimGraphGen gen = new SimGraphGen();
        gen.writeRandomIntraMatrix(outputFile, manyRows, mean, dev);
    }
    
    public void generateExternInterMatrix(String outputFile,int manyRows, int manyCols){
        SimGraphGen gen = new SimGraphGen();
        gen.writeRandomInterMatrix(outputFile, manyRows, manyCols, mean, dev);
    }
    
    public void generateExternNodeNames(){
        String outputFile = "../../data/testdata/performance_profile/inputs/25600nodes/xml_hier_general_25600nodes_names.txt";
        int[] setSizes= {6400,6400,6400,6400};
        SimGraphGen gen = new SimGraphGen();
        gen.writeNodeNames(setSizes, outputFile);
    }
    
    public static void main(String[] args) throws IOException{
        PerformanceAnalyzer analyzer = new PerformanceAnalyzer();
        //performanceAnalysisOnHierGeneralGraph();
        //performanceAnalysisOnXmlHierGeneralGraph();
        //analyzer.generateSingleGraph();
        //long start = System.currentTimeMillis();
        
        analyzer.runAlgorithmOnSingleFile(
               "../../data/testdata/performance_profile/inputs/25600nodes/xml_hier_general_graph_25600nodes.txt",
                "../../data/testdata/performance_profile/outputs/25600nodes/xml_hier_general_25600nodes_output.txt",
                "./parameters.ini",
                true);
        
        //long end = System.currentTimeMillis();
        //System.out.println("Running time:  "+(end-start)/1000.0);
        /* Generate the sim graphs. */
        //analyzer.generateGraphs400Nodes();
        //analyzer.generateGraphs800Nodes();
        //analyzer.generateGraphs1600Nodes();
        //analyzer.generateGraphs3200Nodes();
        //analyzer.generateGraphs6400Nodes();
        //analyzer.generateGraphs9600Nodes();
        //analyzer.generateGraphs12800Nodes();
        //analyzer.generateExternNodeNames();
        /*
        analyzer.generateExternIntraMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_0_0.txt", 6400);
        analyzer.generateExternIntraMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_1_1.txt", 6400);
        analyzer.generateExternIntraMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_2_2.txt", 6400);
        analyzer.generateExternIntraMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_3_3.txt", 6400);
                */
        //analyzer.generateExternInterMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_0_1.txt", 6400, 6400);
        //analyzer.generateExternInterMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_1_2.txt", 6400, 6400);
        //analyzer.generateExternInterMatrix("../../data/testdata/performance_profile/inputs/25600nodes/matrix_2_3.txt", 6400, 6400);
    }
}
