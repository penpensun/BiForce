/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.algorithms;
import biforce.graphs.BipartiteGraph_deprecated;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Arrays;


/**
 *
 * @author Peng Sun
 * This class impelments the general and specific training
 */
public class Training {
    
    
    public static Param singleTrain (String input, TrainingParam initset, 
            float thresh, int dim, float upperth, float lowerth, float step, 
            int trainingiter) throws IOException
    {
        TrainingParam[] parasetarray = new TrainingParam[2];
        parasetarray[0] = initset;
        for(int i=1;i<parasetarray.length;i++)
        {
            //for a parameter x, we randomly generate a parameter y within the range of (0,2x);
            int maxIter = (int)(Math.random()*10*initset.getMaxIter());
            float fatt = (float)(Math.random()*10*initset.getFatt());
            float frep = (float)(Math.random()*10*initset.getFrep());
            float M0 = (float)(Math.random()*10*initset.getM0());
            float radius = (float)(Math.random()*10*initset.getRadius());
            parasetarray[i] = new TrainingParam(maxIter,fatt,frep,M0,dim,radius,thresh,upperth,lowerth,step);
        }
            /*
         * Perform the training for given iterations
         */
        for(int i=0;i<trainingiter;i++)
        {
            //run each parameter set on all the files in the Inputfolder, and set the cost of the parameter set as the sum
            //of all the costs
            //for all the parameter sets
            for(TrainingParam paraset: parasetarray)
            {
                float Cost = (float)BiForceOnGraph2.run(new BipartiteGraph_deprecated(input), paraset).getCost();            
                paraset.setCost(Cost);
            }
            System.out.println("Iteration:  "+i);
            //according to the editing results, permutate the parameters
            parasetarray = getNewTrainingParam(parasetarray);
        }
        return parasetarray[0];
    }
    
    
    public static TrainingParam[] getNewTrainingParam(TrainingParam[] inputparam)
    {
        TrainingParam[] NewTrainingSets = new TrainingParam[12];
        //the first and second training sets of the new sets are the best and second best training sets in the 
        //input sets
        // the sorting needs test
        Arrays.sort(inputparam);
        NewTrainingSets[0] = inputparam[0];
        NewTrainingSets[1] = inputparam[1];
        //for test
        System.out.println("Inital Set:");
        System.out.println("maxiter  fatt  frep  M0  radius");
        for(int i=0;i<inputparam.length;i++)
        {
            System.out.println(inputparam[i].getMaxIter()+"  "+inputparam[i].getFatt()+"  "+
                    inputparam[i].getFrep()+"  "+inputparam[i].getM0()+"  "+inputparam[i].getRadius()+
                    "  "+inputparam[i].getCost());
        }
        //the third set in the new array is the mean of the first 3 sets in the old one
        // This part needs test
        int maxiter;
        float fatt,frep,M0,radius;
        maxiter = (int)((inputparam[0].getMaxIter()+inputparam[1].getMaxIter()+inputparam[2].getMaxIter())/3);
        fatt = (inputparam[0].getFatt()+inputparam[1].getFatt()+inputparam[2].getFatt())/3;
        frep = (inputparam[0].getFrep()+inputparam[1].getFrep()+inputparam[2].getFrep())/3;
        M0 = (inputparam[0].getM0()+inputparam[1].getM0()+inputparam[2].getM0())/3;
        radius = (inputparam[0].getRadius()+inputparam[1].getRadius()+inputparam[2].getRadius())/3;
        NewTrainingSets[2] = new TrainingParam(maxiter,fatt,frep,M0,3, radius,
                inputparam[0].getThresh(),
                inputparam[0].getUpperth(),inputparam[0].getLowerth(),inputparam[0].getStep());       
        //the fourth to the sixth are the permutation of the first 3 sets in the old array
        for(int i=3;i<=5;i++)
        {
            maxiter = inputparam[(int)(Math.random()*3)].getMaxIter();
            fatt = inputparam[(int)(Math.random()*3)].getFatt();
            frep = inputparam[(int)(Math.random()*3)].getFrep();
            M0 = inputparam[(int)(Math.random()*3)].getM0();
            radius = inputparam[(int)(Math.random()*3)].getRadius();
            NewTrainingSets[i] = new TrainingParam(maxiter,fatt,frep,M0,3, radius,
                    inputparam[0].getThresh(),
                inputparam[0].getUpperth(),inputparam[0].getLowerth(),inputparam[0].getStep());  
        }  
        //create the next 3 arrays around the best one
        for(int i=6;i<=8;i++)
        {
            maxiter = (int)(inputparam[0].getMaxIter()*(Math.random()/5+0.9));
            fatt = (float)(inputparam[0].getFatt()*(Math.random()/5+0.9));
            frep = (float)(inputparam[0].getFrep()*(Math.random()/5+0.9));
            M0 = (float)(inputparam[0].getM0()*(Math.random()/5+0.9));
            radius = (float)(inputparam[0].getRadius()*(Math.random()/5+0.9));
            NewTrainingSets[i] = new TrainingParam(maxiter,fatt,frep,M0,3, radius,
                    inputparam[0].getThresh(),
                inputparam[0].getUpperth(),inputparam[0].getLowerth(),inputparam[0].getStep());  
        }
        //the last 3, randomly generated, within the range of (0,2x), x is the original value in the old array
        for(int i=9;i<=11;i++)
        {
            maxiter = (int)(inputparam[0].getMaxIter()*(Math.random()*2));
            fatt = (float)(inputparam[0].getFatt()*(Math.random()*2));
            frep = (float)(inputparam[0].getFrep()*(Math.random()*2));
            M0 = (float)(inputparam[0].getM0()*(Math.random()*2));
            radius = (float)(inputparam[0].getRadius()*(Math.random()*2));
            NewTrainingSets[i] = new TrainingParam(maxiter,fatt,frep,M0,3, radius,
                    inputparam[0].getThresh(),
                inputparam[0].getUpperth(),inputparam[0].getLowerth(),inputparam[0].getStep());  
        }
        System.out.println("New Set:");
        System.out.println("maxiter  fatt  frep  M0  radius");
        for(int i=0;i<inputparam.length;i++)
        {
            System.out.println(NewTrainingSets[i].getMaxIter()+"  "+NewTrainingSets[i].getFatt()+"  "+
                    NewTrainingSets[i].getFrep()+"  "+NewTrainingSets[i].getM0()+"  "+NewTrainingSets[i].getRadius()+
                    "  "+NewTrainingSets[i].getCost());
        }
        return NewTrainingSets;
    }
    
    
    /**
     * This method performs general training on a folder of files
     * @param inputfolder
     * @param initset
     * @param thresh
     * @param dim
     * @param upperth
     * @param lowerth
     * @param step
     * @param trainingiter
     * @param logfile
     * @return 
     * @throws java.io.IOException 
     */
    public TrainingParam GeneralTraining(String inputfolder, TrainingParam initset, 
            float thresh, int dim, float upperth, float lowerth, float step,
            int trainingiter, String logfile) throws IOException
    {
        //init the log file
        FileWriter fw = new FileWriter(logfile);
        BufferedWriter bw = new BufferedWriter(fw);
        TrainingParam[] paramarray = new TrainingParam[12];
        paramarray[0] = new TrainingParam(186,1.245f,1.687f,633.0f, dim, 200.0f,
            thresh, lowerth, upperth,step);
        for(int i=1;i<paramarray.length;i++)
        {
            //for a parameter x, we randomly generate a parameter y within the range of (0,2x);
            int maxIter = (int)(Math.random()*10*paramarray[0].getMaxIter());
            float fatt = (float)(Math.random()*10*paramarray[0].getFatt());
            float frep = (float)(Math.random()*10*paramarray[0].getFrep());
            float M0 = (float)(Math.random()*10*paramarray[0].getM0());
            float radius = (float)(Math.random()*10*paramarray[0].getRadius());
            paramarray[i] = new TrainingParam(maxIter,fatt,frep,M0,dim, radius,
            thresh, lowerth, upperth,step);
            
        }
        //output the initial ParaSetArray to logfile
        bw.write("Init set:\n");
        for(int i=0;i<paramarray.length;i++)
        {
            bw.write(paramarray[i].getMaxIter()+"   "+paramarray[i].getFatt()+"   "+
                    paramarray[i].getFrep()+"   "+paramarray[i].getM0()+"   "+paramarray[i].getRadius()+"\n");
            bw.flush();
        }
        /*
         * Perform the training for given iterations
         */
        for(int i=0;i<trainingiter;i++)
        {
            //run each parameter set on all the files in the Inputfolder, and set the cost of the parameter set as the sum
            //of all the costs
            //for all the parameter sets
            for(TrainingParam param: paramarray)
            {
                float Cost = 0;
                String[] FileList = new File(inputfolder).list();
                //for each file
                for(String InputFile: FileList)
                {
                    Cost += BiForceOnGraph2.run(new BipartiteGraph_deprecated(inputfolder+"/"+InputFile), param).getCost();
                }
                //System.out.println("Cost  "+Cost);
                param.setCost(Cost);
            }
            System.out.println("Iteration:  "+i);
            //according to the editing results, permutate the parameters
            paramarray = getNewTrainingParam(paramarray);
            //output the newly obtained array
            bw.write("Training iter : "+ i+ "\n");
            for(int j=0;j<paramarray.length;j++)
            {
                bw.write(paramarray[j].getMaxIter()+"   "+paramarray[j].getFatt()+"   "+
                        paramarray[j].getFrep()+"   "+paramarray[j].getM0()+"   "+paramarray[j].getRadius()+"\n");
                bw.flush();
            }
        }
        
        //output the best set of parameter
        Arrays.sort(paramarray);
        bw.write("Final para set\n");
        for(int j=0;j<paramarray.length;j++)
        {
            bw.write(paramarray[j].getMaxIter()+"   "+paramarray[j].getFatt()+"   "+
                    paramarray[j].getFrep()+"   "+paramarray[j].getM0()+"   "+paramarray[j].getRadius()+"\n");
            bw.flush();
        }
        return paramarray[0];
    }
    
}
