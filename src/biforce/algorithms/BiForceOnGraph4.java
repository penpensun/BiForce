/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.algorithms;
import biforce.constants.BiForceConstants;
import biforce.graphs.Graph2;
import biforce.graphs.Action2;
import biforce.graphs.Vertex2;
import biforce.graphs.Cluster2;
import biforce.graphs.Subgraph2;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Stack;
import java.io.IOException;
import java.util.Collections;
/**
 * This biforce is based on Graph2.
 * @author Peng Sun.
 */
public class BiForceOnGraph4 {
    /**
     * This method calculates the costs for a given cluster assignment
     * 
     * @Untested 
     * @param Points
     * @return 
     * @throws IllegalArgumentException. When there is point without a cluster assignment.
     */
    private void assignActions(Graph2 graph){

        //first check if there is no unassigned vertex
        for(Vertex2 vtx:graph.getVertices())
            if(vtx.getClustNum() ==-1)
                throw new IllegalArgumentException("BiForceOnGraph4:  "+
                        vtx.getValue()+" not assigned with any cluster.");

        for(int i=0;i<graph.getVertices().size();i++)
            for(int j=i+1;j<graph.getVertices().size();j++){
                Vertex2 vtx1 = graph.getVertices().get(i);
                Vertex2 vtx2 = graph.getVertices().get(j);
                
                double ew = graph.edgeWeight(vtx1, vtx2);
                //if there is no edge between them, then continue;
                if(ew == 0 || Double.isNaN(ew))
                    continue;
                //case 1, in the same cluster with negative edge: then we need an addition
                if(vtx1.getClustNum() ==vtx2.getClustNum() &&
                        ew <0 ){
                   graph.pushAction(vtx1, vtx2);
                }
                
                //case 2, in different clusters but with a positive edge: we need a deletion
                else if(vtx1.getClustNum() !=vtx2.getClustNum()
                        && ew > 0){
                   graph.pushAction(vtx1, vtx2);
                } 
            }
    }
    
    /**
     * This method assigns clusters to each node in the given graph.
     * @param graph 
     */
    private void assignClusters(Graph2 graph){
        int maxClusterIdx = 0;
        for(int i=0;i<graph.vertexCount();i++)
            if(maxClusterIdx < graph.getVertices().get(i).getClustNum())
                maxClusterIdx = graph.getVertices().get(i).getClustNum();
        
        //Assign the vertex
        ArrayList<Vertex2>[] clusterArray = new ArrayList[maxClusterIdx+1];
        for(int i=0;i<=maxClusterIdx;i++)
            clusterArray[i] = new ArrayList<>();
        
        for(Vertex2 vtx: graph.getVertices())
            clusterArray[vtx.getClustNum()].add(vtx);
        
        ArrayList<Cluster2> clusters = new ArrayList();
        try{
        for(int i=0;i<=maxClusterIdx;i++)
            clusters.add(new Cluster2(clusterArray[i]));
                }catch(IllegalArgumentException e){
            System.out.print("catch");
        }
        graph.setClusters(clusters);
    }
    
    
    /**
     * This class returns the cost of editing from a given set of points. Without constructing the Actions objects, this method
     * should return the cost much quicker
     * @param graph
     * @param p
     * @return 
     */
    public double computeCost(Graph2 graph){

        double cost = 0;
        //first check if there is no unassigned vertex
        for(Vertex2 vtx:graph.getVertices())
            if(vtx.getClustNum() ==-1)
                throw new IllegalArgumentException(vtx.getValue()+" not assigned with any cluster");
        
        for(int i=0;i< graph.getVertices().size();i++)
            for(int j=i+1;j<graph.getVertices().size();j++){
                Vertex2 vtx1 = graph.getVertices().get(i);
                Vertex2 vtx2 = graph.getVertices().get(j);
                
                double ew = graph.edgeWeight(vtx1, vtx2);
                /* If there is no edge between vtx1 and vtx2, then continue. */
                if(Double.isNaN(ew))
                    continue;
                //case 1, in the same cluster with negative edge: then we need an addition
                if(vtx1.getClustNum() ==vtx2.getClustNum()
                        && ew < 0)
                   cost -= ew;           
                //case 2, in different clusters but with a positive edge: we need a deletion
                if(vtx1.getClustNum() !=vtx2.getClustNum()
                        && ew > 0)
                   cost += ew;           
            }
        //System.out.println(cost);
        return cost;
    }
    
    
    /**
     * This method returns the difference of the cost (a positive value means increase in cost, and a 
     * negative value means decrease in cost) if we merge cluster c1 and c2 together.
     * @param Points
     * @param c1
     * @param c2
     * @return 
     */
    private double computeMergeCost(Graph2 graph, Cluster2 c1, Cluster2 c2 ){
        double CostDiff = 0;
        for(Vertex2 pt1: c1.getVertices())
            for(Vertex2 pt2:c2.getVertices()){
                double ew = graph.edgeWeight(pt1, pt2);
                if(ew == 0 || Double.isNaN(ew))
                    continue;
                if(ew<0)
                    CostDiff-= ew;
                else
                    CostDiff-= ew;
            }
        return CostDiff;
    }
    
