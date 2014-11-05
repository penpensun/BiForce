/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
/**
 *
 * @author Administrator
 */
public class Param {
    int maxIter = -1;
    double fatt = -1;
    double frep = -1;
    double M0 = -1;
    int dim = -1;
    double radius = -1;
    double thresh = 0;
    double upperth = -1;
    double lowerth = -1;
    double step=-1;
    
    /**
     * Init the param with different parameters
     * @param maxIter
     * @param fatt
     * @param frep
     * @param M0
     * @param dim
     * @param radius 
     * @param thresh
     * @param upperth
     * @param lowerth
     * @param step
     */
    public Param(int maxIter, 
            double fatt, double frep, 
            double M0, int dim, double radius,
            double thresh,
            double upperth, double lowerth, double step)
    {
        this.maxIter = maxIter;
        this.fatt = fatt;
        this.frep = frep;
        this.M0=M0;
        this.dim=dim;
        this.radius=radius;
        this.thresh = thresh;
        this.upperth = upperth;
        this.lowerth = lowerth;
        this.step = step;
    }
    
    /**
     * Init with default values
     */
    public Param()
    {
        fatt = 2.4839598967501715;
        frep = 1.3228008374592575;
        M0=51.835535150936714;
        radius = 112.46725298831082;
        dim = 3;
        thresh = 0;
        upperth = 200;
        lowerth = 0;
        step = 0.5;
    }
    
    public Param(String parafile)
    {
        try{
        BufferedReader br = new BufferedReader(new FileReader(parafile));
        String line;
        while((line = br.readLine())!= null)
        {
            //jump the comment lines
            if(line.startsWith("#"))
                continue;
            String prefix = line.substring(0,line.indexOf('='));
            String value = line.substring(line.indexOf('=')+1);
            //
            try{
                switch (prefix) {
                    case "fatt":
                        fatt = Double.parseDouble(value);
                        break;
                    case "frep":
                        frep = Double.parseDouble(value);
                        break;
                    case "M0":
                        M0=Double.parseDouble(value);
                        break;
                    case "radius":
                        radius = Double.parseDouble(value);
                        break;
                    case "maxIter":
                        maxIter = Integer.parseInt(value);
                        break;
                    case "dim":
                        dim = Integer.parseInt(value);
                        break;
                    case "thresh":
                        thresh= Double.parseDouble(value);
                        break;
                    case "upperth":
                        upperth = Double.parseDouble(value);
                        break;
                    case "lowerth":
                        lowerth= Double.parseDouble(value);
                        break;
                    case "step":
                        step= Double.parseDouble(value);
                        break;
                }
            }catch(NullPointerException | NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        //check if all parameters are initialized
        if(fatt ==-1 || frep == -1 ||M0 == -1 || radius == -1 || 
                maxIter == -1 || lowerth == -1 || upperth ==-1 || step ==-1)
            throw new IllegalArgumentException("Not all parameters are initialized");
        //check dim
        if(dim > 3 || dim <2)
            throw new IllegalArgumentException("Dimension must be 2 or 3");
        }
        catch(IOException e){
            System.out.println("(Param) IOException");
        }
    }
    
    public final double getFatt()
    {
        return fatt;
    }
    
    public final double getFrep()
    {
        return frep;
    }
    
    public final double getM0()
    {
        return M0;
    }
    
    public final double getRadius()
    {
        return radius;
    }
    
    public final int getMaxIter()
    {
        return maxIter;
    }
    
    
    public final int getDim()
    {
        return dim;
    }
    
    public final double getThresh()
    {
        return thresh;
    }
    
    public final double getLowerth()
    {
        return lowerth;
    }
    
    public final double getUpperth()
    {
        return upperth;
    }
    
    public final double getStep()
    {
        return step;
    }
    
    
    public final void setThresh(double thresh)
    {
        this.thresh = thresh;
    }
    
    
    
    public static Param readParams(String paramfile){
        return new Param(paramfile);
    }
}
