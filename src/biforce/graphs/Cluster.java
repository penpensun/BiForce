/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;

import java.util.ArrayList;

/**
 * This class 
 * @author Peng 
 */
/**
 * This class is prepared for post-processing
 * @author Peng
 */
public class Cluster implements Comparable<Cluster>
{
    int testid;
    ArrayList<Vertex> vertices; 
    public Cluster(ArrayList<Vertex> Vertices)
    {
        if(Vertices == null)
            throw new IllegalArgumentException("Parameter to constructor cannot be null");
        this.vertices = Vertices;
    }
    
    public Cluster(){}
    
    public int getSize()
    {
        return vertices.size();
    }
    
    public int getClustIdx()
    {
        return vertices.get(0).ClustNum;
    }
    public ArrayList<Vertex> getVertices()
    {
        return vertices;
    }
    public void setVertices(ArrayList<Vertex> Vertices)
    {
        this.vertices = Vertices;
    }
    public void addVertex(Vertex vtx)
    {
        vertices.add(vtx);
    }
    
    public boolean removeVertex(Vertex vtx)
    {
        return vertices.remove(vtx);
    }
    
    public void setClustNum(int ClustNum)
    {
        if(ClustNum<0)
            throw new IllegalArgumentException("Cluster number cannot be smaller than 0");
        for(Vertex vtx:vertices)
            vtx.ClustNum = ClustNum;
    }
    
    @Override
    public int compareTo(Cluster c)
    {
        if(this.getSize() >c.getSize())
            return 1;
        else if( this.getSize() == c.getSize())
            return 0;
        else return -1;
    }
    
    public void addCluster(Cluster c)
    {
        c.setClustNum(vertices.get(0).ClustNum);
        this.vertices.addAll(c.vertices);
    }
    
    public static void movePoint(Vertex vtx, Cluster Source, Cluster Target)
    {
        Source.removeVertex(vtx);
        Target.addVertex(vtx);
        vtx.ClustNum = Target.getClustIdx();
    }
    
    
    
}

