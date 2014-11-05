/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.sim;
import java.util.ArrayList;
import biforce.graphs.*;
import java.util.Random;
import java.util.Collections;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
/**
 * This class generates the simulated (artificial) graphs for study.
 * @author penpen926
 */
public class SimGraphGen {
    /**
     * Graph generator 1: 
     * To generate sim. graphs with given number of vertices and 
     * randomly assign the edge weights following to a norm distribution with given 
     * mean and standard deviation. The threshold is fixed to be 0.
     * 
     * This sim graph generator is for general graph.
     * @param vtxNum 
     * @return The ideal editing cost.
     */
    public static double generatorGeneralGraph1(int vtxNum, String outputFile, 
            double mean, double stdev){
        /* The pivots pivotIndexes stands tell the division of the pre-defined clusters.
         Each cluster contains the vertices with index from pivotIdexes[i] to (but not
         include) pivotIndexes[i+1] i>=0. For the first cluster, we can infer its
         size.*/
        ArrayList<Integer> pivotIndexes = new ArrayList<>();
        int assignedElements = 0; /* Number of elements assigned. */
        do{
            /* Randomly get an integer from 1 to vtxNum-assignedElements. */
            assignedElements += (int)( Math.random()*(vtxNum- assignedElements)+1);
            pivotIndexes.add(assignedElements);
            
        }while(vtxNum-assignedElements != 0);
                
        /* Now create the edge weight matrix. */
        double[][] edgeWeights = new double[vtxNum][vtxNum] ;
        for(int i =0;i<edgeWeights.length;i++)
            for(int j=0;j<edgeWeights[0].length;j++)
                edgeWeights[i][j] = Double.NaN;
        /* Init the cost. */
        double cost = 0;
        /* Assign the edge weights. */
        for(int i=0;i<edgeWeights.length;i++)
            for(int j=i+1;j<edgeWeights[0].length;j++){
                if(inSameClust(i,j,pivotIndexes)){
                    /* Use util.Random to generate normal distribution and get edge
                     weights for intra-edges.*/
                    Random r = new Random();
                    double ew = r.nextGaussian()*stdev+mean;
                    edgeWeights[i][j] = ew;
                    edgeWeights[j][i] = ew;
                    if(edgeWeights[i][j]<0)
                        cost-= edgeWeights[i][j]; /* If we have a negative intra-edge, then add it
                         * to cost.*/
                }
                else{
                    /* For the inter-edges. */
                    Random r = new Random();
                    double ew = r.nextGaussian()*stdev-mean;
                    edgeWeights[i][j] = ew;
                    edgeWeights[j][i] = ew;
                    if(edgeWeights[i][j] >0)
                        cost += edgeWeights[i][j]; /* if we have a positive inter-edge, the we add
                         * it to cost.*/
                }
            }
        /* Output the graph file. */
        /* First output the number of vertices. */
        try{
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(vtxNum+"\r\n");
        for(int i=0;i<edgeWeights.length;i++)
            for(int j=i+1;j<edgeWeights.length;j++){
                bw.write("v"+i+"\t"+"v"+j+"\t"+edgeWeights[i][j]+"\r\n");
            }
        
        bw.flush();
        bw.close();
        fw.close();
        }catch(IOException e){
            System.out.println("File output error, output file path:  "+outputFile);
        }
        return cost;
    }
    
