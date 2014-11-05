/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.algorithms;

import biforce.graphs.MatrixBipartiteGraph;
import biforce.graphs.MatrixBipartiteGraph2;
import biforce.graphs.MatrixHierNpartiteGraph;
import biforce.graphs.MatrixHierGeneralGraph;
import biforce.graphs.MatrixGraph;
import biforce.sim.SimGraphGen;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.IOException;

/**
 *
 * @author penpen926
 */
public class BiForceOnGraph4Test {
    String testMatrixBipartiteGraph2Input = "../../data/testdata/unit_test/MatrixBipartiteGraph/run_input.txt";
    String testMatrixGraphInput = "../../data/testdata/unit_test/MatrixGraph/sim_run_input.txt";
    String testMatrixHierNpartiteGraphInput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/run_input.txt";
    String testMatrixHierGeneralGraphInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/run_input.txt";
    String paramFile = "./parameters.ini";
    public BiForceOnGraph4Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * This method tests BiForceOnGraph4 on MatrixBipartiteGraph2.
     */
    @Test
    public void testRunOnMatrixBipartiteGraph() {
        try{
            Param p = Param.readParams(paramFile);
            MatrixBipartiteGraph graph = new MatrixBipartiteGraph(testMatrixBipartiteGraph2Input);
            //Create a algorithm instance
            BiForceOnGraph3 bifoce3 = new BiForceOnGraph3();
            bifoce3.run(graph,p);
            
            System.out.println(graph.getCost());
            
            MatrixBipartiteGraph2 graph2 = new MatrixBipartiteGraph2(testMatrixBipartiteGraph2Input,false,0);
            BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
            biforce4.run(graph2, p);
            System.out.println(graph.getCost());
            assertEquals(graph.getCost(),graph2.getCost(),0.01);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void testRunOnMatrixGraph(){
        System.out.println("(testRunOnMatrixGraph) Test starts.");
        /* BiForce is a heuristic algorithm and sometimes cannot give the standard result.
         * Thus, we set the pass-condition as a range of [0.95,1.05] of the standard result.
         * We run the problem for 20 times for repetition.*/
        for(int i=0;i<20;i++){
            try{
                Param p = Param.readParams(paramFile);
                /* Generate a random npartiteInstance. */
                double stdCost = SimGraphGen.generatorGeneralGraph1(50, testMatrixGraphInput, 20, 15);
                MatrixGraph graph = new MatrixGraph(testMatrixGraphInput,true,0);
                BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
                biforce4.run(graph, p);
                System.out.println(graph.getCost());
                assertEquals(stdCost,graph.getCost(), stdCost*0.05);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixGraph) Test ends");
    }
    
    @Test
    public void testRunOnMatrixHierNpartiteGraph(){
        /*
         * This test method tests the algorithm on MatrixHierNpartiteGraph.
         */
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test starts. ");
        for(int i=0;i<20;i++){
            try{
                Param p = Param.readParams(paramFile);
                //int[] sizes = {20,20,20,20};
                int[] sizes = {20,20};
                double stdCost = SimGraphGen.generatorHierNpartiteGraph(sizes, 
                        testMatrixHierNpartiteGraphInput, 20, 15);
                MatrixHierNpartiteGraph npartiteInstance = new MatrixHierNpartiteGraph(testMatrixHierNpartiteGraphInput,
                        false, 0);
                BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
                biforce4.run(npartiteInstance, p);
                System.out.print("NpartiteGraph: "+ npartiteInstance.getCost());
                //assertEquals(stdCost,npartiteInstance.getCost(), stdCost*0.05);
                
                
                MatrixBipartiteGraph2 bipartiteInstance = new MatrixBipartiteGraph2(
                        testMatrixHierNpartiteGraphInput,false,0);
                biforce4.run(bipartiteInstance,p);
                System.out.println(" BipartiteGraph:  "+bipartiteInstance.getCost());
                assertEquals(bipartiteInstance.getCost(),npartiteInstance.getCost(),0.5);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test ends");
    }
    
    
    /**
     * This test method compares the performance of biforce algorithm on MatrixHierNpartiteGraph and MatrixBipartiteGraph2. 
     */
    @Test 
    public void testComparisonBiAndHierNpartiteGraph(){
        System.out.println("(testComparisonBiAndHierNpartiteGraph) Test starts.");
        try{
            Param p = Param.readParams(paramFile);
            int[] sizes = {20,20};
            double stdCost = SimGraphGen.generatorHierNpartiteGraph(sizes, 
                        testMatrixHierNpartiteGraphInput, 20, 15);
            /* Create MatrixHierNpartiteGraph .*/
            MatrixHierNpartiteGraph npartiteInstance = new MatrixHierNpartiteGraph(testMatrixHierNpartiteGraphInput,
                    false, 0);
            /* Run on MatrixHierNpartiteGraph .*/
            BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
            biforce4.run(npartiteInstance, p);
            System.out.print("NpartiteGraph: "+ npartiteInstance.getCost());
            //assertEquals(stdCost,npartiteInstance.getCost(), stdCost*0.05);

            /* Create MartrixBipartiteGraph2. */
            MatrixBipartiteGraph2 bipartiteInstance = new MatrixBipartiteGraph2(
                    testMatrixHierNpartiteGraphInput,false,0);
            /* Run on MatrixBipartiteGraph2. */
            biforce4.run(bipartiteInstance,p);
            
            /* Comparison between the results .*/
            System.out.println("The editing cost: npartite  "+npartiteInstance.getCost()+"  bipartite  "+bipartiteInstance.getCost()+"\r\n");
            assertEquals(bipartiteInstance.getCost(),npartiteInstance.getCost(),0.5);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("(testComparisonBiAndHierNpartiteGraph) Test ends. ");
    }
    
    @Test
    public void testRunOnMatrixHierGeneralGraph(){
        /*
         * This test method tests the algorithm on MatrixHierNpartiteGraph.
         */
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test starts. ");
        for(int i=0;i<20;i++){
            try{
                Param p = Param.readParams(paramFile);
                //int[] sizes = {20,20,20,20};
                int[] sizes = {20,20};
                double stdCost = SimGraphGen.generatorHierGeneralGraph(sizes, 
                        testMatrixHierGeneralGraphInput, 20, 15);
                MatrixHierGeneralGraph hierGeneralGraph = new MatrixHierGeneralGraph(testMatrixHierGeneralGraphInput,false,0);
                
                BiForceOnGraph4 biforce4 = new BiForceOnGraph4();
                
                biforce4.run(hierGeneralGraph,p);
                System.out.println(" hierGeneralGraph cost:  "+hierGeneralGraph.getCost());
                System.out.println(" Standard cost:  "+stdCost);
                assertEquals(stdCost,hierGeneralGraph.getCost(),5);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test ends");
    }
    
    
    
}
