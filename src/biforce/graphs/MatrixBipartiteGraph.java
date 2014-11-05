/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;

import java.util.ArrayList;
import biforce.constants.BiForceConstants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;


/**
 * This class is designed for bipartite graph based on matrix data structure.
 * This class implements Graph and extends ClustEdit in a preliminary way.
 * @author Peng Sun
 */
public class MatrixBipartiteGraph extends ClustEdit implements Graph {
    //This variable vertices stores the vertices in a graph in the form of ArrayList<Vertex>
    ArrayList<Vertex> vertices = null;
    //This variable stores the cost of the cluster editing, with initial value of 0
    double cost=0;
    //These two variable store the sizes of two sets.
    int set0Size, set1Size;
    //This 2-dimensional array stores the adjacency matrix
    double[][] edgeWeights = null;
    
    /** Below are the methods implementing methods in Graph**/
    
    /**
     * This method initializes the MatrixBipartiteGraph object given the path of input file.
     * @param InputFile
     * @throws IOException 
     */
    public MatrixBipartiteGraph(String InputFile) throws IOException{
        //Init the vertices and the actions
        vertices = new ArrayList<>();
        Actions = new ArrayList<>();
        //Read the input file for the first time, to init the 
        //EdgeWeight matrix
        FileReader fr = new FileReader(InputFile);
        BufferedReader br= new BufferedReader(fr);
        String line;
        //no header in the file
        
        //Size1, Size2 are two variables just to init EdgeWeight matrix.
        //When the input file is read for the first time, we do not create any vertex or add any vertex into
        //arraylist vertices.
        int Size1 = 0;
        int Size2 = 0;
        
        // Here, we used 2 hashset to count how many vertices we have
        HashSet<String> Set1 = new HashSet<>();
        HashSet<String> Set2 = new HashSet<>();
        
        while((line = br.readLine())!= null)
        {
            String[] split  = line.split("\t");
            //if there is only vertex, no other link
            if(split.length ==2)
            {
                
                if(Integer.parseInt(split[1]) == 0 )
                    Set1.add(String.copyValueOf(split[0].toCharArray()));
                else if(Integer.parseInt(split[1]) == 1)
                    Set2.add(String.copyValueOf(split[0].toCharArray()));
                
            }
            else{
                if(Integer.parseInt(split[1]) == 0)
                    Set1.add(String.copyValueOf(split[0].toCharArray()));
                else if(Integer.parseInt(split[1]) == 1)
                    Set2.add(String.copyValueOf(split[0].toCharArray()));

                if(Integer.parseInt(split[3]) == 0)
                    Set1.add(String.copyValueOf(split[2].toCharArray()));
                else if(Integer.parseInt(split[3]) == 1)
                    Set2.add(String.copyValueOf(split[2].toCharArray()));
            }
        }
        br.close();
        fr.close();
        
        Size1 = Set1.size();
        Size2 = Set2.size();
        Set1 = null;
        Set2 = null;
        //U. note: in the new version, we are to implement an npartitegraph based on matrix.
        //Edges between two arbitrarily different vertex sets are permitted.
        //Thus, if we have n vertex sets, we have n(n-1)/2 edge weight matrices
        edgeWeights = new double[Size1][Size2];
        //re-read the file, create vertices and init edge weights.
        fr = new FileReader(InputFile);
        br = new BufferedReader(fr);
        while((line = br.readLine())!= null)
        {
            String[] split = line.split("\t");
            //if there is only vertex, no other link
            if(split.length ==2)
            {
            }
            else{
                String Vtx1Name = String.copyValueOf(split[0].toCharArray());
                //int Vtx1Lvl = Integer.parseInt(String.copyValueOf(split[1].toCharArray()));
                int Vtx1Lvl = Integer.parseInt(String.copyValueOf(split[1].toCharArray()));
                String Vtx2Name = String.copyValueOf(split[2].toCharArray());
                //int Vtx2Lvl = Integer.parseInt(String.copyValueOf(split[3].toCharArray()));
                int Vtx2Lvl = Integer.parseInt(String.copyValueOf(split[3].toCharArray()));

                //U. note: here it's not proper to use addVertex() just to search for an existing vertex.
                //Thus, we should add a new method findVertex(). maybe a static method in Class Vertex, or a normal
                //method in the new NpartiteGraph class
                int Idx1 = pushVertex(Vtx1Name,Vtx1Lvl);
                int Idx2 = pushVertex(Vtx2Name,Vtx2Lvl);
                double ew = Double.parseDouble(String.copyValueOf(split[4].toCharArray()));
                
                //U. note: here we need a method to assign edgeweight.
                if(Vtx1Lvl == 0 && Vtx2Lvl ==1)
                    edgeWeights[Idx1][Idx2] = ew;
                else if(Vtx1Lvl ==1 && Vtx2Lvl ==0)
                    edgeWeights[Idx2][Idx1] = ew;
                else throw new IllegalArgumentException("Vertex lvl can only be 0 or 1");
            }
        }
        br.close();
        fr.close();
        //Init the clusters.
        Clusters = new ArrayList<>();
    }

