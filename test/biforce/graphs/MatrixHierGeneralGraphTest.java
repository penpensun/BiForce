/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import biforce.algorithms.*;
import java.io.*;

/**
 *
 * @author penpen926
 */
public class MatrixHierGeneralGraphTest {
    String testReadGraphNoheaderInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_readgraph_noheader.txt";
    String testReadGraphHeaderInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_readgraph_header.txt";
    String testBfsInput = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_bfs_input.txt";
    String testReadXml = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_readxml.txt";
    String testReadXmlExternFile = "../../data/testdata/unit_test/XmlInputParser/test_readxml.txt";
    String testWriteXml = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_writexml.txt";
    String testWriteCluster = "../../data/testdata/unit_test/MatrixHierGeneralGraph/test_writecluster.txt";
    String testWritePlain ="../../data/testdata/unit_test/MatrixHierGeneralGraph/test_writeplain.txt";
    
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
        HierGraphWIE graph = new HierGraphWIE(testBfsInput,true,false,0);
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
        
        HierSubgraphWIE sub1 = 
                new HierSubgraphWIE(verticesSub1,graph);
        HierSubgraphWIE sub2 = 
                new HierSubgraphWIE(verticesSub2,graph);
        HierSubgraphWIE sub3 = 
                new HierSubgraphWIE(verticesSub3,graph);
        /* Get the bfs subgraphs. */
        HierSubgraphWIE bfs1 = graph.bfs(a1);
        HierSubgraphWIE bfs2 = graph.bfs(b1);
        HierSubgraphWIE bfs3 = graph.bfs(a5);
        HierSubgraphWIE bfs4 = graph.bfs(d1);
        HierSubgraphWIE bfs5 = graph.bfs(a6);
        
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
        instance.detractThresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detractThresh method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testDetractThresh_float() {
        System.out.println("detractThresh");
        float thresh = 0.0f;
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
        float expResult = 0.0f;
        float result = instance.dist(vtx1, vtx2);
        assertEquals(expResult, result, 0.0f);
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
        HierGraphWIE instance = null;
        float expResult = 0.0f;
        float result = instance.edgeWeight(vtx1, vtx2);
        assertEquals(expResult, result, 0.0f);
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
        HierGraphWIE instance = null;
        float expResult = 0.0f;
        float result = instance.edgeWeight(vtxIdx1, vtxIdx2);
        assertEquals(expResult, result, 0.0f);
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
        HierGraphWIE instance = null;
        float[][] expResult = null;
        float[][] result = instance.interEdgeWeightMatrix(i);
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
        HierGraphWIE instance = null;
        float[][] expResult = null;
        float[][] result = instance.intraEdgeWeightMatrix(i);
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
        HierGraphWIE instance = null;
        float expResult = 0.0f;
        float result = instance.getCost();
        assertEquals(expResult, result, 0.0f);
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        HierGraphWIE graph = 
                new HierGraphWIE(testReadGraphNoheaderInput,false,false,0);
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
        HierGraphWIE graph = 
                new HierGraphWIE(testReadGraphHeaderInput,true,false,0);
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
    
    @Test
    public void testReadGraphInXML(){
        System.out.println("(biforce.graph.MatrixHierGeneralGraphTest.testReadGraphInXML) Test starts. ");
        HierGraphWIE graph = new HierGraphWIE(testReadXml,false, true,0);
        /* Check the nodes. */
        /* Init nodes. */
        Vertex2 a1 = new Vertex2("a1",0,0);
        Vertex2 a2 = new Vertex2("a2",0,1);
        Vertex2 a3 = new Vertex2("a3",0,2);
        Vertex2 a4 = new Vertex2("a4",0,3);
        
        Vertex2 b1 = new Vertex2("b1",1,0);
        Vertex2 b2 = new Vertex2("b2",1,1);
        Vertex2 b3 = new Vertex2("b3",1,2);
        Vertex2 b4 = new Vertex2("b4",1,3);
        Vertex2 b5 = new Vertex2("b5",1,4);
        
        Vertex2 c1 = new Vertex2("c1",2,0);
        Vertex2 c2 = new Vertex2("c2",2,1);
        Vertex2 c3 = new Vertex2("c3",2,2);
        
        Vertex2 d1 = new Vertex2("d1",3,0);
        Vertex2 d2 = new Vertex2("d2",3,1);
        /* Check the nodes. */
        assertEquals(14,graph.vertexCount()); /* Check the number of the nodes in the graph. */
        
        assertTrue(graph.getVertices().contains(a1)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a1").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a2)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a2").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a3)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a3").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a4)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a4").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(b1)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b1").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b2)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b2").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b3)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b3").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b4)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b4").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b5)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b5").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(c1)); /* Check if the graph contains c1. */
        assertEquals(2, graph.getVertex("c1").getVtxSet()); /* Check if the vtxSet of c1 is 2. */
        
        assertTrue(graph.getVertices().contains(c2)); /* Check if the graph contains c2. */
        assertEquals(2, graph.getVertex("c2").getVtxSet()); /* Check if the vtxSet of c2 is 2. */
        
        assertTrue(graph.getVertices().contains(c3)); /* Check if the graph contains c3. */
        assertEquals(2, graph.getVertex("c3").getVtxSet()); /* Check if the vtxSet of c3 is 2. */
        
        assertTrue(graph.getVertices().contains(d1)); /* Check if the graph contains d1. */
        assertEquals(3, graph.getVertex("d1").getVtxSet());  /* Check if the vtxSet of d1 is 3. */
        
        assertTrue(graph.getVertices().contains(d2)); /* Check if the graph contains d2. */
        assertEquals(3, graph.getVertex("d2").getVtxSet());  /* Check if the vtxSet of d2 is 3. */
        
        /* Check the edge weights. */
        /* Init the intra matrices. */
        float[][] intraMatrix1 ={
            {Float.NaN,1.2f,1.3f,1.4f},
            {1.2f,Float.NaN,1.5f,1.6f},
            {1.3f,1.5f,Float.NaN,1.7f},
            {1.4f,1.6f,1.7f,Float.NaN}};
        float[][] intraMatrix2 = {
            {Float.NaN, 2.1f, 2.2f, 2.3f, 2.4f},
            {2.1f, Float.NaN, 2.5f, 2.6f, 2.7f},
            {2.2f, 2.5f, Float.NaN, 2.8f, 2.9f},
            {2.3f, 2.6f, 2.8f, Float.NaN, 3.0f},
            {2.4f, 2.7f, 2.9f, 3.0f, Float.NaN}};
        float[][] intraMatrix3 = {
            {Float.NaN, 3.1f, 3.2f},
            {3.1f, Float.NaN, 3.3f},
            {3.2f, 3.3f, Float.NaN}};
        float[][] intraMatrix4 = {
            {Float.NaN, 4.0f},
            {4.0f, Float.NaN}};
        /* Init the inter matrix. */
        float[][] interMatrix1={
            {1.1f, 1.2f, 1.3f, 1.4f, 1.5f},
            {2.1f, 2.2f, 2.3f, 2.4f, 2.5f},
            {3.1f, 3.2f, 3.3f, 3.4f, 3.5f},
            {4.1f, 4.2f, 4.3f, 4.4f, 4.5f}};
        float[][] interMatrix2={
            {1.6f, 1.7f, 1.8f},
            {2.6f, 2.7f, 2.8f},
            {3.6f, 3.7f, 3.8f},
            {4.6f, 4.7f, 4.8f},
            {5.6f, 5.7f, 5.8f}};
        float[][] interMatrix3={
            {6.1f, 6.2f},
            {7.1f, 7.2f},
            {8.1f, 8.2f}};
        ArrayList<Vertex2[]> nodes = new ArrayList<>();
        Vertex2[] row1 = {a1,a2,a3,a4};
        nodes.add(row1);
        Vertex2[] row2 = {b1,b2,b3,b4,b5};
        nodes.add(row2);
        Vertex2[] row3 = {c1,c2,c3};
        nodes.add(row3);
        Vertex2[] row4 = {d1,d2};
        nodes.add(row4);
        
        /* Check the intra edge weights. */
        for(int i=0;i<row1.length;i++)
            for(int j=0;j<row1.length;j++)
               assertEquals(intraMatrix1[i][j],graph.edgeWeight(row1[i], row1[j]),0.0001);
       for(int i=0;i<row2.length;i++)
            for(int j=0;j<row2.length;j++)
               assertEquals(intraMatrix2[i][j],graph.edgeWeight(row2[i], row2[j]),0.0001);
       for(int i=0;i<row3.length;i++)
           for(int j=0;j<row3.length;j++)
               assertEquals(intraMatrix3[i][j],graph.edgeWeight(row3[i], row3[j]),0.0001);
       for(int i=0;i<row4.length;i++)
           for(int j=0;j<row4.length;j++)
               assertEquals(intraMatrix4[i][j],graph.edgeWeight(row4[i], row4[j]),0.0001);
       
       /* Check the inter-matrix. */
       for(int i=0;i<row1.length;i++)
           for(int j=0;j<row2.length;j++)
               assertEquals(interMatrix1[i][j], graph.edgeWeight(row1[i], row2[j]),0.0001);
       
       for(int i=0;i<row2.length;i++)
           for(int j=0;j<row3.length;j++)
               assertEquals(interMatrix2[i][j], graph.edgeWeight(row2[i], row3[j]),0.0001);
       
       for(int i=0;i<row3.length;i++)
           for(int j=0;j<row4.length;j++)
               assertEquals(interMatrix3[i][j], graph.edgeWeight(row3[i], row4[j]),0.0001);
            
        System.out.println("(biforce.graph.MatrixHierGeneralGraphTest.testReadGraphInXML) Test ends");
    }

    @Test
    /**
     * This tests to read from an xml file with external files.
     */
    public void testReadGraphInXmlExternFile(){
        System.out.println("(biforce.graph.MatrixHierGeneralGraphTest.testReadGraphInXMLExternFile) Test starts. ");
        HierGraphWIE graph = new HierGraphWIE(testReadXmlExternFile,false, true,0);
        /* Check the nodes. */
        /* Init nodes. */
        Vertex2 a1 = new Vertex2("a1",0,0);
        Vertex2 a2 = new Vertex2("a2",0,1);
        Vertex2 a3 = new Vertex2("a3",0,2);
        Vertex2 a4 = new Vertex2("a4",0,3);
        
        Vertex2 b1 = new Vertex2("b1",1,0);
        Vertex2 b2 = new Vertex2("b2",1,1);
        Vertex2 b3 = new Vertex2("b3",1,2);
        Vertex2 b4 = new Vertex2("b4",1,3);
        Vertex2 b5 = new Vertex2("b5",1,4);
        
        Vertex2 c1 = new Vertex2("c1",2,0);
        Vertex2 c2 = new Vertex2("c2",2,1);
        Vertex2 c3 = new Vertex2("c3",2,2);
        
        Vertex2 d1 = new Vertex2("d1",3,0);
        Vertex2 d2 = new Vertex2("d2",3,1);
        /* Check the nodes. */
        assertEquals(14,graph.vertexCount()); /* Check the number of the nodes in the graph. */
        
        assertTrue(graph.getVertices().contains(a1)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a1").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a2)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a2").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a3)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a3").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(a4)); /* Check if the graph contains a1. */
        assertEquals(0, graph.getVertex("a4").getVtxSet()); /* Check if the vtxSet of a1 is 0. */
        
        assertTrue(graph.getVertices().contains(b1)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b1").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b2)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b2").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b3)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b3").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b4)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b4").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(b5)); /* Check if the graph contains b1. */
        assertEquals(1, graph.getVertex("b5").getVtxSet()); /* Check if the vtxSet of a1 is 1. */
        
        assertTrue(graph.getVertices().contains(c1)); /* Check if the graph contains c1. */
        assertEquals(2, graph.getVertex("c1").getVtxSet()); /* Check if the vtxSet of c1 is 2. */
        
        assertTrue(graph.getVertices().contains(c2)); /* Check if the graph contains c2. */
        assertEquals(2, graph.getVertex("c2").getVtxSet()); /* Check if the vtxSet of c2 is 2. */
        
        assertTrue(graph.getVertices().contains(c3)); /* Check if the graph contains c3. */
        assertEquals(2, graph.getVertex("c3").getVtxSet()); /* Check if the vtxSet of c3 is 2. */
        
        assertTrue(graph.getVertices().contains(d1)); /* Check if the graph contains d1. */
        assertEquals(3, graph.getVertex("d1").getVtxSet());  /* Check if the vtxSet of d1 is 3. */
        
        assertTrue(graph.getVertices().contains(d2)); /* Check if the graph contains d2. */
        assertEquals(3, graph.getVertex("d2").getVtxSet());  /* Check if the vtxSet of d2 is 3. */
        
        /* Check the edge weights. */
        /* Init the intra matrices. */
        float[][] intraMatrix1 ={
            {Float.NaN,1.2f,1.3f,1.4f},
            {1.2f,Float.NaN,1.5f,1.6f},
            {1.3f,1.5f,Float.NaN,1.7f},
            {1.4f,1.6f,1.7f,Float.NaN}};
        float[][] intraMatrix2 = {
            {Float.NaN, 2.1f, 2.2f, 2.3f, 2.4f},
            {2.1f, Float.NaN, 2.5f, 2.6f, 2.7f},
            {2.2f, 2.5f, Float.NaN, 2.8f, 2.9f},
            {2.3f, 2.6f, 2.8f, Float.NaN, 3.0f},
            {2.4f, 2.7f, 2.9f, 3.0f, Float.NaN}};
        float[][] intraMatrix3 = {
            {Float.NaN, 3.1f, 3.2f},
            {3.1f, Float.NaN, 3.3f},
            {3.2f, 3.3f, Float.NaN}};
        float[][] intraMatrix4 = {
            {Float.NaN, 4.0f},
            {4.0f, Float.NaN}};
        /* Init the inter matrix. */
        float[][] interMatrix1={
            {1.1f, 1.2f, 1.3f, 1.4f, 1.5f},
            {2.1f, 2.2f, 2.3f, 2.4f, 2.5f},
            {3.1f, 3.2f, 3.3f, 3.4f, 3.5f},
            {4.1f, 4.2f, 4.3f, 4.4f, 4.5f}};
        float[][] interMatrix2={
            {1.6f, 1.7f, 1.8f},
            {2.6f, 2.7f, 2.8f},
            {3.6f, 3.7f, 3.8f},
            {4.6f, 4.7f, 4.8f},
            {5.6f, 5.7f, 5.8f}};
        float[][] interMatrix3={
            {6.1f, 6.2f},
            {7.1f, 7.2f},
            {8.1f, 8.2f}};
        ArrayList<Vertex2[]> nodes = new ArrayList<>();
        Vertex2[] row1 = {a1,a2,a3,a4};
        nodes.add(row1);
        Vertex2[] row2 = {b1,b2,b3,b4,b5};
        nodes.add(row2);
        Vertex2[] row3 = {c1,c2,c3};
        nodes.add(row3);
        Vertex2[] row4 = {d1,d2};
        nodes.add(row4);
        
        /* Check the intra edge weights. */
        for(int i=0;i<row1.length;i++)
            for(int j=0;j<row1.length;j++)
               assertEquals(intraMatrix1[i][j],graph.edgeWeight(row1[i], row1[j]),0.0001);
       for(int i=0;i<row2.length;i++)
            for(int j=0;j<row2.length;j++)
               assertEquals(intraMatrix2[i][j],graph.edgeWeight(row2[i], row2[j]),0.0001);
       for(int i=0;i<row3.length;i++)
           for(int j=0;j<row3.length;j++)
               assertEquals(intraMatrix3[i][j],graph.edgeWeight(row3[i], row3[j]),0.0001);
       for(int i=0;i<row4.length;i++)
           for(int j=0;j<row4.length;j++)
               assertEquals(intraMatrix4[i][j],graph.edgeWeight(row4[i], row4[j]),0.0001);
       
       /* Check the inter-matrix. */
       for(int i=0;i<row1.length;i++)
           for(int j=0;j<row2.length;j++)
               assertEquals(interMatrix1[i][j], graph.edgeWeight(row1[i], row2[j]),0.0001);
       
       for(int i=0;i<row2.length;i++)
           for(int j=0;j<row3.length;j++)
               assertEquals(interMatrix2[i][j], graph.edgeWeight(row2[i], row3[j]),0.0001);
       
       for(int i=0;i<row3.length;i++)
           for(int j=0;j<row4.length;j++)
               assertEquals(interMatrix3[i][j], graph.edgeWeight(row3[i], row4[j]),0.0001);
            
        System.out.println("(biforce.graph.MatrixHierGeneralGraphTest.testReadGraphInXMLExternFile) Test ends");
    }
    /**
     * Test of restoreThresh method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testRestoreThresh() {
        System.out.println("restoreThresh");
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        float edgeWeight = 0.0f;
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        float[][] dispVector = null;
        HierGraphWIE instance = null;
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
        HierGraphWIE instance = null;
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
        System.out.println("(MatrixHierGeneralGraphTest.testWriteGraphTo) Test starts.");
        /* Test writig the graph in xml format. */
        HierGraphWIE graph = new HierGraphWIE(testReadXml,false,true);
        graph.writeGraphTo(testWriteXml, true); /* Output the graph.*/
        HierGraphWIE test = new HierGraphWIE(testWriteXml,false, true);
        //assertTrue(graph.isSame(test));
        /* Test writing the graph in plain format. */
        graph.writeGraphTo(testWritePlain, false);
        System.out.println("(MatrixHierGeneralGraphTest.testWriteGraphTo) Test ends.");
    }

    /**
     * Test of writeClusterTo method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testWriteClusterTo() {
        System.out.println("(MatrixHierGeneralGraphTest.testWriteClusterTo) Test starts. ");
        HierGraphWIE graph = new
                HierGraphWIE(testReadXml,false,true);
        Param p =new Param("./parameters.ini");
        BiForceOnGraph4 algorithm = new BiForceOnGraph4();
        try{
            graph = (HierGraphWIE)algorithm.run(graph, p,1,false);
        }catch(IOException e){
            System.err.println("(MatrixHierGeneralGraphTest.testWriteClusterTo) Algorithm error.");
            return;
        }
        graph.writeClusterTo(testWriteCluster,true);
        System.out.println("(MatrixHierGeneralGraphTest.testWriteClusterTo) Test ends.");
    }

    /**
     * Test of writeResultInfoTo method, of class MatrixHierGeneralGraph.
     */
    @Test
    public void testWriteResultInfoTo() {
        System.out.println("writeResultInfoTo");
        String FilePath = "";
        HierGraphWIE instance = null;
        instance.writeResultInfoTo(FilePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
