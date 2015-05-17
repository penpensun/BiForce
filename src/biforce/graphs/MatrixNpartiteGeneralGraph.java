/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is for the npartite graph with edges within each vertex sub set.
 * @author Peng Sun
 */
public class MatrixNpartiteGeneralGraph extends Graph2{
    protected float cost = 0; /* This stores the editing cost.*/
    protected int[] setSizes = null;
    /* Here since we have different sets of edges between different vertex sets,
     * we got to have more than one edge weights matrices.*/
    protected ArrayList<float[][]> intraEdgeWeights = null;
    protected ArrayList<float[][]> interEdgeWeights = null;
    /* This matrix stores the distances. */
    /* Distances, unlike edge weight, must be defined between two arbitrary vertices. It does not matter 
    whether the two vertices come from the same set or not, since later single-linkage and kmeans clustering
    use pairwise distances. */
    /* Note that the distance matrix is a LOWER TRIANGULAR MATRIX. */
    /* In MatrixHierNpartiteGraph, since we have distances between different vertices, we have 
    more than one distances.*/
    //ArrayList<float[][]> distances = null;
    /* 2nd version of distances, for test. */
    protected float[][] distMatrix = null;
    /**
     * Constructor.
     * @param filePath 
     * @param isHeader 
     * @param isXmlFile 
     */
    public MatrixNpartiteGeneralGraph(String filePath, boolean isHeader, boolean isXmlFile){
        /* Init the vertices arrayList. */
        vertices = new ArrayList<>();
        /* Init the distance arrayList. */
        //distances = new ArrayList<>();
        /* Init the action arrayList. */
        actions = new ArrayList<>();
        /* If the input is in xml format. */
        if(isXmlFile)
            try{
                readXmlGraph(filePath);
            }catch(IOException e){
                System.out.println("(biforce.graphs.MatrixHierGeneralGraph.constructor) "
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
                return;
            }
        /* If the input file is with header.*/
        else if(isHeader)
            try{
                readGraphWithHeader(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            }
        
        else
           try{
                readGraph(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            } 
        /* 2nd version of the distance matrix. Now the first version of MatrixHierGeneraGraph */
        distMatrix = new float[vertices.size()][vertices.size()];
        for(int i=0;i<distMatrix.length;i++)
            for(int j=0;j<distMatrix[0].length;j++)
                distMatrix[i][j] = Float.NaN;
                
    }
    
    /**
     * Constructor.
     * @param filePath
     * @param isHeader
     * @param isXmlFile
     * @param thresh 
     */
    public MatrixNpartiteGeneralGraph(String filePath, boolean isHeader, boolean isXmlFile,float thresh){
        /* Init the vertices arrayList. */
        vertices = new ArrayList<>();
        /* Init the distance arrayList. */
        //distances = new ArrayList<>();
        /* Init the action arrayList. */
        actions = new ArrayList<>();
       /* If the input is in xml format. */
        if(isXmlFile)
            try{
                readXmlGraph(filePath);
            }catch(IOException e){
                System.out.println("(biforce.graphs.MatrixHierGeneralGraph.constructor) "
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
                return;
            }
        /* If the input file is with header.*/
        else if(isHeader)
            try{
                readGraphWithHeader(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            }
        
        else
           try{
                readGraph(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            } 
        setThreshold(thresh);
        detractThresh();
        
        /* 2nd version of the distance matrix. Now the first version of MatrixHierGeneralGraph.*/
        distMatrix = new float[vertices.size()][vertices.size()];
        for(int i=0;i<distMatrix.length;i++)
            for(int j=0;j<distMatrix[0].length;j++)
                distMatrix[i][j] = Float.NaN;
    }
    
    
    /**
     * This constructor assigns the variables with given parameters.
     * @param vertices
     * @param actions
     * @param clusters
     * @param setSizes
     * @param intraEdgeWeights
     * @param interEdgeWeights 
     */
    public MatrixNpartiteGeneralGraph(ArrayList<Vertex2> vertices, ArrayList<Action2> actions, 
            ArrayList<Cluster2> clusters, 
            int[] setSizes,
            ArrayList<float[][]> intraEdgeWeights, 
            ArrayList<float[][]> interEdgeWeights){
        this.vertices = vertices;
        this.actions = actions;
        this.setSizes = setSizes;
        this.intraEdgeWeights=  intraEdgeWeights;
        this.interEdgeWeights = interEdgeWeights;
    }
    
    /**
     * This method performs breadth-first search in MatrixHierNpartiteGraph.
     * @param Vtx
     * @return 
     */
    @Override
    public MatrixNpartiteGeneralSubgraph bfs(Vertex2 Vtx) {
        LinkedList<Vertex2> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex2> result = new ArrayList<>();

        for(Vertex2 vtx: getVertices()){
            marker.put(vtx.toString(), Boolean.FALSE);
        }
        //enqueue source and mark source
         queue.add(Vtx);
         result.add(Vtx);
         marker.put(Vtx.toString(),true);

         //while queue is not empty
         while(!queue.isEmpty()){
             //dequeue an item from queue into CurrentVtx
             Vertex2 CurrentVtx = queue.pollLast();
             //get the nei of CurrentVtx
             ArrayList<Vertex2> nei = neighbours(CurrentVtx);

             /* If no neighbour is found, then we continue. */
             if(nei == null)
                 continue;
             //for each neighbour
             for(Vertex2 vtx: nei){
                 if(!marker.get(vtx.toString())){
                     marker.put(vtx.toString(),true);
                     queue.add(vtx);
                     result.add(vtx);
                 }
             }

         }
         /* Create a new subkpartitegraph. */
         MatrixNpartiteGeneralSubgraph sub = new MatrixNpartiteGeneralSubgraph(result,this);
         return sub;
    }
    
    /**
     * This method returns all connected components.
     * @return 
     */
    @Override
    public ArrayList<MatrixNpartiteGeneralSubgraph> connectedComponents() {
        ArrayList<MatrixNpartiteGeneralSubgraph> connectecComps = new ArrayList<>();
        //create a indicator LinkedList of vertices, when a vertex is included in one of the subgraphs, then it is 
        //removed from the indicator LinkedList
        LinkedList<Vertex2> indicatorList = new LinkedList<>();
        //add all the vertex into the IndicatorArray
        for(Vertex2 vtx: getVertices()){
            indicatorList.add(vtx);
        }
        //While there is still unvisited vertex, we use it as the seed to search for subgraphs.
        while(!indicatorList.isEmpty()){
            Vertex2 Seed = indicatorList.pollFirst();
            MatrixNpartiteGeneralSubgraph comp = bfs(Seed);
            connectecComps.add(comp);
            //remove all the vertex in the comp from indicatorList
            for(Vertex2 vtx: comp.getSubvertices()){
                indicatorList.remove(vtx);
            }
        }
        connectecComps.trimToSize();
        return connectecComps;
    }
    
    /**
     * This method detracts all edge weights in matrices.
     */
    @Override
    public final void detractThresh() {
        /* Detract the threshold from inter-edgeweights. */
        for(int i=0;i<interEdgeWeights.size();i++){
            float[][] weights = interEdgeWeights.get(i);
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] -=thresh;
        }
        /* Detract the threshold from the intra-edgeweights.*/
        for(int i=0;i<intraEdgeWeights.size();i++){
            float[][] weights = intraEdgeWeights.get(i);
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] -= thresh;
        }
        this.threshDetracted = true;
    }

    @Override
    public final void detractThresh(float thresh) {
        /* Check if the threshold has already detracted */
        if(threshDetracted)
            throw new IllegalArgumentException("(MatrixHierNpartiteGraph.detractThresh)"
                    + "  The threshold is already detracted.");
        else
            setThreshold(thresh);
        detractThresh();
    }
    
    @Override
    public final void detractThresh(float[] thresh){
        if(threshDetracted)
            throw new IllegalArgumentException("(MatrixHierNpartiteGraph.detractThresh) The threshold is already detracted.");
        else{
            // Check the length.
            if(thresh.length != interEdgeWeights.size()+intraEdgeWeights.size())
                throw new IllegalArgumentException("(MatrixHierNpartiteGraph.detractThresh) The length of the threshold array does not fit the graph:  "+thresh.length+"  "+interEdgeWeights.size()+intraEdgeWeights.size());
            
            for(int i=0;i<thresh.length;i++){
                if(i%2 == 0){
                    // The threshold for an intra edge weight matrix.
                    float[][] weights = intraEdgeWeights.get(i/2);
                    for(int j=0;j<weights.length;j++)
                        for(int k=0;k<weights[0].length;k++)
                            weights[j][k] -=thresh[i];
                }
                else{
                    //The threshold for an inter edge weight matrix.
                    float[][] weights = interEdgeWeights.get(i/2);
                    for(int j=0;j<weights.length;j++)
                        for(int k=0;k<weights[0].length;k++)
                            weights[j][k] -=thresh[i];
                }
            }
        }
        this.threshDetracted = true;
    }
    
    /**
     * This method gets the distance between two vertices. 
     * @param vtx1
     * @param vtx2
     * @return 
     */
    @Override
    public float dist(Vertex2 vtx1, Vertex2 vtx2) {
        /* First check if the distances between the two vertices are defined. */
        /*
        if( vtx1.getVtxSet() - vtx2.getVtxSet() != 1 &&
                vtx1.getVtxSet() - vtx2.getVtxSet() != -1)
            return Double.NaN;
        int minVtxSet = Math.min(vtx1.getVtxSet(),vtx2.getVtxSet());
        if(minVtxSet == vtx1.getVtxSet())
            return distances.get(minVtxSet)[vtx1.getVtxIdx()][vtx2.getVtxSet()];
        else
            return distances.get(minVtxSet)[vtx2.getVtxIdx()][vtx1.getVtxSet()];
        */
        /* 2nd version of distances. */
        return distMatrix[vtx1.getDistIdx()][vtx2.getDistIdx()];
        
    }

    @Override
    public float edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        /* Check if it is an intra-edge. */
        if(vtx1.getVtxSet() == vtx2.getVtxSet())
            /* First get the edge weight matrix and then get the right edge weight. */
            return intraEdgeWeights.get(vtx1.getVtxSet())
                    [vtx1.getVtxIdx()][vtx2.getVtxIdx()];
        /* Check if it  is an inter-edge.  */
        if(vtx1.getVtxSet() - vtx2.getVtxSet() == 1 ||
                vtx1.getVtxSet() - vtx2.getVtxSet() == -1 ||
                vtx1.getVtxSet() - vtx2.getVtxSet() == setSizes.length+1 ||
                vtx1.getVtxSet() - vtx2.getVtxSet() == -(setSizes.length+1)){
            int minVtxLvl = Math.min(vtx1.getVtxSet(),vtx2.getVtxSet());
            if(minVtxLvl == vtx1.getVtxSet())
                return interEdgeWeights.get(minVtxLvl)[vtx1.getVtxIdx()][vtx2.getVtxIdx()];
            else
                return interEdgeWeights.get(minVtxLvl)[vtx2.getVtxIdx()][vtx1.getVtxIdx()];
        }
        /* If it is not an intra-edge nor is it an inter-edge, then we return NaN for cross edges. */
        return Float.NaN;
    }

    @Override
    public float edgeWeight(int vtxIdx1, int vtxIdx2) {
        throw new UnsupportedOperationException("This edgeWeight(int, int) is not supported in MatrixHierNpartiteGraph.");
    }
    
    /**
     * Return the edge weight matrix
     * 
     * @question return final ?
     * @param i
     * @return 
     */
    public float[][] interEdgeWeightMatrix(int i){
        /* First check the index. */
        if( i<0 || i>= interEdgeWeights.size()){
            throw new IllegalArgumentException("(MatrixHierGeneralGraph.interEdgeWeighMatrix) Index out of bound:  "+
                    i);
        }
        return interEdgeWeights.get(i);   
    }
    
    
    public float[][] intraEdgeWeightMatrix(int i){
        /* First check the index. */
        if( i<0 || i>= intraEdgeWeights.size()){
            throw new IllegalArgumentException("(MatrixHierGeneralGraph.intraEdgeWeighMatrix) Index out of bound:  "+
                    i);
        }
        return intraEdgeWeights.get(i);   
    }
    
}
