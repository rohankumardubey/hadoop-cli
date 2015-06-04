package com.appsintheopen.hadoopcli;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class ReadFiles {

    public static void main (String [] args) throws Exception{
        try{
            
            Configuration conf = new Configuration();
            conf.addResource(new Path("/Users/sodonnel/source/puppet/core-site.xml"));
            conf.addResource(new Path("/Users/sodonnel/source/puppet/hdfs-site.xml"));
            conf.addResource(new Path("/Users/sodonnel/source/puppet/mapred-site.xml"));
            
            
                FileSystem fs = FileSystem.get(conf);
                
         /*       
                DistributedFileSystem hdfs = (DistributedFileSystem) fs;
                DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();
                 
                String[] names = new String[dataNodeStats.length];
                for (int i = 0; i < dataNodeStats.length; i++) {
                    names[i] = dataNodeStats[i].getHostName();
                    System.out.println((dataNodeStats[i].getHostName()));
                }
                
                */
                HdfsCompleter comp = new HdfsCompleter(conf);
                
                comp.getFiles("/user");
                
/*                FileStatus[] status = fs.listStatus(new Path("/user/oozie"));

                for (int i=0;i<status.length;i++){
                   System.out.println(status[i].getPath());
                   status[i].
                      //  BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(status[i].getPath())));
                      //  String line;
                      //  line=br.readLine();
                      //  while (line != null){
                      //          System.out.println(line);
                      //          line=br.readLine();
                      //  }
                }
                */
        } catch(Exception e){
            System.out.println(e);
                System.out.println("File not found");
        }
        
}
    
}
