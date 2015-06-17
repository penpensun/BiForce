/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.sim;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;
import biforce.graphs.MatrixGraph;
import biforce.graphs.MatrixSubgraph;
import biforce.graphs.MatrixHierNpartiteGraph;
import biforce.graphs.MatrixHierNpartiteSubgraph;
import biforce.graphs.MatrixHierGeneralGraph;
import biforce.graphs.MatrixHierGeneralSubgraph;

/**
 *
 * @author penpen926
 */
public class SimGraphGenTest {
    SimGraphGen gen = null;
    public SimGraphGenTest() {
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
     * Test of generatorGeneralGraph1 method, of class SimGraphGen.
     */
    @Test
    public void testGeneratorGeneralGraph1() {
        System.out.println("(SimGraphGen.generatorGeneralGraph1) Test starts.");
        /* Assign all arguments. */
        int vtxNum = 20;
        String outputFile = "../../data/testdata/unit_test/SimGraphGen/outputfile_generatorGeneralGraph1.txt";
        float mean = 10.0f;
        float stdev = 0.0f;
        /* Create the graph. */
        float cost = gen.generatorGeneralGraph1(vtxNum, outputFile, mean, stdev);
        /* Read the graph. */
        MatrixGraph testGraph = new MatrixGraph(outputFile,true,false,0);
        /* Test the inputted graph. */
        assertEquals(20,testGraph.vertexCount());
        ArrayList<MatrixSubgraph>  connComps = new ArrayList<MatrixSubgraph>(
                testGraph.connectedComponents());
        assertEquals(true, testGraph.connectedComponents().size()>0);
        System.out.println("The number of connected components: "+connComps.size());
        
        /* Check each connComp to see if they are bigger than 0 (not empty). */
        for(int i=0;i<connComps.size();i++)
            assertEquals(true,connComps.get(i).vertexCount()>0);
        /* Check some intra and inter edge weights. */
        /* Check intra edge weights. */
        for(int i=0;i<connComps.size();i++){
            /* First we try to find a connComp with more than one vertices. */
            if(connComps.get(i).vertexCount()>1)
                /* If the current connComp has more then 1 vertex, we test the edge between 
                 * the FIRST and the LAST vertices in this subgraph. */
                assertEquals(10,connComps.get(i).edgeWeight(
                        connComps.get(i).getSubvertices().get(0),
                        connComps.get(i).getSubvertices().get(connComps.get(i).vertexCount()-1)),
                        0.0001);
        }
        /* Check the inter edge weights. */
        for(int i=0;i<connComps.size();i++)
            for(int j=i+1;j<connComps.size();j++)
                /* We check the edge between the FIRST vertices of the two different subgraph. */
                assertEquals(-10, testGraph.edgeWeight(
                        connComps.get(i).getSubvertices().get(0),
                        connComps.get(j).getSubvertices().get(0)),0.0001);
        /* Check if the cost is 0.*/
        assertEquals(0,cost,0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("(SimGraphGen.generatorGeneralGraph1) Test ends.");
    }
    
    
    /*
     * Test of inSameCluster method, of class SimGraphGen.
     */
    @Test
    public void testInSameCluster(){
        System.out.println("(SimGraphGen.inSameCluster) Test starts. ");
        int vtxNum= 20;
        ArrayList<Integer> pivotIndexes = new ArrayList<>();
        pivotIndexes.add(4);
        pivotIndexes.add(10);
        pivotIndexes.add(16);
        /* Test the cases for IN the same cluster. */
        assertEquals(true,gen.inSameClust(0,3,pivotIndexes));
        assertEquals(true,gen.inSameClust(4,5,pivotIndexes));
        assertEquals(true,gen.inSameClust(10,13, pivotIndexes));
        assertEquals(true,gen.inSameClust(17, 20, pivotIndexes));
        
        /* Test the cases for NOT IN the same cluster. */
        assertEquals(false,gen.inSameClust(0, 4, pivotIndexes));
        assertEquals(false,gen.inSameClust(0, 12, pivotIndexes));
        assertEquals(false,gen.inSameClust(4, 15, pivotIndexes));
        assertEquals(false,gen.inSameClust(5, 18, pivotIndexes));
        assertEquals(false,gen.inSameClust(13, 19, pivotIndexes));
        System.out.println("(SimGraphGen.inSameCluster) Test ends. ");
    }

    /**
     * Test of generatorHierNpartiteGraph method, of class SimGraphGen.
     */
    @Test
    public void testGeneratorHierNpartiteGraph() {
        System.out.println("(SimGraphGen.generatorHierNpartiteGraph) Test starts. ");
        int[] setSizes = new int[4];
        setSizes[0] = 10;
        setSizes[1] = 15;
        setSizes[2] = 20;
        setSizes[3] = 18;
        
        String outputFile = "../../data/testdata/unit_test/SimGraphGen/outputfile_generatorHierNpartiteGraph.txt";
        float mean = 20.0f;
        float stdev = 0.0f;
        float result = gen.generatorHierNpartiteGraph(setSizes, outputFile, mean, stdev);
        /* Read the graph. */
        MatrixHierNpartiteGraph graph = new MatrixHierNpartiteGraph(outputFile, false,0);
        
        float expRes = 0;
        /* First check the number of vertices. */
        //assertEquals(63, graph.vertexCount());
        ArrayList<MatrixHierNpartiteSubgraph> connComps = graph.connectedComponents();
        
        assertEquals(true, connComps.size()>0);
        System.out.println("The number of connected components: "+connComps.size());
        /* Check each connComp to see if they are bigger than 0 (not empty). */
        for(int i=0;i<connComps.size();i++)
            assertEquals(true,connComps.get(i).vertexCount()>0);
        
        /* Check the edge weight matrices. */
        for(int i=0;i<setSizes.length-1;i++){
            float[][] weights = graph.edgeWeightMatrix(i);
            assertEquals(setSizes[i],weights.length);
            assertEquals(setSizes[i+1],weights[0].length);
            
        }
            
        System.out.println("(SimGraphGen.generatorHierNpartiteGraph) Test ends. ");
        
    }
    
    
    /**
     * This method tests the generatorHierGeneralGraph method.
     */
    @Test
    public void testGeneratorHierGeneralGraph(){
        System.out.println("(biforce.sim.SimGraphGenTest.testGeneratorHierGeneralGraph) Test starts.");
        int[] setSizes = new int[4];
        setSizes[0] = 10;
        setSizes[1] = 15;
        setSizes[2] = 20;
        setSizes[3] = 18;
        
        String outputFile = "../../data/testdata/unit_test/SimGraphGen/outputfile_generatorHierGeneralGraph.txt";
        float mean = 20.0f;
        float stdev = 0.0f;
        float result = gen.generateHierGeneralGraph(setSizes, outputFile, mean, stdev);
        /* Read the graph. */
        MatrixHierGeneralGraph graph = new MatrixHierGeneralGraph(outputFile, false,false,0);
        
        float expRes = 0;
        /* First check the number of vertices. */
        //assertEquals(63, graph.vertexCount());
        ArrayList<MatrixHierGeneralSubgraph> connComps = graph.connectedComponents();
        
        assertEquals(true, connComps.size()>0);
        System.out.println("The number of connected components: "+connComps.size());
        /* Check each connComp to see if they are bigger than 0 (not empty). */
        for(int i=0;i<connComps.size();i++)
            assertEquals(true,connComps.get(i).vertexCount()>0);
        
        /* Check if the setSizes match the matrix sizes. */
        /* Check if the setSizes match the sizes of the inter edge weight matrices. */
        for(int i=0;i<setSizes.length-1;i++){
            float[][] weights = graph.interEdgeWeightMatrix(i);
            assertEquals(setSizes[i],weights.length);
            assertEquals(setSizes[i+1],weights[0].length);
            
        }
        /* Check if the setSizes match the sizes of the intra edge weigt matrices. */
        for(int i=0;i<setSizes.length;i++){
            float[][] weights = graph.intraEdgeWeightMatrix(i);
            assertEquals(setSizes[i],weights.length);
        }
        
        System.out.println("(biforce.sim.SimGraphGenTest.testGeneratorHierGeneralGraph) Test ends.");
    }
    
    
    @Test
    public void testGeneratorHierGeneralGraphXml(){
        System.out.println("(biforce.sim.SimGraphGenTest.testGeneratorHierGeneraGraphXml) Test starts.");
        int[] setSizes = new int[4];
        setSizes[0] = 10;
        setSizes[1] = 15;
        setSizes[2] = 20;
        setSizes[3] = 18;
        
        String outputFile = "../../data/testdata/unit_test/SimGraphGen/outputfile_hiergeneralgraphxml.txt";
        float mean = 20.0f;
        float stdev = 0.0f;
        float result = gen.generateHierGeneralGraphXml(setSizes, outputFile, mean, stdev);
        MatrixHierGeneralGraph graph = new MatrixHierGeneralGraph(outputFile, false,true,0);
        float expRes = 0;
        /* First check the number of vertices. */
        //assertEquals(63, graph.vertexCount());
        ArrayList<MatrixHierGeneralSubgraph> connComps = graph.connectedComponents();
        
        assertEquals(true, connComps.size()>0);
        System.out.println("The number of connected components: "+connComps.size());
        /* Check each connComp to see if they are bigger than 0 (not empty). */
        for(int i=0;i<connComps.size();i++)
            assertEquals(true,connComps.get(i).vertexCount()>0);
        
        /* Check if the setSizes match the matrix sizes. */
        /* Check if the setSizes match the sizes of the inter edge weight matrices. */
        for(int i=0;i<setSizes.length-1;i++){
            float[][] weights = graph.interEdgeWeightMatrix(i);
            assertEquals(setSizes[i],weights.length);
            assertEquals(setSizes[i+1],weights[0].length);
            
        }
        /* Check if the setSizes match the sizes of the intra edge weigt matrices. */
        for(int i=0;i<setSizes.length;i++){
            float[][] weights = graph.intraEdgeWeightMatrix(i);
            assertEquals(setSizes[i],weights.length);
        }
        System.out.println("(biforce.sim.SimGraphGenTest.testGeneratorHierGeneraGraphXml) Test ends.");
    }
}
