/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class describes one preliminary implementation of bipartite Subgraph based on Matrix.
 * Note: Subgraph does not receive any editing actions, all editing actions go to the supergraph.
 * @author penpen926
 */
public class BipartiteSubgraph implements Subgraph, 
        Comparable<BipartiteSubgraph> {
    //This variable stores the vertices in the subgraph
    Collection<Vertex> SubVertices;
    //This references points to the super graph
    BipartiteGraph Supergraph;
    
    /**
     * Constructor
     * @param SubVertices. The vertices for subgraph.
     * @param SuperGraph. The super graph.
     */
    public BipartiteSubgraph(Collection<Vertex> SubVertices,
            BipartiteGraph SuperGraph){
        this.Supergraph = SuperGraph;
        this.SubVertices = SubVertices;
    }
    
    @Override
    public int compareTo(BipartiteSubgraph g1){
        if(g1.getVertexCount() < this.getVertexCount()){
           return 1;
        }
        if (g1.getVertexCount() > this.getVertexCount()){
            return -1;
        }
        else return 0;
    }
    
    /**
     * This method returns the neighbors of a given vertex.
     * @param Seed. The vertex whose neighbors are to be returned.
     * @return The neighbors is returned as a collection of vertices.
     */
    @Override
    public Collection<Vertex> getNeighbors(Vertex Seed){
        //1. Get the neighbors in the super graph
        Collection<Vertex> SuperNeighbors = Supergraph.getNeighbors(Seed);
        ArrayList<Vertex> SubgraphNeighbors = new ArrayList<>();
        Iterator<Vertex> SuperNeighborsIter = SuperNeighbors.iterator();
        //2. For each neighbors, if it is in the subgraph, then we put them into a new arraylist containing all neighbors in subgraph.
        while(SuperNeighborsIter.hasNext()){
            Vertex Neighbor = SuperNeighborsIter.next();
            if(SubVertices.contains(Neighbor))
                SubgraphNeighbors.add(Neighbor);
        }
        return SubgraphNeighbors;
        
    }
    
    /**
     * This method returns the vertices of a subgraph.
     * @return The set of vertices in the subgraph is returned as a collection of vertices.
     */
    @Override
    public Collection<Vertex> getSubVertices(){
        return SubVertices;
    }
    
    /**
     * This method returns the weight of two given vertices.
     * @param Vtx1
     * @param Vtx2
     * @return double
     */
    @Override
    public double getEdgeWeight(Vertex Vtx1, Vertex Vtx2){
        return Supergraph.getEdgeWeight(Vtx1, Vtx2);
    }
    
    /**
     * This method returns the size of a given set, i.e. the number of vertices in a given set.
     * @param SetIdx: The index of the set, starting from 0.
     * @return The size of the set given index.
     */
    @Override
    public int getSetSize(int SetIdx){
        int Size = 0;
        for(Vertex vtx:SubVertices){
            if(vtx.getVertexSet() == SetIdx)
                Size++;
        }
        return Size;
    }
    
     /**
     * This method returns the number of vertices in the graph, i.e. the number of vertices in the whole graph.
     * @return The number of vertices is returned.
     */
    @Override
    public int getVertexCount(){
        return SubVertices.size();
    }
    
    /**
     * This method returns the number of sets in the subgraph, i.e. the number of vertex sets in the subgraph.
     * @return The number of vertex sets is returned.
     */
    @Override
    public int getSetCount(){
        //Just return 2;
        return 2;
    }
  
    /**
     * This method returns all connected components of a given graph.
     * @return The collection of subgraphs, each of which is a connected component.
     */
    @Override
    public Collection<BipartiteSubgraph> getAllConnectedComponents(){
        ArrayList<BipartiteSubgraph> AllConnectedComponents = new ArrayList<>();
        //create a indicator LinkedList of vertices, when a vertex is included in one of the subgraphs, then it is 
        //removed from the indicator LinkedList
        LinkedList<Vertex> IndicatorList = new LinkedList<>();
        //add all the vertex into the IndicatorArray
        for(Vertex vtx: getSubVertices())
        {
            IndicatorList.add(vtx);
        }
        //While there is still unvisited vertex, we use it as the seed to search for subgraphs.
        while(!IndicatorList.isEmpty())
        {
            Vertex Seed = IndicatorList.pollFirst();
            BipartiteSubgraph ConnectedComponent = runBFS(Seed);
            AllConnectedComponents.add(ConnectedComponent);
            //remove all the vertex in the ConnectedComponent from IndicatorList
            for(Vertex vtx: ConnectedComponent.getSubVertices())
            {
                IndicatorList.remove(vtx);
            }
        }
        AllConnectedComponents.trimToSize();
        return AllConnectedComponents;
        
    }
    
    /**
     * This method returns 
     * @return Super graph is returned.
     */
    @Override
    public BipartiteGraph getSuperGraph(){
        return Supergraph;
    }
    
    /**
     * This method returns the connected component with starting given vertex of vtx.
     * @param Vtx. The starting vertex to perform BFS.
     * @return The connected component.
     */
    @Override
    public BipartiteSubgraph runBFS(Vertex Vtx){
         LinkedList<Vertex> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex> result = new ArrayList<>();

        for(Vertex vtx: getSubVertices())
        {
            marker.put(vtx.toString(), Boolean.FALSE);
        }

        //enqueue source and mark source
         queue.add(Vtx);
         result.add(Vtx);
         marker.put(Vtx.toString(),true);

         //while queue is not empty
         while(!queue.isEmpty())
         {
             //dequeue an item from queue into CurrentVtx
             Vertex CurrentVtx = queue.pollLast();
             //get the neighbours of CurrentVtx
             ArrayList<Vertex> Neighbours = new ArrayList<>(getNeighbors(CurrentVtx));

             //for each neighbour
             for(Vertex vtx: Neighbours)
             {
                 if(!marker.get(vtx.toString()))
                 {
                     marker.put(vtx.toString(),true);
                     queue.add(vtx);
                     result.add(vtx);
                 }
             }

         }
         //create a new subkpartitegraph
         BipartiteSubgraph sub = new BipartiteSubgraph(result,this.Supergraph);
         return sub;
    }
    
    /**
     * This method sets the vertices in the graph.
     * @param Vertices. The collection of vertices.
     */
    @Override
    public void setVertices(Collection<Vertex> Vertices){
        SubVertices = Vertices;
    }
    
    /**
     * This method sets the edge weight between two given vertices.
     * @param Vtx1.
     * @param Vtx2.
     * @param EdgeWeight
     */
    @Override
    public void setEdgeWeight(Vertex Vtx1, Vertex Vtx2, double EdgeWeight){
        Supergraph.setEdgeWeight(Vtx1, Vtx2, EdgeWeight);
    }
    
    
    
    
    
}
