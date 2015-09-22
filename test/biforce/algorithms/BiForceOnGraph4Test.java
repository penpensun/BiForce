/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.algorithms;

import biforce.graphs.BipartiteGraph;
import biforce.graphs.BipartiteGraph2;
import biforce.graphs.HierGraph;
import biforce.graphs.HierGraphWIE;
import biforce.graphs.NpartiteGraph;
import biforce.graphs.GeneralGraph;
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
    
    String testMatrixGeneralNpartiteGraphInput = "../../data/testdata/unit_test/MatrixGeneralNpartiteGraph/input.txt";
    String testMatrixGeneralNpartiteGraphXml = "../../data/testdata/unit_test/MatrixGeneralNpartiteGraph/input.xml";
    String testMatrixGeneralNpartiteGraphEntity = "../../data/testdata/unit_test/MatrixGeneralNpartiteGraph/entity.txt";
    String testMatrixGeneralNpartiteGraphMatrixPrefix = "../../data/testdata/unit_test/MatrixGeneralNpartiteGraph/matrix";
    String paramFile = "./parameters.ini";
    SimGraphGen gen = null;
    public BiForceOnGraph4Test() {
        gen = new SimGraphGen();
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
            BipartiteGraph graph = new BipartiteGraph(testMatrixBipartiteGraph2Input);
            //Create a algorithm instance
            BiForceOnGraph3 bifoce3 = new BiForceOnGraph3();
            bifoce3.run(graph,p);
            
            System.out.println(graph.getCost());
            
            BipartiteGraph2 graph2 = new BipartiteGraph2(testMatrixBipartiteGraph2Input,false,0);
            BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
            biforce4.run(graph2, p,1,false);
            System.out.println(graph.getCost());
            assertEquals(graph.getCost(),graph2.getCost(),0.01);
            System.out.println("The cost: "+graph.getCost()+"\t"+graph2.getCost());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //@Test
    
    public void testRunOnMatrixGraph(){
        System.out.println("(testRunOnMatrixGraph) Test starts.");
        /* BiForce is a heuristic algorithm and sometimes cannot give the standard result.
         * Thus, we set the pass-condition as a range of [0.95,1.05] of the standard result.
         * We run the problem for 20 times for repetition.*/
        for(int i=0;i<20;i++){
            try{
                Param p = Param.readParams(paramFile);
                /* Generate a random npartiteInstance. */
                double stdCost = gen.generatorGeneralGraph1(2000, testMatrixGraphInput, 20, 10);
                GeneralGraph graph = new GeneralGraph(testMatrixGraphInput,true,false,0);
                BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
                biforce4.run(graph, p,1,false);
                System.out.println(graph.getCost());
                assertEquals(stdCost,graph.getCost(), stdCost*0.05);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixGraph) Test ends");
    }
    
    //@Test
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
                double stdCost = gen.generatorHierNpartiteGraph(sizes, 
                        testMatrixHierNpartiteGraphInput, 20, 15);
                HierGraph npartiteInstance = new HierGraph(testMatrixHierNpartiteGraphInput,
                        false, 0);
                BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
                biforce4.run(npartiteInstance, p,1,false);
                System.out.print("NpartiteGraph: "+ npartiteInstance.getCost());
                //assertEquals(stdCost,npartiteInstance.getCost(), stdCost*0.05);
                BipartiteGraph2 bipartiteInstance = new BipartiteGraph2(
                        testMatrixHierNpartiteGraphInput,false,0);
                
                biforce4.run(bipartiteInstance,p,1,false);
                System.out.println(" BipartiteGraph:  "+bipartiteInstance.getCost());
                assertEquals(bipartiteInstance.getCost(),npartiteInstance.getCost(),0.5);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test ends");
    }
    
    
    //@Test
    public void testRunOnMatrixGeneralNpartiteGraph(){
        System.out.println("(testRunOnMatrixGeneralNpartiteGraph) Test starts. ");
        try{
            Param p = Param.readParams(paramFile);
            float stdCost =gen.generateDrugReposSimXml(testMatrixGeneralNpartiteGraphInput, testMatrixGeneralNpartiteGraphXml, 
                    testMatrixGeneralNpartiteGraphEntity, testMatrixGeneralNpartiteGraphMatrixPrefix, 20, 15);
            NpartiteGraph input = new NpartiteGraph(testMatrixGeneralNpartiteGraphXml, false, true);
            input.setThreshold(0);
            BiForceOnGraph4 biforce4 = new BiForceOnGraph4();
            biforce4.run(input, p, 1, false);
            System.out.println("MatrixGeneralNpartiteGraph: "+ input.getCost());
            System.out.println("Standard cost:  "+stdCost);
            assertEquals(stdCost,input.getCost(),stdCost/20);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        
        System.out.println("(testRunOnMatrixGeneralNpartiteGraph) Test ends. ");
    }
    
    
    /**
     * This test method compares the performance of biforce algorithm on MatrixHierNpartiteGraph and MatrixBipartiteGraph2. 
     */
    //@Test 
    public void testComparisonBiAndHierNpartiteGraph(){
        System.out.println("(testComparisonBiAndHierNpartiteGraph) Test starts.");
        try{
            Param p = Param.readParams(paramFile);
            int[] sizes = {20,20};
            double stdCost = gen.generatorHierNpartiteGraph(sizes, 
                        testMatrixHierNpartiteGraphInput, 20, 15);
            /* Create MatrixHierNpartiteGraph .*/
            HierGraph npartiteInstance = new HierGraph(testMatrixHierNpartiteGraphInput,
                    false, 0);
            /* Run on MatrixHierNpartiteGraph .*/
            BiForceOnGraph4 biforce4  =new BiForceOnGraph4();
            biforce4.run(npartiteInstance, p,1,false);
            System.out.print("NpartiteGraph: "+ npartiteInstance.getCost());
            //assertEquals(stdCost,npartiteInstance.getCost(), stdCost*0.05);

            /* Create MartrixBipartiteGraph2. */
            BipartiteGraph2 bipartiteInstance = new BipartiteGraph2(
                    testMatrixHierNpartiteGraphInput,false,0);
            /* Run on MatrixBipartiteGraph2. */
            biforce4.run(bipartiteInstance,p,1,false);
            
            /* Comparison between the results .*/
            System.out.println("The editing cost: npartite  "+npartiteInstance.getCost()+"  bipartite  "+bipartiteInstance.getCost()+"\r\n");
            assertEquals(bipartiteInstance.getCost(),npartiteInstance.getCost(),0.5);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("(testComparisonBiAndHierNpartiteGraph) Test ends. ");
    }
    
    //@Test
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
                double stdCost = gen.generateHierGeneralGraph(sizes, 
                        testMatrixHierGeneralGraphInput, 20, 15);
                HierGraphWIE hierGeneralGraph = new HierGraphWIE(testMatrixHierGeneralGraphInput,false,false,0);
                
                BiForceOnGraph4 biforce4 = new BiForceOnGraph4();
                
                biforce4.run(hierGeneralGraph,p,1,false);
                System.out.println(" hierGeneralGraph cost:  "+hierGeneralGraph.getCost());
                System.out.println(" Standard cost:  "+stdCost);
                assertEquals(stdCost,hierGeneralGraph.getCost(),stdCost/20);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("(testRunOnMatrixHierNpartiteGraph) Test ends");
    }
    
    
    
}
