/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mac-97-41
 */
public class MatrixGraphTest {
    /* The input files for test cases. */
    String testReadGraphInput = "../../data/testdata/unit_test/MatrixGraph/readgraph_input.txt";
    String testReadGraphWithHeaderInput = "../../data/testdata/unit_test/MatrixGraph/readgraphwithheader_input.txt";
    
    public MatrixGraphTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of bfs method, of class MatrixGraph.
     */
    @Test
    public void testBfs() {
        System.out.println("(MatrixGraphTest.testBfs) Test starts.");
        Vertex2 Vtx = null;
        GeneralGraph instance = null;
        Subgraph2 expResult = null;
        Subgraph2 result = instance.bfs(Vtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectedComponents method, of class MatrixGraph.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("connectedComponents");
        GeneralGraph instance = null;
        List<? extends Subgraph2> expResult = null;
        List<? extends Subgraph2> result = instance.connectedComponents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixGraph.
     */
    @Test
    public void testDetractThresh_0args() {
        System.out.println("detractThresh");
        GeneralGraph instance = null;
        instance.detractThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixGraph.
     */
    @Test
    public void testDetractThresh_float() {
        System.out.println("detractThresh");
        float thresh = 0.0f;
        GeneralGraph instance = null;
        instance.detractThresh(thresh);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dist method, of class MatrixGraph.
     */
    @Test
    public void testDist() {
        System.out.println("dist");
        Vertex2 Vtx1 = null;
        Vertex2 Vtx2 = null;
        GeneralGraph instance = null;
        float expResult = 0.0f;
        float result = instance.dist(Vtx1, Vtx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixGraph.
     */
    @Test
    public void testEdgeWeight_Vertex2_Vertex2() {
        System.out.println("edgeWeight");
        Vertex2 Vtx1 = null;
        Vertex2 Vt2 = null;
        GeneralGraph instance = null;
        float expResult = 0.0f;
        float result = instance.edgeWeight(Vtx1, Vt2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixGraph.
     */
    @Test
    public void testEdgeWeight_int_int() {
        System.out.println("edgeWeight");
        int vtxIdx1 = 0;
        int vtxIdx2 = 0;
        GeneralGraph instance = null;
        float expResult = 0.0f;
        float result = instance.edgeWeight(vtxIdx1, vtxIdx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class MatrixGraph.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        GeneralGraph instance = null;
        float expResult = 0.0f;
        float result = instance.getCost();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isActionTaken method, of class MatrixGraph.
     */
    @Test
    public void testIsActionTaken() {
        System.out.println("isActionTaken");
        int ActIndx = 0;
        GeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.isActionTaken(ActIndx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of neighbours method, of class MatrixGraph.
     */
    @Test
    public void testNeighbours() {
        System.out.println("neighbours");
        Vertex2 vtx = null;
        GeneralGraph instance = null;
        ArrayList<Vertex2> expResult = null;
        ArrayList<Vertex2> result = instance.neighbours(vtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pushAction method, of class MatrixGraph.
     */
    @Test
    public void testPushAction() {
        System.out.println("pushAction");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        GeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.pushAction(vtx1, vtx2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readGraph method, of class MatrixGraph.
     */
    @Test
    public void testReadGraph() throws Exception {
        System.out.println("(MatrixGraph.testReadGraph) Test starts.");
        
        GeneralGraph instance = new GeneralGraph(testReadGraphInput,false,false);
        /* Check the number of vertices. */
        assertEquals(5,instance.vertexCount());
        /* Create reference vertices. */
        Vertex2 refv1 = new Vertex2("v1",-1,-1);
        Vertex2 refv2 = new Vertex2("v2",-1,-1);
        Vertex2 refv3 = new Vertex2("v3",-1,-1);
        Vertex2 refv4 = new Vertex2("v4",-1,-1);
        Vertex2 refv5 = new Vertex2("v5",-1,-1);
        Vertex2 refv6 = new Vertex2("v6",-1,-1);
        ArrayList<Vertex2> refVertices = new ArrayList<>();
        refVertices.add(refv1);
        refVertices.add(refv2);
        refVertices.add(refv3);
        refVertices.add(refv4);
        refVertices.add(refv5);
        refVertices.add(refv6);
        /* Check the vertices. */
        MatrixBipartiteGraph2Test.assertEqualArrayList(instance.getVertices(), refVertices);
        
        /* Check the edges. */
        /* Get vertices in the graph. */
        Vertex2 v1 = instance.getVertex("v1");
        Vertex2 v2 = instance.getVertex("v2");
        Vertex2 v3 = instance.getVertex("v3");
        Vertex2 v4 = instance.getVertex("v4");
        Vertex2 v5 = instance.getVertex("v5");
        Vertex2 v6 = instance.getVertex("v6");
        
        assertEquals(5.3,instance.edgeWeight(v1,v2),0.00001);
        assertEquals(5.3,instance.edgeWeight(v2,v1),0.00001);
        assertEquals(5.7,instance.edgeWeight(v2,v3),0.00001);
        assertEquals(4.0,instance.edgeWeight(v2,v4),0.00001);
        assertEquals(5.0,instance.edgeWeight(v2,v5),0.00001);
        assertEquals(3.0,instance.edgeWeight(v3,v4),0.00001);
        assertEquals(2.0,instance.edgeWeight(v3,v5),0.00001);
        
        /* Check some self-loops, see if they remain Double.NaN. */
        assertEquals(true, Double.isNaN(instance.edgeWeight(v4, v4)));
        assertEquals(true, Double.isNaN(instance.edgeWeight(v2, v2)));
        System.out.println("(MatrixGraph.testReadGraph) Test ends.");
    }
    
    /**
     * Test of readGraphWithHeader method, of class MatrixGraph.
     */
    @Test
    public void testReadGraphWtihHeader(){
        System.out.println("(MatrixGraph.testReadGraphWithHeader) Test starts.");
        
        GeneralGraph instance = new GeneralGraph(testReadGraphInput,false,false);
        /* Check the number of vertices. */
        assertEquals(5,instance.vertexCount());
        /* Create reference vertices. */
        Vertex2 refv1 = new Vertex2("v1",-1,-1);
        Vertex2 refv2 = new Vertex2("v2",-1,-1);
        Vertex2 refv3 = new Vertex2("v3",-1,-1);
        Vertex2 refv4 = new Vertex2("v4",-1,-1);
        Vertex2 refv5 = new Vertex2("v5",-1,-1);
        Vertex2 refv6 = new Vertex2("v6",-1,-1);
        ArrayList<Vertex2> refVertices = new ArrayList<>();
        refVertices.add(refv1);
        refVertices.add(refv2);
        refVertices.add(refv3);
        refVertices.add(refv4);
        refVertices.add(refv5);
        refVertices.add(refv6);
        /* Check the vertices. */
        MatrixBipartiteGraph2Test.assertEqualArrayList(instance.getVertices(), refVertices);
        
        /* Check the edges. */
        /* Get vertices in the graph. */
        Vertex2 v1 = instance.getVertex("v1");
        Vertex2 v2 = instance.getVertex("v2");
        Vertex2 v3 = instance.getVertex("v3");
        Vertex2 v4 = instance.getVertex("v4");
        Vertex2 v5 = instance.getVertex("v5");
        Vertex2 v6 = instance.getVertex("v6");
        
        assertEquals(5.3,instance.edgeWeight(v1,v2),0.00001);
        assertEquals(5.3,instance.edgeWeight(v2,v1),0.00001);
        assertEquals(5.7,instance.edgeWeight(v2,v3),0.00001);
        assertEquals(4.0,instance.edgeWeight(v2,v4),0.00001);
        assertEquals(5.0,instance.edgeWeight(v2,v5),0.00001);
        assertEquals(3.0,instance.edgeWeight(v3,v4),0.00001);
        assertEquals(2.0,instance.edgeWeight(v3,v5),0.00001);
        
        /* Check some self-loops, see if they remain Double.NaN. */
        assertEquals(true, Double.isNaN(instance.edgeWeight(v4, v4)));
        assertEquals(true, Double.isNaN(instance.edgeWeight(v2, v2)));
        System.out.println("(MatrixGraph.testReadGraphWithHeader) Test ends.");
    }

    /**
     * Test of restoreThresh method, of class MatrixGraph.
     */
    @Test
    public void testRestoreThresh() {
        System.out.println("restoreThresh");
        GeneralGraph instance = null;
        instance.restoreThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAction method, of class MatrixGraph.
     */
    @Test
    public void testRemoveAction() {
        System.out.println("removeAction");
        int Index = 0;
        GeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.removeAction(Index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEdgeWeight method, of class MatrixGraph.
     */
    @Test
    public void testSetEdgeWeight() {
        System.out.println("setEdgeWeight");
        Vertex2 Vtx1 = null;
        Vertex2 Vtx2 = null;
        float EdgeWeight = 0.0f;
        GeneralGraph instance = null;
        instance.setEdgeWeight(Vtx1, Vtx2, EdgeWeight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of takeAction method, of class MatrixGraph.
     */
    @Test
    public void testTakeAction() {
        System.out.println("takeAction");
        int Index = 0;
        GeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeAction(Index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of takeActions method, of class MatrixGraph.
     */
    @Test
    public void testTakeActions() {
        System.out.println("takeActions");
        GeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeActions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateDist method, of class MatrixGraph.
     */
    @Test
    public void testUpdateDist() {
        System.out.println("updateDist");
        GeneralGraph instance = null;
        instance.updateDist();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePos method, of class MatrixGraph.
     */
    @Test
    public void testUpdatePos() {
        System.out.println("updatePos");
        float[][] dispVector = null;
        GeneralGraph instance = null;
        instance.updatePos(dispVector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertexSetCount method, of class MatrixGraph.
     */
    @Test
    public void testVertexSetCount() {
        System.out.println("vertexSetCount");
        GeneralGraph instance = null;
        int expResult = 0;
        int result = instance.vertexSetCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeGraphTo method, of class MatrixGraph.
     */
    @Test
    public void testWriteGraphTo() {
        System.out.println("writeGraphTo");
        String FilePath = "";
        GeneralGraph instance = null;
        instance.writeGraphTo(FilePath,false);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeClusterTo method, of class MatrixGraph.
     */
    @Test
    public void testWriteClusterTo() {
        System.out.println("writeClusterTo");
        String FilePath = "";
        GeneralGraph instance = null;
        instance.writeClusterTo(FilePath,true);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeResultInfoTo method, of class MatrixGraph.
     */
    @Test
    public void testWriteResultInfoTo() {
        System.out.println("writeResultInfoTo");
        String FilePath = "";
        GeneralGraph instance = null;
        instance.writeResultInfoTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readGraphWithHeader method, of class MatrixGraph.
     */
    @Test
    public void testReadGraphWithHeader() throws Exception {
        System.out.println("readGraphWithHeader");
        String filePath = "";
        GeneralGraph instance = null;
        instance.readGraphWithHeader(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    
    
}