   /**
    * This method returns the difference of the cost (a positive value means increase in cost, and a 
    * negative value means decrease in cost) if we move the given vertex to the cluster clt from its original
    * cluster
    * @param graph
    * @param toMove
    * @param sourceClr
    * @param destClr
    * @return 
    */
    private double computeMoveCost(Graph2 graph, Vertex2 toMove, 
            Cluster2 sourceClr, Cluster2 destClr){
        //check if the point toMove belongs to the cluster sourceClr
        if(toMove.getClustNum() != sourceClr.getClustIdx()){
            System.out.println("(BiForceOnGraph4 computeMoveCost) Vertex to move is in cluster: "
                    + toMove.getClustNum());
            System.out.println("(BiForceOnGraph4 computeMoveCost) Dest cluster: "
                    + sourceClr.getVertices().get(0).getClustNum());
            throw new IllegalArgumentException("(BiForceOnGraph4 computeMoveCost) "
                    + "The point to move does not belong to the source cluster.");
        }
        double costDiff = 0;
        /* Compute the costDiff for moving the given vertex out of the sourceClr. */
        for(Vertex2 vtx: sourceClr.getVertices()){
            double ew = graph.edgeWeight(toMove, vtx);
            /* If there is a non-defined edge, then we continue. */
            if(Double.isNaN(ew))
                continue;
            /* If there is a negative-edge, it gives negative difference since we have to 
             delete the inserted edge.*/
            if(ew <0)
                costDiff += ew;
            /* If there is a positive-edge, it gives positive difference since we have to
             delete the original edge.*/
            else
                costDiff += ew;
        }
        /* Compute the costDiff for moving the given vertex into the given destClr. */
        for(Vertex2 vtx: destClr.getVertices()){
            
            double ew = graph.edgeWeight(toMove,vtx);
            /* If there is a non-defined edge, then we continue. */
            if(ew == 0)
                continue;
            /* If there is a negative-edge, it gives positive difference since we have to 
              insert the missing edge.*/
            if(ew<0)
                costDiff -= ew;
            /* If there is a positive-edge, it gives negative difference since we have to
             insert back the deleted edge.*/
            else
                costDiff -= ew;
        }
        return costDiff;
    }
    
    /**
     * This method implements the cooling process.
     * @param M0
     * @param iter
     * @return 
     */
    private double cooling(double M0, int iter)
    {
        
        if(iter == 1)
            return M0;
        else if(iter == 2)
            return M0/3.0*2.0;
        else
            return M0/iter*3;
    }
    
