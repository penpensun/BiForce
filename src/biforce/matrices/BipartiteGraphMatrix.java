/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.matrices;
import biforce.graphs.BipartiteGraph_deprecated;
import biforce.graphs.Cluster;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class describes a matrix based on a bipartite graph and is used to compute biclustering. 
 * This class must not be confused by MatrixBipartiteGraph. 
 * MatrixBipartiteGraph is a graph based on matrix as its data structure.
 * BipartiteGraphMatrix is a matrix based on bipartite graph, initialized given the path of the matrix.
 * This class implements the Matrix interface.
 * @author Peng Sun
 */
public class BipartiteGraphMatrix{
    BipartiteGraph_deprecated Graph =null;
    
    /**
     * Inits matrix on input.
     * @param input
     * @param hasHeader
     * @throws IOException 
     */
    public BipartiteGraphMatrix(String input, boolean hasHeader) throws IOException
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
            Graph =  new BipartiteGraph_deprecated(edgweight, RowNames, ColNames);
        else
            Graph = new BipartiteGraph_deprecated(edgweight);
    }
    
    /**
     * This method returns the bipartitegraph.
     * @return 
     */
    public BipartiteGraph_deprecated getGraph ()
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
         try{ 
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
