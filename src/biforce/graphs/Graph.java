/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;
import java.util.List;

/**
 * This interface gives some standard definitions of a graph
 * @author Peng Sun
 */
public interface Graph {
    
    /**
     * This method returns all connected components of a given graph.
     * @return All connected components are returned as a collection of subgraphs.
     */
    public List<? extends Subgraph> getAllConnectedComponents();
    
    
    /**
     * This method returns the edge weight between two given vertices.
     * @param Vtx1
     * @param Vtx2
     * The two vertices between the edge weight is to be retrieved.
     * @return 
     * The edge weight returned.
     */
    public double getEdgeWeight(Vertex Vtx1, Vertex Vtx2);
    
    /**
     * This method returns a collection of vertices which are neighbouring to the given vertex
     * @param Vtx: The given vertex whose neighbors are to be returned.
     * @return Collection<Vertex>: The collection of vertices, i.e. neighbors to be returned
     */
    public List<Vertex> getNeighbors(Vertex Vtx);
    
    /**
     * This method returns the size of a given set, i.e. the number of vertices in a given set.
     * @param SetIdx: The index of the set, starting from 0.
     */
    public int getSetSize(int SetIdx);
    
    /**
     * This method returns the number of sets in the subgraph, i.e. the number of vertex sets in the subgraph.
     */
    public int getSetCount();
    
    /**
     * This method returns the number of vertices in the graph, i.e. the number of vertices in the whole graph.
     */
    public int getVertexCount();
    
    /**
     * This method returns all vertices of a given graph. 
     * @return The collection of vertices.
     */
    public List<Vertex> getVertices();
    
    /**
     * This method reads the graph instance from a given file path.
     * @param FilePath The path of the input graph.
     */
    public void readGraph(String FilePath);
    
    /**
     * This method returns the connected component with starting given vertex of vtx.
     * @param Vtx. The starting vertex to perform BFS.
     * @return The connected component.
     */
    public Subgraph runBFS(Vertex Vtx);
    
    /**
     * This method sets the vertices in the graph.
     * @param Vertices. The collection of vertices.
     */
    public void setVertices(List<Vertex> Vertices);
    
    /**
     * This method sets the edge weight between two given vertices.
     * @param Vtx1.
     * @param Vtx2.
     * @param EdgeWeight
     */
    public void setEdgeWeight(Vertex Vtx1, Vertex Vtx2, double EdgeWeight); 
    
    /**
     * This method writes the graph to a given file.
     * @param FilePath 
     */
    public void writeGraphTo(String FilePath);
}
