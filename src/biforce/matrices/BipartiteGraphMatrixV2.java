package biforce.matrices;
import biforce.graphs.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is the BipartiteGraphMatrix class in the new design.
 * @author Peng Sun
 */
public class BipartiteGraphMatrixV2{
    //This variable stores the reference to the MatrixBipartiteGraph， which is going 
    //to be inited when the data from input file is read.
    BipartiteGraph Graph;
    /**
     * Inits matrix on input.
     * @param input
     * @param hasHeader
     * @throws IOException 
     */
    public BipartiteGraphMatrixV2(String input, boolean hasHeader) throws IOException
    {
        //these two variables stores the size of rows and columns
        int row=0,col=0;
        //declare the ArrayList of RowNames and ColNames
        ArrayList<String> RowNames = null;
        ArrayList<String> ColNames = null;
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);
        
        String line= null;
        // If the file has header, then create an arraylist from the row names
        if(hasHeader)
        {
            RowNames = new ArrayList<>();
            line = br.readLine();
            String[] split = line.split("\t");
            for (String token : split) {
                RowNames.add(String.copyValueOf(token.toCharArray()));
            }
        }
        
        //read the file first time, to init row and col (the number of rows and columns)
        //to init col, we first read one line, and plus one to row
        line = br.readLine();
        row++;
        //if we have headers, then col = split.length-1
        if(hasHeader)
            col = line.split("\t").length-1;
        else
            col = line.split("\t").length;
        
        while((line = br.readLine())!= null)
        {
            row++;
            //if we have headers, then we must init ColNames
            if(hasHeader)
            {
                ColNames.add(String.copyValueOf(line.substring(0,line.indexOf("\t")).toCharArray()));
            }
        }
        
        //trim RowNames and ColNames to acutal sizes.
        if(hasHeader)
        {
            RowNames.trimToSize();
            ColNames.trimToSize();
        }
        //close the file and finish the first read
        fr.close();
        br.close();
        //create the edge weight matrix
        double[][] edgweight = new double[row][col];
        
        //start the second read
        fr = new FileReader(input);
        br = new BufferedReader(fr);
        
        //if there is header, then jump it
        if(hasHeader)
            br.readLine();
        
        int CurrentRow = 0;
        while((line =br.readLine())!= null)
        {
            String[] split = line.split("\t");
            //if we have headers, then we jump the first column
            
           if(hasHeader)
           {
               for(int i=1;i<split.length;i++)
                   edgweight[CurrentRow][i-1] = Double.parseDouble(split[i]);
           }
           else
           {
               for(int i=0;i<split.length;i++)
                   edgweight[CurrentRow][i] = Double.parseDouble(split[i]);
           }
           CurrentRow++;
        }
        
        if(hasHeader)
            Graph =  new BipartiteGraph(edgweight, RowNames, ColNames);
        else
            Graph = new BipartiteGraph(edgweight);
    }
    
    /**
     * This method returns the bipartitegraph.
     * @return 
     */
    public BipartiteGraph getGraph ()
    {
        return Graph;
    }
    
    /**
     * Write clusters
     * @param outfile
     * @throws IOException 
     */
    public void writeClusters(String outfile) throws IOException
    {
         try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outfile ,true));
            for(Cluster cls:Graph.getClusters())
            {
                if(cls.getVertices().isEmpty())
                    continue;
                for(int i=0;i<cls.getVertices().size()-1;i++)
                    bw.write(cls.getVertices().get(i).Value+",");
                bw.write(cls.getVertices().get(cls.getVertices().size()-1).Value+"\r\n");
            }
            bw.flush();
        }catch(IOException e){e.printStackTrace();}
    }
    
}
