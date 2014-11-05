/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

/**
 * New version of action.
 * @author peng sun
 */
public class Action2 {
    private Vertex2 vtx1;
    private Vertex2 vtx2;
    private double OriginalWeight;
    
    public Action2(Vertex2 vtx1, Vertex2 vtx2, double OriginalWeight)
    {
        this.vtx1 = vtx1;
        this.vtx2 = vtx2;
        this.OriginalWeight = OriginalWeight;
    }
    
    public Vertex2 getVtx1()
    {
        return vtx1;
    }
    
    public Vertex2 getVtx2()
    {
        return vtx2;
    }
    
    public double getOriginalWeight() 
    {
        return OriginalWeight;
    }
}