    /**
     * This method initializes the MatrixBipartiteGraph object with the edge weight matrix, 
     * arraylist of row names and arraylist of column names. The vertices are named after 
     * row names and column names.
     * @param EdgeWeights
     * @param RowNames
     * @param ColNames 
     */
    public MatrixBipartiteGraph(double[][] EdgeWeights, ArrayList<String> RowNames, ArrayList<String> ColNames){
        //first assign the EdgeWeights in this class to the given EdgeWeights
        this.edgeWeights = EdgeWeights;
        //then create vertices for rows
        for(int i=0;i<EdgeWeights.length;i++)
        {
            vertices.add(new Vertex(RowNames.get(i),0,i));
        }
        
        //create vertices for columns
        for(int i=0;i<EdgeWeights[0].length;i++)
        {
            vertices.add(new Vertex(ColNames.get(i),1,i));
        }
        
        set0Size = EdgeWeights.length;
        set1Size = EdgeWeights[0].length;
        Actions= new ArrayList();
        Clusters = new ArrayList();
    }
    
    /**
     * This method initializes the MatrixBipartiteGraph object with the edge weight matrix.
     * The vertices in row are named as "Rxx" and the vertices in column are named
     * as "Cxx".
     * @param EdgeWeights 
     */
    public MatrixBipartiteGraph(double[][] EdgeWeights){
        //init vertices
        vertices = new ArrayList();
        Actions = new ArrayList();
        //first assign the EdgeWeights in this class to the given EdgeWeights
        this.edgeWeights = EdgeWeights;
        //then create vertices for rows
        for(int i=0;i<EdgeWeights.length;i++)
        {
            vertices.add(new Vertex("R"+(i+1),0,i));
        }
        
        //create vertices for columns
        for(int i=0;i<EdgeWeights[0].length;i++)
        {
            vertices.add(new Vertex("C"+(i+1),1,i));
        }
        
        set0Size = EdgeWeights.length;
        set1Size = EdgeWeights[0].length;
        Actions = new ArrayList();
        Clusters = new ArrayList();
    }
    
    
    
    /**
     * This method returns all connected components of a given graph.
     * @return All connected components are returned as a collection of subgraphs.
     */
    @Override
    public ArrayList<MatrixBipartiteSubgraph> getAllConnectedComponents(){
        ArrayList<MatrixBipartiteSubgraph> AllConnectedComponents = new ArrayList<>();
        //create a indicator LinkedList of vertices, when a vertex is included in one of the subgraphs, then it is 
        //removed from the indicator LinkedList
        LinkedList<Vertex> IndicatorList = new LinkedList<>();
        //add all the vertex into the IndicatorArray
        for(Vertex vtx: getVertices())
        {
            IndicatorList.add(vtx);
        }
        //While there is still unvisited vertex, we use it as the seed to search for subgraphs.
        while(!IndicatorList.isEmpty())
        {
            Vertex Seed = IndicatorList.pollFirst();
            MatrixBipartiteSubgraph ConnectedComponent = runBFS(Seed);
            AllConnectedComponents.add(ConnectedComponent);
            //remove all the vertex in the ConnectedComponent from IndicatorList
            for(Vertex vtx: ConnectedComponent.getSubVertices())
            {
                IndicatorList.remove(vtx);
            }
        }
        AllConnectedComponents.trimToSize();
        return AllConnectedComponents;
    }
    

