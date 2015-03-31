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
 * This is a subgraph of MatrixHierGeneralGraph
 * @author penpen926
 */
public class MatrixHierGeneralSubgraph extends Subgraph2 implements
    Comparable<MatrixHierGeneralSubgraph>{
    /* The supergraph. */
    MatrixHierGeneralGraph supergraph = null;
    
    public MatrixHierGeneralSubgraph
            (ArrayList<Vertex2> subVertices, MatrixHierGeneralGraph supergraph){
        this.subvertices = subVertices;
        this.supergraph = supergraph;
    }
    /**
     * This method returns the connected component given a vertex using breadth-first search.
     * @param Vtx
     * @return 
     */
    @Override
    public MatrixHierGeneralSubgraph bfs(Vertex2 Vtx) {
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
         MatrixHierGeneralSubgraph sub = new MatrixHierGeneralSubgraph(result,this.supergraph);
         return sub;
    }
    
    @Override
    public int compareTo(MatrixHierGeneralSubgraph o) {
        if(this.vertexCount() < o.vertexCount())
            return -1;
        else if(this.vertexCount() == o.vertexCount())
            return 0;
        else return 1;
    }

    /**
     * This method returns the connected components in this subgraph.
     * @return 
     */
    @Override
    public List<? extends Subgraph2> connectedComponents() {
        ArrayList<MatrixHierGeneralSubgraph> connectedComps = new ArrayList<>();
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
            MatrixHierGeneralSubgraph ConnectedComponent = bfs(Seed);
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
    public float edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        return supergraph.edgeWeight(vtx1,vtx2);
    }

    @Override
    public boolean equals(Object o){
        /* First we check if the given object is an instance of MatrixHierGeneralSubgraph. */
        if(o instanceof MatrixHierGeneralSubgraph){
            MatrixHierGeneralSubgraph sub = (MatrixHierGeneralSubgraph)o;
            ArrayList<Vertex2> subVertices2 = sub.getSubvertices();
            /* Then we check the sizes of the two Vertex2 arraylists. */
            if(this.subvertices.size() != subVertices2.size())
                return false;
            /* Then we check the vertex one by one. */
            /* We first generate a copy of the subvertices in this class. */
            ArrayList<Vertex2> subverticesCopy = new ArrayList(subvertices);
            for(int i=0;i<subVertices2.size();i++){
                if(!subverticesCopy.remove(subVertices2.get(i)))
                    return false;
            }
            /* If all vertices in subVertices2 can be found in subverticesCopy, 
             * then we return true.*/
            return true;
        }
        else
            return false;
    }
    @Override
    public MatrixHierGeneralGraph getSuperGraph() {
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
    public void setEdgeWeight(Vertex2 vtx1, Vertex2 vtx2, float edgeWeight) {
        supergraph.setEdgeWeight(vtx1, vtx2, edgeWeight);
    }

    @Override
    public int vertexSetCount() {
        return subvertices.size();
    }

    
}