    /**
     * This method calculates and returns the displacement vector.
     * @param graph
     * @param p
     * @return 
     */
    private double[][] displace(Graph2 graph, Param p, int iter){
        double[][] dispVectors = new double[graph.vertexCount()][p.getDim()];
        
        /* To avoid unnecessary calculation, we calculate attCoeff and repCoeff first. */
        double attCoeff =p.getFatt()/graph.vertexCount();
        double repCoeff = p.getFrep()/graph.vertexCount();
        
        /* Calculate the displacement vector for each point. */
        for(int i=1;i<graph.vertexCount();i++){
            for(int j=0;j<i;j++){
                Vertex2 vtx1 = graph.getVertices().get(i);
                Vertex2 vtx2 = graph.getVertices().get(j);
                double ew = graph.edgeWeight(vtx1, vtx2);
                /* If there is no edge between Pt1 and Pt2, continue. */
                if(Double.isNaN(ew))
                    continue;
                double dist = graph.dist(vtx1,vtx2);
                if(dist == 0)
                    continue;
                /* Calculate the force */
                /* If there is an edge between two node, then we calculate 
                 * the attration force threshold. */
                double force;
                
                if(ew >p.getThresh()){
                    force = (ew- p.getThresh())*Math.log10(dist+1)*attCoeff/dist; 
                }
                else{
                    /* Else, we calculate the repulsion force. */
                    force = repCoeff*(ew - p.getThresh())/Math.log10(dist+1)/dist;
                }
                //for each dim
                for(int d=0;d<p.getDim();d++){
                    //update the displacement vector for Pt1
                    dispVectors[i][d] += (vtx2.getCoords()[d] - vtx1.getCoords()[d])*force;
                    //update the displacement vector for Pt2, with same magnitude but opposite direction;
                    dispVectors[j][d] -= (vtx2.getCoords()[d] - vtx1.getCoords()[d])*force;
                }
            }
        }
        /* Adjust the dispVectors according to the cooling factor. */
        double Mi = cooling(p.getM0(),iter);
        for(int i=0;i<graph.vertexCount();i++){
            /* The magnitude of the original displacement vector. */
            double Mag = getMagnitude(dispVectors[i]);
            //System.out.println("i: "+iter+"\tmagnitude: "+Mag+"\tcooling: "+Mi);
            /* The minor of Mi and Mag. */
            double MinM = Math.min(1, Mi/Mag);
            for(int j=0;j<p.getDim();j++)
                dispVectors[i][j] *= MinM;
        }
        return dispVectors;
    }
    
    
    /**
     * This method returns the first unassigned vertex in the vertex list. 
     * If there is no unassigned vertex, then null is returned.
     * @param vertexList
     * @return 
     */
    private Vertex2 getFirstUnassignedVertex(ArrayList<Vertex2> VertexList){
        for(Vertex2 vtx: VertexList){
            if(vtx.getClustNum() == -1)
                return vtx;
        }
        return null;
    }
    
    
    /**
     * This method returns the magnitude of a vector based on coordinates.
     * @param Coords
     * @return 
     */
    private double getMagnitude(double[] Coords)
    {
        double M = 0;
        for(int i=0;i<Coords.length;i++){
            M+= Coords[i]*Coords[i];
        }
        return Math.sqrt(M);
    }
    
