/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import biforce.graphs.BipartiteGraph_deprecated;
import biforce.graphs.SubBipartiteGraph;
import biforce.matrices.BipartiteGraphMatrix;
import biforce.graphs.Vertex;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import biforce.log.LogFile;
/**
 *
 * @author Peng
 * This class mainly deals with matrin input
 */
public class BiForceOnMatrix2 {
    
    public static BipartiteGraphMatrix runMatrix(BipartiteGraphMatrix input, String clusterMode,Param p) throws IOException
    {
       reassignEdgeWeights(input,clusterMode, p);
       BiForceOnGraph2.run(input.getGraph(), p);
       return input;
    }
  
    static void reassignEdgeWeights(BipartiteGraphMatrix input, String clusterMode, Param p)
    {
        //parameter check for ClusterType,
        //ClusterType can only have 3 different values: overexpressed, underexpressed and lowdeviated.
        int type = -1;
        if(clusterMode.equals("underexpressed")||clusterMode.equals("u"))
        {
            type = 2;
        }
        else if(clusterMode.equals("overexpressed")||clusterMode.equals("o"))
        {
            type = 1;
        }
        else if(clusterMode.equals("lowdeviated")||clusterMode.equals("l"))
        {
            type = 3;
        }
        else if(clusterMode.equals("highdeviated")||clusterMode.equals("h"))
        {
            type = 4;
        }
        else throw new IllegalArgumentException("Cluster type not correct");
        
   
        double[][] edgeweights = input.getGraph().getEdgeWeightMatrix();
        //according to different cluster type, we have to modify the values in the matrix
        if(type ==1)
        //if to cluster overexpressed clusters, we change nothing
        {for(int i=0;i<edgeweights.length;i++)
                for(int j=0;j<edgeweights[0].length;j++)
                    edgeweights[i][j] -= p.getThresh();
        }
        else if(type == 2)
        {
            //if to clluster underexpressed clusters, we reverse the values in the matrix
            for(int i=0;i<edgeweights.length;i++)
                for(int j=0;j<edgeweights[0].length;j++)
                    edgeweights[i][j] = 2*p.getThresh()- edgeweights[i][j];
               
        }
        else if(type ==3)
        //if we are to cluster low-deviated values, we have to first compute the mean of the matrix and use
        // - Math.abs(EdgeWeights[i][j] - Mean) as edge weights
        {
            //assign new values
            for(int i=0;i<edgeweights.length;i++)
                for(int j=0;j<edgeweights[0].length;j++)
                    edgeweights[i][j] = -Math.abs(edgeweights[i][j]-p.getThresh());
        }
        else{
            for(int i=0;i<edgeweights.length;i++)
                for(int j=0;j<edgeweights[0].length;j++)
                    edgeweights[i][j] = Math.abs(edgeweights[i][j]-p.getThresh());
        }
    }
    
    
    
    
    /**
     * This method
     * @param InputFile
     * @return
     * @throws IOException 
     */
    
    /*
    static BipartiteGraph MatrixConverter(String InputFile, String clustermode, 
            double thresh, boolean hasHeader) throws IOException
    {
        //parameter check for ClusterType,
        //ClusterType can only have 3 different values: overexpressed, underexpressed and lowdeviated.
        int type = -1;
        if(clustermode.equals("underexpressed")||clustermode.equals("u"))
        {
            type = 2;
        }
        else if(clustermode.equals("overexpressed")||clustermode.equals("o"))
        {
            type = 1;
        }
        else if(clustermode.equals("lowdeviated")||clustermode.equals("l"))
        {
            type = 3;
        }
        else if(clustermode.equals("highdeviated")||clustermode.equals("h"))
        {
            type = 4;
        }
        else throw new IllegalArgumentException("Cluster type not correct");
        
        
        
        //according to different cluster type, we have to modify the values in the matrix
        if(type ==1)
        //if to cluster overexpressed clusters, we change nothing
        {for(int i=0;i<edgweight.length;i++)
                for(int j=0;j<edgweight[0].length;j++)
                    edgweight[i][j] -= thresh;
        }
        else if(type == 2)
        {
            //if to clluster underexpressed clusters, we reverse the values in the matrix
            for(int i=0;i<edgweight.length;i++)
                for(int j=0;j<edgweight[0].length;j++)
                    edgweight[i][j] = 2*thresh- edgweight[i][j];
               
        }
        else if(type ==3)
        //if we are to cluster low-deviated values, we have to first compute the mean of the matrix and use
        // - Math.abs(EdgeWeights[i][j] - Mean) as edge weights
        {
            //assign new values
            for(int i=0;i<edgweight.length;i++)
                for(int j=0;j<edgweight[0].length;j++)
                    edgweight[i][j] = -Math.abs(edgweight[i][j]-thresh);
        }
        else{
            for(int i=0;i<edgweight.length;i++)
                for(int j=0;j<edgweight[0].length;j++)
                    edgweight[i][j] = Math.abs(edgweight[i][j]-thresh);
        }
        
        
        
    }
    */
    
    
}