    /**
     * This method returns the edge weight between two given vertices.
     * @param Vtx1
     * @param Vtx2
     * The two vertices between the edge weight is to be retrieved.
     * @return 
     * The edge weight returned.
     *  
     * User note: in the new version, we have N(N-1)/2 edge weight matrices. To make sure which matrix to store the edge weight
     * we use a hashmap to store all the hashmaps. HashMap<Integer,Double[][]>, the integer is the Arrays.hashCode() of the 
     * vertex set array. A vertex set array is an array of two integers, storing the two sets of the two vertex incident to an
     * edge. The two sets are stored in an ascending order. Then use Arrays.hashCode() to generate the hashcode of the vertex set
     * array. Thus the edge weights between two given vertex set can be stored in its corresponding matrix.
     */
    @Override
    public double getEdgeWeight(Vertex Vtx1, Vertex Vtx2){
        if(Vtx1.vtxSet == Vtx2.vtxSet)
            return 0;
        if(Vtx1.vtxSet == 0 && Vtx2.vtxSet ==1)
            return edgeWeights[Vtx1.getIdx()][Vtx2.getIdx()];
        else if(Vtx1.vtxSet == 1 && Vtx2.vtxSet == 0)
            return edgeWeights[Vtx2.getIdx()][Vtx1.getIdx()];
        else 
            throw new IllegalArgumentException("Edge level can only be 0 or 1:  "+Vtx1.vtxSet+"   "+Vtx2.vtxSet);
    }
    
    /**
     * This method returns a 2-dimensional matrix as the edge weights.
     * @return 
     */
    public double[][] getEdgeWeightMatrix(){
        return edgeWeights;
    }
    
   
    /**
     * This method returns a collection of vertices which are neighbouring to the given vertex
     * @param Seed: The given vertex whose neighbors are to be returned.
     * @return Collection<Vertex>: The collection of vertices, i.e. neighbors to be returned
     */
    @Override
    public ArrayList<Vertex> getNeighbors(Vertex Seed){
        ArrayList<Vertex> Neighbors = new ArrayList();
        for(Vertex vtx: vertices)
        {
            if(getEdgeWeight(vtx,Seed) > Thresh)
                Neighbors.add(vtx);
        }
        Neighbors.trimToSize();
        return Neighbors;
    }
    
    /**
     * This method returns the size of a given set, i.e. the number of vertices in a given set.
     * @param SetIdx: The index of the set, starting from 0.
     * @return The size of the vertex set given index.
     * 
     */
    @Override
    public int getSetSize(int SetIdx){
        //First, check the SetIdx
        if(SetIdx <0 ||SetIdx >1)
            throw new IllegalArgumentException("In a bipartite graph, the index of a set can only be 0 or 1");
        else if(SetIdx == 0) return set0Size;
        return set1Size;
    }
    
    /**
     * This method returns the number of sets in the subgraph, i.e. the number of vertex sets in the subgraph.
     * @return Since it's bipartite graph, 2 is returned.
     */
    @Override
    public int getSetCount(){
        return 2;
    }
    
    
    /**
     * This method returns
     */
    /**
     * This method returns the number of vertices in the graph, i.e. the number of vertices in the whole graph.
     * @return 
     */
    @Override
    public int getVertexCount(){
        return set0Size+set1Size;
    }
    
