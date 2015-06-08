package com.appsintheopen.hadoopcli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import jline.console.completer.Completer;

public class HdfsCompleter implements Completer {
    
  private FileSystem fs;
  private String fsScheme; 
    
  public HdfsCompleter(Configuration conf) throws IOException {
    fs = FileSystem.get(conf);
    fsScheme = fs.getUri().toString();
  }

  @Override
  public int complete(String buffer, int cursor, List<CharSequence> candidates) {
    // Treat a null buffer as empty string
    if (buffer == null) {
      buffer = "";
    }
        
    FileStatus[] files;
    try {
      files = getFiles(buffer);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return -1;
    }
        
    if (files.length == 0) {
      return -1;
    }
        
    int matches = 0;
    
    for (int i = 0; i < files.length; i++) {
      if (removeScheme(files[i].getPath().toString()).startsWith(buffer)) {
        matches ++;
      }
    }

    for (int i = 0; i < files.length; i++) {
      String pathName = removeScheme(files[i].getPath().toString());
      if (pathName.startsWith(buffer)) {
        CharSequence name = files[i].getPath().getName() + (matches == 1 && files[i].isDirectory() ? "/" : " ");
        candidates.add(name);
      }
    }

    final int index = buffer.lastIndexOf("/");
    
    return index + 1; //separator().length();
        
  }
    
  public FileStatus[] getFiles(String path) throws IOException {
        
    Path   directory;
    Path thePath = path.isEmpty() ? fs.getHomeDirectory() : new Path(path );
    
    if (thePath.isRoot()) {
      directory = thePath;
    } else {
      // If the original path string has a trailing "/" then we know it was already
      // a directory. When the string is turned into a "path" the trailing / is lost.
      // This is why the following statement users both thePath and path :-/
      if (fs.isDirectory(thePath) && path.endsWith("/")) {
        directory = thePath;
      } else {
        directory = thePath.getParent();
      }
    }
        
    try {
      if (fs.exists(directory)) {
        //Path[] paths = FileUtil.stat2Paths(fs.listStatus(directory));
        return fs.listStatus(directory);
      } else {
        return new FileStatus[0];
      }
    } catch (org.apache.hadoop.security.AccessControlException e) {
      // If you don't have permission, then the file 'doesn't exist'
      return new FileStatus[0];
    }
  }
    
  private String removeScheme(String path) {
    return path.replace(fsScheme, "");
  }

}
