/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performance;
import biforce.graphs.*;
import java.io.*;
/**
 * This class deals with the comparison between nforce on MatrixGraph and TransClust
 * 
 * @author penpen926
 */
public class TransClustComparer {
    /**
     * This method generates the inputfile for transClust,given the input for nforce.
     * Pre-cond: The valid input file for nforce.
     * Post-cond: The valid input file for transClust
     * @param inputFile
     * @param outputFile 
     */
    public void genInput(String inputFile, String outputFile){
        MatrixGraph inputGraph  = new MatrixGraph(inputFile, true,false,0);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
            bw.write(inputGraph.vertexCount()+"\n");
            for(Vertex2 v: inputGraph.getVertices())
                bw.write(v.getValue()+"\n");
            // Write the matrix
            for(int i=0;i<inputGraph.vertexCount();i++){
                for(int j=0;j<inputGraph.vertexCount()-1;j++){
                    if(Float.isNaN(inputGraph.edgeWeight(i, j)))
                        bw.write(0+"\t");
                    else bw.write(inputGraph.edgeWeight(i, j)+"\t");
                }
                if(Float.isNaN(inputGraph.edgeWeight(i, inputGraph.vertexCount()-1)))
                    bw.write(0+"\n");
                else 
                    bw.write(inputGraph.edgeWeight(i, inputGraph.vertexCount()-1)+"\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        }catch(IOException e){
            System.err.println("(TransClustComparer.genInput) File writing error.");
        }
    }
    
    public static void main(String[] args){
        new TransClustComparer().genInput("../../data/testdata/unit_test/MatrixGraph/sim_run_input.txt", 
                "../../data/testdata/unit_test/MatrixGraph/sim_run_input_transclust.txt");
    }
}