    /**
     * This method returns all vertices of a given graph. 
     * @return The collection of vertices.
     */
    @Override
    public ArrayList<Vertex> getVertices(){
        return vertices;
    }
     /**
     * This method adds one vertex into the graph. This method is only used to initialize the graph.
     * After the graph is inited, no vertex insertion is allowed.
     * @param VertexName
     * @param VertexSet
     * @return The index of the added vertex.
     */
    private int pushVertex(String VertexName, int VertexSet){
        Vertex vtx = new Vertex(VertexName,VertexSet,-1);
        //U. Note: we should add a new meothod, searching a vertex in a given
        //vertex list (arrayList or Linkedlist). This method should be a static
        //method
        int Idx = ((ArrayList<Vertex>)vertices).indexOf(vtx);
        if(Idx == -1)
        {
            vertices.add(vtx);
            //set the index of the new vertex according to its vertex set
            if(VertexSet == 0)
            {
                vtx.VtxIdx = set0Size;
                set0Size++;
                return set0Size-1;
            }
            else if(VertexSet ==1)
            {
                vtx.VtxIdx = set1Size;
                set1Size++;
                return set1Size-1;
            }
            else
                throw new IllegalArgumentException("Vertex lvel can only be 0 or 1");
        }
        else
            return ((ArrayList<Vertex>)vertices).get(Idx).getIdx();
    }
    
    /**
     * This method reads the graph instance from a given file path.
     * @param FilePath The path of the input graph.
     */
    @Override
    public void readGraph(String filePath){
        vertices = new ArrayList<>();
        
        //Read the input file for the first time, to init the 
        //EdgeWeight matrix
        FileReader fr =null;
        try{
            fr= new FileReader(filePath);
        }catch(FileNotFoundException e){e.printStackTrace();}
        BufferedReader br= new BufferedReader(fr);
        String line = null;
        
        
        //Size1, Size2 are two variables just to init EdgeWeight matrix.
        //When the input file is read for the first time, we do not create any vertex or add any vertex into
        //arraylist vertices.
        int size1 = 0;
        int size2 = 0;
        
        // Here, we used 2 hashset to count how many vertices we have
        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();

        while(true)
        {
            try{
                line = br.readLine();
            }catch(IOException e){e.printStackTrace();}
            if(line == null)
                break;
            
            String[] split  = line.split("\t");
            //if there is only vertex, no other link
            if(split.length ==2)
            {
                if(Integer.parseInt(split[1]) == 0 )
                    set1.add(String.copyValueOf(split[0].toCharArray()));
                else if(Integer.parseInt(split[1]) == 1)
                    set2.add(String.copyValueOf(split[0].toCharArray()));
                
            }
            else{
                if(Integer.parseInt(split[1]) == 0)
                    set1.add(String.copyValueOf(split[0].toCharArray()));
                else if(Integer.parseInt(split[1]) == 1)
                    set2.add(String.copyValueOf(split[0].toCharArray()));

                if(Integer.parseInt(split[3]) == 0)
                    set1.add(String.copyValueOf(split[2].toCharArray()));
                else if(Integer.parseInt(split[3]) == 1)
                    set2.add(String.copyValueOf(split[2].toCharArray()));
            }
        }
        try{
            br.close();
            fr.close();
        }catch(IOException e){e.printStackTrace();}
        
        size1 = set1.size();
        size2 = set2.size();
        set1 = null;
        set2 = null;
        //U. note: in the new version, we are to implement an npartitegraph based on matrix.
        //Edges between two arbitrarily different vertex sets are permitted.
        //Thus, if we have n vertex sets, we have n(n-1)/2 edge weight matrices
        edgeWeights = new double[size1][size2];
        //re-read the file, create vertices and init edge weights.
        try{
            fr = new FileReader(filePath);
        }catch(FileNotFoundException e){e.printStackTrace();}
        br = new BufferedReader(fr);
        
        while(true)
        {
            try{
                line = br.readLine();
            }catch(IOException e){e.printStackTrace();}
            if(line == null)
                break;
            String[] split = line.split("\t");
            //if there is only vertex, no other link
            if(split.length ==2)
            {
            }
            else{
                String vtx1Name = String.copyValueOf(split[0].toCharArray());
                //int Vtx1Lvl = Integer.parseInt(String.copyValueOf(split[1].toCharArray()));
                int vtx1Lvl = Integer.parseInt(String.copyValueOf(split[1].toCharArray()));
                String vtx2Name = String.copyValueOf(split[2].toCharArray());
                //int Vtx2Lvl = Integer.parseInt(String.copyValueOf(split[3].toCharArray()));
                int vtx2Lvl = Integer.parseInt(String.copyValueOf(split[3].toCharArray()));
                
                //U. note: here it's not proper to use addVertex() just to search for an existing vertex.
                //Thus, we should add a new method findVertex(). maybe a static method in Class Vertex, or a normal
                //method in the new NpartiteGraph class
                int idx1 = pushVertex(vtx1Name,vtx1Lvl);
                int idx2 = pushVertex(vtx2Name,vtx2Lvl);
                double ew = Double.parseDouble(String.copyValueOf(split[4].toCharArray()));
                
                //U. note: here we need a method to assign edgeweight.
                if(vtx1Lvl == 0 && vtx2Lvl ==1)
                    edgeWeights[idx1][idx2] = ew;
                else if(vtx1Lvl ==1 && vtx2Lvl ==0)
                    edgeWeights[idx2][idx1] = ew;
                else throw new IllegalArgumentException("Vertex lvl can only be 0 or 1");
            }
        }
        try{
            br.close();
            fr.close();
        }catch(IOException e){e.printStackTrace();}     
    }
    
