/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;
import java.util.ArrayList;
import java.util.List;
/**
 * This abstract class is version 2 of subgraph, modified according to GraphV2.
 * 
 * Major modifications:
 * (1) Changed from "interface" to "abstract class". In abstract class Subgraph, element 
 * Subvertices is defined, to store the vertices.
 * @author penpen926
 */
public abstract class Subgraph2 {
    /* Elements. */
    /* The vertices in the subgraph. */
    protected ArrayList<Vertex2> subvertices;

    
    /* Methods. */
    /* Return a connected component by breadth-first search given a vertex. */
    public abstract Subgraph2 bfs(Vertex2 Vtx);
    /* Return all connected components. */
    public abstract List<? extends Subgraph2> connectedComponents();
    
    /* This method returns the edge weight. */
    public abstract double edgeWeight(Vertex2 vtx1, Vertex2 vtx2);
    
    /* This method returns true, since it's a subgraph. */
    public final boolean isSubgraph(){
        return true;
    }
    
    /* Return the Subvertices in this subgraph. */
    public final ArrayList<Vertex2> getSubvertices(){
        return subvertices;
    }
    
    /* This method returns the super graph. */
    public abstract Graph2 getSuperGraph();
    
    /* Return the neighbours of a given vertex. */
    public abstract ArrayList<Vertex2> neighbours(Vertex2 vtx);
    
    
    public abstract void setEdgeWeight(Vertex2 Vtx1, Vertex2 Vtx2, double EdgeWeight);
    /* Set the sub vertices. */
    public final void setSubvertices(ArrayList<Vertex2> Subvertices){
        this.subvertices = Subvertices;
    }
    
    
    /* Return the number of vertex sets. */
    public abstract int vertexSetCount();
    
    /* Return the number of vertices. */
    public int vertexCount(){
        return subvertices.size();
    }
    
    
}

