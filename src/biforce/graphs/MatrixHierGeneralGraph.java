/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biforce.graphs;

import biforce.constants.BiForceConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.jdom2.*;
import org.jdom2.input.*;
import java.io.File;

/**
 * This graph is an upgraded version of MatrixHierNpartiteGraph, allowing edges within the 
 * same vertex subset.
 * @author penpen926
 */
public class MatrixHierGeneralGraph extends Graph2{
    protected double cost = 0; /* This stores the editing cost.*/
    protected int[] setSizes = null;
    /* Here since we have different sets of edges between different vertex sets,
     * we got to have more than one edge weights matrices.*/
    protected ArrayList<double[][]> intraEdgeWeights = null;
    protected ArrayList<double[][]> interEdgeWeights = null;
    /* This matrix stores the distances. */
    /* Distances, unlike edge weight, must be defined between two arbitrary vertices. It does not matter 
    whether the two vertices come from the same set or not, since later single-linkage and kmeans clustering
    use pairwise distances. */
    /* Note that the distance matrix is a LOWER TRIANGULAR MATRIX. */
    /* In MatrixHierNpartiteGraph, since we have distances between different vertices, we have 
    more than one distances.*/
    //ArrayList<double[][]> distances = null;
    /* 2nd version of distances, for test. */
    protected double[][] distMatrix = null;
    
