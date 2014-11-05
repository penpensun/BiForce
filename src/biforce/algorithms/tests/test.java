/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms.tests;
import biforce.io.Main;
import biforce.io.Main2;
import java.io.IOException;
import java.io.File;
/**
 *
 * @author Peng Sun
 * The test class
 */
public class test {
    /**
     * This method runs biforces on a group of graphs.
     * @throws IOException 
     */
    public void testGraphs() throws IOException{
        String InputFolder = "./test/test_graphs/";
        String OutputFile ="./test/test_graphs_output_v2";
        String ParameterFile = "./parameters.ini";
        String[] files = new File(InputFolder).list();
        for(String file:files)
        {
            Main.runGraph(InputFolder+"/"+file, OutputFile,null,ParameterFile);
        }
        
    }
    /**
     * This method tests on the graphs the new design.
     * @throws IOException 
     */
    public void testGraphsV3() throws IOException{
        String InputFolder = "./test/test_graphs/";
        String OutputFile ="./test/test_graphs_output_v3";
        String ParameterFile = "./parameters.ini";
        String[] files = new File(InputFolder).list();
        for(String file:files)
        {
            Main2.runGraph(InputFolder+"/"+file, OutputFile,null,ParameterFile);
        }
    }
    /**
     * This method tests on the matrices the new design.
     * @throws IOException 
     */
    public void testMatrixV3() throws IOException{
        String InputFolder = "./test/test_matrices/";
        String OutputFile ="./test/test_matrices_output_v3";
        String ParameterFile = "./parameters.ini";
        String[] files = new File(InputFolder).list();
        for(String file:files)
        {
            Main2.runMatrix(InputFolder+"/"+file,"l", OutputFile,1,false,ParameterFile);
        }
    }
    
    public void testMatrices() throws IOException{
        
    }
    public static void main(String args[]) throws IOException
    {
        new test().testGraphsV3();
        //Main.testmatrices();
        new test().testMatrixV3();
    }
}
