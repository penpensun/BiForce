/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * This Graph2 abstract class is the new design for more modulized bi-force (bi-forceV4).
 * The main feature of this version graph is:
 * (1) Incorporating distance computing capacity into Graph classes, rather than leave them 
 * to bi-force algorithm classes.
 * 
 * (2) Incorporating the functions of class ClustEditing, which enables Graph classes to 
 * take, add and remove editing actions.
 * 
 * (3) Using a more modulized design for newcoming classes for general and n-partite graphs.
 * 
 * (4) A minor modification on io module, to output information of graph, clusters 
 * and clustering results.
 * @author Peng
 */
public abstract class Graph2 {
    /* The arraylist of editing actions. Init value: null. */
    protected ArrayList<Action2> actions = null;
    /* The arraylist of clusters after algorithm been executed. Init value: null. */
    protected ArrayList<Cluster2> clusters = null;
    /* The arraylist of all vertices in the graph. Init value: null. */
    protected ArrayList<Vertex2> vertices = null;
    
    /* This is a special flag, indicating whether all edge weights in the graph have already 
     * been "normalized"/"shifted" by detracting the current edge threshold.
     * 
     * Hopefully, this new design is abe to cut running time.
     */
    protected boolean threshDetracted = false;
    protected float thresh = Float.MAX_VALUE;
    /* This method returns a connected component (Subgraph2) given a vertex, based on
     * breadth-first search.*/
    public abstract Subgraph2 bfs(Vertex2 Vtx);
    
    /* This method returns the connected components in a graph.*/
    public abstract List<? extends Subgraph2> connectedComponents();
    
    /* This method detracts all edge weights with threshold. */
    public abstract void detractThresh();
    
    /* This method detracts all edge weights with the given threshold, provided
    no threshold-detraction has been performed.*/
    public abstract void detractThresh(float thresh);
    
    /* This method detracts all edge weights with the given threshold array. For multiple thresholds. */
    public abstract void detractThresh(float[] thresh);
       
    /* This method returns the distance between two vertices. */
    public abstract float dist(Vertex2 Vtx1, Vertex2 Vtx2);
    /* This method returns the edge weight given two vertices. */
    public abstract float edgeWeight(Vertex2 Vtx1, Vertex2 Vt2);
    /* This method returns the edge weight given two indexes. */
    public abstract float edgeWeight(int vtxIdx1, int vtxIdx2);
     /* This method returns all actions. */
    public final ArrayList<Action2> getActions(){
        return actions;
    }
    
    /* This method returns the methods. */
    public final ArrayList<Cluster2> getClusters(){
        return clusters;
    }
    
    /* This method returns the editing cost. */
    public abstract float getCost();
    
    /**
     * This method returns the threshold.
     * @return 
     */
    public final float getThreshold(){
        return thresh;
    }
    
    /* This method returns the vertices. */
    public final ArrayList<Vertex2> getVertices(){
        return vertices;
    }
    
    /**
     * This method returns the vertex object given its value. 
     * If not found, then null is returned.
     * @param value
     * @return 
     */
    public final Vertex2 getVertex(String value){
        for(Vertex2 vtx:vertices){
            if(vtx.getValue().equals(value))
                return vtx;
        }
        return null;
    }
    
    /**
     * This returns if the threshold returned.
     * @return 
     */
    public boolean isThreshDetracted(){
        return threshDetracted;
    }
    
    
    /* This method returns false, since it's not a subgraph. */
    public final boolean isSubgraph(){
        return false;
    }
    
    /* This method returns if the action with given index is taken. */
    public abstract boolean isActionTaken(int ActIndx);
    
    /* This method returns the neighbours of a given vertex. */
    public abstract ArrayList<Vertex2> neighbours(Vertex2 vtx);
    /**
     * This method adds an Action to the Actions in the graph object, given two vertices
     * and the boolean indicating the current being inserted actions is a edge-insertion 
     * or not.
     * @param vtx1
     * @param vtx2
     * @param IsEdgeInsertion
     * @return The index of inserted vertex.
     */
    public abstract boolean pushAction(Vertex2 vtx1, Vertex2 vtx2); 
    
    /* This method reads a graph from a given input file. */
    public abstract void readGraph(String FilePath) throws IOException;
    
    /* This method restores the edge weight with threshold. */
    public abstract void restoreThresh();
    /* This method removes an action from the action list. */
    public abstract boolean removeAction(int Index);
    
    /* This method sets the clusters in the current graph. */
    public final void setClusters(ArrayList<Cluster2> Clusters){
        this.clusters = Clusters;
    }
    
    /* This method sets the edge weight given two vertices. */
    public abstract void setEdgeWeight(Vertex2 Vtx1, Vertex2 Vtx2, float EdgeWeight);
    
    /* This method sets the threshold. */
    public void setThreshold(float thresh){
        this.thresh = thresh;
    }
    /* This method sets the vertices. */
    public final void setVertices(ArrayList<Vertex2> Vertices){
        this.vertices = Vertices;
    }
    /* This method takes an action given the index. */
    public abstract boolean takeAction(int Index);
    /* This method takes all actions. */
    public abstract boolean takeActions();
    
    /* This method is to update (re-compute) the pariwise distances, after displacement
     * is performed in the algorithm. */
    public abstract void updateDist();
    
    /* This method updates the positions using the displacement vector.*/
    public abstract void updatePos(float[][] dispVector);
    /* This method returns the number of vertex sets. */
    public abstract int vertexSetCount();
    
    /* This method returns the number of vertices. */
    public int vertexCount(){
        return vertices.size();
    }
    
    /**
     * 
     * @param graph
     * @return 
     */
    public abstract boolean isSame(Graph2 graph);
    
    /* This method writes the graph into a given file path. */
    public abstract void writeGraphTo(String FilePath, boolean isXmlFile);
    
    /* This method writes the cluster result into a given file path. */
    public abstract void writeClusterTo(String FilePath, boolean isXmlFile);
    
    /* This method writes the info of result into a given file path. */
    public abstract void writeResultInfoTo(String FilePath);
    
    /**
     * This method writes the information of vertices into the given file, used to output log file.
     * @param filePath 
     */
    public void writeVertexInfo(String filePath){
        try{
        FileWriter fw = new FileWriter(filePath);
        BufferedWriter bw  = new BufferedWriter(fw);
        String line =null;
        for(Vertex2 vtx: vertices){
            bw.write(vtx.getValue()+"\t"+vtx.getVtxSet()+"\t"+vtx.getVtxIdx()+"\t"+vtx.getDistIdx()+"\t"+
                    vtx.getClustNum());
            for(int i=0;i<vtx.getCoords().length;i++)
                bw.write("\t"+vtx.getCoords()[i]);
            bw.write("\n");
        }
        bw.flush();
        bw.close();
        fw.close();
        }catch(IOException e){
            System.err.println("(Graph2.writeVertexInfo) The file writer init error.");
        }
    }
    
    public abstract void writerInterEwMatrix(String outFile, int idx);
    public abstract void writeIntraEwMatrix(String outFile, int idx);
    public abstract void writeDistanceMatrix(String file);
}
