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
    double cost;
    public TrainingParam(int maxIter, 
            double fatt, double frep, 
            double M0, int dim, double radius,
            double thresh,
            double upperth, double lowerth, double step)
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
    
    public double getCost()
    {
        return cost;
    }
    public void setCost(double cost)
    {
        this.cost = cost;
    }
    
    
}
