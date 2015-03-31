/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;

/**
 *
 * @author Peng
 * 
 */
public class TrainingParam extends Param {
    float cost;
    public TrainingParam(int maxIter, 
            float fatt, float frep, 
            float M0, int dim, float radius,
            float thresh,
            float upperth, float lowerth, float step)
    {
        super.maxIter = maxIter;
        super.fatt = fatt;
        super.frep = frep;
        super.M0=M0;
        super.dim=dim;
        super.radius=radius;
        super.thresh = thresh;
        super.upperth = upperth;
        super.lowerth = lowerth;
        super.step = step;
        this.cost = 0; 
    }
    
    public float getCost()
    {
        return cost;
    }
    public void setCost(float cost)
    {
        this.cost = cost;
    }
    
    
}