    /**
     * Constructor.
     * @param filePath 
     */
    public MatrixHierGeneralGraph(String filePath, boolean isHeader, boolean isXmlFile){
        /* Init the vertices arrayList. */
        vertices = new ArrayList<>();
        /* Init the distance arrayList. */
        //distances = new ArrayList<>();
        /* Init the action arrayList. */
        actions = new ArrayList<>();
        /* If the input is in xml format. */
        if(isXmlFile)
            try{
                readGraphInXml(filePath);
            }catch(IOException e){
                System.out.println("(biforce.graphs.MatrixHierGeneralGraph.constructor) "
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
                return;
            }
        /* If the input file is with header.*/
        else if(isHeader)
            try{
                readGraphWithHeader(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            }
        
        else
           try{
                readGraph(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            } 
        /* 2nd version of the distance matrix. Now the first version of MatrixHierGeneraGraph */
        distMatrix = new double[vertices.size()][vertices.size()];
        for(int i=0;i<distMatrix.length;i++)
            for(int j=0;j<distMatrix[0].length;j++)
                distMatrix[i][j] = Double.NaN;
                
    }
    
    public MatrixHierGeneralGraph(String filePath, boolean isHeader, boolean isXmlFile,double thresh){
        /* Init the vertices arrayList. */
        vertices = new ArrayList<>();
        /* Init the distance arrayList. */
        //distances = new ArrayList<>();
        /* Init the action arrayList. */
        actions = new ArrayList<>();
       /* If the input is in xml format. */
        if(isXmlFile)
            try{
                readGraphInXml(filePath);
            }catch(IOException e){
                System.out.println("(biforce.graphs.MatrixHierGeneralGraph.constructor) "
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
                return;
            }
        /* If the input file is with header.*/
        else if(isHeader)
            try{
                readGraphWithHeader(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            }
        
        else
           try{
                readGraph(filePath);
            }catch(IOException e){
                System.out.println("(MatrixHierGeneralGraph.constructor)"
                        + " MatrixHierGeneralGraph readGraphWithHeader failed:"
                        + " "+filePath);
            } 
        setThreshold(thresh);
        detractThresh();
        
        /* 2nd version of the distance matrix. Now the first version of MatrixHierGeneralGraph.*/
        distMatrix = new double[vertices.size()][vertices.size()];
        for(int i=0;i<distMatrix.length;i++)
            for(int j=0;j<distMatrix[0].length;j++)
                distMatrix[i][j] = Double.NaN;
    }
    
    /**
     * This method performs breadth-first search in MatrixHierNpartiteGraph.
     * @param Vtx
     * @return 
     */
    @Override
    public MatrixHierGeneralSubgraph bfs(Vertex2 Vtx) {
        LinkedList<Vertex2> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex2> result = new ArrayList<>();

        for(Vertex2 vtx: getVertices()){
            marker.put(vtx.toString(), Boolean.FALSE);
        }
        //enqueue source and mark source
         queue.add(Vtx);
         result.add(Vtx);
         marker.put(Vtx.toString(),true);

         //while queue is not empty
         while(!queue.isEmpty()){
             //dequeue an item from queue into CurrentVtx
             Vertex2 CurrentVtx = queue.pollLast();
             //get the nei of CurrentVtx
             ArrayList<Vertex2> nei = neighbours(CurrentVtx);

             /* If no neighbour is found, then we continue. */
             if(nei == null)
                 continue;
             //for each neighbour
             for(Vertex2 vtx: nei){
                 if(!marker.get(vtx.toString())){
                     marker.put(vtx.toString(),true);
                     queue.add(vtx);
                     result.add(vtx);
                 }
             }

         }
         /* Create a new subkpartitegraph. */
         MatrixHierGeneralSubgraph sub = new MatrixHierGeneralSubgraph(result,this);
         return sub;
    }
    
    /**
     * This method returns all connected components.
     * @return 
     */
    @Override
    public ArrayList<MatrixHierGeneralSubgraph> connectedComponents() {
        ArrayList<MatrixHierGeneralSubgraph> connectecComps = new ArrayList<>();
        //create a indicator LinkedList of vertices, when a vertex is included in one of the subgraphs, then it is 
        //removed from the indicator LinkedList
        LinkedList<Vertex2> indicatorList = new LinkedList<>();
        //add all the vertex into the IndicatorArray
        for(Vertex2 vtx: getVertices()){
            indicatorList.add(vtx);
        }
        //While there is still unvisited vertex, we use it as the seed to search for subgraphs.
        while(!indicatorList.isEmpty()){
            Vertex2 Seed = indicatorList.pollFirst();
            MatrixHierGeneralSubgraph comp = bfs(Seed);
            connectecComps.add(comp);
            //remove all the vertex in the comp from indicatorList
            for(Vertex2 vtx: comp.getSubvertices()){
                indicatorList.remove(vtx);
            }
        }
        connectecComps.trimToSize();
        return connectecComps;
    }
    
    /**
     * This method detracts all edge weights in matrices.
     */
    @Override
    public final void detractThresh() {
        /* Detract the threshold from inter-edgeweights. */
        for(int i=0;i<interEdgeWeights.size();i++){
            double[][] weights = interEdgeWeights.get(i);
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] -=thresh;
        }
        /* Detract the threshold from the intra-edgeweights.*/
        for(int i=0;i<intraEdgeWeights.size();i++){
            double[][] weights = intraEdgeWeights.get(i);
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] -= thresh;
        }
        this.threshDetracted = true;
    }

    @Override
    public final void detractThresh(double thresh) {
        /* Check if the threshold has already detracted */
        if(threshDetracted)
            throw new IllegalArgumentException("(MatrixHierNpartiteGraph.detractThresh)"
                    + "  The threshold is already detracted.");
        else
            setThreshold(thresh);
        detractThresh();
    }
    
    /**
     * This method gets the distance between two vertices. 
     * @param vtx1
     * @param vtx2
     * @return 
     */
    @Override
    public double dist(Vertex2 vtx1, Vertex2 vtx2) {
        /* First check if the distances between the two vertices are defined. */
        /*
        if( vtx1.getVtxSet() - vtx2.getVtxSet() != 1 &&
                vtx1.getVtxSet() - vtx2.getVtxSet() != -1)
            return Double.NaN;
        int minVtxSet = Math.min(vtx1.getVtxSet(),vtx2.getVtxSet());
        if(minVtxSet == vtx1.getVtxSet())
            return distances.get(minVtxSet)[vtx1.getVtxIdx()][vtx2.getVtxSet()];
        else
            return distances.get(minVtxSet)[vtx2.getVtxIdx()][vtx1.getVtxSet()];
        */
        /* 2nd version of distances. */
        return distMatrix[vtx1.getDistIdx()][vtx2.getDistIdx()];
        
    }

    @Override
    public double edgeWeight(Vertex2 vtx1, Vertex2 vtx2) {
        /* Check if it is an intra-edge. */
        if(vtx1.getVtxSet() == vtx2.getVtxSet())
            /* First get the edge weight matrix and then get the right edge weight. */
            return intraEdgeWeights.get(vtx1.getVtxSet())
                    [vtx1.getVtxIdx()][vtx2.getVtxIdx()];
        /* Check if it  is an inter-edge.  */
        if(vtx1.getVtxSet() - vtx2.getVtxSet() == 1 ||
                vtx1.getVtxSet() - vtx2.getVtxSet() == -1){
            int minVtxLvl = Math.min(vtx1.getVtxSet(),vtx2.getVtxSet());
            if(minVtxLvl == vtx1.getVtxSet())
                return interEdgeWeights.get(minVtxLvl)[vtx1.getVtxIdx()][vtx2.getVtxIdx()];
            else
                return interEdgeWeights.get(minVtxLvl)[vtx2.getVtxIdx()][vtx1.getVtxIdx()];
        }
        /* If it is not an intra-edge nor is it an inter-edge, then we return NaN for cross edges. */
        return Double.NaN;
    }

    @Override
    public double edgeWeight(int vtxIdx1, int vtxIdx2) {
        throw new UnsupportedOperationException("This edgeWeight(int, int) is not supported in MatrixHierNpartiteGraph.");
    }
    
    /**
     * Return the edge weight matrix
     * 
     * @question return final ?
     * @param i
     * @return 
     */
    public double[][] interEdgeWeightMatrix(int i){
        /* First check the index. */
        if( i<0 || i>= interEdgeWeights.size()){
            throw new IllegalArgumentException("(MatrixHierGeneralGraph.interEdgeWeighMatrix) Index out of bound:  "+
                    i);
        }
        return interEdgeWeights.get(i);   
    }
    
    
    public double[][] intraEdgeWeightMatrix(int i){
        /* First check the index. */
        if( i<0 || i>= intraEdgeWeights.size()){
            throw new IllegalArgumentException("(MatrixHierGeneralGraph.intraEdgeWeighMatrix) Index out of bound:  "+
                    i);
        }
        return intraEdgeWeights.get(i);   
    }
    /**
     * This method computes the euclidean distances between two vertices.
     * @param vtx1
     * @param vtx2
     * @return 
     */
    private double euclidDist(Vertex2 vtx1,Vertex2 vtx2){
        if(vtx1.getCoords() == null ||
                vtx2.getCoords() == null)
            throw new IllegalArgumentException ("(MatrixHierNpartiteGraph.euclidDist) Null coordinates:  "+
                    "vtx1:  "+vtx1.getCoords().toString()+"  vtx2:  "+vtx2.getCoords().toString());
        //check if they have the same dimension
        if(vtx1.getCoords().length !=vtx2.getCoords().length )
            throw new IllegalArgumentException("Point 1 and Point 2 have different dimension");
        double Dist= 0;
        for(int i=0;i<vtx1.getCoords().length;i++){
            Dist+= (vtx1.getCoords()[i]-vtx2.getCoords()[i])*
                    (vtx1.getCoords()[i]-vtx2.getCoords()[i]);
        }
        Dist = Math.sqrt(Dist);
        return Dist;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean isActionTaken(int actIdx) {
        Vertex2 vtx1 = actions.get(actIdx).getVtx1();
        Vertex2 vtx2 = actions.get(actIdx).getVtx2();
        if(edgeWeight(vtx1,vtx2) == actions.get(actIdx).getOriginalWeight())
            return false;
        else return true;
    }

    @Override
    public ArrayList<Vertex2> neighbours(Vertex2 currentVtx) {
        /* Here we add a pre-condition: Check if the threshold is detracted. */
        if(!threshDetracted){
            System.out.println
                    ("(MatrixHierNpartiteGraph.neighbours) Threshold must be detracted first.");
            detractThresh();
        }
        ArrayList<Vertex2> neighbours = new ArrayList<>();
        for(Vertex2 vtx: vertices){
            /* If they are from the same set, then it's impossible there's an edge
             * between them.*/
            double ew  = edgeWeight(currentVtx, vtx);
            if(!Double.isNaN(ew) && ew>0)
                neighbours.add(vtx);       
        }
        //neighbours.trimToSize();
        /* If no neighbour is found, then return null. */
        if(neighbours.isEmpty())
            return null;
        return neighbours;
    }

    @Override
    public boolean pushAction(Vertex2 vtx1, Vertex2 vtx2) {
        /* First check if thresh is already detracted, if not we have 
         to first detract threshold. */
        if(!threshDetracted)
            detractThresh();
        
        double ew = edgeWeight(vtx1, vtx2);
        if(ew == 0)
            throw new IllegalArgumentException("There is a null-edge between vtx1 and vtx2");
        Action2 act = null;
        act = new Action2(vtx1,vtx2,ew);
        actions.add(act);
        if(act.getOriginalWeight() > 0){
            cost += act.getOriginalWeight();
        }
        else{
            cost -= act.getOriginalWeight();
        }
        return true;
    }
    
    /**
     * This method pushes a vertex into the graph.
     * @param vtx
     * @return 
     */
    private int pushVertex(String value, int vertexSet){
        Vertex2 vtx = new Vertex2(value,vertexSet,-1);
        //U. Note: we should add a new meothod, searching a vertex in a given
        //vertex list (arrayList or Linkedlist). This method should be a static
        //method
        int idx = vertices.indexOf(vtx);
        if(idx == -1){
            try{
            /* 2nd version of distances. */
            vtx.setDistIdx(vertices.size());
            vertices.add(vtx);
            //set the index of the new vertex according to its vertex set
            vtx.setVtxIdx(setSizes[vertexSet]);
            setSizes[vertexSet]++;
            }catch(IndexOutOfBoundsException e){
                System.out.println("(MatrixHierNpartiteGraph.pushVertex) setSizes: Index out of bounds exception: "+vertexSet+"  "+setSizes.length);
                return -1;
            }
            return vtx.getVtxIdx();
        }
        else
            return ((ArrayList<Vertex2>)vertices).get(idx).getVtxIdx();
        
    }

    /**
     * Read the graph from the input file.
     * @param filePath
     * @throws IOException 
     */
    @Override
    public final void readGraph(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        /* Reader the file for the first time and gather the information
         * about the number of sets, the number of vertices.*/
        int maxSetIdx = 0;
        while((line = br.readLine())!= null){
            String[] splits = line.split("\\s+");
            /* If the splits has the length of 2, then we have only one node in the line. */
            if(splits.length == 2){
                /* Get the max size index.*/
                try{
                int setIdx = Integer.parseInt(splits[1]);
                if(maxSetIdx < setIdx)
                    maxSetIdx = setIdx;
                }catch(NumberFormatException e){
                    System.out.println("(MatrixHierNpartiteGraph.readGraph) Number format information: "+
                            line);
                    return;
                }
            }
            /* If the splits has the length of 4, then we have two nodes in the line. */
            else if(splits.length ==5 ){
                try{
                int setIdx1 = Integer.parseInt(splits[1]);
                int setIdx2 = Integer.parseInt(splits[3]);
                if(maxSetIdx <setIdx1)
                    maxSetIdx = setIdx1;
                if(maxSetIdx <setIdx2)
                    maxSetIdx = setIdx2;
                }catch(NumberFormatException e){
                    System.out.println("(MatrixHierNpartiteGraph.readGraph) Number format information: "+
                            line);
                    return;
                }
            }
            /* Else, there must be an error. */
            else{
                System.out.println("(MatrixHierNpartiteGraph.readGraph) Line with wrong tokens: "+
                        line);
                return;
            }
            
        }
        /* Init setSizes. */
        setSizes = new int[maxSetIdx+1];
        br.close();
        fr.close();
        
        /* Read the file for the second time, get the sizes of all vertex sets,
         to init the edgeWeight matrix, but not to assign any values in setSizes.
         The values in setSizes are left for pushVertex() to init.*/
        int[] sizes = new int[maxSetIdx+1];
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        line = null;
        /* We use hashsets to get the numbers of vertices. */
        ArrayList<HashSet<String>> vertexHashSets = new ArrayList<>();
        /* Init all hashsets in vertexHashSets. */
        for(int i=0;i<maxSetIdx+1;i++)
            vertexHashSets.add(new HashSet<String>());
        
        while((line = br.readLine())!= null){
            String[] splits = line.split("\\s+");
            /* If there are two tokens in the line, then we have one vertex in the line. */
            if(splits.length == 2)
                vertexHashSets.get(Integer.parseInt(splits[1]))
                        .add(String.copyValueOf(splits[0].toCharArray()));
            else if(splits.length ==5){
                vertexHashSets.get(Integer.parseInt(splits[1]))
                        .add(String.copyValueOf(splits[0].toCharArray()));;
                vertexHashSets.get(Integer.parseInt(splits[3]))
                        .add(String.copyValueOf(splits[2].toCharArray()));;
            }
        }
        
        /* Then we give values to sizes. */
        for(int i=0;i<maxSetIdx+1;i++)
            sizes[i] = vertexHashSets.get(i).size();
        
        /* Init thte edgeWeights matrix. */
        interEdgeWeights = new ArrayList<>();
        intraEdgeWeights = new ArrayList<>();
        /* Init the values for interEdgeWeights. */
        for(int i=0;i<maxSetIdx;i++){
            double[][] weights = new double[sizes[i]][sizes[i+1]];
            /* Init the values. */
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] = Double.NaN;
            interEdgeWeights.add(weights);
        }
        /* Init the values for intraEdgeWeights. */
        for(int i=0;i<=maxSetIdx;i++){
            double[][] weights = new double[sizes[i]][sizes[i]];
            /* Init the values. */
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] = Double.NaN;
            intraEdgeWeights.add(weights);
        }
        br.close();
        fr.close();
        
        /* Read the file for the third time, assign the edge weights. */
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        while((line = br.readLine())!= null){
            String[] splits = line.split("\\s+");
            /* If we have only one vertex in the line, we just push it into vertices arrayList. */
            if(splits.length ==2){
                String vtxName = String.copyValueOf(splits[0].toCharArray());
                int vtxLvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtxLvl = Integer.parseInt(String.copyValueOf(splits[1].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(MatrixHierGeneralGraph.readGraph) Invalid format for vertex set index:  "+splits[1]);
                    System.exit(0);
                }
                //Push vertex
                pushVertex(vtxName,vtxLvl);
            }
            /* If not, then we have two vertices in the line, we push them into vertices and assign
             * the edge weight between them.*/
            else{
                String vtx1Name = String.copyValueOf(splits[0].toCharArray());
                //int Vtx1Lvl = Integer.parseInt(String.copyValueOf(splits2[1].toCharArray()));
                int vtx1Lvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtx1Lvl = Integer.parseInt(String.copyValueOf(splits[1].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(MatrixHierGeneralGraph.readGraph) Invalid format for vertex set index:  "+splits[0]);
                    System.exit(0);
                }
                
                String vtx2Name = String.copyValueOf(splits[2].toCharArray());
                //int Vtx2Lvl = Integer.parseInt(String.copyValueOf(splits2[3].toCharArray()));
                int vtx2Lvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtx2Lvl = Integer.parseInt(String.copyValueOf(splits[3].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(MatrixHierGeneralGraph.readGraph) Invalid format for vertex set index:  "+splits[3]);
                    System.exit(0);
                }
                /* Check if it is a inter-edge. */
                if(vtx1Lvl - vtx2Lvl == 1 || vtx1Lvl - vtx2Lvl == -1){
                    /* The vertices are pushed into the graph and the edge weight is read. */
                    int idx1 = pushVertex(vtx1Name,vtx1Lvl);
                    int idx2 = pushVertex(vtx2Name,vtx2Lvl);
                    double ew = Double.parseDouble(String.copyValueOf(splits[4].toCharArray()));

                    //U. note: here we need a method to assign inter-edgeweight.
                    int minVtxLvl = Math.min(vtx1Lvl, vtx2Lvl);
                    if(vtx1Lvl == minVtxLvl)
                        interEdgeWeights.get(minVtxLvl)[idx1][idx2] = ew;
                    else
                        interEdgeWeights.get(minVtxLvl)[idx2][idx1] = ew;
                }
                /* Check if it is a intra-edge. */
                else if(vtx1Lvl== vtx2Lvl){
                    /* The two vertices are pushed into the graph and the edge weight is read.*/
                    int idx1 = pushVertex(vtx1Name, vtx1Lvl);
                    int idx2 = pushVertex(vtx2Name, vtx2Lvl);
                    double ew = Double.parseDouble(String.copyValueOf(splits[4].toCharArray()));
                    /*Assign the intra-edgeweight. */
                    intraEdgeWeights.get(vtx1Lvl)[idx1][idx2] = ew;
                    intraEdgeWeights.get(vtx1Lvl)[idx2][idx1] = ew;
                }
                /* Else, it must be a cross-level edge, then we throw out an exception .*/
                else
                    throw new IllegalArgumentException("(MatrixHierGeneralGraph.readGraph) Cross level edge: "+line);
            }
        }
        br.close();
        fr.close();
        /* Init the cluster object. */
        clusters = new ArrayList<>();
    }

    /**
     * The rule for header:
     * NumberOfSets Set1Size Set2Size ... SetNSize
     * @param filePath
     * @throws IOException 
     */
    public final void readGraphWithHeader(String filePath) throws IOException{
        FileReader fr = new FileReader(filePath);
        BufferedReader br =new BufferedReader(fr);
        /* Read the header, assign setSizes */
        String line = null;
        line = br.readLine();
        String[] headerSplits = line.split("\\s+");
        /* Create the dummy set sizes just to create edgeWeights, because of method pushVertex().*/
        int[] sizes = null;
        try{
        sizes = new int[Integer.parseInt(headerSplits[0])];
        for(int i=0;i<headerSplits.length-1;i++){
            sizes[i] = Integer.parseInt(headerSplits[i+1]);
        }
        }catch(NumberFormatException e){
            System.out.println("(MatrixHierGeneralGraph.readGraphWithHeader) Header number format problem: "+line);
            System.exit(0);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("((MatrixHierGeneralGraph.readGraphWithHeader) Number of sets and the given sizes do not match: "+line);
            System.exit(0);
        }
        /* However, here we must int setSizes, set all elements to 0. */
        setSizes = new int[Integer.parseInt(headerSplits[0])];
        for(int i=0;i<setSizes.length;i++)
            setSizes[i] = 0;
        /* Init the interEdgeWeight matrix. */
        interEdgeWeights = new ArrayList<>();
        intraEdgeWeights = new ArrayList<>(); 
        for(int i=0;i<setSizes.length-1;i++){
            double[][] weights = new double[sizes[i]][sizes[i+1]];
            /* Init the values. */
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] = Double.NaN;
            interEdgeWeights.add(weights);
        }
        /* Init the intraEdgeWegith matrix. */
        for(int i=0;i<setSizes.length;i++){
            double[][] weights = new double[sizes[i]][sizes[i]];
            for(int j=0;j<weights.length;j++)
                for(int k=0;k<weights[0].length;k++)
                    weights[j][k] = Double.NaN;
            
            intraEdgeWeights.add(weights);
        }
        
        /* Read the graph.*/
        while((line = br.readLine())!= null){
            String[] splits = line.split("\\s+");
            /* If we have only one vertex in the line, we just push it into vertices arrayList. */
            if(splits.length ==2){
                String vtxName = String.copyValueOf(splits[0].toCharArray());
                int vtxLvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtxLvl = Integer.parseInt(String.copyValueOf(splits[1].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(readGraph) Invalid format for vertex set index:  "+splits[1]);
                    System.exit(0);
                }
                //Push vertex
                pushVertex(vtxName,vtxLvl);
            }
            /* If not, then we have two vertices in the line, we push them into vertices and assign
             * the edge weight between them.*/
            else{
                String vtx1Name = String.copyValueOf(splits[0].toCharArray());
                //int Vtx1Lvl = Integer.parseInt(String.copyValueOf(splits2[1].toCharArray()));
                int vtx1Lvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtx1Lvl = Integer.parseInt(String.copyValueOf(splits[1].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(readGraph) Invalid format for vertex set index:  "+splits[0]);
                    System.exit(0);
                }
                
                String vtx2Name = String.copyValueOf(splits[2].toCharArray());
                //int Vtx2Lvl = Integer.parseInt(String.copyValueOf(splits2[3].toCharArray()));
                int vtx2Lvl = -1;
                /* Catch the NumberFormatException. */
                try{
                    vtx2Lvl = Integer.parseInt(String.copyValueOf(splits[3].toCharArray()));
                }catch(NumberFormatException e){
                    System.out.println("(readGraph) Invalid format for vertex set index:  "+splits[3]);
                    System.exit(0);
                }

                /* Check if it is a inter-edge. */
                if(vtx1Lvl - vtx2Lvl == 1 || vtx1Lvl - vtx2Lvl == -1){
                    /* The vertices are pushed into the graph and the edge weight is read. */
                    int idx1 = pushVertex(vtx1Name,vtx1Lvl);
                    int idx2 = pushVertex(vtx2Name,vtx2Lvl);
                    double ew = Double.parseDouble(String.copyValueOf(splits[4].toCharArray()));

                    //U. note: here we need a method to assign inter-edgeweight.
                    int minVtxLvl = Math.min(vtx1Lvl, vtx2Lvl);
                    if(vtx1Lvl == minVtxLvl)
                        interEdgeWeights.get(minVtxLvl)[idx1][idx2] = ew;
                    else
                        interEdgeWeights.get(minVtxLvl)[idx2][idx1] = ew;
                }
                /* Check if it is a intra-edge. */
                else if(vtx1Lvl== vtx2Lvl){
                    /* The two vertices are pushed into the graph and the edge weight is read.*/
                    int idx1 = pushVertex(vtx1Name, vtx1Lvl);
                    int idx2 = pushVertex(vtx2Name, vtx2Lvl);
                    double ew = Double.parseDouble(String.copyValueOf(splits[4].toCharArray()));
                    /*Assign the intra-edgeweight. */
                    intraEdgeWeights.get(vtx1Lvl)[idx1][idx2] = ew;
                    intraEdgeWeights.get(vtx1Lvl)[idx2][idx1] = ew;
                }
                /* Else, it must be a cross-level edge, then we throw out an exception .*/
                else
                    throw new IllegalArgumentException("(MatrixHierGeneralGraph.readGraph) Cross level edge: "+line);
            }
        }
        br.close();
        fr.close();
        /* Init the cluster object. */
        clusters = new ArrayList<>();
    }
    
    /**
     * This method reads graph from the "entity-matrix" style input.
     * @throws IOException 
     * @param filePath The input file. 
     * untested.
     */
    public final void readGraphInXml(String filePath) throws IOException{
        /* Read the input file. */
        SAXBuilder builder = new SAXBuilder();
        Document graphInput = null;
        try{
            graphInput = builder.build(new File(filePath));
        }catch(JDOMException e){
            System.out.println("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) Document init error:  "+filePath);
            return;
        }
        /* Get the root element. */
        Element docRoot, entity;
        try{
            docRoot = graphInput.getRootElement();
        }catch(IllegalStateException e){
            System.out.println("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The input document does not have a root element.");
            e.printStackTrace();
            return;
        }
        /* 1. Anaylze the element "entity", get the name of the nodes.*/
        /* Get the child element of "entity", throw an IllegalStateException.*/
        entity = docRoot.getChild("entity");
        if(entity == null) /* If no such "entity" child element is found, then throw the exception.*/
            throw new IllegalStateException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The input document does not have an \"entity\" child element.");
        
        /* Get how many levels are there in this graph.*/
        String levelStr = entity.getAttributeValue("levels"); /* Get how many sets are there in the graph. */
        /* levelStr is required. */
        if(levelStr == null){
            /* If no "levels" attribute is defined, then an exception is thrown. */
            throw new IllegalArgumentException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) Attribute \"levels\" must be given.");
        }
        try{
            setSizes = new int[Integer.parseInt(levelStr)];/* Init setSizes. */
        }catch(NumberFormatException e){
            throw new NumberFormatException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) Attribute \"levels\" number format error:  "+levelStr);
        }
        /* Check if there is an external file for the node names.*/
        String hasExternEntityFile = entity.getAttributeValue("externalFile");
        /* Get the content in entity. */
        String entityContent = entity.getContent(0).getValue().trim();
        /* Check entityContent. It cannot be null.*/
        if(entityContent == null)
            throw new IllegalStateException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The content of the element entity is null.");
        /* If there is an external file for the nodes' names, then the content of the element should be the path
        of the external file. Otherwise, it should be the nodes' names themselves. */
        if(hasExternEntityFile == null || hasExternEntityFile.equalsIgnoreCase("false")){
            XmlInputParser parser = new XmlInputParser();
            parser.parseEntityString(entityContent,this);
        }
        /* If the node names are stored in an external file.*/
        else if(hasExternEntityFile.equalsIgnoreCase("true")){
            XmlInputParser parser = new XmlInputParser();
            parser.parseEntityFile(entityContent,this);
        }
        else 
            throw new IllegalStateException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) Illegal attribute of \"externalFile\": "+hasExternEntityFile);
        /* Init all matrices. */
        /* We have setSizes.length intraEdgeWeightMatrix. */
        intraEdgeWeights = new ArrayList<> ();
        for(int i=0;i<setSizes.length;i++){
            double[][] intraMatrix = new double[setSizes[i]][setSizes[i]]; 
            /* Give matrix the init values. */
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i];k++)
                    intraMatrix[j][k] = Double.NaN;
            /* Push this intraMatrix to intraEdgeWeights. */
            intraEdgeWeights.add(intraMatrix);
        }
        /* Init the inter matrices. */
        interEdgeWeights = new ArrayList<>();
        for(int i=0;i<setSizes.length-1;i++){
            double[][] interMatrix = new double[setSizes[i]][setSizes[i+1]];
            /* Give matrix the init values. */
            for(int j=0;j<setSizes[i];j++)
                for(int k=0;k<setSizes[i+1];k++)
                    interMatrix[j][k] = Double.NaN;
            /* Push this interMatrix into interEdgeWeights. */
            interEdgeWeights.add(interMatrix);
        }
        /* Read the edge weights from the xml input file. */
        ArrayList<Element> matrixElementList = new ArrayList<>(docRoot.getChildren("matrix"));
        /* First check the number of elements in matrixElementList, if not equal to 2*setSizes.length-1. */
        if(matrixElementList.size() != 2*setSizes.length-1)
            throw new IllegalArgumentException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The number of matrices is wrong:  "+matrixElementList.size());
        
        /* 2. Assign the edge weights. */
        for(Element matrix: matrixElementList){
            /* First check where is this matrix. */
            String matrixLevel = matrix.getAttributeValue("matrixLevel");
            if(matrixLevel == null) /* The matrixLevel attribute is required. */
                throw new IllegalArgumentException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The matrixLevel attribute is required.");
            String[] matrixLevelSplits = matrixLevel.split("\\s+");
            /* If string matrixLevel cannot be splitted into two splits, then it must be wrong. */
            if(matrixLevelSplits.length !=2)
                throw new IllegalArgumentException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The attribute matrixLevel error:  "+matrixLevel);
            /* Get the two levels. */
            int matrixLevel1=-1,matrixLevel2=-1;
            try{
                matrixLevel1= Integer.parseInt(String.copyValueOf(matrixLevelSplits[0].toCharArray()));
                matrixLevel2= Integer.parseInt(String.copyValueOf(matrixLevelSplits[1].toCharArray()));
            }catch(NumberFormatException e){
                throw new IllegalArgumentException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) The attribute matrixLevel error:  "+matrixLevel);
            }
            /* Check if the matrix is a intra- or inter edge weight matrix. */
            if(matrixLevel1 == matrixLevel2){ /* Intra matrix. */
                /* Read the content. */
                String matrixContent = matrix.getContent(0).getValue().trim();
                /* Check if the matrix is stored in an external file.*/
                String hasExternMatrixFile = matrix.getAttributeValue("externalFile");
                if(hasExternMatrixFile == null || hasExternMatrixFile.equalsIgnoreCase("false")){
                    XmlInputParser parser = new XmlInputParser();
                    parser.parseIntraMatrixString(matrixContent, matrixLevel1, this);
                }
                /* If the node names are stored in an external file.*/
                else if(hasExternMatrixFile.equalsIgnoreCase("true")){
                    XmlInputParser parser = new XmlInputParser();
                    parser.parseIntraMatrixFile(matrixContent,matrixLevel1,this);
                }
                else
                    throw new IllegalStateException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml) Illegal attribute of \"externalFile\": "+hasExternMatrixFile);
            }
            else{ 
                /* For inter-matrix. */
                String matrixContent = matrix.getContent(0).getValue().trim();
                /* Check if the matrix is stored in an external file.*/
                String hasExternMatrixFile=matrix.getAttributeValue("externalFile");
                if(hasExternMatrixFile == null|| hasExternMatrixFile.equalsIgnoreCase("false")){
                    XmlInputParser parser = new XmlInputParser();
                    parser.parseInterMatrixString(matrixContent, matrixLevel1, matrixLevel2, this);
                }
                else if(hasExternMatrixFile.equalsIgnoreCase("true")){
                    XmlInputParser parser = new XmlInputParser();
                    parser.parseInterMatrixFile(matrixContent, matrixLevel1, matrixLevel2, this);
                }
                else
                    throw new IllegalStateException("(biforce.graphs.MatrixHierGeneralGraph.readGraphInXml))Illegal attribute of \"externalFile\": "+hasExternMatrixFile);
                        
            }
        }
        clusters = new ArrayList<>();
    }
    
    /**
     * This method re-add the threshold to edge weights, provided the threshold has been
     * detracted before.
     */
    @Override
    public void restoreThresh() {
        /* First check if the thresh is detracted, if not then throw an exception. */
        if(!threshDetracted)
            throw new IllegalArgumentException("(MatrixBipartiteGraph2.restoreThresh)"
                    + "  The threshold is not yet detracted.");
        /* Second check if the threshold has been set. */
        if(thresh == Double.MAX_VALUE)
            throw new IllegalArgumentException("(MatrixBipartiteGraph2.detractThresh)"
                    + "  The threshold must be set at first.");
        /* Restore the edge weights. */
        else{
            for(int i=0;i<interEdgeWeights.size();i++){
                double[][] weights = interEdgeWeights.get(i);
                for(int j=0;j<weights.length;j++)
                    for(int k=0;k<weights[0].length;k++)
                        weights[j][k] +=thresh;
            }
            for(int i=0;i<intraEdgeWeights.size();i++){
                double[][] weights = intraEdgeWeights.get(i);
                for(int j=0;j<weights.length;j++)
                    for(int k=0;k<weights[0].length;k++)
                        weights[j][k] += thresh;
            }
        }
        threshDetracted = false;
    }

    /**
     * This method removes the action with the given index from the arraylist actions.
     * @param Index
     * @return 
     */
    @Override
    public boolean removeAction(int Index) {
        try{
            actions.remove(Index);
        }catch(IndexOutOfBoundsException e){
            System.out.println("(MatrixHierNpartiteGraph.removeAction) Index out of bounds: "+
                   Index+"  size: "+actions.size());
            return false;
        }
        return true;
    }
    
    /**
     * This method sets the distance between two given vertices. 
     * Apparently, the two vertices must be in the "neighbouring" vertex sets.
     * @param vtx1
     * @param vtx2
     * @param dist 
     */
    public void setDist(Vertex2 vtx1, Vertex2 vtx2){
        /* For the 2nd version of distance, the first version of MatixHierGeneralGraph. */
        distMatrix[vtx1.getDistIdx()][vtx2.getDistIdx()] = euclidDist(vtx1,vtx2);
        distMatrix[vtx2.getDistIdx()][vtx2.getDistIdx()] = euclidDist(vtx2,vtx1);
    }

    @Override
    public void setEdgeWeight(Vertex2 vtx1, Vertex2 vtx2, double edgeWeight) {
        /* Check if it is an inter-edgeweight. */
        if(vtx1.getVtxSet() - vtx2.getVtxSet() == 1 || 
                vtx1.getVtxSet() - vtx2.getVtxSet() == -1){
            int minVtxSet = Math.min(vtx1.getVtxSet(),vtx2.getVtxSet());
            if(minVtxSet == vtx1.getVtxSet())
                interEdgeWeights.get(minVtxSet)
                        [vtx1.getVtxIdx()][vtx2.getVtxIdx()]= edgeWeight;
            else
                interEdgeWeights.get(minVtxSet)
                        [vtx2.getVtxIdx()][vtx1.getVtxIdx()]= edgeWeight;
        }
        /* Check if it is an intra-edgeweight. */
        if(vtx1.getVtxSet() == vtx2.getVtxSet())
            intraEdgeWeights.get(vtx1.getVtxSet())[vtx1.getVtxIdx()][vtx2.getVtxIdx()]= edgeWeight;
    }

    @Override
    public boolean takeAction(int idx) {
        /* First the check the index. */
        if(idx <0 || idx >= actions.size())
            throw new IllegalArgumentException("(MatrixBipartitGraph2.takeAction) Wrong action index: "+
                    idx);
        Action2 act = actions.get(idx);
        if(act.getOriginalWeight()>0)
            setEdgeWeight(act.getVtx1(),act.getVtx2(),BiForceConstants.FORBIDDEN);
        else
            setEdgeWeight(act.getVtx1(),act.getVtx2(),BiForceConstants.PERMENANT);
        return true;
    }

    @Override
    public boolean takeActions() {
        boolean flag = true;
        for(int i=0;i<actions.size();i++){
            if(!takeAction(i))
                flag = false;
        }
        return flag;
    }

    /**
     * This method updates/re-sets all pairwise distances.
     */
    @Override
    public void updateDist() {
        for(int i=0;i<vertices.size();i++)
            for(int j=0;j<vertices.size();j++)
                setDist(vertices.get(i),vertices.get(j));
                
    }

    /**
     * This method updates the positions of the vertices given the displacement 
     * vectors for all vertices.
     * @param dispVector 
     */
    @Override
    public void updatePos(double[][] dispVector) {
        /* For each vertex, update the position. */
        for(int i=0;i<vertexCount();i++){
            /* Compute the new coordinates. */
            double[] coords = vertices.get(i).getCoords();
            for(int j=0;j<coords.length;j++){
                coords[j] += dispVector[i][j];
            }
            /* Set the coordinates. */
            vertices.get(i).setCoords(coords);
        }
    }

    /**
     * This method returns the number of vertex sets.
     * @return 
     */
    @Override
    public int vertexSetCount() {
        return setSizes.length;
    }

    @Override
    public void writeGraphTo(String FilePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeClusterTo(String FilePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeResultInfoTo(String FilePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    
}
