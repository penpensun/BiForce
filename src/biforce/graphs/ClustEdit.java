/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.graphs;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/**
 * This abstract class depicts ClustEdit, an abstract class that is used to carry out cluster editing. 
 * 
 * @author Peng Sun
 */
public abstract class ClustEdit {
    //This is the collection of actions.
    protected List<Action> Actions = null;
    //This variable stores the threshold for an edge. 
    protected double Thresh = 0;
    //This variable stores the clusters.
    protected ArrayList<Cluster> Clusters;
    
    /**
     * This method checks if the given action is carried out.
     * @param ActIdx The index of action to check.
     * @return If the action is already carried out, then returns yes.
     */
    public abstract boolean isActionCarried(int ActIdx);
    
    /**
     * This method returns the actions in the form of collection.
     * @return The collection of actions.
     */
    public abstract List<Action> getAllActions();
    
    /**
     * This method returns the total cost to pay at the current stage.
     * @return 
     */
    public double getCost(){
        double cost = 0;
        for(Action Act: Actions){
            cost+= Math.abs(Act.getOriginalWeight()-Thresh);
        }
        return cost;
    }
    
    /**
     * This method returns the threshold for cluster editing.
     * @return 
     */
    public double getThresh(){
        return Thresh;
    }
    
    /**
     * This method returns the list of clusters.
     * @return 
     */
    public ArrayList<Cluster> getClusters(){
        return Clusters;
    }
    
    /**
     * This method set the clusters
     * @param Clusters 
     */
    public void setClusters(ArrayList<Cluster> Clusters){
        this.Clusters = Clusters;
    }
    
    /**
     * 
     * @param Thresh
     */
    public void setThresh(double Thresh){
        this.Thresh = Thresh;
    }
    /**
     * This method pushes one action into Actions.
     * @param Vtx1
     * @param Vtx2
     * The two vertices are given to locate the edge.
     * @param EdgeWeight 
     * The target EdgeWeight.
     * @return 
     */
    public abstract boolean pushAction(Vertex Vtx1, Vertex Vtx2, double EdgeWeight);
    
    /**
     * This method carries out of the action in the action list.
     * @param Index
     * @return If the action is successfully carried out, then returns yes, otherwise no.
     */
    public abstract boolean takeAction(int Index);
    
    /**
     * This method carries out all actions in Actions.
     * @return If all actions in Actions are successfully carried out, then returns yes, otherwise no.
     */
    public abstract boolean takeAllActions();
    
    
    /**
     * This method removes the action given the index.
     * @param ActIdx
     * @return If the remove is successfully carried out, then return yes.
     */
    public abstract boolean removeAction(int ActIdx);
    
    /**
     * This method writes clustering result into a given file.
     * @param FilePath 
     */
    public abstract void writeResultTo(String FilePath);
}
