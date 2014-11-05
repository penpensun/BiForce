/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;
import java.util.Collection;
/**
 * This class describes subgraph.
 * @author penpen926
 */
public interface Subgraph{
   
    /**
     * This method returns the neighbors of a given vertex.
     * @param Seed. The vertex whose neighbors are to be returned.
     * @return The neighbors is returned as a collection of vertices.
     */
    public Collection<Vertex> getNeighbors(Vertex Seed);
    
    /**
     * This method returns the vertices of a subgraph.
     * @return The set of vertices in the subgraph is returned as a collection of vertices.
     */
    public Collection<Vertex> getSubVertices();
    
    /**
     * This method returns the weight of two given vertices.
     */
    public double getEdgeWeight(Vertex vtx1, Vertex vtx2);
    
    /**
     * This method returns the size of a given set, i.e. the number of vertices in a given set.
     * @param SetIdx: The index of the set, starting from 0.
     */
    public int getSetSize(int SetIdx);
    
     /**
     * This method returns the number of vertices in the graph, i.e. the number of vertices in the whole graph.
     * @return The number of vertices is returned.
     */
    public int getVertexCount();
    
    /**
     * This method returns the number of sets in the subgraph, i.e. the number of vertex sets in the subgraph.
     * @return The number of vertex sets is returned.
     */
    public int getSetCount();
  
    /**
     * This method returns all connected components of a given graph.
     * @return The collection of subgraphs, each of which is a connected component.
     */
    public Collection<? extends Subgraph> getAllConnectedComponents();
    
    /**
     * This method returns 
     * @return Super graph is returned.
     */
    public Graph getSuperGraph();
    
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
    public void setVertices(Collection<Vertex> Vertices);
    
    /**
     * This method sets the edge weight between two given vertices.
     * @param Vtx1.
     * @param Vtx2.
     * @param EdgeWeight
     */
    public void setEdgeWeight(Vertex Vtx1, Vertex Vtx2, double EdgeWeight); 
    
    
    
    
}
