/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author penpen926
 */
public class MatrixHierGeneralSubgraphTest {
    String testConstructorSubgraph = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_bfs_input.txt";
    public MatrixHierGeneralSubgraphTest() {
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

    /* 
     * Test of the constructor of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testConstructor(){
        System.out.println("(MatrixHierGeneralSubgraphTest.constructor) Test starts.");
        MatrixHierGeneralGraph graphInstance =
                new MatrixHierGeneralGraph(testConstructorSubgraph,true,0);
        /* Create an arraylist of nodes for subgraph. */
        ArrayList<Vertex2> subgraphVertices = new ArrayList<Vertex2>();
        /* Get the vertices. */
        Vertex2 a4 = graphInstance.getVertex("a4");
        Vertex2 a5 = graphInstance.getVertex("a5");
        Vertex2 c3 = graphInstance.getVertex("c3");
        Vertex2 c4 = graphInstance.getVertex("c4");
        Vertex2 d1 = graphInstance.getVertex("d1");
        Vertex2 b2 = graphInstance.getVertex("b2");
        /* Add the vertices. */
        subgraphVertices.add(a4);
        subgraphVertices.add(a5);
        subgraphVertices.add(c3);
        subgraphVertices.add(c4);
        subgraphVertices.add(d1);
        subgraphVertices.add(b2);
        /* Create the subgraph */
        MatrixHierGeneralSubgraph subgraphInstance = 
                new MatrixHierGeneralSubgraph(subgraphVertices,graphInstance);
        /* Check the number of vertices. */
        assertEquals(6,subgraphInstance.vertexCount());
        assertEquals(1.2,subgraphInstance.edgeWeight(a4,a5),0.0001);
        assertEquals(2.1,subgraphInstance.edgeWeight(a4,b2),0.0001);
        assertEquals(3.3,subgraphInstance.edgeWeight(b2,c3),0.0001);
        assertEquals(3.4,subgraphInstance.edgeWeight(c3,c4),0.0001);
        assertEquals(4.0,subgraphInstance.edgeWeight(c4,d1),0.0001);
        /* Check several non-edges. */
        assertEquals(true,Double.isNaN(subgraphInstance.edgeWeight(a5,c3)));
        assertEquals(true,Double.isNaN(subgraphInstance.edgeWeight(a4,c4)));
        assertEquals(true,Double.isNaN(subgraphInstance.edgeWeight(c4,b2)));
    }
    /**
     * Test of bfs method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testBfs() {
        System.out.println("bfs");
        Vertex2 Vtx = null;
        MatrixHierGeneralSubgraph instance = null;
        MatrixHierGeneralSubgraph expResult = null;
        MatrixHierGeneralSubgraph result = instance.bfs(Vtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectedComponents method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("connectedComponents");
        MatrixHierGeneralSubgraph instance = null;
        List expResult = null;
        List result = instance.connectedComponents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edgeWeight method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testEdgeWeight() {
        System.out.println("edgeWeight");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        MatrixHierGeneralSubgraph instance = null;
        double expResult = 0.0;
        double result = instance.edgeWeight(vtx1, vtx2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuperGraph method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testGetSuperGraph() {
        System.out.println("getSuperGraph");
        MatrixHierGeneralSubgraph instance = null;
        MatrixHierGeneralGraph expResult = null;
        MatrixHierGeneralGraph result = instance.getSuperGraph();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of neighbours method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testNeighbours() {
        System.out.println("neighbours");
        Vertex2 vtx = null;
        MatrixHierGeneralSubgraph instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.neighbours(vtx);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEdgeWeight method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testSetEdgeWeight() {
        System.out.println("setEdgeWeight");
        Vertex2 vtx1 = null;
        Vertex2 vtx2 = null;
        double edgeWeight = 0.0;
        MatrixHierGeneralSubgraph instance = null;
        instance.setEdgeWeight(vtx1, vtx2, edgeWeight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vertexSetCount method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testVertexSetCount() {
        System.out.println("vertexSetCount");
        MatrixHierGeneralSubgraph instance = null;
        int expResult = 0;
        int result = instance.vertexSetCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class MatrixHierGeneralSubgraph.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        MatrixHierGeneralSubgraph o = null;
        MatrixHierGeneralSubgraph instance = null;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