    /**
     * This method initializes the layout of a given graph.
     * @param graph
     * @param p
     * @return 
     */
    private ArrayList<Vertex2> initLayout(Graph2 graph, Param p){
        
        int dim = p.getDim();
        double radius = p.getRadius();
        
        Random rd = new Random();
        int vtces = graph.vertexCount();
        ArrayList<Vertex2> vertexList;
        vertexList = new ArrayList<>();
        Iterator it = graph.getVertices().iterator();
        int i = 0;
        //for each vertex create a point with random coordinates
        
        //initiate two constant, used to distribute points on the surface of a sphere
        double inc = (double)(Math.PI * (3- Math.sqrt(5)));
        double off = (double)2.0/vtces;
        while(it.hasNext()){
            double[] coords = new double[dim];
            //retrieve the vertex
            Vertex2 vtx=(Vertex2)it.next();
            // if dim==2 distribute points equidistantly on the circle
            if (dim==2){
                //coords[0] = (radius*Math.cos((i*2*Math.PI)/vtces)) + rd.nextGaussian();
                coords[0] = (double)(radius*Math.cos((i*2*Math.PI)/vtces));
                //coords[1] = radius*Math.sin((i*2*Math.PI)/vtces)+rd.nextGaussian();
                coords[1] = (double)(radius*Math.sin((i*2*Math.PI)/vtces));
                vtx.setCoords(coords);
                vertexList.add(vtx);
                i++;
            }
            //if dim == 3, distribute the points uniformly on the surface of the sphere
            //Here, we use an algorithm called on greek golden ratio, the "Golden Section Spiral"
            else if(dim == 3)
            {
                double y = i * off -1 +(off/2.0);
                double r = Math.sqrt(1-y*y);
                double phi = i*inc;
                coords[0] = Math.cos(phi)*r*radius;
                coords[1] = y*radius;
                coords[2] = Math.sin(phi)*r*radius;
                vtx.setCoords(coords);
                vertexList.add(vtx);
                i++;
            }
            else {
                double norm=0;
                //generate dim pseudorandom numbers between -1 and 1
                // normalize these vectors and scale them to a length of 50
                for (int j=0; j<dim; j++){
                    coords[j] = rd.nextDouble()*2 -1;
                    norm += Math.pow(coords[j], 2);
                }
                norm = Math.sqrt(norm);
                for (int j=0; j<dim; j++){
                    coords[j] /= norm;
                    coords[j] *= radius;
                }
                vtx.setCoords(coords);
                vertexList.add(vtx);
            }
        }
        return vertexList;
    }
    
    
    /**
     * This is the version 2 of k-means clustering, with some bug fixed.
     * @param Points
     * @param k 
     */
    private void kmeansClust(Graph2 input, int k, Param p){
        /* Init the cluster number of the vertices. */
        for(Vertex2 vtx: input.getVertices())
            vtx.setClustNum(-1);
        /* Randomly choose k points as k centroids. */
        ArrayList<Integer> vtxIndexes = new ArrayList<>();
        /* Init the coords array for centroids. */
        double[][] centroids = new double[k][p.getDim()];
        /* Create an array to record the sizes of all clusters. */
        int[] clusterSizes = new int[k];
        for(int i=0;i<clusterSizes.length;i++){
            clusterSizes[i] = 0;
        }
        /* Init the coordinates of centroids. */
        for (double[] cen : centroids){
            for (int j = 0; j<p.getDim(); j++){
                cen[j] = -1;
            }
        }
        /* Randomly pick up k nodes as the initial k centroids. */
        for(int i=0;i<input.vertexCount();i++){
            vtxIndexes.add(i);
        }
        vtxIndexes.trimToSize();

        for(int i =0 ;i<k;i++){
            int idx = (int)(Math.random()*vtxIndexes.size());
            /* Create the coordinate array, and copy the coordinates*/
            System.arraycopy(input.getVertices().get(i).getCoords(), 0, 
                    centroids[i], 0, p.getDim());
            vtxIndexes.remove(idx);    
        }
        
        boolean isConverged = false;
        while(!isConverged){
            isConverged = true;
            /* Assign the points to closest centroid. */
            for(Vertex2 vtx:input.getVertices()){
                double minDist = Double.MAX_VALUE;
                int closestCentroidIdx = -1;
                for(int i=0;i<centroids.length;i++){
                    double dist = euclidDist(vtx,centroids[i],p);
                    if(dist<minDist){
                        minDist = dist;
                        closestCentroidIdx = i;
                    }
                }
                
                /* Check the validity of closestCentroidIdx, minDist. */
                if(closestCentroidIdx == -1)
                    throw new IllegalArgumentException("(BiForceOnGraph4 kmeansClust) "
                            + "Nearest centroid idx cannot be -1:  "+vtx.getValue());
               /* Check if any change is made. */ 
               if(vtx.getClustNum() != closestCentroidIdx)
                   isConverged = false;
               vtx.setClustNum(closestCentroidIdx);
               clusterSizes[closestCentroidIdx]++;
            }
            
            /* After assigning clusters, 
             * re-compute the coordinates for centroids. */
            for(int i=0;i<centroids.length;i++){
                /* Jump over the clusters with no points in it. */
                if(clusterSizes[i] == 0)
                    continue;
                /* For the cluster with point(s), 
                 * re-initialize the coordinates of centroids. */
                for(int j=0;j<p.getDim();j++)
                    centroids[i][j] = 0;
            }
            /* Re-compute the coordinates for centroids. */
            /* First compute addition. */
            for(Vertex2 vtx:input.getVertices()){
                for(int i=0;i<p.getDim();i++)
                    centroids[vtx.getClustNum()][i] += vtx.getCoords()[i];
            }
            /* Second compute the average. */
            for(int i=0;i<centroids.length;i++){
                /*If no points in this cluster then we do nothing. */
                if(clusterSizes[i] == 0){}
                else{
                    for(int j=0;j<p.getDim();j++)
                        centroids[i][j]/=clusterSizes[i];
                }
            }
        }
        
        /* Finally, check if there's any point unassigned. */
        for(Vertex2 vtx:input.getVertices()){
            if(vtx.getClustNum() == -1)
                throw new IllegalArgumentException("Not all points are assigned with clusters");
        }
    }
    
    
    /**
     * This is the main entrance of the algorithm, it runs Bi-Force on a given 
     * MatrixBipartiteGraph object.
     * @param graph
     * @param p
     * @return
     * @throws IOException 
     */
    public Graph2 run(Graph2 graph, Param p) throws IOException{
        /* First we have to detract the threshold. */
        if(!graph.isThreshDetracted())
            graph.detractThresh(p.getThresh());
        /* Compute the intial layout. */
        initLayout(graph, p);
        graph.updateDist();
        /* For a certain number of iterations, we compute the displacement 
         * vector and update the vertex positions. */       
        for(int i=0;i< p.getMaxIter();i++){
            graph.updatePos(displace(graph,p,i));
            graph.updateDist();
        }
        
        /*Starting from the lower bound of the threshold, 
         *until the upper bound of the threshold.We try to find the dist 
         *threhold resulting in the smallest editing cost for test, 
         *we record the best DistThr. */
        
        double minCostSL = Double.MAX_VALUE;
        double costAti;
        double minCostThresh=0;
        int[] slClusters = new int[graph.getVertices().size()];
        for(double i = p.getLowerth(); i<= p.getUpperth();i+= p.getStep()){
            singleLinkageClust(graph,i);
            costAti = computeCost(graph);
            if(costAti< minCostSL){
                minCostSL = costAti;
                minCostThresh = i;
                //record the cluster assignment
                for(int j=0;j<graph.getVertices().size();j++)
                    slClusters[j] = graph.getVertices().get(j).getClustNum(); 
            }
        }
        double minCostKmeans = Double.MAX_VALUE;
        int minK = 0;
        double costAtk;
        int[] kmeansClusters = new int[graph.getVertices().size()];
        
        //in case the initial k=2 is larger than or equal to VertexList.size()/3
        if(graph.getVertices().size() < 9){
            for(int k=2;k<=4;k++){
                kmeansClust(graph,k,p);
                costAtk = computeCost(graph);
                if(minCostKmeans == -1 || costAtk < minCostKmeans){
                    minCostKmeans = costAtk;
                    minK = k;
                    //record the cluster assignment 
                    for(int i=0;i<graph.getVertices().size();i++)
                        kmeansClusters[i] = graph.getVertices().get(i).getClustNum();
                }
            }
        }
        else if(graph.getVertices().size()<200){
            for(int k=2;k<graph.getVertices().size()/4;k++){
                kmeansClust(graph,k,p);
                costAtk = computeCost(graph);
                if(minCostKmeans == -1 || costAtk < minCostKmeans){
                    minCostKmeans = costAtk;
                    minK = k;
                    //record the cluster assignment 
                    for(int i=0;i<graph.getVertices().size();i++)
                        kmeansClusters[i] = graph.getVertices().get(i).getClustNum();
                }
            }
        }
        else{
             for(int k=2;k<20;k++){
                kmeansClust(graph,k,p);
                costAtk = computeCost(graph);
                if(minCostKmeans == -1 || costAtk < minCostKmeans){
                    minCostKmeans = costAtk;
                    minK = k;
                    //record the cluster assignment 
                    for(int i=0;i<graph.getVertices().size();i++)
                        kmeansClusters[i] = graph.getVertices().get(i).getClustNum();
                }
            }
        }

        //check whether single linkage or kmeans give the better result
        if(minCostKmeans <= minCostSL){
            //System.out.println("K means is better");
            //System.out.println("Best k is "+minK);
            //restore the cluster assignment of kmeans
            for(int i=0;i<graph.getVertices().size();i++)
                graph.getVertices().get(i).setClustNum(kmeansClusters[i]);
        }
        else{
            //restore the cluster assignments of singlelinkage clustering
            for(int i=0;i<graph.getVertices().size();i++)
                graph.getVertices().get(i).setClustNum(slClusters[i]);
        }
        //assing the clusters
        assignClusters(graph);
        
        //post-processing
        //Step 1, merge Clusters
        boolean next;   
        ArrayList<Cluster2> clusters = graph.getClusters();
        do{
            next = false;
            Collections.sort(clusters);
            for(int i=0;i<clusters.size();i++)
                for(int j=i+1;j<clusters.size();j++){
                    //if this merge can really reduce the cost
                    if(computeMergeCost(graph, clusters.get(i),clusters.get(j))<0){
                        //then we merge the two clusters, assigning the cluster number to be the smaller one
                        clusters.get(i).addCluster(clusters.get(j));
                        
                        //remove cluster j
                        clusters.remove(j);
                        j--;//since the index j object is removed, all subsequent objects are shifted left, thus we have to 
                        // minus 1 from j.
                        //proceed to another round
                        next = true;
                    }
                }
            graph.setClusters(clusters);
            
        }while(next);

        do{
            next = false;
            Collections.sort(clusters);
            for(int i=0;i<clusters.size();i++){
                ArrayList<Vertex2> VerticesClusteri = clusters.get(i).getVertices();
                Iterator<Vertex2> VtxIter = VerticesClusteri.iterator();
                while(VtxIter.hasNext()){
                    Vertex2 vtx = VtxIter.next();
                    for(int j=i+1;j<clusters.size();j++){
                        //if this merge can really reduce the cost
                        if(computeMoveCost(graph, vtx,clusters.get(i), clusters.get(j))<0){
                            //then we assign new cluster number to the current point
                            Cluster2.movePoint(vtx, clusters.get(i), clusters.get(j));
                            next = true;
                            break;
                        }
                    }
                    if(next == true)
                        break;
                }
            }
        }while(next);
         //Assign actions based on the current clusters
        assignActions(graph);
        //Carry out all actions.
        graph.takeActions();
        return graph;
    }
    
    
    /**
     * This method performs single-linkage cluster.
     * @param VertexList
     * @param distThresh 
     */
    private void singleLinkageClust(Graph2 graph, double distThresh){

        //init all the clust num to be -1
        for(Vertex2 vtx:graph.getVertices())
            vtx.setClustNum(-1);
        int currentClustIdx = 0;
        Vertex2 noClustVtx;
        
        while((noClustVtx = getFirstUnassignedVertex(graph.getVertices()))!= null){
            noClustVtx.setClustNum(currentClustIdx);
            //check if all the vertex are assigned with some cluster number
            
            Stack<Vertex2> verticesToVisit = new Stack<>();
            verticesToVisit.add(noClustVtx);
            while(!verticesToVisit.isEmpty()){
                Vertex2 seed = verticesToVisit.pop();
                /* For all the other unassigned vertices in the VertexList. */
                for(Vertex2 vtx: graph.getVertices()){
                    if(vtx.getClustNum() != -1)
                        continue;

                    if( graph.dist(vtx,seed) < distThresh){
                        vtx.setClustNum(currentClustIdx);
                        verticesToVisit.add(vtx);
                    }
                }
            }
            /* Increase the cluster number by 1. */
            currentClustIdx++;
        }
    }
    
    /**
     * 
     * @param vtx
     * @param coords
     * @param p
     * @return 
     */
    private double euclidDist(Vertex2 vtx, double[] coords, Param p){
        //check if they have the same dimension
        //System.out.println(pt.getCoordinates().length);
        //System.out.println(coords.length);
        if(vtx.getCoords().length !=coords.length )
        {
            System.out.println(vtx.getCoords().length);
            System.out.println(coords.length);
            throw new IllegalArgumentException("Point 1 and the given coordinates have different dimension");
        }
        double Dist= 0;
        for(int i=0;i<p.getDim();i++)
        {
            Dist+= (vtx.getCoords()[i]-coords[i])*
                    (vtx.getCoords()[i]-coords[i]);
        }
        Dist = Math.sqrt(Dist);
        return Dist;
        
    }
    

}
