/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import biforce.constants.BiForceConstants;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.IOException;
/**
 *
 * @author penpen926
 */
public class MatrixBipartiteGraph2Test {
    String testHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_header";
    String testNoHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_noheader";
    String testInvalidHeaderInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/readgraph_invalidheader";
    String testNeighboursInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/neighbour_input.txt";
    String testEdgeDetractInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/edge_detract.txt";
    String testEdgeWeightInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/edgeweight_input";
    String testDistInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/distance_input";
    String testBfsInput = "../../data/testdata/unit_test/MatrixBipartiteGraph/bfs_input";
    String testConnectedComponents="../../data/testdata/unit_test/MatrixBipartiteGraph/connected_component.txt";
    public MatrixBipartiteGraph2Test() {
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

    
    @Test
    /**
     * Test of constructor, of class MatrixBipartiteGraph2.
     */
    public void testConstructor(){
        System.out.println("### testConstructor starts.");
        MatrixBipartiteGraph2 graphHeader = new MatrixBipartiteGraph2(testHeaderInput,true);
        MatrixBipartiteGraph2 graphNoheader = new MatrixBipartiteGraph2(testNoHeaderInput,false);
        MatrixBipartiteGraph graphStableVer = null;
        try{
            graphStableVer = new MatrixBipartiteGraph(testNoHeaderInput);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        /* Get all vertices. */
        
        /* verticesHeader are the vertices obtained by the new constructor in 
         * MatrixBipartiteGraph2, reading an input file with header, by readGraphWithHeader()
           mehthod. */
        ArrayList<Vertex2> verticesHeader = graphHeader.getVertices();
        /* verticesNoheader are the vertices obtained by the new constructor in
           MatrixBipartiteGraph2, reading an input file without header, by readGraph() method.*/
        ArrayList<Vertex2> verticesNoheader = graphNoheader.getVertices();
        /* verticesStableVer are the vertices obtained by the last stable version, namely,
         * MatrixBipartiteGraph.*/
        ArrayList<Vertex> verticesStableVer = graphStableVer.getVertices();
        
        /* Get the values and indexes in verticesHeader. */
        ArrayList<String> valuesHeader = new ArrayList<>();
        ArrayList<Integer> indexesHeader = new ArrayList<>();
        for(Vertex2 vtx2: verticesHeader){
            valuesHeader.add(vtx2.getValue());
            indexesHeader.add(vtx2.getVtxIdx());
        }
        
        /* Get the values and indexes in verticesNoheader. */
        ArrayList<String> valuesNoheader = new ArrayList<>();
        ArrayList<Integer> indexesNoheader = new ArrayList<>();
        for(Vertex2 vtx2: verticesNoheader){
            valuesNoheader.add(vtx2.getValue());
            indexesNoheader.add(vtx2.getVtxIdx());
        }
        
        /* Get the values and indexes in verticesStableVer. */
        
        ArrayList<String> valuesStableVer = new ArrayList<>();
        ArrayList<Integer> indexesStableVer = new ArrayList<>();
        for(Vertex vtx: verticesStableVer){
            valuesStableVer.add(vtx.Value);
            indexesStableVer.add(vtx.VtxIdx);
        }
        
        /* Check if two arraylists are same. */
        assertEquals(valuesHeader.size(), valuesStableVer.size());
        assertEquals(valuesNoheader.size(), valuesStableVer.size());
        /* For each element in valuesToTest, check if it is also in valuesControl. */
        for(int i=0;i<valuesHeader.size();i++){
            int indexHeaderFound = valuesStableVer.indexOf(valuesHeader.get(i));
            assertEquals(indexHeaderFound== -1,false);
            System.out.println(indexesHeader.get(i)+"  "+indexesStableVer.get(i));
            assertEquals(indexesHeader.get(i),indexesStableVer.get(i));
        }
        
        /* Check the sizes of two vertex sets. */
        assertEquals(graphHeader.set0Size,graphStableVer.set0Size);
        assertEquals(graphHeader.set1Size,graphStableVer.set1Size);
        
        /* Check the edge weights. */
        for(int i=0;i<graphHeader.set0Size;i++)
            for(int j=0;j<graphHeader.set1Size;j++)
                assertEquals(graphHeader.edgeWeight(i, j),
                        graphStableVer.getEdgeWeightMatrix()[i][j],0.0001);
        
        
        for(int i=0;i<valuesNoheader.size();i++){
            int indexNoheaderFound = valuesStableVer.indexOf(valuesNoheader.get(i));
            assertEquals(indexNoheaderFound == -1, false);
            System.out.println(indexesNoheader.get(i)+"  "+indexesStableVer.get(i));
            assertEquals(indexesNoheader.get(i),indexesStableVer.get(i));
        }
        
        /* Check the sizes of two vertex sets. */
        assertEquals(graphNoheader.set0Size,graphStableVer.set0Size);
        assertEquals(graphNoheader.set1Size,graphStableVer.set1Size);
        /* Check the edge weights. */
        for(int i=0;i<graphNoheader.set0Size;i++)
            for(int j=0;j<graphNoheader.set1Size;j++)
                assertEquals(graphNoheader.edgeWeight(i, j),
                        graphStableVer.getEdgeWeightMatrix()[i][j],0.0001);
        System.out.println("### testConstructor ends.");
        System.out.println();
    }
    
    @Test
    /*
     * This test method tests if the constructor can correctly recognize the errors.
     */
    public void testErrorConstructor(){
        System.out.println("### testErrorConstructor starts.");
        MatrixBipartiteGraph2 errorGraph = new MatrixBipartiteGraph2(testInvalidHeaderInput,true);
        System.out.println("### testErrorConstructor ends.");
        System.out.println();
    }
    /**
     * Test of bfs method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testBfs() {
        System.out.println("### testBfs starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
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
        MatrixBipartiteSubgraph2 bfsSubgraph1 = graph.bfs(a1);
        MatrixBipartiteSubgraph2 bfsSubgraph2 = graph.bfs(b3);
        MatrixBipartiteSubgraph2 bfsSubgraph3 = graph.bfs(b6);
        MatrixBipartiteSubgraph2 bfsSubgraph4 = graph.bfs(a4);
        MatrixBipartiteSubgraph2 bfsSubgraph5 = graph.bfs(a7);
        
        /* Compare the two subgraphs. */
        assertEquals(true,bfsSubgraph1.equals(correctSubgraph1));
        assertEquals(true,bfsSubgraph2.equals(correctSubgraph1));
        assertEquals(true,bfsSubgraph3.equals(correctSubgraph2));
        assertEquals(true,bfsSubgraph4.equals(correctSubgraph2));
        assertEquals(true,bfsSubgraph5.equals(correctSubgraph3));
        
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("### testBfs ends.");
        System.out.println();
        
    }

    /**
     * Test of connectedComponents method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testConnectedComponents() {
        System.out.println("### testConnectedComponents starts.");
        MatrixBipartiteGraph2 input = new MatrixBipartiteGraph2(testConnectedComponents,true,0);
        /* Create the correct connected components. */
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
                new ArrayList<>(input.connectedComponents());
        
        /* Compare the results. */
        assertEquals(correctConnComp.size(), connectedComponents.size());
        for(MatrixBipartiteSubgraph2 sub: connectedComponents){
            assertEquals(correctConnComp.contains(sub),true);
        }
        
        System.out.println("### testConnectedComponents ends.");
    }
    
