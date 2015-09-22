/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import biforce.graphs.BipartiteGraph_deprecated;
import biforce.graphs.Vertex;
import biforce.graphs.Cluster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Stack;
 
/**
 *
 * @author Peng Sun 
 */
public class BiForceOnGraph2 {
    
    
   public static BipartiteGraph_deprecated run(BipartiteGraph_deprecated graph, Param p) throws IOException
    {
        InitialLayout(graph, p);
        //Get the list of vertices in the input graph
        ArrayList<Vertex> VertexList = new ArrayList<>(graph.getVertices());
        /*
        * For a certain number of iterations, we displace the nodes and update the position of them
        */       
        for(int i=0;i< p.getMaxIter();i++)
        {
            update(graph,getDisplacement(graph,p),i,p);
        }
        //calculate the pairwise distances
        double[][] distances = new double[graph.getVertexCount()][graph.getVertexCount()];
        
        for(int i =0;i<VertexList.size();i++)
        {
            for(int j =i+1;j<VertexList.size();j++)
            {
                Vertex vtx1 = VertexList.get(i);
                Vertex vtx2 = VertexList.get(j);
                
                double dist = getEuclidDist(vtx1,vtx2,p);
                putDist(distances, graph, vtx1,vtx2,dist);
            }
        }
       
        //Starting from the lower bound of the threshold, until the upper bound of the threshold
        //We try to find the dist threhold resulting in the smallest editing cost
        //for test, we record the best DistThr
        

        double MinCostSL = Double.MAX_VALUE;
        double CostAtThreshi;
        double MinDistThresh=0;
        int[] SingleLinkageAssignments = new int[VertexList.size()];
        for(double i = p.getLowerth(); i<= p.getUpperth();i+= p.getStep())
        {
            SingleLinkage(graph,distances,i);
            CostAtThreshi = computeCost(graph,p);
            if(CostAtThreshi< MinCostSL)
            {
                MinCostSL = CostAtThreshi;
                MinDistThresh = i;
                //record the cluster assignment
                for(int j=0;j<VertexList.size();j++)
                    SingleLinkageAssignments[j] = VertexList.get(j).ClustNum; 
            }
        }
        double MinCostKmeans = Double.MAX_VALUE;
        int MinK = 0;
        double CostAtK;
        int[] KmeansAssignments = new int[VertexList.size()];
        
        //in case the initial k=2 is larger than or equal to VertexList.size()/3
        if(VertexList.size() < 9)
        {
            for(int k=2;k<=4;k++)
            {
                Kmeans(graph,k,p);
                CostAtK = computeCost(graph,p);
                if(MinCostKmeans == -1 || CostAtK < MinCostKmeans)
                {
                    MinCostKmeans = CostAtK;
                    MinK = k;
                    //record the cluster assignment 
                    for(int i=0;i<VertexList.size();i++)
                        KmeansAssignments[i] = VertexList.get(i).ClustNum;
                }
            }
        }
        else if(VertexList.size()<200){
            for(int k=2;k<VertexList.size()/4;k++)
            {
                Kmeans(graph,k,p);
                CostAtK = computeCost(graph,p);
                if(MinCostKmeans == -1 || CostAtK < MinCostKmeans)
                {
                    MinCostKmeans = CostAtK;
                    MinK = k;
                    //record the cluster assignment 
                    for(int i=0;i<VertexList.size();i++)
                        KmeansAssignments[i] = VertexList.get(i).ClustNum;
                }
            }
        }
        else
        {
             for(int k=2;k<20;k++)
            {
                Kmeans(graph,k,p);
                CostAtK = computeCost(graph, p);
                if(MinCostKmeans == -1 || CostAtK < MinCostKmeans)
                {
                    MinCostKmeans = CostAtK;
                    MinK = k;
                    //record the cluster assignment 
                    for(int i=0;i<VertexList.size();i++)
                        KmeansAssignments[i] = VertexList.get(i).ClustNum;
                }
            }
        }

        //check whether single linkage or kmeans give the better result
        if(MinCostKmeans <= MinCostSL)
        {
            //System.out.println("K means is better");
            //System.out.println("Best k is "+MinK);
            //restore the cluster assignment of kmeans
            for(int i=0;i<VertexList.size();i++)
                VertexList.get(i).ClustNum = KmeansAssignments[i];
        }
        else
        {
            //restore the cluster assignments of singlelinkage clustering
            for(int i=0;i<VertexList.size();i++)
                VertexList.get(i).ClustNum = SingleLinkageAssignments[i];
        }
        //assing the clusters
        assignClusters(graph);

        //post-processing
        //Step 1, merge Clusters
        boolean next;   
        
        ArrayList<Cluster> clusters = graph.getClusters();

        do
        {
            next = false;
            Collections.sort(clusters);
            for(int i=0;i<clusters.size();i++)
                for(int j=i+1;j<clusters.size();j++)
                {
                    //if this merge can really reduce the cost
                    if(computeMergeCost(graph, clusters.get(i),clusters.get(j),p) <0)
                    {
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
            for(int i=0;i<clusters.size();i++)
            {
                ArrayList<Vertex> VerticesClusteri = clusters.get(i).getVertices();
                Iterator<Vertex> VtxIter = VerticesClusteri.iterator();
                while(VtxIter.hasNext())
                {
                    Vertex vtx = VtxIter.next();
                    for(int j=i+1;j<clusters.size();j++)
                    {
                        //if this merge can really reduce the cost
                        if(computeMoveCost(graph, vtx,clusters.get(i), clusters.get(j),p)<0 )
                        {
                            //then we assign new cluster number to the current point
                            Cluster.movePoint(vtx, clusters.get(i), clusters.get(j));
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
        assignActions(graph,p);
        //Carry out all actions.
        graph.takeAllActions(p.getThresh());
        return graph;
    }
    
    /**
     * 
     * @param inputfile
     * @param p
     * @return
     * @throws IOException 
     */
    public static BipartiteGraph_deprecated run(String inputfile, Param p) throws IOException
    {
        BipartiteGraph_deprecated graph = new BipartiteGraph_deprecated(inputfile);
        return run(graph,p);
    }
    
    
    
    //Peng: this method should return an ArrayList<Point> object.
    private static ArrayList<Vertex> InitialLayout(BipartiteGraph_deprecated graph, Param p){
        
        int dim = p.getDim();
        double radius = p.getRadius();
        
        Random rd = new Random();
        int vtces = graph.getVertexCount();
        ArrayList<Vertex> VertexList  = new ArrayList<>();
        Iterator it = graph.getVertices().iterator();
        int i = 0;
        //for each vertex create a point with random coordinates
        
        //initiate two constant, used to distribute points on the surface of a sphere
        double inc = (double)(Math.PI * (3- Math.sqrt(5)));
        double off = (double)2.0/vtces;
        while(it.hasNext()){
            double[] coords = new double[dim];
            //retrieve the vertex
            Vertex vtx=(Vertex)it.next();
            // if dim==2 distribute points equidistantly on the circle
            if (dim==2){
                //coords[0] = (radius*Math.cos((i*2*Math.PI)/vtces)) + rd.nextGaussian();
                coords[0] = (double)(radius*Math.cos((i*2*Math.PI)/vtces));
                //coords[1] = radius*Math.sin((i*2*Math.PI)/vtces)+rd.nextGaussian();
                coords[1] = (double)(radius*Math.sin((i*2*Math.PI)/vtces));
                vtx.Coords = coords;
                VertexList.add(vtx);
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
                vtx.Coords = coords;
                VertexList.add(vtx);
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
                vtx.Coords = coords;
                VertexList.add(vtx);
            }
        }
        return VertexList;
    }
    
    
    /**
     * This method calculates and returns the displacement vector.
     * @param graph
     * @param p
     * @return 
     */
    private static double[][] getDisplacement(BipartiteGraph_deprecated graph, Param p)
    {
        ArrayList<Vertex> Vertices = new ArrayList(graph.getVertices());
        double[][] DispVectors = new double[graph.getVertexCount()][p.getDim()];
        //To avoid unnecessary calculation, we calculate AttCoeff and RepCoeff first
        double AttCoeff =p.getFatt()/graph.getVertexCount();
        double RepCoeff = p.getFrep()/graph.getVertexCount();
        //calculate the displacement vector for each point
        for(int i=1;i<graph.getVertexCount();i++)
        {
            for(int j=0;j<i;j++)
            {
                Vertex Vtx1 = Vertices.get(i);
                Vertex Vtx2 = Vertices.get(j);
                double EdgeWeight = graph.getEdgeWeight(Vtx1, Vtx2);
                //if thereis no edge between Pt1 and Pt2, continue;
                if(EdgeWeight == 0)
                    continue;
                double Dist = getEuclidDist(Vtx1,Vtx2,p);
                if(Dist == 0)
                    continue;
                //Calculate the force
                //if there is an edge between two node, then we calculate the attration force
                //threshold
                double Force;
                
                if(EdgeWeight >p.getThresh()){
                    Force = (EdgeWeight- p.getThresh())*Math.log10(Dist+1)*AttCoeff/Dist; 
                }
                else{
                    //else, we calculate the repulsion force
                    Force = RepCoeff*(EdgeWeight - p.getThresh())/Math.log10(Dist+1)/Dist;
                }
                //for each dim
                for(int d=0;d<p.getDim();d++)
                {
                    //update the displacement vector for Pt1
                    DispVectors[i][d] += (Vtx2.Coords[d] - Vtx1.Coords[d])*Force;
                    //update the displacement vector for Pt2, with same magnitude but opposite direction;
                    DispVectors[j][d] -= (Vtx2.Coords[d] - Vtx1.Coords[d])*Force;
                }

            }
        }
        return DispVectors;
        
    }
    
    //The method returns the euclidean distance between two points
    private static double getEuclidDist(Vertex Vtx1, Vertex Vtx2, Param p)
    {
        //check if they have the same dimension
        if(Vtx1.Coords.length !=Vtx2.Coords.length )
            throw new IllegalArgumentException("Point 1 and Point 2 have different dimension");
        double Dist= 0;
        for(int i=0;i<p.getDim();i++)
        {
            Dist+= (Vtx1.Coords[i]-Vtx2.Coords[i])*
                    (Vtx1.Coords[i]-Vtx2.Coords[i]);
        }
        Dist = Math.sqrt(Dist);
        return Dist;
    }
    
    //this GetEuclidDist() uses a point and a coord array as arguments.
    private static double getEuclidDist(Vertex vtx, double[] coords, Param p)
    {
        //check if they have the same dimension
        //System.out.println(pt.getCoordinates().length);
        //System.out.println(coords.length);
        if(vtx.Coords.length !=coords.length )
        {
            System.out.println(vtx.Coords.length);
            System.out.println(coords.length);
            throw new IllegalArgumentException("Point 1 and the given coordinates have different dimension");
        }
        double Dist= 0;
        for(int i=0;i<p.getDim();i++)
        {
            Dist+= (vtx.Coords[i]-coords[i])*
                    (vtx.Coords[i]-coords[i]);
        }
        Dist = Math.sqrt(Dist);
        return Dist;
        
    }
    
    //this method performs the updating of the coords of all the points
    private static void update(BipartiteGraph_deprecated input, 
            ArrayList<double[]> DispVectors, int iter, Param p)
    {
        ArrayList<Vertex> VertexList = new ArrayList(input.getVertices());
        //this variable stores the magnitude of the current iteration. which limits the max
        //magnitude of movemment;
        double Mi = Cooling(p.getM0(),iter);
        //for all the points
        for(int i=0;i<VertexList.size();i++)
        {
            //The magnitude of the original displacement vector
            double Mag = getMagnitude(DispVectors.get(i));
//            System.out.println("i: "+iter+"\tmagnitude: "+Mag+"\tcooling: "+Mi);
            //The minor of Mi and Mag
            double MinM = Math.min(1, Mi/Mag);
            //The coordinates of the points
            double[] Coords = VertexList.get(i).Coords;
            //for each dimension, update the position of the point
            for(int j=0;j<p.getDim();j++)
            {
                Coords[j] += DispVectors.get(i)[j]*MinM;
            }
            //set the coords
            VertexList.get(i).Coords = Coords;
            //referencing the redundant variables to null;
        }
    }
    
    
    private static void update(BipartiteGraph_deprecated input,
            double[][] DispVectors, int iter,Param p)
    {
        ArrayList<Vertex> VertexList = new ArrayList(input.getVertices());
        //this variable stores the magnitude of the current iteration. which limits the max
        //magnitude of movemment;
        double Mi = Cooling(p.getM0(),iter);
        //for all the points
        for(int i=0;i<VertexList.size();i++)
        {
            //The magnitude of the original displacement vector
            double Mag = getMagnitude(DispVectors[i]);
//            System.out.println("i: "+iter+"\tmagnitude: "+Mag+"\tcooling: "+Mi);
            //The minor of Mi and Mag
            double MinM = Math.min(1, Mi/Mag);
            //The coordinates of the points
            double[] Coords = VertexList.get(i).Coords;
            //for each dimension, update the position of the point
            for(int j=0;j<p.getDim();j++)
            {
                Coords[j] += DispVectors[i][j]*MinM;
            }
            //set the coords
            VertexList.get(i).Coords = Coords;
            //referencing the redundant variables to null;
        }
    }
    
    
    //this method performs the cooling process
    private static double Cooling(double M0, int iter)
    {
        
        if(iter == 1)
            return M0;
        else if(iter == 2)
            return M0/3.0*2.0;
        else
            return M0/iter*3;
    }
    
    //this methods return the magnitude of a coord array
    //in our case, it return the euclidean length.
    private static double getMagnitude(double[] Coords)
    {
        double M = 0;
        for(int i=0;i<Coords.length;i++)
        {
            M+= Coords[i]*Coords[i];
        }
        return Math.sqrt(M);
    }
    /**
     * This is the version 2 of k-means clustering, with some bug fixed.
     * @param Points
     * @param k 
     */
    private static void Kmeans(BipartiteGraph_deprecated input, int k, Param p)
    {
        ArrayList<Vertex> VertexList = new ArrayList<>(input.getVertices());
        for(Vertex vtx: VertexList)
            vtx.ClustNum=-1;
        //randomly choose k points as k centroids
        ArrayList<Integer> VtxIdxArray = new ArrayList<>();
        //init the coords array for Centroid
        double[][] Centroids = new double[k][p.getDim()];
        int[] ClusterSize = new int[k];
        for(int i=0;i<ClusterSize.length;i++)
        {
            ClusterSize[i] = 0;
        }
        for (double[] Centroid : Centroids) {
            for (int j = 0; j<p.getDim(); j++) {
                Centroid[j] = -1;
            }
        }
        for(int i=0;i<VertexList.size();i++)
        {
            VtxIdxArray.add(i);
        }
        VtxIdxArray.trimToSize();
        //randomly pick up k nodes as the initial k centroids
        for(int i =0 ;i<k;i++)
        {
            int idx = (int)(Math.random()*VtxIdxArray.size());
            System.arraycopy(VertexList.get(idx).Coords, 0, Centroids[i], 0, p.getDim());
            VtxIdxArray.remove(idx);    
        }
        
        boolean isConverged = false;
        while(!isConverged)
        {
            isConverged = true;
            //assign the points with nearest centroid
            for(Vertex vtx:VertexList)
            {
                double MinDist = Double.MAX_VALUE;
                int NearestCentroidIdx = -1;
                for(int i=0;i<Centroids.length;i++)
                {
                    double dist = getEuclidDist(vtx,Centroids[i],p);
                    if(dist<MinDist)
                    {
                        MinDist = dist;
                        NearestCentroidIdx = i;
                    }
                }
                
                //check the validity of NearestCentroidIdx, MinDist
                if(NearestCentroidIdx == -1)
                    throw new IllegalArgumentException("nearest centroid idx cannot be -1");
               //check if any change is made 
               if(vtx.ClustNum != NearestCentroidIdx)
                   isConverged = false;
               vtx.ClustNum=NearestCentroidIdx;
               ClusterSize[NearestCentroidIdx]++;
            }
            
            //re-compute the coordinates for centroids
            for(int i=0;i<Centroids.length;i++)
            {
                //jump over the clusters with no points in it
                if(ClusterSize[i] == 0)
                    continue;
                for(int j=0;j<p.getDim();j++)
                    Centroids[i][j] = 0;
            }
            for(Vertex vtx:VertexList)
            {
                for(int i=0;i<p.getDim();i++)
                    Centroids[vtx.ClustNum][i] += vtx.Coords[i];
            }
            //compute the average of the coordinate su,
            for(int i=0;i<Centroids.length;i++)
            {
                //if no points in this cluster
                //then we do nothing
                if(ClusterSize[i] == 0){}
                else{
                    for(int j=0;j<p.getDim();j++)
                        Centroids[i][j]/=ClusterSize[i];
                }
            }
        }
        
        //finally, check if there's any point unassigned
        for(Vertex vtx:VertexList)
        {
            if(vtx.ClustNum == -1)
                throw new IllegalArgumentException("Not all points are assigned with clusters");
        }
    }
      
    

    /**
     * 
     * @param VertexList
     * @param DistThresh 
     */
    private static void SingleLinkage(BipartiteGraph_deprecated graph, double[][] distances, double DistThresh)
    {
        ArrayList<Vertex> VertexList = new ArrayList(graph.getVertices());
        //init all the clust num to be -1
        for(Vertex vtx:VertexList)
            vtx.ClustNum = -1;
        int CurrentClustNum = 0;
        Vertex UnassignedVtx;
        
        while((UnassignedVtx = getFirstUnassignedVertex(VertexList))!= null)
        {
            UnassignedVtx.ClustNum = CurrentClustNum;
            //check if all the vertex are assigned with some cluster number
            
            Stack<Vertex> VerticesToVisit = new Stack<>();
            VerticesToVisit.add(UnassignedVtx);
            while(!VerticesToVisit.isEmpty())
            {
                Vertex Seed = VerticesToVisit.pop();
                //for all the other unassigned vertices in the VertexList
                for(Vertex vtx: VertexList)
                {
                    if(vtx.ClustNum != -1)
                        continue;

                    if(getDist(graph, distances,vtx,Seed) < DistThresh)
                    {
                        vtx.ClustNum = CurrentClustNum;
                        VerticesToVisit.add(vtx);
                    }
                }
            }
            //increase the cluster number by 1
            CurrentClustNum++;
        }
    }
    
   
    /**
     * This method returns the first unassigned vertex in the vertex list. If there is no unassigned vertex, then null is returned.
     * @param VertexList
     * @return 
     */
    private static Vertex getFirstUnassignedVertex(ArrayList<Vertex> VertexList)
    {
        for(Vertex vtx: VertexList)
        {
            if(vtx.ClustNum == -1)
                return vtx;
        }
        return null;
    }
    
    /**
     * This method calculates the costs for a given cluster assignment
     * 
     * @param Points
     * @return 
     * @throws IllegalArgumentException. When there is point without a cluster assignment.
     */
    private static void assignActions(BipartiteGraph_deprecated graph, Param p) throws IllegalArgumentException
    {
        ArrayList<Vertex> VertexList = new ArrayList(graph.getVertices());
        //first check if there is no unassigned vertex
        for(Vertex vtx:VertexList)
            if(vtx.ClustNum ==-1)
                throw new IllegalArgumentException(vtx.Value+" not assigned with any cluster");

        for(int i=0;i<VertexList.size();i++)
            for(int j=i+1;j<VertexList.size();j++)
            {
                Vertex vtx1 = VertexList.get(i);
                Vertex vtx2 = VertexList.get(j);
                if(vtx1.vtxSet == vtx2.vtxSet)
                    continue;
                double EdgeWeight = graph.getEdgeWeight(vtx1, vtx2);
                //if there is no edge between them, then continue;
                if(EdgeWeight == 0)
                    continue;
                //case 1, in the same cluster with negative edge: then we need an addition
                if(vtx1.ClustNum ==vtx2.ClustNum
                        && EdgeWeight < p.getThresh())
                {
                   graph.addAction(vtx1, vtx2,p.getThresh());
                }
                
                //case 2, in different clusters but with a positive edge: we need a deletion
                else if(vtx1.ClustNum !=vtx2.ClustNum
                        && EdgeWeight > p.getThresh())
                {
                   graph.addAction(vtx1, vtx2,p.getThresh());
                } 
            }
    }
    
    
    
    private static void assignClusters(BipartiteGraph_deprecated graph)
    {
        ArrayList<Vertex> vertices = new ArrayList(graph.getVertices());
        int maxClusterIdx = 0;
        for(int i=0;i<graph.getVertexCount();i++)
            if(maxClusterIdx < vertices.get(i).ClustNum)
                maxClusterIdx = vertices.get(i).ClustNum;
        
        //Assign the vertex
        ArrayList<Vertex>[] clusterArray = new ArrayList[maxClusterIdx+1];
        for(int i=0;i<=maxClusterIdx;i++)
            clusterArray[i] = new ArrayList<>();
        
        for(Vertex vtx: vertices)
            clusterArray[vtx.ClustNum].add(vtx);
        
        ArrayList<Cluster> clusters = new ArrayList();
        try{
        for(int i=0;i<=maxClusterIdx;i++)
            clusters.add(new Cluster(clusterArray[i]));
                }catch(IllegalArgumentException e){
            System.out.print("catch");
        }
        graph.setClusters(clusters);
    }
    /**
     * This method output the coordinates of all the points into the given file.
     * 
     * @param File
     * @param Points 
     * @throws IOException 
     */
    private void OutputPoints(String File,ArrayList<Vertex> VertexList) throws IOException
    {
        FileWriter fw = new FileWriter(File);
        BufferedWriter bw = new BufferedWriter(fw);
        for(Vertex pt:VertexList)
        {
            double[] coords = pt.Coords;
            for(int i=0;i<coords.length-1;i++)
            {
                bw.write(coords[i]+"\t");
            }
            bw.write(coords[coords.length-1]+"\n");
        }
        bw.flush();
        fw.close();
        bw.close();
    }
    
    /**
     * This method outputs the points with the assignments of the clusters
     * @param File
     * @param VertexList
     * @throws IOException 
     */
    public void OutputPointClusters(String File, ArrayList<Vertex> VertexList) throws IOException
    {
        FileWriter fw = new FileWriter(File);
        BufferedWriter bw = new BufferedWriter(fw);
        for(Vertex vtx:VertexList)
        {
            double[] coords = vtx.Coords;
            for(int i=0;i<coords.length;i++)
            {
                bw.write(coords[i]+"\t");
            }
            bw.write(vtx.ClustNum+"\n");
        }
        bw.flush();
        fw.close();
        bw.close();
    }
    
    /**
     * This class returns the cost of editing from a given set of points. Without constructing the Actions objects, this method
     * should return the cost much quicker
     * @param graph
     * @param p
     * @return 
     */
    public static double computeCost(BipartiteGraph_deprecated graph, Param p)
    {
        ArrayList<Vertex> VertexList = new ArrayList(graph.getVertices());
        double cost = 0;
        //first check if there is no unassigned vertex
        for(Vertex vtx:VertexList)
            if(vtx.ClustNum ==-1)
                throw new IllegalArgumentException(vtx.Value+" not assigned with any cluster");
        
        for(int i=0;i< VertexList.size();i++)
            for(int j=i+1;j<VertexList.size();j++)
            {
                Vertex vtx1 = VertexList.get(i);
                Vertex vtx2 = VertexList.get(j);
                if(vtx1.vtxSet == vtx2.vtxSet)
                    continue;
                double EdgeWeight = graph.getEdgeWeight(vtx1, vtx2);
                //if there is no edge between them, then continue;
                
                //case 1, in the same cluster with negative edge: then we need an addition
                if(vtx1.ClustNum ==vtx2.ClustNum
                        && EdgeWeight < p.getThresh())
                {
                   cost += p.getThresh() - EdgeWeight;
                }            
                //case 2, in different clusters but with a positive edge: we need a deletion
                if(vtx1.ClustNum !=vtx2.ClustNum
                        && EdgeWeight > p.getThresh())
                {
                   cost += EdgeWeight- p.getThresh();
                }           
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
    private static double computeMergeCost(BipartiteGraph_deprecated graph, Cluster c1, Cluster c2, Param p )
    {
        double CostDiff = 0;
        for(Vertex pt1: c1.getVertices())
            for(Vertex pt2:c2.getVertices())
            {
                double EdgeWeight = graph.getEdgeWeight(pt1, pt2);
                if(EdgeWeight == 0)
                    continue;
                if(EdgeWeight<=p.getThresh())
                {
                    CostDiff+= p.getThresh()-EdgeWeight;
                }
                else
                    CostDiff-= EdgeWeight-p.getThresh();
            }
        return CostDiff;
    }
    /**
     * This method returns the difference of the cost (a positive value means increase in cost, and a 
     * negative value means decrease in cost) if we move the given vertex to the cluster clt from its original
     * cluster.
     * @param Points
     * @param pt
     * @param clt
     * @return 
     */
    private static double computeMoveCost(BipartiteGraph_deprecated graph, Vertex toMove, Cluster SourceCls, Cluster TargetCls, Param p)
    {
        //check if the point toMove belongs to the cluster SourceCls
        if(toMove.ClustNum != SourceCls.getClustIdx())
        {
            System.out.println("ToMove "+ toMove.ClustNum);
            System.out.println("Sourcecls "+ SourceCls.getVertices().get(0).ClustNum);
            throw new IllegalArgumentException("The point to move does not belong to the source cluster");
        }
        double CostDiff = 0;
        for(Vertex vtx: SourceCls.getVertices())
        {
            double EdgeWeight = graph.getEdgeWeight(toMove, vtx);
            if(EdgeWeight == 0)
                continue;
            if(EdgeWeight <= p.getThresh())
                CostDiff -= p.getThresh()-EdgeWeight;
            else
                CostDiff += EdgeWeight-p.getThresh();
        }
        
        for(Vertex vtx: TargetCls.getVertices())
        {
            double EdgeWeight = graph.getEdgeWeight(toMove,vtx);
            if(EdgeWeight == 0)
                continue;
            if(EdgeWeight<= p.getThresh())
                CostDiff += p.getThresh()-EdgeWeight;
            else
                CostDiff -= EdgeWeight - p.getThresh();
        }
        return CostDiff;
    }
    
    
    
   
    /**
     * This method outputs the details of cluster assignment
     * @param OutputPath
     * @param VertexList
     * @throws IOException 
     */
    public void OutputClusterDetails(String OutputPath,ArrayList<Vertex> VertexList) throws IOException
    {
        FileWriter fw = new FileWriter(OutputPath);
        BufferedWriter bw = new BufferedWriter(fw);
        for(Vertex vtx:VertexList)
        {
            bw.write(vtx.Value+"\t"+vtx.ClustNum+"\n");
            bw.flush();
        }
        bw.close();
        fw.close();
    }
    
    
    public static void putDist(double[][] Distances, BipartiteGraph_deprecated graph,
            Vertex vtx1, Vertex vtx2, double dist)
    {
        Distances[vtx1.getIdx()+vtx1.vtxSet*graph.getSet1Size()]
                [vtx2.getIdx()+vtx2.vtxSet*graph.getSet1Size()] = dist;
        Distances[vtx2.getIdx()+vtx2.vtxSet*graph.getSet1Size()]
                [vtx1.getIdx()+vtx1.vtxSet*graph.getSet1Size()] = dist;           
    }
    
    
    public static double getDist( BipartiteGraph_deprecated graph,double[][] Distances, Vertex vtx1, Vertex vtx2)
    {
        return Distances[vtx1.getIdx()+vtx1.vtxSet*graph.getSet1Size()][vtx2.getIdx()+vtx2.vtxSet*graph.getSet1Size()];
    }
}




/**
 * This class is prepared for post-processing
 * @author Peng
 */

/*
class Cluster implements Comparable<Cluster>
{
    ArrayList<Vertex> Vertices; 
    public Cluster(ArrayList<Vertex> Vertices)
    {
        if(Vertices == null)
            throw new IllegalArgumentException("Parameter to constructor cannot be null");
        if(Vertices.isEmpty())
            throw new IllegalArgumentException("Parameter to constructor cannot be empty");
        this.Vertices = Vertices;
    }
    public int getSize()
    {
        return Vertices.size();
    }
    
    public int getClustIdx()
    {
        return Vertices.get(0).clustnum;
    }
    public ArrayList<Vertex> getVertices()
    {
        return Vertices;
    }
    public void setVertices(ArrayList<Vertex> Vertices)
    {
        this.Vertices = Vertices;
    }
    public void addVertex(Vertex vtx)
    {
        Vertices.add(vtx);
    }
    
    public boolean removeVertex(Vertex vtx)
    {
        return Vertices.remove(vtx);
    }
    
    public void setClustNum(int ClustNum)
    {
        if(ClustNum<0)
            throw new IllegalArgumentException("Cluster number cannot be smaller than 0");
        for(Vertex vtx:Vertices)
            vtx.clustnum = ClustNum;
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
        c.setClustNum(Vertices.get(0).clustnum);
        this.Vertices.addAll(c.Vertices);
    }
    
    public static void movePoint(Vertex vtx, Cluster Source, Cluster Target)
    {
        Source.removeVertex(vtx);
        Target.addVertex(vtx);
        vtx.clustnum = Target.getClustIdx();
    }
}
*/