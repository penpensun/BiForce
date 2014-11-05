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
 * This class
 * @author penpen926
 */
public class MatrixHierNpartiteSubgraph extends Subgraph2 implements
        Comparable<MatrixHierNpartiteSubgraph>{
    /* The supergraph. */
    MatrixHierNpartiteGraph supergraph = null;
    
    public MatrixHierNpartiteSubgraph(ArrayList<Vertex2> subVertices, MatrixHierNpartiteGraph supergraph){
        this.subvertices = subVertices;
        this.supergraph = supergraph;
    }
    /**
     * This method returns the connected component given a vertex using breadth-first search.
     * @param Vtx
     * @return 
     */
    @Override
    public MatrixHierNpartiteSubgraph bfs(Vertex2 Vtx) {
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
         /* Create a new subhierNpartitegraph. */
         MatrixHierNpartiteSubgraph sub = new MatrixHierNpartiteSubgraph(result,this.supergraph);
         return sub;
    }

    /**
     * This method returns the connected components in this subgraph.
     * @return 
     */
    @Override
    public List<? extends Subgraph2> connectedComponents() {
        ArrayList<MatrixHierNpartiteSubgraph> connectedComps = new ArrayList<>();
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
            MatrixHierNpartiteSubgraph ConnectedComponent = bfs(Seed);
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
     * This method returns the edge weight given two vertices.
     * @param vtx1
     * @param vtx2
     * @return 
     */
    @Override
    public double edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        return supergraph.edgeWeight(vtx1,vtx2);
    }

    @Override
    public MatrixHierNpartiteGraph getSuperGraph() {
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

    @Override
    public void setEdgeWeight(Vertex2 vtx1, Vertex2 vtx2, double edgeWeight) {
        supergraph.setEdgeWeight(vtx1, vtx2, edgeWeight);
    }

    @Override
    public int vertexSetCount() {
        return subvertices.size();
    }

    @Override
    public int compareTo(MatrixHierNpartiteSubgraph o) {
        if(this.vertexCount() < o.vertexCount())
            return -1;
        else if(this.vertexCount() == o.vertexCount())
            return 0;
        else return 1;
    }
    
}
