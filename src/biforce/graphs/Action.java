/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

/**
 *
 * @author Peng
 * 
 */
public class Action {
    private Vertex vtx1;
    private Vertex vtx2;
    private double OriginalWeight;
    
    public Action(Vertex vtx1, Vertex vtx2, double OriginalWeight)
    {
        this.vtx1 = vtx1;
        this.vtx2 = vtx2;
        this.OriginalWeight = OriginalWeight;
    }
    
    public Vertex getVtx1()
    {
        return vtx1;
    }
    
    public Vertex getVtx2()
    {
        return vtx2;
    }
    
    public double getOriginalWeight() 
    {
        return OriginalWeight;
    }
}
