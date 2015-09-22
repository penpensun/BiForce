/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biforce.io;

import biforce.algorithms.Param;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import biforce.graphs.BipartiteGraph_deprecated;
import biforce.graphs.SubBipartiteGraph;
import biforce.graphs.Vertex;

/**
 *
 * @author mac-97-41
 */
public class ResultWriter {
    static void writeMatrixResult(BipartiteGraph_deprecated graph, Param p,
            String outputfile, int sizerank) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile,true));
        //for all the clusters
        
        int CurrentCluster = 0;
        ArrayList<SubBipartiteGraph> SubGraphs = new ArrayList<SubBipartiteGraph>(graph.getAllConnectedComponents(p.getThresh()) );
        Collections.sort(SubGraphs);
        if(sizerank ==-1)
            sizerank = SubGraphs.size();
        for(int subidx=SubGraphs.size()-1;subidx>SubGraphs.size()-1-sizerank;subidx--)
        {
            SubBipartiteGraph sub = SubGraphs.get(subidx);
            //remove all the biclusters smaller than SizeLimit
            //this indicator indicates whether we have checked all the clusters
            ArrayList<Vertex> RowSet = new ArrayList<Vertex>();
            ArrayList<Vertex> ColSet = new ArrayList<Vertex>();
            for(Vertex vtx:sub.getSubVertexSet())
            {                
                if(vtx.vtxSet == 0)
                    RowSet.add(vtx);
                else if(vtx.vtxSet ==1)
                    ColSet.add(vtx);
            }
            //output the row set
            if(!RowSet.isEmpty())
            {
                for(int i=0;i<RowSet.size()-1;i++)
                    bw.write(RowSet.get(i).Value+",");
                bw.write(RowSet.get(RowSet.size()-1).Value+"\n");
            }         
            //output the col set
            if(!ColSet.isEmpty())
            {
                for(int i=0;i<ColSet.size()-1;i++)
                    bw.write(ColSet.get(i).Value+",");
                bw.write(ColSet.get(ColSet.size()-1).Value+"\n");
            }
            CurrentCluster++;
        }
        
        bw.write("<\\output>"+outputfile+"\r\n");
        bw.flush();
        bw.close();
    }
    
}
