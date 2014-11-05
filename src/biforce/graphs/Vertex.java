package biforce.graphs;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peng
 * this abstract class implements a 
 */
public class Vertex implements Comparable<Vertex> {
    //this indicate which set the vertex belongs to
    public int vtxSet;
    public String Value;
    public int VtxIdx = 0;
    public double[] Coords;
    public int ClustNum=-1;
    
    public Vertex(String value, int level, int vtxidx)
    {
        this.Value = value;
        this.vtxSet = level;
        this.Coords = null;
        this.VtxIdx = vtxidx;
    }
    
    public Vertex(String value, int level, double[] coords, int vtxidx)
    {
        this.Value = value;
        this.vtxSet = level;
        this.Coords = coords;
        this.VtxIdx = vtxidx;
    }
    
    @Override
    public boolean equals(Object o)
    {
        //first check if the object is an instace of ActinoVertex
        if(!(o instanceof Vertex ))
            throw new IllegalArgumentException("this given object is not an instance of ActinoVertex");
        Vertex act = (Vertex)o;
        return (Value.equals(act.Value) &&
                (vtxSet == act.vtxSet));
    }

    @Override
    public int hashCode() {
        return Value.hashCode();
    }
    
    @Override
    public int compareTo(Vertex vtx)
    {
        //first check if the object is an instace of ActinoVertex
        if(!(vtx instanceof Vertex)) {
            throw new IllegalArgumentException("this given object is not an instance of ActinoVertex");
        }
        return (Value.compareTo(vtx.Value));
    }
    
    public int getVertexSet(){
        return vtxSet;
    }
    public int getIdx()
    {
        return VtxIdx;
    }
    
    
}
