/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import java.util.ArrayList;

/**
 * The second version of cluster.
 * @author Peng Sun
 */
public class Cluster2 implements Comparable<Cluster2>{
    int testid;
    ArrayList<Vertex2> vertices; 
    public Cluster2(ArrayList<Vertex2> Vertices)
    {
        if(Vertices == null)
            throw new IllegalArgumentException("Parameter to constructor cannot be null");
        
        this.vertices = Vertices;
    }
    
    public Cluster2(){}
    
    public int getSize()
    {
        return vertices.size();
    }
    
    public int getClustIdx()
    {
        return vertices.get(0).getClustNum();
    }
    /**
     * Return the vertex with index of i.
     * @param i
     * @return 
     */
    public Vertex2 getVertex(int index){
        return vertices.get(index);
    }
    public ArrayList<Vertex2> getVertices()
    {
        return vertices;
    }
    public void setVertices(ArrayList<Vertex2> Vertices)
    {
        this.vertices = Vertices;
    }
    public void addVertex(Vertex2 vtx)
    {
        vertices.add(vtx);
    }
    
    public boolean removeVertex(Vertex2 vtx)
    {
        return vertices.remove(vtx);
    }
    
    public void setClustNum(int ClustNum)
    {
        if(ClustNum<0)
            throw new IllegalArgumentException("Cluster number cannot be smaller than 0");
        for(Vertex2 vtx:vertices)
            vtx.setClustNum(ClustNum);
    }
    
    @Override
    public int compareTo(Cluster2 c)
    {
        if(this.getSize() >c.getSize())
            return 1;
        else if( this.getSize() == c.getSize())
            return 0;
        else return -1;
    }
    
    public void addCluster(Cluster2 c)
    {
        c.setClustNum(vertices.get(0).getClustNum());
        this.vertices.addAll(c.vertices);
    }
    
    public static void movePoint(Vertex2 vtx, Cluster2 Source, Cluster2 Target)
    {
        Source.removeVertex(vtx);
        Target.addVertex(vtx);
        vtx.setClustNum(Target.getClustIdx());
    }
}
