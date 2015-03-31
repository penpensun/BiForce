/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the subgraph of MatrixGraph.
 * @author penpen926
 */
public class MatrixSubgraph extends Subgraph2
    implements Comparable<MatrixSubgraph>{

    private MatrixGraph supergraph = null;
    
    public MatrixSubgraph(ArrayList<Vertex2> subvertices,
            MatrixGraph supergraph){
        this.subvertices = subvertices;
        this.supergraph = supergraph;
    }
    /**
     * This method performs the bfs.
     * @param Vtx
     * @return 
     */
    @Override
    public MatrixSubgraph bfs(Vertex2 Vtx) {
        LinkedList<Vertex2> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex2> result = new ArrayList<>();

        for(Vertex2 vtx: getSubvertices())
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
             //dequeue an item from queue into currentVtx
             Vertex2 currentVtx = queue.pollLast();
             //get the nei of currentVtx
             ArrayList<Vertex2> nei = neighbours(currentVtx);

             /* If no neighbour is found, then we continue. */
             if(nei == null)
                 continue;
             //for each neighbour
             for(Vertex2 vtx: nei)
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
         MatrixSubgraph sub = new MatrixSubgraph(result,this.supergraph);
         return sub;
    }

    @Override
    public int compareTo(MatrixSubgraph o) {
        if(this.vertexCount() < o.vertexCount())
            return -1;
        else if(this.vertexCount() == o.vertexCount())
            return 0;
        else return 1;
    }
    
    /**
     * This method returns the connencted component given a vertex to search.
     * @return 
     */
    @Override
    public List<? extends Subgraph2> connectedComponents() {
        ArrayList<MatrixSubgraph> connectedComps = new ArrayList<>();
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
            MatrixSubgraph connComps = bfs(Seed);
            connectedComps.add(connComps);
            //remove all the vertex in the connComps from indicatorList
            for(Vertex2 vtx: connComps.getSubvertices()){
                indicatorList.remove(vtx);
            }
        }
        connectedComps.trimToSize();
        return connectedComps;
    }
    
    /**
     * This method returns the edge weight
     * @param vtx1
     * @param vtx2
     * @return 
     */
    @Override
    public float edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        return supergraph.edgeWeight(vtx1, vtx2);
    }

    /**
     * This method returns the supergraph.
     * @return 
     */
    @Override
    public Graph2 getSuperGraph() {
        return supergraph;
    }

    /**
     * This method returns the neighbours of a given vertex.
     * @param vtx
     * @return 
     */
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
     * This method sets the edge weight between two vertices.
     * @param Vtx1
     * @param Vtx2
     * @param EdgeWeight 
     */
    @Override
    public void setEdgeWeight(Vertex2 vtx1, Vertex2 vtx2, float edgeWeight) {
        supergraph.setEdgeWeight(vtx1, vtx2, edgeWeight);
    }

    /**
     * This method returns the count of the vertices in the graph.
     * @return 
     */
    @Override
    public int vertexSetCount() {
        return subvertices.size();
    }

}