    /**
     * This method returns the connected component with starting given vertex of vtx.
     * @param Vtx. The starting vertex to perform BFS.
     * @return The connected component.
     */
    @Override
    public MatrixBipartiteSubgraph runBFS(Vertex Vtx){
        LinkedList<Vertex> queue = new LinkedList<>();
        //create a marker
        HashMap<String, Boolean> marker = new HashMap<>();
        //init the haspmap

        //create a new arrayList<Vertex> as result
        ArrayList<Vertex> result = new ArrayList<>();

        for(Vertex vtx: getVertices())
        {
            marker.put(vtx.toString(), Boolean.FALSE);
        }

        //enqueue source and mark source
         queue.add(Vtx);
         result.add(Vtx);
         marker.put(Vtx.toString(),true);

         //while queue is not empty
         while(!queue.isEmpty())
         {
             //dequeue an item from queue into CurrentVtx
             Vertex CurrentVtx = queue.pollLast();
             //get the neighbours of CurrentVtx
             ArrayList<Vertex> Neighbours = getNeighbors(CurrentVtx);

             //for each neighbour
             for(Vertex vtx: Neighbours)
             {
                 if(!marker.get(vtx.toString()))
                 {
                     marker.put(vtx.toString(),true);
                     queue.add(vtx);
                     result.add(vtx);
                 }
             }

         }
         //create a new subkpartitegraph
         MatrixBipartiteSubgraph sub = new MatrixBipartiteSubgraph(result,this);
         return sub;
    }
    
    /**
     * This method sets the vertices in the graph.
     * @param Vertices. The collection of vertices.
     */
    @Override
    public void setVertices(List<Vertex> Vertices){
        //If the given Vertices is an instance of arraylist, then we assign our vertice directly.
        if(Vertices instanceof ArrayList)
            this.vertices = (ArrayList<Vertex>)Vertices;
        //Else
        else this.vertices = new ArrayList<>(Vertices);
    }
    
    /**
     * This method sets the edge weight between two given vertices.
     * @param Vtx1.
     * @param Vtx2.
     * @param EdgeWeight
     */
    @Override
    public void setEdgeWeight(Vertex Vtx1, Vertex Vtx2, double EdgeWeight){
        if(Vtx1.vtxSet == 0 && Vtx2.vtxSet ==1)
            edgeWeights[Vtx1.VtxIdx][Vtx2.VtxIdx] = EdgeWeight;
        else if(Vtx1.vtxSet == 1 && Vtx2.vtxSet == 0)
            edgeWeights[Vtx2.getIdx()][Vtx1.getIdx()] = EdgeWeight;
        else 
            throw new IllegalArgumentException("Edge level can only be 0 or 1:  "+Vtx1.vtxSet+"   "+Vtx2.vtxSet);
    } 
    
