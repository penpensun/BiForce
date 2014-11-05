/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import biforce.graphs.MatrixBipartiteGraph;
import biforce.matrices.BipartiteGraphMatrix;
import biforce.matrices.BipartiteGraphMatrixV2;
import java.io.IOException;

/**
 * This class implements the algorithm of BiForce on matrix
 * @author Peng Sun
 */
public class BiForceOnMatrix3 {
    
    /**
     * This method is the main entrance of biforce on matrix.
     * @param Input
     * @param clusterMode
     * @param p
     * @return
     * @throws IOException 
     */
    public BipartiteGraphMatrixV2 run(BipartiteGraphMatrixV2 Input, String ClusterMode,Param p) throws IOException{
        reassignEdgeWeights(Input,ClusterMode, p);
        new BiForceOnGraph3().run(Input.getGraph(), p);
        return Input;
    }   
    
    /**
     * This method reassigns the edge weights of a given MatrixBipartiteGraph according to the different ClusterMode given.
     * @param Input
     * @param clusterMode
     * @param p 
     */
    private void reassignEdgeWeights(BipartiteGraphMatrixV2 Input, String ClusterMode, Param p){
        //parameter check for ClusterType,
        //ClusterType can only have 3 different values: overexpressed, underexpressed and lowdeviated.
        int type = -1;
        if(ClusterMode.equals("underexpressed")||ClusterMode.equals("u"))
        {
            type = 2;
        }
        else if(ClusterMode.equals("overexpressed")||ClusterMode.equals("o"))
        {
            type = 1;
        }
        else if(ClusterMode.equals("lowdeviated")||ClusterMode.equals("l"))
        {
            type = 3;
        }
        else if(ClusterMode.equals("highdeviated")||ClusterMode.equals("h"))
        {
            type = 4;
        }
        else throw new IllegalArgumentException("Cluster type not correct");
        
   
        double[][] EdgeWeights = Input.getGraph().getEdgeWeightMatrix();
        //according to different cluster type, we have to modify the values in the matrix
        if(type ==1)
        //if to cluster overexpressed clusters, we change nothing
        {for(int i=0;i<EdgeWeights.length;i++)
                for(int j=0;j<EdgeWeights[0].length;j++)
                    EdgeWeights[i][j] -= p.getThresh();
        }
        else if(type == 2)
        {
            //if to clluster underexpressed clusters, we reverse the values in the matrix
            for(int i=0;i<EdgeWeights.length;i++)
                for(int j=0;j<EdgeWeights[0].length;j++)
                    EdgeWeights[i][j] = 2*p.getThresh()- EdgeWeights[i][j];
               
        }
        else if(type ==3)
        //if we are to cluster low-deviated values, we have to first compute the mean of the matrix and use
        // - Math.abs(EdgeWeights[i][j] - Mean) as edge weights
        {
            //assign new values
            for(int i=0;i<EdgeWeights.length;i++)
                for(int j=0;j<EdgeWeights[0].length;j++)
                    EdgeWeights[i][j] = -Math.abs(EdgeWeights[i][j]-p.getThresh());
        }
        else{
            for(int i=0;i<EdgeWeights.length;i++)
                for(int j=0;j<EdgeWeights[0].length;j++)
                    EdgeWeights[i][j] = Math.abs(EdgeWeights[i][j]-p.getThresh());
        }
        
    }
    
    
}
