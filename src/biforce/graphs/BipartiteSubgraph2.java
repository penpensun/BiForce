/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.*;

/**
 * The matrix bipartite subgraph 
 * @author Peng Sun
 */
public class BipartiteSubgraph2 extends Subgraph2 
        implements Comparable<BipartiteSubgraph2> {
    /* This is the supergraph. */
    private BipartiteGraph2 supergraph = null;
    
    
    /**
     * The constructor. 
     */
    public BipartiteSubgraph2(ArrayList<Vertex2> subvertices,
            BipartiteGraph2 supergraph){
        this.subvertices = subvertices;
        this.supergraph = supergraph;     
    }
    
    @Override
    public BipartiteSubgraph2 bfs(Vertex2 Vtx) {
        LinkedList<Vertex2> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex2> result = new ArrayList<>();

        for(Vertex2 vtx: getSubvertices()){
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
         //Create a new subkpartitegraph
         BipartiteSubgraph2 sub = new BipartiteSubgraph2(result,this.supergraph);
         return sub;
    }
    
    /**
     * This method compares two subgraphs, based on the number of vertices.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(BipartiteSubgraph2 o) {
        if(this.vertexCount() < o.vertexCount())
            return -1;
        else if(this.vertexCount() == o.vertexCount())
            return 0;
        else return 1;
    }

    /**
     * Get all connected components.
     * @return 
     */
    @Override
    public List<BipartiteSubgraph2> connectedComponents() {
        ArrayList<BipartiteSubgraph2> connectedComps = new ArrayList<>();
        //create a indicator LinkedList of vertices, when a vertex is included in one of the subgraphs, then it is 
        //removed from the indicator LinkedList
        LinkedList<Vertex2> indicatorList = new LinkedList<>();
        //add all the vertex into the IndicatorArray
        for(Vertex2 vtx: getSubvertices()){
            indicatorList.add(vtx);
        }
        //While there is still unvisited vertex, we use it as the seed to search for subgraphs.
        while(!indicatorList.isEmpty()){
            Vertex2 Seed = indicatorList.pollFirst();
            BipartiteSubgraph2 ConnectedComponent = bfs(Seed);
            connectedComps.add(ConnectedComponent);
            //remove all the vertex in the ConnectedComponent from indicatorList
            for(Vertex2 vtx: ConnectedComponent.getSubvertices()){
                indicatorList.remove(vtx);
            }
        }
        connectedComps.trimToSize();
        return connectedComps;
    }

    /**
     * Return the edge weight.
     * @param vtx1
     * @param vtx2
     * @return 
     */
    @Override
    public float edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        return supergraph.edgeWeight(vtx1, vtx2);
    }
    
    /**
     * This method returns if the given object is equal to the current subgraph.
     * @param subgraph
     * @return 
     */
    @Override
    public boolean equals(Object input){
        if(!(input instanceof BipartiteSubgraph2 ))
            throw new IllegalArgumentException("(MatrixBipartiteSubgraphV2.equals) "
                    + "The input object is not a MatrixBipartiteSubgraphV2.");
        
        BipartiteSubgraph2 subgraph = (BipartiteSubgraph2)input;
        if(this.subvertices.size() != subgraph.subvertices.size())
            return false;
        else{
            for(Vertex2 vtx: subvertices)
                if(!subgraph.subvertices.contains(vtx))
                    return false;
        }
        return true;  
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.supergraph);
        return hash;
    }

    /**
     * Return the supergraph.
     * @return 
     */
    @Override
    public BipartiteGraph2 getSuperGraph() {
        return supergraph;
    }
    
    

    @Override
    public ArrayList<Vertex2> neighbours(Vertex2 vtx) {
        ArrayList<Vertex2> superNbs = supergraph.neighbours(vtx);
        ArrayList<Vertex2> answer = new ArrayList<>();
        
        /* Check if the vertex has neighbours in the supergraph. */
        if(superNbs == null)
            return null;
        for(Vertex2 v:superNbs){
            if(subvertices.contains(v))
                answer.add(v);
        }
        return answer;
    }

    /**
     * Set the edge weight between vtx1 and vtx2.
     * @param Vtx1
     * @param Vtx2
     * @param EdgeWeight 
     */
    @Override
    public void setEdgeWeight(Vertex2 vtx1, Vertex2 vtx2, float edgeWeight) {
        supergraph.setEdgeWeight(vtx1, vtx2, edgeWeight);
    }

    /**
     * This method returns how many vertex sets are there in the Subgraph, namely, 2.
     * @return 
     */
    @Override
    public final int vertexSetCount() {
        return 2;
    }

    
    
}
