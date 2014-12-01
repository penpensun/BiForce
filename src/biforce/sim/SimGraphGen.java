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
import java.io.File;
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
    public double generatorGeneralGraph1(int vtxNum, String outputFile, 
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
    public double generatorHierNpartiteGraph(int[] setSizes,
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
    public double generateHierGeneralGraph(int[] setSizes,
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
     * This method generates a hierGeneralGraph in the xml format. 
     * @param setSizes
     * @param outputFile
     * @param mean
     * @param stdev
     * @return 
     */
    public double generateHierGeneralGraphXml(int[] setSizes,
            String outputFile, double mean, double stdev){
        /* First and foremost, check the legality of all parameters. */
        /* setSizes cannot be null.*/
        if(setSizes == null)
            throw new IllegalArgumentException("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) Parameter setSizes is null.");
        /* Elements in setSizes cannot be smaller than 1. */
        for(int i=0;i<setSizes.length;i++){
            if(setSizes[i]<=0)
                throw new IllegalArgumentException("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) The element "+i+" in setSizes is smaller or equal to zero:  "+setSizes[i]);
        }
        /* The String outputFile cannot be null.*/
        if(outputFile == null)
            throw new IllegalArgumentException("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) The parameter outputFile is null.");
        /* outputFile must be a legal file path. */
        /* Generate xml file. */
        FileWriter fw =null; 
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
        }catch(IOException e){
            System.out.println("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) Outputfile error: "+outputFile);
            e.printStackTrace();
            return -1;
        }
        /* The mean must be a legal double. */
        if(Double.isNaN(mean))
            throw new IllegalArgumentException("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) Parameter mean is not a double float number.");
        if(Double.isNaN(stdev) || stdev<0)
            throw new IllegalArgumentException("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) Parameter stdev is illegal:  "+stdev);
        
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
        /* Output the root element.*/
        try{
            bw.write("<document>\n");
            /* Output the entity.*/
            bw.write("<entity levels=\""+setSizes.length+"\">\n");
            for(int i=0;i<setSizes.length;i++){
                for(int j=0;j<setSizes[i]-1;j++)
                    bw.write(i+"_"+j+"\t"); /* Output the row from 0 to setSizes[i]-1. */
                bw.write(i+"_"+(setSizes[i]-1)+"\n");
            }
            bw.write("</entity>\n");
            /* Output the intra-matrix. */
            for(int i=0;i<setSizes.length;i++){
                bw.write("<matrix matrixLevel=\""+i+" "+i+"\">\n");
                /* Output the ith intra-matrix. */
                double[][] intraMatrix = intraEdgeWeights.get(i);
                for(int j=0;j<intraMatrix.length;j++){
                    for(int k=0;k<intraMatrix[0].length-1;k++)
                        bw.write(intraMatrix[j][k]+"\t"); 
                    bw.write(intraMatrix[j][intraMatrix[0].length-1]+"\n");
                }
                bw.write("</matrix>\n");
            }
            /* Output the inter-matrix.*/
            for(int i=0;i<setSizes.length-1;i++){
                bw.write("<matrix matrixLevel=\""+i+" "+(i+1)+"\">\n");
                /* Output the ith inter-matrix. */
                double[][] interMatrix = interEdgeWeights.get(i);
                for(int j=0;j<interMatrix.length;j++){
                    for(int k=0;k<interMatrix[0].length-1;k++)
                        bw.write(interMatrix[j][k]+"\t");
                    bw.write(interMatrix[j][interMatrix[0].length-1]+"\n");
                }
                bw.write("</matrix>\n");
            }
            bw.write("</document>\n");
            bw.flush();
            bw.close();
            fw.close();
        }catch(IOException e){
            System.out.println("(biforce.sim.SimGraphGen.generatorHierGeneralGraphXml) Write file failed:  "+outputFile);
            e.printStackTrace();
            return -1;
        }
        
        return cost;
    }
    
    /**
     * This method generates a random matrix and writes into the given outputfile.
     * @param outputFile
     * @param manyRows
     * @param manyCols 
     * @param mean 
     * @param dev 
     */
    public void writeRandomInterMatrix(String outputFile, int manyRows, int manyCols,
            double mean, double dev){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
        }catch(IOException e){
            e.printStackTrace();
        }
        Random r = new Random();
        try{
        for(int i=0;i<manyRows;i++){
            for(int j=0;j<manyCols-1;j++){
                double ew = r.nextGaussian()*dev+mean;
                bw.write(ew+"\t");
            }
            double ew = r.nextGaussian()*dev+mean;
            bw.write(ew+"\n");
        }
        bw.flush();
        bw.close();
        fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void writeRandomIntraMatrix(String outputFile, int manyRows,
            double mean, double dev){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
        }catch(IOException e){
            e.printStackTrace();
        }
        Random r = new Random();
        /* Generate the matrix.*/
        double[][] ew = new double[manyRows][manyRows];
        for(int i=0;i<ew.length;i++)
            for(int j=0;j<ew[0].length;j++)
                ew[i][j] = Double.NaN;
        
        for(int i=0;i<manyRows;i++)
            for(int j=i+1;j<manyRows;j++){
                ew[i][j]=r.nextGaussian()*dev+mean;
                ew[j][i]=ew[i][j];
            }
        try{
        for(int i=0;i<manyRows;i++){
            for(int j=0;j<manyRows-1;j++){ 
                bw.write(ew[i][j]+"\t");
            }
            bw.write(ew[i][manyRows-1]+"\n");
        }
        bw.flush();
        bw.close();
        fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * This method 
     * @param setSizes 
     */
    public void writeNodeNames(int[] setSizes, String outputFile){
        try{
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i=0;i<setSizes.length;i++){
            for(int j=0;j<setSizes[i]-1;j++){
                bw.write(i+"_"+j+"\t");
            }
            bw.write(i+"_"+(setSizes[i]-1)+"\n");
        }
        bw.flush();
        bw.close();
        fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * This method works for generatorGeneralGraph1(), to check whether two given indexes 
     * in the same pre-defined clusters.
     * @param i
     * @param j
     * @return 
     */
    public boolean inSameClust(int i, int j, ArrayList<Integer> pivotIndexes){
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