    /** Below are the methods implementing the abstract methods in ClustEdit **/
    /**
     * This method pushes one action into Actions.
     * @param Vtx1
     * @param Vtx2
     * The two vertices are given to locate the edge.
     * @param NewWeight 
     * The target EdgeWeight.
     * @return 
     */
    @Override
    public boolean pushAction(Vertex Vtx1, Vertex Vtx2, double NewWeight){
        double EdgeWeight = getEdgeWeight(Vtx1, Vtx2);
        if(EdgeWeight == 0)
            throw new IllegalArgumentException("There is a null-edge between vtx1 and vtx2");
        Action act = new Action(Vtx1,Vtx2,EdgeWeight);
        Actions.add(act);
        if(act.getOriginalWeight() > Thresh)
        {
            cost += act.getOriginalWeight() - Thresh;
        }
        else
        {
            cost += Thresh - act.getOriginalWeight();
        }
        return true;
    }
    
    /**
     * This method carries out of the action in the action list.
     * @param Index
     * @return If the action is successfully carried out, then returns yes, otherwise no.
     */
    @Override
    public boolean takeAction(int Index){
        Action act = ((ArrayList<Action>)Actions).get(Index);
        if(act.getOriginalWeight() > Thresh)
        {
            setEdgeWeight(act.getVtx1(),act.getVtx2(),BiForceConstants.FORBIDDEN);
        }
        else
        {
            setEdgeWeight(act.getVtx1(),act.getVtx2(),BiForceConstants.PERMENANT);
        }
        return true;
    }
    
    /**
     * This method carries out all actions in Actions.
     * @return If all actions in Actions are successfully carried out, then returns yes, otherwise no.
     */
    @Override
    public boolean takeAllActions(){
        for(int i=0;i<Actions.size();i++)
            takeAction(i);
        return true;
    }
    
    /**
     * This method checks if the given action is carried out.
     * @return If the action is already carried out, then returns yes.
     */
    @Override
    public boolean isActionCarried(int ActIdx){
        Action act = ((ArrayList<Action>)Actions).get(ActIdx);
        if(getEdgeWeight(act.getVtx1(),act.getVtx2()) == BiForceConstants.FORBIDDEN ||
                getEdgeWeight(act.getVtx1(),act.getVtx2()) == BiForceConstants.FORBIDDEN)
            return true;
        else return false;
    }
    
    /**
     * This method removes the action given the index.
     * @param ActIdx
     * @return If the remove is successfully carried out, then return yes.
     */
    @Override
    public boolean removeAction(int ActIdx){
        if(ActIdx<0)
            throw new IllegalArgumentException("Action index smaller than 0.");
        else if (ActIdx > Actions.size())
            throw new IllegalArgumentException("Action index larger than size");
        ((ArrayList<Action>)Actions).remove(ActIdx);
        return true;
    }
    
    /**
     * This method returns the actions in the form of collection.
     * @return The collection of actions.
     */
    @Override
    public List<Action> getAllActions(){
        return Actions;
    }
    
    /**
     * This method writes the clustering result into a given path.
     * @param OutputPath 
     */
    @Override
    public void writeResultTo(String OutputPath){
        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(OutputPath ,true));
                bw.write("<cost>"+this.getCost()+"<\\cost>\r\n");
                bw.write("<clusters>\r\n");
                for(Cluster cls:Clusters)
                {
                    if(cls.getVertices().isEmpty())
                        continue;
                    for(int i=0;i<cls.getVertices().size()-1;i++)
                        bw.write(cls.getVertices().get(i).Value+",");
                    bw.write(cls.getVertices().get(cls.getVertices().size()-1).Value+"\r\n");
                }
                bw.write("<\\clusters>\r\n");
                bw.flush();
        }catch(IOException e){e.printStackTrace();}
    }
    
    /**
     * This method write
     * @param OutputPath 
     */
    @Override
    public void writeGraphTo(String OutputPath){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(OutputPath));
            for(int i=0;i<vertices.size();i++)
            {
                for(int j=i+1;j<vertices.size();j++)
                    bw.write(vertices.get(i).Value+"\t"+vertices.get(i).vtxSet+"\t"+
                            vertices.get(j).Value+"\t"+vertices.get(j).vtxSet+"\t"+
                            getEdgeWeight(vertices.get(i),vertices.get(j))+"\r\n");
            }
            bw.flush();
            bw.close();
        }catch(IOException e){e.printStackTrace();}
        
    }
    
}
