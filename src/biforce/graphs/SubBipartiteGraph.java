/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.Collection;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SubBipartiteGraph extends BipartiteGraph implements Comparable<SubBipartiteGraph>{
    //this is the vertex set of the vertices in the subgraph
    ArrayList<Vertex> SubVertexSet;
    //constructors
    BipartiteGraph SuperGraph;
        
    public  SubBipartiteGraph (ArrayList<Vertex> SubVertexSet,
            BipartiteGraph graph )
    {
        super(graph);
        this.SuperGraph = graph;
        this.SubVertexSet = SubVertexSet;
    }
    
    //the get and the set method
    public Collection<Vertex> getSubVertexSet()
    {
        return SubVertexSet;
    }
    public void setSubVertexSet(ArrayList<Vertex> SubVertexSet)
    {
        this.SubVertexSet = SubVertexSet;
    }
    
    
    // check wheter number of nodes of input graph1 is
    // smaller than number of nodes of input graph 2
    @Override
    public int compareTo(SubBipartiteGraph g1){
        if(g1.getSubVertexSet().size() < this.getSubVertexSet().size()){
           return 1;
        }
        if (g1.getSubVertexSet().size() > this.getSubVertexSet().size()){
            return -1;
        }
        else return 0;
    }
    
    
    @Override
    public double getEdgeWeight(Vertex vtx1, Vertex vtx2)
    {
        return SuperGraph.getEdgeWeight(vtx1, vtx2);
    }
    
    
    @Override
    public int getVertexCount()
    {
        return SubVertexSet.size();
    }
    
    @Override
    public Collection<Vertex> getVertices()
    {
        return SubVertexSet;
    }
    
}
