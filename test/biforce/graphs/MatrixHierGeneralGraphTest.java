/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author penpen926
 */
public class MatrixHierGeneralGraphTest {
    String testReadGraphNoheaderInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_readgraph_noheader.txt";
    String testReadGraphHeaderInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_readgraph_header.txt";
    String testBfsInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_bfs_input.txt";
    public MatrixHierGeneralGraphTest() {
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
     * Test of bfs method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testBfs() {
        System.out.println("(MatrixHierGeneralGraphTest.testBfs) Test starts.");
        MatrixHierGeneralGraph graph = new MatrixHierGeneralGraph(testBfsInput,true,0);
        /* Get the vertices in the graph. */
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 a3 = graph.getVertex("a3");
        Vertex2 a4 = graph.getVertex("a4");
        Vertex2 a5 = graph.getVertex("a5");
        Vertex2 a6 = graph.getVertex("a6");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 c1 = graph.getVertex("c1");
        Vertex2 c2 = graph.getVertex("c2");
        Vertex2 c3 = graph.getVertex("c3");
        Vertex2 c4 = graph.getVertex("c4");
        Vertex2 d1 = graph.getVertex("d1");
        /* Create the correct answer1. */
        ArrayList<Vertex2> verticesSub1 = new ArrayList<>();
        verticesSub1.add(a1);
        verticesSub1.add(a2);
        verticesSub1.add(a3);
        verticesSub1.add(b1);
        verticesSub1.add(c1);
        verticesSub1.add(c2);
        
        ArrayList<Vertex2> verticesSub2 = new ArrayList<>();
        verticesSub2.add(a4);
        verticesSub2.add(a5);
        verticesSub2.add(b2);
        verticesSub2.add(c3);
        verticesSub2.add(c4);
        verticesSub2.add(d1);
        
        ArrayList<Vertex2> verticesSub3 = new ArrayList<>();
        verticesSub3.add(a6);
        
        MatrixHierGeneralSubgraph sub1 = 
                new MatrixHierGeneralSubgraph(verticesSub1,graph);
        MatrixHierGeneralSubgraph sub2 = 
                new MatrixHierGeneralSubgraph(verticesSub2,graph);
        MatrixHierGeneralSubgraph sub3 = 
                new MatrixHierGeneralSubgraph(verticesSub3,graph);
        /* Get the bfs subgraphs. */
        MatrixHierGeneralSubgraph bfs1 = graph.bfs(a1);
        MatrixHierGeneralSubgraph bfs2 = graph.bfs(b1);
        MatrixHierGeneralSubgraph bfs3 = graph.bfs(a5);
        MatrixHierGeneralSubgraph bfs4 = graph.bfs(d1);
        MatrixHierGeneralSubgraph bfs5 = graph.bfs(a6);
        
        assertEquals(true, sub1.equals(bfs1));
        assertEquals(true, sub1.equals(bfs2));
        assertEquals(true, sub2.equals(bfs3));
        assertEquals(true, sub2.equals(bfs4));
        assertEquals(true, sub3.equals(bfs5));
        /* To comapre subgraph. */
        
        System.out.println("(MatrixHierGeneralGraphTest.testBfs) Test ends");
    }

    /**
     * Test of connectedComponents method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("connectedComponents");
        MatrixHierGeneralGraph instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.connectedComponents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testDetractThresh_0args() {
        System.out.println("detractThresh");
        MatrixHierGeneralGraph instance = null;
        instance.detractThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testDetractThresh_double() {
        System.out.println("detractThresh");
        double thresh = 0.0;
        MatrixHierGeneralGraph instance = null;
        instance.detractThresh(thresh);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dist method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testDist() {
        System.out.println("dist");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierGeneralGraph instance = null;
        double expResult = 0.0;
        double result = instance.dist(vtx1, vtx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testEdgeWeight_Vertex2_Vertex2() {
        System.out.println("edgeWeight");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierGeneralGraph instance = null;
        double expResult = 0.0;
        double result = instance.edgeWeight(vtx1, vtx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testEdgeWeight_int_int() {
        System.out.println("edgeWeight");
        int vtxIdx1 = 0;
        int vtxIdx2 = 0;
        MatrixHierGeneralGraph instance = null;
        double expResult = 0.0;
        double result = instance.edgeWeight(vtxIdx1, vtxIdx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of interEdgeWeightMatrix method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testInterEdgeWeightMatrix() {
        System.out.println("interEdgeWeightMatrix");
        int i = 0;
        MatrixHierGeneralGraph instance = null;
        double[][] expResult = null;
        double[][] result = instance.interEdgeWeightMatrix(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of intraEdgeWeightMatrix method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testIntraEdgeWeightMatrix() {
        System.out.println("intraEdgeWeightMatrix");
        int i = 0;
        MatrixHierGeneralGraph instance = null;
        double[][] expResult = null;
        double[][] result = instance.intraEdgeWeightMatrix(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        MatrixHierGeneralGraph instance = null;
        double expResult = 0.0;
        double result = instance.getCost();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of isActionTaken method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testIsActionTaken() {
        System.out.println("isActionTaken");
        int actIdx = 0;
        MatrixHierGeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.isActionTaken(actIdx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of neighbours method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testNeighbours() {
        System.out.println("neighbours");
        Vertex2 currentVtx = null;
        MatrixHierGeneralGraph instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.neighbours(currentVtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of pushAction method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testPushAction() {
        System.out.println("pushAction");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierGeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.pushAction(vtx1, vtx2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readGraph method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testReadGraph() throws Exception {
        System.out.println("(MatrixHierGeneralGraphTest.testReadGraph) Test starts.");
        MatrixHierGeneralGraph graph = 
                new MatrixHierGeneralGraph(testReadGraphNoheaderInput,false,0);
        /* Check if the threshold is detracted. */
        assertEquals(true,graph.isThreshDetracted());
        /* Check the number of vertex sets. */
        assertEquals(4, graph.setSizes.length);
        /* Check the number of vertices in each set. */
        assertEquals(4,graph.setSizes[0]);
        assertEquals(2,graph.setSizes[1]);
        assertEquals(3,graph.setSizes[2]);
        assertEquals(2,graph.setSizes[3]);
        /* Check the size of vertices in the graph. */
        assertEquals(11, graph.vertexCount());
        /* Next, check the structure of the graph. */
        /* First extract all vertices. */
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 a3 = graph.getVertex("a3");
        Vertex2 a4 = graph.getVertex("a4");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 c1 = graph.getVertex("c1");
        Vertex2 c2 = graph.getVertex("c2");
        Vertex2 c3 = graph.getVertex("c3");
        Vertex2 d1 = graph.getVertex("d1");
        Vertex2 d2 = graph.getVertex("d2");
        /* Check the edge weights. */
        assertEquals(1.0,graph.edgeWeight(a1,a2),0.0001);
        assertEquals(1.1,graph.edgeWeight(a3,a2),0.0001);
        assertEquals(1.2,graph.edgeWeight(a1,a3),0.0001);
        assertEquals(2.0,graph.edgeWeight(b1,a2),0.0001);
        assertEquals(2.1,graph.edgeWeight(a3,b2),0.0001);
        assertEquals(2.2,graph.edgeWeight(b2,a4),0.0001);
        assertEquals(3.0,graph.edgeWeight(c1,b2),0.0001);
        assertEquals(3.1,graph.edgeWeight(c2,c1),0.0001);
        assertEquals(4.0,graph.edgeWeight(c2,d1),0.0001);
        assertEquals(4.1,graph.edgeWeight(c3,d1),0.0001);
        assertEquals(4.2,graph.edgeWeight(d2,d1),0.0001);
        /* Random check 5 non-edges. */
        assertEquals(true,Double.isNaN(graph.edgeWeight(a1,a4)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(a1,b2)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(b2,c2)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(c2,c3)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(c1,d1)));
        System.out.println("(MatrixHierGeneralGraphTest.testReadGraph) Test ends.");
    }
    /**
     * Test of readGraphWithHeader method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testReadGraphWithHeader() throws Exception {
        System.out.println("(MatrixHierGeneralGraphTest.testReadGraphWithHeader) Test starts.");
        MatrixHierGeneralGraph graph = 
                new MatrixHierGeneralGraph(testReadGraphHeaderInput,true,0);
        /* Check if the threshold is detracted. */
        assertEquals(true,graph.isThreshDetracted());
        /* Check the number of vertex sets. */
        assertEquals(4, graph.setSizes.length);
        /* Check the number of vertices in each set. */
        assertEquals(4,graph.setSizes[0]);
        assertEquals(2,graph.setSizes[1]);
        assertEquals(3,graph.setSizes[2]);
        assertEquals(2,graph.setSizes[3]);
        /* Check the size of vertices in the graph. */
        assertEquals(11, graph.vertexCount());
        /* Next, check the structure of the graph. */
        /* First extract all vertices. */
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 a3 = graph.getVertex("a3");
        Vertex2 a4 = graph.getVertex("a4");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 c1 = graph.getVertex("c1");
        Vertex2 c2 = graph.getVertex("c2");
        Vertex2 c3 = graph.getVertex("c3");
        Vertex2 d1 = graph.getVertex("d1");
        Vertex2 d2 = graph.getVertex("d2");
        /* Check the edge weights. */
        assertEquals(1.0,graph.edgeWeight(a1,a2),0.0001);
        assertEquals(1.1,graph.edgeWeight(a3,a2),0.0001);
        assertEquals(1.2,graph.edgeWeight(a1,a3),0.0001);
        assertEquals(2.0,graph.edgeWeight(b1,a2),0.0001);
        assertEquals(2.1,graph.edgeWeight(a3,b2),0.0001);
        assertEquals(2.2,graph.edgeWeight(b2,a4),0.0001);
        assertEquals(3.0,graph.edgeWeight(c1,b2),0.0001);
        assertEquals(3.1,graph.edgeWeight(c2,c1),0.0001);
        assertEquals(4.0,graph.edgeWeight(c2,d1),0.0001);
        assertEquals(4.1,graph.edgeWeight(c3,d1),0.0001);
        assertEquals(4.2,graph.edgeWeight(d2,d1),0.0001);
        /* Random check 5 non-edges. */
        assertEquals(true,Double.isNaN(graph.edgeWeight(a1,a4)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(a1,b2)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(b2,c2)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(c2,c3)));
        assertEquals(true,Double.isNaN(graph.edgeWeight(c1,d1)));
        System.out.println("(MatrixHierGeneralGraphTest.testReadGraphWithHeader) Test ends.");
    }
    /**
     * Test of restoreThresh method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testRestoreThresh() {
        System.out.println("restoreThresh");
        MatrixHierGeneralGraph instance = null;
        instance.restoreThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAction method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testRemoveAction() {
        System.out.println("removeAction");
        int Index = 0;
        MatrixHierGeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.removeAction(Index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of setDist method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testSetDist() {
        System.out.println("setDist");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierGeneralGraph instance = null;
        instance.setDist(vtx1, vtx2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEdgeWeight method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testSetEdgeWeight() {
        System.out.println("setEdgeWeight");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        double edgeWeight = 0.0;
        MatrixHierGeneralGraph instance = null;
        instance.setEdgeWeight(vtx1, vtx2, edgeWeight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of takeAction method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testTakeAction() {
        System.out.println("takeAction");
        int idx = 0;
        MatrixHierGeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeAction(idx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of takeActions method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testTakeActions() {
        System.out.println("takeActions");
        MatrixHierGeneralGraph instance = null;
        boolean expResult = false;
        boolean result = instance.takeActions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of updateDist method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testUpdateDist() {
        System.out.println("updateDist");
        MatrixHierGeneralGraph instance = null;
        instance.updateDist();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of updatePos method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testUpdatePos() {
        System.out.println("updatePos");
        double[][] dispVector = null;
        MatrixHierGeneralGraph instance = null;
        instance.updatePos(dispVector);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertexSetCount method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testVertexSetCount() {
        System.out.println("vertexSetCount");
        MatrixHierGeneralGraph instance = null;
        int expResult = 0;
        int result = instance.vertexSetCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeGraphTo method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testWriteGraphTo() {
        System.out.println("writeGraphTo");
        String FilePath = "";
        MatrixHierGeneralGraph instance = null;
        instance.writeGraphTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeClusterTo method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testWriteClusterTo() {
        System.out.println("writeClusterTo");
        String FilePath = "";
        MatrixHierGeneralGraph instance = null;
        instance.writeClusterTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeResultInfoTo method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testWriteResultInfoTo() {
        System.out.println("writeResultInfoTo");
        String FilePath = "";
        MatrixHierGeneralGraph instance = null;
        instance.writeResultInfoTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
