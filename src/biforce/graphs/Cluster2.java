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
    
    public int size()
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
        if(this.size() >c.size())
            return 1;
        else if( this.size() == c.size())
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
    
    /**
     * Compute the cluster distance according to the given cluster type.
     * @param clust1
     * @param clust2
     * @param graph
     * @param type 1: Single-linkage, 2: Complete-linkage 3: Average-linkage
     */
    public static float dist(Cluster2 clust1, Cluster2 clust2, Graph2 graph, int type){
        if(type<=0 || type >3)
            throw new IllegalArgumentException("(Cluster.dist) Type can only be 1,2 or 3. Yours is:  "+type);
        float dist;
          
        switch(type){
            case 1:// Single linkage.
                dist = Float.MAX_VALUE;
                for(int i=0;i<clust1.size();i++)
                    for(int j=0;j<clust2.size();j++){
                        float vertexDist = graph.dist(clust1.getVertex(i),clust2.getVertex(j));
                        if(dist> vertexDist)
                            dist = vertexDist;
                    }
                return dist;
             
            case 2:
                dist = Float.MIN_VALUE;
                for(int i=0;i<clust1.size();i++)
                    for(int j=0;j<clust2.size();j++){
                        float vertexDist = graph.dist(clust1.getVertex(i), clust2.getVertex(j));
                        if(dist<vertexDist)
                            dist = vertexDist;
                    }
                return dist;
                
            case 3:
                dist = 0;
                for(int i=0;i<clust1.size();i++)
                    for(int j=0;j<clust2.size();j++){
                        dist+= graph.dist(clust1.getVertex(i),clust2.getVertex(j));
                    }
                return dist/(clust1.size()*clust2.size());
        }
        return -1;
    }
    
}
