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
public class MatrixBipartiteSubgraph2Test {
    String testHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_header";
    String testNoHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_noheader";
    String testInvalidHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_invalidheader";
    String testNeighboursInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/neighbour_input.txt";
    String testEdgeDetractInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/edge_detract.txt";
    String testEdgeWeightInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/edgeweight_input";
    String testDistInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/distance_input";
    String testBfsInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/bfs_input";
    String testConnCompInput="../../data/testdata/unit_test/MatrixBipartiteGraph/connected_component.txt";
    
    public MatrixBipartiteSubgraph2Test() {
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
     * Test of bfs method, of class MatrixBipartiteSubgraph2.
     */
    @Test
    public void testBfs() {
        System.out.println("### MatrixBipartiteSubgraph2 testBfs starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        /* Create the subgraph. */
        MatrixBipartiteSubgraph2 subgraph = new MatrixBipartiteSubgraph2(graph.getVertices(),graph);
        
        /* Get the vertices. */
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b3 = graph.getVertex("b3");
        Vertex2 b6 = graph.getVertex("b6");
        Vertex2 a4 = graph.getVertex("a4");
        Vertex2 a7 = graph.getVertex("a7");
        
        /* Create the subgraphs. */
        ArrayList<Vertex2> subVertices1 = new ArrayList<>();
        subVertices1.add(a1);
        subVertices1.add(graph.getVertex("b1"));
        subVertices1.add(graph.getVertex("a2"));
        subVertices1.add(graph.getVertex("a3"));
        subVertices1.add(graph.getVertex("b2"));
        subVertices1.add(graph.getVertex("b3"));
        subVertices1.add(graph.getVertex("b4"));
        MatrixBipartiteSubgraph2 correctSubgraph1 = new MatrixBipartiteSubgraph2(subVertices1,graph);
        
        ArrayList<Vertex2> subVertices2 = new ArrayList<>();
        subVertices2.add(graph.getVertex("a4"));
        subVertices2.add(graph.getVertex("a5"));
        subVertices2.add(graph.getVertex("a6"));
        subVertices2.add(graph.getVertex("b5"));
        subVertices2.add(graph.getVertex("b6"));
        MatrixBipartiteSubgraph2 correctSubgraph2 = new MatrixBipartiteSubgraph2(subVertices2,graph);
        
        
        ArrayList<Vertex2> subVertices3 = new ArrayList<>();
        subVertices3.add(graph.getVertex("a7"));
        MatrixBipartiteSubgraph2 correctSubgraph3 = new MatrixBipartiteSubgraph2(subVertices3,graph);
        
        /* Obtain the subgraphs by bfs(). */
        MatrixBipartiteSubgraph2 bfsSubgraph1 = subgraph.bfs(a1);
        MatrixBipartiteSubgraph2 bfsSubgraph2 = subgraph.bfs(b3);
        MatrixBipartiteSubgraph2 bfsSubgraph3 = subgraph.bfs(b6);
        MatrixBipartiteSubgraph2 bfsSubgraph4 = subgraph.bfs(a4);
        MatrixBipartiteSubgraph2 bfsSubgraph5 = subgraph.bfs(a7);
        
        
        /* Compare the two subgraphs. */
        assertEquals(bfsSubgraph1.equals(correctSubgraph1),true);
        assertEquals(bfsSubgraph2.equals(correctSubgraph1),true);
        assertEquals(bfsSubgraph3.equals(correctSubgraph2),true);
        assertEquals(bfsSubgraph4.equals(correctSubgraph2),true);
        assertEquals(bfsSubgraph5.equals(correctSubgraph3),true);
        System.out.println("### MatrixBipartiteSubgraph2 testBfs ends.");
    }

    /**
     * Test of compareTo method, of class MatrixBipartiteSubgraph2.
     */
    @Test
    public void testCompareTo() {
        System.out.println("### MatrixBipartiteSubgraph2 testCompareTo starts. ");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 a3 = graph.getVertex("a3");
        
        /* Create subgraphs to compare. */
        ArrayList<Vertex2> subgraph1Vertices = new ArrayList<>();
        subgraph1Vertices.add(a1);
        subgraph1Vertices.add(a2);
        subgraph1Vertices.add(a3);
        MatrixBipartiteSubgraph2 sub1 = new MatrixBipartiteSubgraph2(subgraph1Vertices, graph);
        
        ArrayList<Vertex2> subgraph2Vertices = new ArrayList<>();
        subgraph2Vertices.add(a1);
        subgraph2Vertices.add(a2);
        MatrixBipartiteSubgraph2 sub2 = new MatrixBipartiteSubgraph2(subgraph2Vertices, graph);
        
        assertEquals(sub1.compareTo(sub2)>0,true);
        
        System.out.println("### MatrixBipartiteSubgraph2 testCompareTo ends.");
    }

    /**
     * Test of connectedComponents method, of class MatrixBipartiteSubgraph2.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("### MatrixBipartiteSubgraph2 testConnectedComponents starts.");
        MatrixBipartiteGraph2 input = new MatrixBipartiteGraph2(testConnCompInput,true,0);
        MatrixBipartiteSubgraph2 sub = new MatrixBipartiteSubgraph2(input.vertices,input);
        
        ArrayList<Vertex2> subvertices1 = new ArrayList<>();
        subvertices1.add(input.getVertex("a1"));
        subvertices1.add(input.getVertex("a2"));
        subvertices1.add(input.getVertex("a3"));
        subvertices1.add(input.getVertex("b1"));
        subvertices1.add(input.getVertex("b2"));
        MatrixBipartiteSubgraph2 subgraph1 =new MatrixBipartiteSubgraph2(subvertices1,input);
        
        ArrayList<Vertex2> subvertices2 = new ArrayList<>();
        subvertices2.add(input.getVertex("b3"));
        MatrixBipartiteSubgraph2 subgraph2 = new MatrixBipartiteSubgraph2(subvertices2,input);
        
        ArrayList<Vertex2> subvertices3 = new ArrayList<>();
        subvertices3.add(input.getVertex("a4"));
        MatrixBipartiteSubgraph2 subgraph3 = new MatrixBipartiteSubgraph2(subvertices3,input);
        
        ArrayList<Vertex2> subvertices4 = new ArrayList<>();
        subvertices4.add(input.getVertex("a5"));
        subvertices4.add(input.getVertex("b4"));
        subvertices4.add(input.getVertex("b5"));
        MatrixBipartiteSubgraph2 subgraph4 = new MatrixBipartiteSubgraph2(subvertices4,input);
        
        ArrayList<MatrixBipartiteSubgraph2> correctConnComp = new ArrayList<>();
        correctConnComp.add(subgraph1);
        correctConnComp.add(subgraph2);
        correctConnComp.add(subgraph3);
        correctConnComp.add(subgraph4);
        
        /* Obtain the connected components by connectedComponents(). */
        ArrayList<MatrixBipartiteSubgraph2> connectedComponents = 
                new ArrayList<>(sub.connectedComponents());
        
        /* Compare the results. */
        assertEquals(correctConnComp.size(), connectedComponents.size());
        for(MatrixBipartiteSubgraph2 connComp: connectedComponents){
            assertEquals(correctConnComp.contains(connComp),true);
        }
        
        System.out.println("### MatrixBipartiteSubgraph2 testConnectedComponents ends.");
    }

    
    /**
     * Test of equals method, of class MatrixBipartiteSubgraph2.
     */
    @Test
    public void testEquals() {
        System.out.println("### MatrixBipartiteSubgraph2 testEquals starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        
        ArrayList<Vertex2> subVertices1 = new ArrayList<>();
        ArrayList<Vertex2> subVertices2 = new ArrayList<>();
        
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 a3 = graph.getVertex("a3");
        
        /* Test the unequal case. */
        subVertices1.add(a1);
        subVertices2.add(b1);
        
        MatrixBipartiteSubgraph2 unequalSub1 = new MatrixBipartiteSubgraph2(subVertices1, graph);
        MatrixBipartiteSubgraph2 unequalSub2 = new MatrixBipartiteSubgraph2(subVertices2, graph);
        
        assertEquals(unequalSub1.equals(unequalSub2),false);
        
        /* Test the equal case. */
        subVertices1.add(b1);
        subVertices2.add(a1);
        
        MatrixBipartiteSubgraph2 equalSub1 = new MatrixBipartiteSubgraph2(subVertices1, graph);
        MatrixBipartiteSubgraph2 equalSub2 = new MatrixBipartiteSubgraph2(subVertices2, graph);
        
        assertEquals(equalSub1.equals(equalSub2),true);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("### MatrixBipartiteSubgraph2 testEquals ends.");
    }

    
    /**
     * Test of neighbours method, of class MatrixBipartiteSubgraph2.
     */
    @Test
    public void testNeighbours() {
        System.out.println("### MatrixBipartiteSubgraph2 testNeighbours starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 a3 = graph.getVertex("a3");
        Vertex2 b3 = graph.getVertex("b3"); 
        Vertex2 b4 = graph.getVertex("b4");
        Vertex2 b5 = graph.getVertex("b5");
        Vertex2 a6 = graph.getVertex("a6");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 a5 = graph.getVertex("a5");
        
        /* Create subgrphs. */
        ArrayList<Vertex2> subVertices1 = new ArrayList<>();
        subVertices1.add(a1);
        subVertices1.add(a2);
        subVertices1.add(b2);
        subVertices1.add(a3);
        subVertices1.add(b3);
        subVertices1.add(b4);
        
        MatrixBipartiteSubgraph2 sub1 = new MatrixBipartiteSubgraph2(subVertices1, graph);
        
        ArrayList<Vertex2> subVertices2 = new ArrayList<>();
        subVertices2.add(b5);
        subVertices2.add(a6);
        subVertices2.add(a1);
        subVertices2.add(b1);
        subVertices2.add(a5);
        MatrixBipartiteSubgraph2 sub2 = new MatrixBipartiteSubgraph2(subVertices2,graph);
        
        /* Obtain the neighbours. */
        ArrayList<Vertex2> nb1 = sub1.neighbours(a1);
        ArrayList<Vertex2> nb2 = sub2.neighbours(b5);
        
        /* Create the correct answers. */
        ArrayList<Vertex2> correctNb1 = new ArrayList<>();
        correctNb1.add(b2);
        
        ArrayList<Vertex2> correctNb2 = new ArrayList<>();
        correctNb2.add(a5);
        correctNb2.add(a6);
        
        /* Check the correctness. */
        assertEquals(MatrixBipartiteGraph2Test.assertEqualArrayList(nb1, correctNb1),true);
        assertEquals(MatrixBipartiteGraph2Test.assertEqualArrayList(nb2, correctNb2),true);
        
        System.out.println("### MatrixBipartiteSubgraph2 testNeighbours ends. ");
    }

    
}