    /**
     * This method tests detractThresh().
     */
    @Test
    public void testDetractThresh(){
        System.out.println("## testDetractThresh starts.  ");
        
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testEdgeDetractInput,true);
        
        MatrixBipartiteGraph2 graphDetracted = new MatrixBipartiteGraph2(testEdgeDetractInput,true,10);
        
        /* Get the edges. */
        double[][] oriEdgeWeights =new double[graph.set0Size][graph.set1Size];
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                oriEdgeWeights[i][j] = graph.edgeWeight(i,j);
        /* This two flags serve as indicators of exception throwing. */
        boolean nonSetFlag = false, doubleDetractFlag = false;
        /* This tests the exception throwing out because of non-set threshold. */
        try{
            graph.detractThresh();
        }catch(IllegalArgumentException e){
            nonSetFlag = true;
        }
        assertEquals(nonSetFlag, true);
        graph.setThreshold(10);
        /* This tests manual threshold-detracting.*/
        graph.detractThresh();
        /* Check if the edge weights are detracted. */
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                assertEquals(oriEdgeWeights[i][j]-10,graph.edgeWeight(i, j),0.001);
        /* This tests the exception throwing because of double thresh-detraction. */
        try{
            graph.detractThresh();
        }catch(IllegalArgumentException e){
            doubleDetractFlag = true;
        }
        assertEquals(doubleDetractFlag,true);
        /* This tests constructor threshold-detracting. */
        /* Check if the edge weights are detracted. */
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                assertEquals(oriEdgeWeights[i][j]-10,graphDetracted.edgeWeight(i, j),0.001);
        System.out.println("## testDetractThresh ends.  ");
    }

    /**
     * Test of dist method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testDist() {
        System.out.println("### testDist starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testDistInput,true,0);
        /* Create the coords for all vertices. */
        double[] a1Coords = {0,0};
        double[] a2Coords = {0,3};
        double[] b1Coords = {1,1};
        double[] b2Coords = {5,1};
        /* Set the coords. */
        Vertex2 a1 = graph.getVertex("a1");
        a1.setCoords(a1Coords);
        Vertex2 a2 = graph.getVertex("a2");
        a2.setCoords(a2Coords);
        Vertex2 b1 = graph.getVertex("b1");
        b1.setCoords(b1Coords);
        Vertex2 b2 = graph.getVertex("b2");
        b2.setCoords(b2Coords);
        
        /* Update the distances. */
        graph.updateDist();
        
        /* Check the distances. */
        assertEquals(graph.dist(a1, b1), Math.sqrt(2),0.001);
        System.out.println(graph.dist(a1, b2));
        assertEquals(graph.dist(a1, b2), Math.sqrt(26), 0.001);
        assertEquals(graph.dist(b1, a2), Math.sqrt(5), 0.001);
        assertEquals(graph.dist(a2, b2), Math.sqrt(29), 0.001);
        assertEquals(graph.dist(a1,a2), 3, 0.001);
        assertEquals(graph.dist(b1,b2), 4, 0.001);
        
        System.out.println("### testDist ends.");
    }

    /**
     * Test of edgeWeight method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testEdgeWeight() {
        System.out.println("### testEdgeWeight starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testEdgeWeightInput,true,0);
        /* Get vertices for test cases. */
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b1 = graph.getVertex("b1");
        Vertex2 a2 = graph.getVertex("a2");
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 b6 = graph.getVertex("b6");
        Vertex2 b3 = graph.getVertex("b3");
        /* Test cases. */
        assertEquals(1,graph.edgeWeight(a1, b1),0.0001);
        assertEquals(2,graph.edgeWeight(a1, b2),0.0001);
        assertEquals(Double.NaN,graph.edgeWeight(a2, b6),0.0001);
        assertEquals(4,graph.edgeWeight(b3, a2),0.0001);
        assertEquals(true,Double.isNaN(graph.edgeWeight(a1, a2)));
        System.out.println("### testEdgeWeight ends.");
    }

    /**
     * Test of isActionTaken method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testIsActionTaken() {
        System.out.println("### testIsActionTaken starts.");
        int ActIndx = 0;
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b1 = graph.getVertex("b1");
        graph.pushAction(a1, b1);
        assertEquals(graph.isActionTaken(0),false);
        graph.takeAction(0);
        assertEquals(graph.isActionTaken(0),true);
        System.out.println("### testIsActionTaken ends.");
    }

    /**
     * Test of getCost method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testGetCost() {
        System.out.println("### testGetCost starts.");
        MatrixBipartiteGraph2 instance = new MatrixBipartiteGraph2(testBfsInput,true);
        instance.setThreshold(0);
        
        assertEquals(instance.getCost(),0,0.0001);
        Vertex2 a1 = instance.getVertex("a1");
        Vertex2 b1 = instance.getVertex("b1");
        
        /* Before detracting threshold. */
        assertEquals(instance.threshDetracted,false);
        instance.pushAction(a1, b1);
        assertEquals(instance.getCost(),1,0.0001);
        /* After pushing action, i.e. automatic detraction has been taken place. */
        assertEquals(instance.threshDetracted,true);
        
        Vertex2 b2 = instance.getVertex("b2");
        instance.pushAction(a1,b2);
        assertEquals(instance.getCost(),3,0.0001);
        
        /* Set edge weight to test pushing edge insertion. */
        
        Vertex2 b4 = instance.getVertex("b4");
        instance.setEdgeWeight(a1, b4, -10);
        instance.pushAction(a1,b4);
        assertEquals(instance.getCost(),13,0.0001);
        
        
        System.out.println("### testGetCost ends. ");
    }

    @Test
    public void testNeighbours(){
        System.out.println("### testnNeighbours starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testNeighboursInput,true);
        graph.detractThresh(0);
        /* Create the seeds of vertices. */
        Vertex2 seed1 = graph.getVertex("a1");
        Vertex2 seed2 = graph.getVertex("a2");
        Vertex2 seed3 = graph.getVertex("a3");
        Vertex2 seed4 = graph.getVertex("b1");
        Vertex2 seed5 = graph.getVertex("a4");
        
        /* To obtain the neighbours. */
        ArrayList<Vertex2> neighbours1 = graph.neighbours(seed1);
        ArrayList<Vertex2> neighbours2 = graph.neighbours(seed2);
        ArrayList<Vertex2> neighbours3 = graph.neighbours(seed3);
        ArrayList<Vertex2> neighbours4 = graph.neighbours(seed4);
        ArrayList<Vertex2> neighbours5 = graph.neighbours(seed5);
        
        /* The correct answers. */
        ArrayList<Vertex2> correctNeighbours1 = new ArrayList<>();
        correctNeighbours1.add(new Vertex2("b1",1,-1));
        correctNeighbours1.add(new Vertex2("b2",1,-1));
        correctNeighbours1.add(new Vertex2("b3",1,-1));
        
        ArrayList<Vertex2> correctNeighbours2 = new ArrayList<>();
        correctNeighbours2.add(new Vertex2("b1",1,-1));
        correctNeighbours2.add(new Vertex2("b3",1,-1));
        correctNeighbours2.add(new Vertex2("b4",1,-1));
        
        ArrayList<Vertex2> correctNeighbours3 = new ArrayList<>();
        correctNeighbours3.add(new Vertex2("b6",1,-1));
        
        ArrayList<Vertex2> correctNeighbours4 = new ArrayList<>();
        correctNeighbours4.add(new Vertex2("a1",0,-1));
        correctNeighbours4.add(new Vertex2("a2",0,-1));
        
        ArrayList<Vertex2> correctNeighbours5 = null;
        
        /* Check if two arraylists are equal.*/
        assertEquals(assertEqualArrayList(neighbours1,correctNeighbours1),true);
        assertEquals(assertEqualArrayList(neighbours2,correctNeighbours2),true);
        assertEquals(assertEqualArrayList(neighbours3,correctNeighbours3),true);
        assertEquals(assertEqualArrayList(neighbours4,correctNeighbours4),true);
        assertEquals(assertEqualArrayList(neighbours5,correctNeighbours5),true);
        System.out.println("### testNeighbours ends.");
        System.out.println();
    }
    /**
     * Test of pushAction method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testPushAction() {
        System.out.println("### testPushAction starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b1 = graph.getVertex("b1");
        graph.pushAction(a1, b1);
        assertEquals(graph.actions.size(),1);
        assertEquals(graph.actions.get(0).getVtx1(),a1);
        assertEquals(graph.actions.get(0).getVtx2(),b1);
        assertEquals(graph.actions.get(0).getOriginalWeight(),1,0.001);
        System.out.println("### testPushAction ends.");
        
    }

    
    
    /**
     * Test of the restoreDetract()
     */
    @Test
    public void testRestoreDetract(){
        System.out.println("## testRestoreThresh starts.  ");
        
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testEdgeDetractInput,true);
        
        MatrixBipartiteGraph2 graphDetracted = new MatrixBipartiteGraph2(testEdgeDetractInput,true,10);
        
        /* Get the edges. */
        double[][] oriEdgeWeights =new double[graph.set0Size][graph.set1Size];
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                oriEdgeWeights[i][j] = graph.edgeWeight(i,j);
        /* This two flags serve as indicators of exception throwing. */
        boolean nonSetFlag = false, doubleDetractFlag = false;
        boolean doubleRestoreFlag = false;
        /* This tests the exception throwing out because of non-set threshold. */
        try{
            graph.restoreThresh();
        }catch(IllegalArgumentException e){
            nonSetFlag = true;
        }
        assertEquals(nonSetFlag, true);
        graph.setThreshold(10);
        /* This tests manual threshold-detracting.*/
        graph.detractThresh();
        /* Check if the edge weights are detracted. */
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                assertEquals(oriEdgeWeights[i][j]-10,graph.edgeWeight(i, j),0.001);
        /* This tests the exception throwing because of double thresh-detraction. */
        try{
            graph.detractThresh();
        }catch(IllegalArgumentException e){
            doubleDetractFlag = true;
        }
        assertEquals(doubleDetractFlag,true);
        /* This tests constructor threshold-detracting. */
        /* Check if the edge weights are detracted. */
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                assertEquals(oriEdgeWeights[i][j]-10,graphDetracted.edgeWeight(i, j),0.001);
        
        /* This tests the restoration of detracted edge weights. */
        graphDetracted.restoreThresh();
        /* Check if the edge weights are restored. */
        for(int i=0;i<graph.set0Size;i++)
            for(int j=0;j<graph.set1Size;j++)
                assertEquals(oriEdgeWeights[i][j],graphDetracted.edgeWeight(i, j),0.001);
        
        try{
            graph.restoreThresh();
        }catch(IllegalArgumentException e){
            doubleRestoreFlag = true;
        }
        assertEquals(doubleRestoreFlag, false);
        System.out.println("## testRestoreThresh ends.  ");
    }

   

    /**
     * Test of setEdgeWeight method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testSetEdgeWeight() {
        System.out.println("### testSetEdgeWeight starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testEdgeWeightInput,true);
        Vertex2 b2 = graph.getVertex("b2");
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 a3 = graph.getVertex("a3");
        Vertex2 b4 = graph.getVertex("b4");
        Vertex2 b6 = graph.getVertex("b6");
        Vertex2 illegalVertex1 = new Vertex2("ilegalVetex1",0,20);
        Vertex2 illegalVertex2 = new Vertex2("ilegalVertex2",0,-1);
        boolean illegalFlag1 = false;
        boolean illegalFlag2 = false;
        
        /* Test case 1: Normal setEdgeWeight*/
        graph.setEdgeWeight(b2, a1, 20);
        assertEquals(graph.edgeWeight(b2,a1),20,0.0001);
        graph.setEdgeWeight(a3,b4,50);
        assertEquals(graph.edgeWeight(a3,b4),50,0.0001);
        graph.setEdgeWeight(a1,b6,4);
        assertEquals(graph.edgeWeight(a1,b6),4,0.001);
        /* Restore the intial state. */
        graph.setEdgeWeight(a1,b2,2);
        graph.setEdgeWeight(a3,b4,3);
        graph.setEdgeWeight(a1,b6,0);
        
        /* Test case 2: After thresh-detraction.*/
        graph.detractThresh(10);
        graph.setEdgeWeight(a1, b2, 20);
        assertEquals(graph.edgeWeight(a1,b2),10,0.0001);
        
        
        /* Test case 3: Illegal case.*/
        boolean negativeIdxFlag = false, outOfBoundIdxFlag = false;
        try{
            graph.setEdgeWeight(illegalVertex1, a1,60);
        }catch(IllegalArgumentException e){
            outOfBoundIdxFlag = true;
        }
        assertEquals(outOfBoundIdxFlag, true);
        
        try{
            graph.setEdgeWeight(illegalVertex2,a3,40);
        }catch(IllegalArgumentException e){
            negativeIdxFlag = true;
        }
        assertEquals(negativeIdxFlag,true);
        System.out.println("### testSetEdgeWeight ends.");
    }

    /**
     * Test of takeAction method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testTakeAction() {
        System.out.println("### testTakeAction starts.");
        System.out.println("### testPushAction starts.");
        MatrixBipartiteGraph2 graph = new MatrixBipartiteGraph2(testBfsInput,true,0);
        Vertex2 a1 = graph.getVertex("a1");
        Vertex2 b1 = graph.getVertex("b1");
        graph.pushAction(a1, b1);
        graph.takeAction(0);
        System.out.println("### testTakeAction ends.");
    }

    /**
     * Test of takeActions method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testTakeActions() {
        System.out.println("### testTakeActions starts.");
        MatrixBipartiteGraph2 instance = new MatrixBipartiteGraph2(testBfsInput,true,0.5);
        /* We push 3 actions into the graph, 2 insertions and 1 deletion. */
        Vertex2 a1 = instance.getVertex("a1");
        Vertex2 b1 = instance.getVertex("b1");
        instance.pushAction(a1,b1);
        
        Vertex2 b2 = instance.getVertex("b2");
        instance.pushAction(a1,b2);
        
        Vertex2 b6 = instance.getVertex("b6");
        instance.pushAction(a1,b6);
        
        /* Take all actions.*/
        instance.takeActions();
        
        /* Check the results. */
        /* First check the number of actions. */
        assertEquals(instance.actions.size(),3);
        assertEquals(instance.edgeWeight(a1,b1),BiForceConstants.FORBIDDEN,0.01);
        assertEquals(instance.edgeWeight(a1,b2),BiForceConstants.FORBIDDEN,0.01);
        assertEquals(instance.edgeWeight(a1, b6),BiForceConstants.PERMENANT,0.01);
        System.out.println("### testTakeActions ends. ");
    }

    /**
     * Test of vertexSetCount method, of class MatrixBipartiteGraph2.
     */
    @Test
    public void testVertexSetCount() {
        System.out.println("### testVertexSetCount starts.");
        MatrixBipartiteGraph2 instance = new MatrixBipartiteGraph2(testBfsInput,true,0);
        assertEquals(instance.vertexCount(),13);
        assertEquals(instance.vertexSetCount(),2);
        
        System.out.println("### testVertexSetCount ends.");
    }

    
    
    /**
     * Check if two arrayList are equal.
     * @param a
     * @param b
     * @return 
     */
    public static boolean assertEqualArrayList(ArrayList a, ArrayList b){
        if(a== null && b == null)
            return true;
        else if(a == null)
            return false;
        else if(b == null)
            return false;
        
        
        if(a.size() != b.size())
            return false;
        for(Object obj: a){
            if(!b.contains(obj))
                return false;
        }
        return true;
    }
}