    /**
     * 
     * Graph generator 2: This generator 
     * @param setSizes
     * @param outputFile
     * @param mean
     * @param stdev
     * @return 
     */
    public static double generatorHierNpartiteGraph(int[] setSizes,
            String outputFile, double mean, double stdev){
        double cost = 0; /* The cost */
        /* Get the vertex count. */
        int vertexCount = 0;
        for(int i=0;i<setSizes.length;i++)
            vertexCount += setSizes[i];
        /* Create a one=dimensional array for all vertices.  */
        ArrayList<String> vertices = new ArrayList<>();
        
        /* Insert the values into the vertices. */
        for(int i=0;i<setSizes.length;i++){
            for(int j=0;j<setSizes[i];j++){
                String value = i+"_"+j;
                vertices.add(value);
            }
        }
        /* Permutate the arraylist. */
        Collections.shuffle(vertices);
        /* Create the pivot index array. */
        ArrayList<Integer> pivotIndexes = new ArrayList<>();
        int assignedElements = 0; /* Number of elements assigned. */
        do{
            /* Randomly get an integer from 1 to vtxNum-assignedElements. */
            assignedElements += (int)( Math.random()*(vertexCount- assignedElements)+1);
            pivotIndexes.add(assignedElements);
            
        }while(vertexCount-assignedElements != 0);
        
        /* Assign the edge weights. */
        ArrayList<double[][]> edgeWeights = new ArrayList<>();
        for(int i=0;i<setSizes.length-1;i++){
            double[][] weights = new double[setSizes[i]][setSizes[i+1]];
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i+1];k++){
                    /* Get the indexes of the two vertices in the vertices array. */
                    String value1 = i+"_"+j;
                    String value2 = (i+1)+"_"+k;
                    int idx1 = vertices.indexOf(value1);
                    int idx2 = vertices.indexOf(value2);
                    /* Check if these two vertices are in the same pre-defiend cluster. */
                    if(inSameClust(idx1,idx2,pivotIndexes)){
                        Random r = new Random();
                        weights[j][k] = r.nextGaussian()*stdev+mean;
                        if(weights[j][k] <0)
                            cost -= weights[j][k];
                    }
                    else{
                        Random r = new Random();
                        weights[j][k] = r.nextGaussian()*stdev-mean;
                        if(weights[j][k]>0)
                            cost+= weights[j][k];
                    }    
                }
            edgeWeights.add(weights);
        }
        
        /* Output the graph (no header). */
        try{
        FileWriter fw =new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        
        /* Output content of the graph .*/
        for(int i=0;i<setSizes.length-1;i++)
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i+1];k++){
                    /* Get the values of the vertices. */
                    String value1 = i+"_"+j;
                    String value2 = (i+1)+"_"+k;
                    bw.write(value1+"\t"+i+"\t"+value2+"\t"+(i+1)+"\t"+edgeWeights.get(i)[j][k]+"\r\n");
                }
            
        bw.flush();
        fw.close();
        bw.close();
        }catch(IOException e){
            System.out.println("File output error");
        }
        return cost;
    }
    
    /**
     * This method generates the geneatorHierGeneralGraph.
     * @param setSizes
     * @param outputFile
     * @param mean
     * @param stdev
     * @return 
     */
    public static double generatorHierGeneralGraph(int[] setSizes,
            String outputFile, double mean, double stdev){
        double cost = 0; /* The cost */
        /* Get the vertex count. */
        int vertexCount = 0;
        for(int i=0;i<setSizes.length;i++)
            vertexCount += setSizes[i];
        /* Create a one=dimensional array for all vertices.  */
        ArrayList<String> vertices = new ArrayList<>();
        
        /* Insert the values into the vertices. */
        for(int i=0;i<setSizes.length;i++){
            for(int j=0;j<setSizes[i];j++){
                String value = i+"_"+j;
                vertices.add(value);
            }
        }
        /* Permutate the arraylist. */
        Collections.shuffle(vertices);
        /* Create the pivot index array. */
        ArrayList<Integer> pivotIndexes = new ArrayList<>();
        int assignedElements = 0; /* Number of elements assigned. */
        do{
            /* Randomly get an integer from 1 to vtxNum-assignedElements. */
            assignedElements += (int)( Math.random()*(vertexCount- assignedElements)+1);
            pivotIndexes.add(assignedElements);
            
        }while(vertexCount-assignedElements != 0);
        
        /* Assign the intra-set edge weights. */
        ArrayList<double[][]> intraEdgeWeights = new ArrayList<>();
        for(int i=0;i<setSizes.length;i++){
            double[][] weights = new double[setSizes[i]][setSizes[i]];
            /* Generate edge weights. */
            for(int j=0;j<setSizes[i];j++)
                for(int k=j+1;k<setSizes[i];k++){
                    /* Get the indexes of the two vertices in the vertices array. */
                    String value1 = i+"_"+j;
                    String value2 = i+"_"+k;
                    int idx1 = vertices.indexOf(value1);
                    int idx2 = vertices.indexOf(value2); 
                    /* Check if these two nodes are in the same pre-defined cluster. */
                    if(inSameClust(idx1, idx2,pivotIndexes)){
                        Random r = new Random();
                        /* In the intra edge weight matrix, we have to assign two edge weights. */
                        weights[j][k] = r.nextGaussian()*stdev+mean;
                        weights[k][j] = weights[j][k];
                        if(weights[j][k]<0)
                            cost-= weights[j][k];
                    }
                    else{
                        Random r = new Random();
                        /* In the intra edge weight matrix, we have to assign two edge weights. */
                        weights[j][k] = r.nextGaussian()*stdev-mean;
                        weights[k][j] = weights[j][k];
                        if(weights[j][k]>0)
                            cost+= weights[j][k];
                    }
                }
            intraEdgeWeights.add(weights);
        }
        /* Assign the inter-set edge weights. */
        ArrayList<double[][]> interEdgeWeights = new ArrayList<>();
        for(int i=0;i<setSizes.length-1;i++){
            double[][] weights = new double[setSizes[i]][setSizes[i+1]];
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i+1];k++){
                    /* Get the indexes of the two vertices in the vertices array. */
                    String value1 = i+"_"+j;
                    String value2 = (i+1)+"_"+k;
                    int idx1 = vertices.indexOf(value1);
                    int idx2 = vertices.indexOf(value2);
                    /* Check if these two vertices are in the same pre-defiend cluster. */
                    if(inSameClust(idx1,idx2,pivotIndexes)){
                        Random r = new Random();
                        weights[j][k] = r.nextGaussian()*stdev+mean;
                        if(weights[j][k] <0)
                            cost -= weights[j][k];
                    }
                    else{
                        Random r = new Random();
                        weights[j][k] = r.nextGaussian()*stdev-mean;
                        if(weights[j][k]>0)
                            cost+= weights[j][k];
                    }    
                }
            interEdgeWeights.add(weights);
        }
        
        /* Output the graph (no header). */
        try{
        FileWriter fw =new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        
        /* Output content of the graph .*/
        /* Output the intra edge weights. */
        for(int i=0;i<setSizes.length;i++)
            for(int j=0;j<setSizes[i];j++)
                for(int k=j+1;k<setSizes[i];k++){
                    /* Get the indexes of the two vertices in the vertices array. */
                    String value1 = i+"_"+j;
                    String value2 = i+"_"+k;
                    bw.write(value1+"\t"+i+"\t"+value2+"\t"+i+"\t"+intraEdgeWeights.get(i)[j][k]+"\r\n");
                }
            
        /* Output the inter edge wegihts. */
        for(int i=0;i<setSizes.length-1;i++)
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i+1];k++){
                    /* Get the values of the vertices. */
                    String value1 = i+"_"+j;
                    String value2 = (i+1)+"_"+k;
                    bw.write(value1+"\t"+i+"\t"+value2+"\t"+(i+1)+"\t"+interEdgeWeights.get(i)[j][k]+"\r\n");
                }
            
        bw.flush();
        fw.close();
        bw.close();
        }catch(IOException e){
            System.out.println("File output error");
        }
        return cost;
    }
    
    /**
     * This method works for generatorGeneralGraph1(), to check whether two given indexes 
     * in the same pre-defined clusters.
     * @param i
     * @param j
     * @return 
     */
    public static boolean inSameClust(int i, int j, ArrayList<Integer> pivotIndexes){
        int maxPivotI=0,maxPivotJ=0;
        for(int k=0;k<pivotIndexes.size();k++){
            if(pivotIndexes.get(k)<=i)
                maxPivotI = pivotIndexes.get(k);
            if(pivotIndexes.get(k)<=j)
                maxPivotJ = pivotIndexes.get(k);
        }
        
        return maxPivotI == maxPivotJ;
    }
}
