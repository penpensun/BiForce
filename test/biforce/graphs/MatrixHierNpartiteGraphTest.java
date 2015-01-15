/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import biforce.algorithms.*;
import java.io.IOException;

/**
 *
 * @author penpen926
 */
public class MatrixHierNpartiteGraphTest {
    String testReadGraphInput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_input_noheader.txt";
    String testReadGraphWithHeaderInput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_input_header.txt";
    String testEdgeWeightInput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_edgeweight_input.txt";
    String testNeighboursInput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_neighbours_input.txt";
    String testOutputPlain = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_write_graph_output.txt";
    String testClusterOutput = "../../data/testdata/unit_test/MatrixHierNpartiteGraph/test_cluster_output.txt";
    public MatrixHierNpartiteGraphTest() {
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
     * Test of bfs method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testBfs() {
        System.out.println("bfs");
        Vertex2 Vtx = null;
        MatrixHierNpartiteGraph instance = null;
        MatrixHierNpartiteSubgraph expResult = null;
        MatrixHierNpartiteSubgraph result = instance.bfs(Vtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectedComponents method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("connectedComponents");
        MatrixHierNpartiteGraph instance = null;
        List expResult = null;
        List result = instance.connectedComponents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testDetractThresh_0args() {
        System.out.println("detractThresh");
        MatrixHierNpartiteGraph instance = null;
        instance.detractThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testDetractThresh_double() {
        System.out.println("detractThresh");
        double thresh = 0.0;
        MatrixHierNpartiteGraph instance = null;
        instance.detractThresh(thresh);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dist method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testDist() {
        System.out.println("dist");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierNpartiteGraph instance = null;
        double expResult = 0.0;
        double result = instance.dist(vtx1, vtx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testEdgeWeight() {
        System.out.println("(MatrixHierNpartiteGraph.edgeWeight) Test starts.");
        MatrixHierNpartiteGraph instance = new MatrixHierNpartiteGraph(testEdgeWeightInput,true,0);
        /* Get the vertices. */
        Vertex2 a1 = instance.getVertex("a1");
        Vertex2 b1 = instance.getVertex("b1");
        Vertex2 b2 = instance.getVertex("b2");
        Vertex2 c1 = instance.getVertex("c1");
        Vertex2 c2 = instance.getVertex("c2");
        Vertex2 d1 = instance.getVertex("d1");
        
        /* Check the edge weights. */
        assertEquals(5.8,instance.edgeWeight(a1,b1),0.0001);
        assertEquals(5.8,instance.edgeWeight(b1,a1),0.0001);
        assertEquals(6.2,instance.edgeWeight(b1,c1),0.0001);
        assertEquals(6.8,instance.edgeWeight(c2,b1),0.0001);
        assertEquals(6.1,instance.edgeWeight(b2,c2),0.0001);
        assertEquals(6.5,instance.edgeWeight(c2,d1),0.0001);
        
        assertEquals(true, Double.isNaN(instance.edgeWeight(a1,c1)));
        assertEquals(true, Double.isNaN(instance.edgeWeight(c2,c1)));
        
        System.out.println("(MatrixHierNpartiteGraph.edgeWeight) Test ends.");
    }

    /**
     * Test of getCost method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        MatrixHierNpartiteGraph instance = null;
        double expResult = 0.0;
        double result = instance.getCost();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isActionTaken method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testIsActionTaken() {
        System.out.println("isActionTaken");
        int actIdx = 0;
        MatrixHierNpartiteGraph instance = null;
        boolean expResult = false;
        boolean result = instance.isActionTaken(actIdx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of neighbours method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testNeighbours() {
        System.out.println("(MatrixHierNpartiteGraph.neighbours) Test starts.");
        MatrixHierNpartiteGraph instance = new MatrixHierNpartiteGraph(testNeighboursInput,true,0);
        /* Get 3 vertices whose neighbours are to be found. */
        Vertex2 b1 = instance.getVertex("b1");
        Vertex2 d1 = instance.getVertex("d1");
        Vertex2 b2 = instance.getVertex("b2");
        /* Create the correct answer. */
        Vertex2 a1 = instance.getVertex("a1");
        Vertex2 a2 = instance.getVertex("a2");
        Vertex2 a3 = instance.getVertex("a3");
        Vertex2 c1 = instance.getVertex("c1");
        Vertex2 c2 = instance.getVertex("c2");
        
        ArrayList<Vertex2> answer1 = new ArrayList<>(); /* Correct answer1. */
        answer1.add(a1);
        answer1.add(a2);
        answer1.add(a3);
        ArrayList<Vertex2> answer2 = new ArrayList<>(); /* Correct answer2. */
        answer2.add(c1);
        /* Compute the neighbours. */
        MatrixBipartiteGraph2Test.assertEqualArrayList(instance.neighbours(b1), answer1);
        MatrixBipartiteGraph2Test.assertEqualArrayList(instance.neighbours(d1), answer2);
        MatrixBipartiteGraph2Test.assertEqualArrayList(instance.neighbours(b2),null);
        
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("(MatrixHierNpartiteGraph.neighbours) Test ends.");
    }

    /**
     * Test of pushAction method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testPushAction() {
        System.out.println("pushAction");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierNpartiteGraph instance = null;
        boolean expResult = false;
        boolean result = instance.pushAction(vtx1, vtx2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readGraph method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testReadGraph() throws Exception {
        System.out.println("(MatrixHierNpartiteGraph.readGraph) Test starts.");
        MatrixHierNpartiteGraph instance = 
                new MatrixHierNpartiteGraph(testReadGraphInput,false,0);
        /* In this test, we check 2 properties: (1) The number and details of all vertices. 
         (2) The structres and weights of all edges.*/
        /* Check the vertices. */
        /* Create correct vertcies. */
        Vertex2 a1 = new Vertex2("a1",0,-1);
        Vertex2 a2 = new Vertex2("a2",0,-1);
        Vertex2 a3 = new Vertex2("a3",0,-1);
        Vertex2 b1 = new Vertex2("b1",1,-1);
        Vertex2 b2 = new Vertex2("b3",1,-1);
        Vertex2 c1 = new Vertex2("c1",2,-1);
        Vertex2 c2 = new Vertex2("c2",2,-1);
        Vertex2 c3 = new Vertex2("c3",2,-1);
        Vertex2 c4 = new Vertex2("c4",2,-1);
        /* Create the arrayList for the correct vertices. */
        ArrayList<Vertex2> correctVertices = new ArrayList<>();
        correctVertices.add(a1);
        correctVertices.add(a2);
        correctVertices.add(a3);
        correctVertices.add(b1);
        correctVertices.add(b2);
        correctVertices.add(c1);
        correctVertices.add(c2);
        correctVertices.add(c3);
        correctVertices.add(c4);
        /* Compare the two arraylist of vertices. */
        MatrixBipartiteGraph2Test.assertEqualArrayList(correctVertices, instance.vertices);
        
        /* Check edges.*/
        /* First get the vertices from the inputted graph for later use to obtain the edge weights. */
        a1 = instance.getVertex("a1");
        a2 = instance.getVertex("a2");
        b1 = instance.getVertex("b1");
        b2 = instance.getVertex("b2");
        c1 = instance.getVertex("c1");
        c2 = instance.getVertex("c2");
        a3 = instance.getVertex("a3");
        c3 = instance.getVertex("c3");
        c4 = instance.getVertex("c4");
        /* First check non-existing edges. */
        assertEquals(true,Double.isNaN(instance.edgeWeight(a1,c3)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(b1,c3)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(c2,a2)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(a1,b1)));
        
        /* Check all edge weights. */
        assertEquals(5.1,instance.edgeWeight(a1,b2),0.0001);
        assertEquals(5.2,instance.edgeWeight(a2,b2),0.0001);
        assertEquals(5.3,instance.edgeWeight(b1,a3),0.0001);
        assertEquals(6.1,instance.edgeWeight(c1,b1),0.0001);
        assertEquals(6.2,instance.edgeWeight(b1,c2),0.0001);
        assertEquals(6.3,instance.edgeWeight(c2,b2),0.0001);
        assertEquals(6.4,instance.edgeWeight(c3,b2),0.0001);
        assertEquals(6.5,instance.edgeWeight(b2,c4),0.0001);
        
        System.out.println("(MatrixHierNpartiteGraph.readGraph) Test ends.");
    }

    /**
     * Test of readGraphWithHeader method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testReadGraphWithHeader() throws Exception {
        System.out.println("(MatrixHierNpartiteGraph.readGraphWithHeader) Test starts.");
        
        MatrixHierNpartiteGraph instance = 
                new MatrixHierNpartiteGraph(testReadGraphWithHeaderInput,true,0);
        /* In this test, we check 2 properties: (1) The number and details of all vertices. 
         (2) The structres and weights of all edges.*/
        /* Check the vertices. */
        /* Create correct vertcies. */
        Vertex2 a1 = new Vertex2("a1",0,-1);
        Vertex2 a2 = new Vertex2("a2",0,-1);
        Vertex2 a3 = new Vertex2("a3",0,-1);
        Vertex2 b1 = new Vertex2("b1",1,-1);
        Vertex2 b2 = new Vertex2("b3",1,-1);
        Vertex2 c1 = new Vertex2("c1",2,-1);
        Vertex2 c2 = new Vertex2("c2",2,-1);
        Vertex2 c3 = new Vertex2("c3",2,-1);
        Vertex2 c4 = new Vertex2("c4",2,-1);
        /* Create the arrayList for the correct vertices. */
        ArrayList<Vertex2> correctVertices = new ArrayList<>();
        correctVertices.add(a1);
        correctVertices.add(a2);
        correctVertices.add(a3);
        correctVertices.add(b1);
        correctVertices.add(b2);
        correctVertices.add(c1);
        correctVertices.add(c2);
        correctVertices.add(c3);
        correctVertices.add(c4);
        /* Compare the two arraylist of vertices. */
        MatrixBipartiteGraph2Test.assertEqualArrayList(correctVertices, instance.vertices);
        
        /* Check edges.*/
        /* First get the vertices from the inputted graph for later use to obtain the edge weights. */
        a1 = instance.getVertex("a1");
        a2 = instance.getVertex("a2");
        b1 = instance.getVertex("b1");
        b2 = instance.getVertex("b2");
        c1 = instance.getVertex("c1");
        c2 = instance.getVertex("c2");
        a3 = instance.getVertex("a3");
        c3 = instance.getVertex("c3");
        c4 = instance.getVertex("c4");
        /* First check non-existing edges. */
        assertEquals(true,Double.isNaN(instance.edgeWeight(a1,c3)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(b1,c3)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(c2,a2)));
        assertEquals(true,Double.isNaN(instance.edgeWeight(a1,b1)));
        
        /* Check all edge weights. */
        assertEquals(5.1,instance.edgeWeight(a1,b2),0.0001);
        assertEquals(5.2,instance.edgeWeight(a2,b2),0.0001);
        assertEquals(5.3,instance.edgeWeight(b1,a3),0.0001);
        assertEquals(6.1,instance.edgeWeight(c1,b1),0.0001);
        assertEquals(6.2,instance.edgeWeight(b1,c2),0.0001);
        assertEquals(6.3,instance.edgeWeight(c2,b2),0.0001);
        assertEquals(6.4,instance.edgeWeight(c3,b2),0.0001);
        assertEquals(6.5,instance.edgeWeight(b2,c4),0.0001);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("(MatrixHierNpartiteGraph.readGraphWithHeader) Test ends.");
        
    }

    /**
     * Test of restoreThresh method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testRestoreThresh() {
        System.out.println("restoreThresh");
        MatrixHierNpartiteGraph instance = null;
        instance.restoreThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAction method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testRemoveAction() {
        System.out.println("removeAction");
        int Index = 0;
        MatrixHierNpartiteGraph instance = null;
        boolean expResult = false;
        boolean result = instance.removeAction(Index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDist method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testSetDist() {
        System.out.println("setDist");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierNpartiteGraph instance = null;
        instance.setDist(vtx1, vtx2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEdgeWeight method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testSetEdgeWeight() {
        System.out.println("(MatrixHierNpartiteGraph.setEdgeWeight) Test starts.");
        MatrixHierNpartiteGraph instance = new MatrixHierNpartiteGraph(testEdgeWeightInput,true,0);
        /* Get the vertices. */
        Vertex2 a1 = instance.getVertex("a1");
        Vertex2 b1 = instance.getVertex("b1");
        Vertex2 b2 = instance.getVertex("b2");
        Vertex2 c1 = instance.getVertex("c1");
        Vertex2 c2 = instance.getVertex("c2");
        Vertex2 d1 = instance.getVertex("d1");
        
        /* Check the edge weights. */
        assertEquals(5.8,instance.edgeWeight(a1,b1),0.0001);
        assertEquals(5.8,instance.edgeWeight(b1,a1),0.0001);
        assertEquals(6.2,instance.edgeWeight(b1,c1),0.0001);
        assertEquals(6.8,instance.edgeWeight(c2,b1),0.0001);
        assertEquals(6.1,instance.edgeWeight(b2,c2),0.0001);
        assertEquals(6.5,instance.edgeWeight(c2,d1),0.0001);
        
        assertEquals(true, Double.isNaN(instance.edgeWeight(a1,c1)));
        assertEquals(true, Double.isNaN(instance.edgeWeight(c2,c1)));
        
        /* Set new weight to edges. */
        instance.setEdgeWeight(a1, b1, 82.2);
        assertEquals(82.2,instance.edgeWeight(a1,b1),0.0001);
        
        /* Test the exception .*/
        boolean exceptionFlag = false;
        try{
            instance.setEdgeWeight(b1, b2, 4.5);
        }catch(IllegalArgumentException e){
            exceptionFlag = true;
        }
        
        assertEquals(true,exceptionFlag);
        
        System.out.println("(MatrixHierNpartiteGraph.setEdgeWeight) Test ends.");
    }

    /**
     * Test of takeAction method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testTakeAction() {
        System.out.println("takeAction");
        int idx = 0;
        MatrixHierNpartiteGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeAction(idx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("Not tested at the current stage.");
    }

    /**
     * Test of takeActions method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testTakeActions() {
        System.out.println("takeActions");
        MatrixHierNpartiteGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeActions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateDist method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testUpdateDist() {
        System.out.println("updateDist");
        MatrixHierNpartiteGraph instance = null;
        instance.updateDist();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePos method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testUpdatePos() {
        System.out.println("updatePos");
        double[][] dispVector = null;
        MatrixHierNpartiteGraph instance = null;
        instance.updatePos(dispVector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertexSetCount method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testVertexSetCount() {
        System.out.println("vertexSetCount");
        MatrixHierNpartiteGraph instance = null;
        int expResult = 0;
        int result = instance.vertexSetCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeGraphTo method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testWriteGraphTo() {
        System.out.println("(MatrixHierNpartiteGraphTest.testWriteGraphTo) Test starts.");
        MatrixHierNpartiteGraph graph = new
                MatrixHierNpartiteGraph(testReadGraphWithHeaderInput,true);
        graph.writeGraphTo(testOutputPlain,false);
        MatrixHierNpartiteGraph out = new 
                MatrixHierNpartiteGraph(testOutputPlain,true);
        
        System.out.println("(MatrixHierNpartiteGraphTest.testWriteGraphTO) Test ends.");
    }

    /**
     * Test of writeClusterTo method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testWriteClusterTo() {
        System.out.println("(MatrixNpartiteGraphTest.testWriteClusterTo) Test starts.");
        MatrixHierNpartiteGraph graph = new 
                MatrixHierNpartiteGraph(testReadGraphWithHeaderInput, true);
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        Param p = new Param("./parameters.ini");
        try{
            algorithm.run(graph, p);
        }catch(IOException e){
            System.err.println("(MatrixNpartiteGraphTest.testWriteClusterTo) Algorithm throws IOException.");
        }
        graph.writeClusterTo(testClusterOutput);
        System.out.println("(MatrixNpartiteGraphTest.testWriteClusterTo) Test ends.");
    }

    /**
     * Test of writeResultInfoTo method, of class MatrixHierNpartiteGraph.
     */
    @Test
    public void testWriteResultInfoTo() {
        System.out.println("writeResultInfoTo");
        String FilePath = "";
        MatrixHierNpartiteGraph instance = null;
        instance.writeResultInfoTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
