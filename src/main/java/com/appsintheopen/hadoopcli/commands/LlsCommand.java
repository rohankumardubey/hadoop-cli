package com.appsintheopen.hadoopcli.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class LlsCommand extends AbstractCommand {
  
  protected String listDirectory;
  protected Path   listPath;

  public LlsCommand(String cmd) {
    super(cmd);
  }

  @Override
  protected int validate() {
    if (paths.size() == 0) {
      // If not path was passed, then list the cwd
      listDirectory = wd();
    } else if (!pathNumber(0).startsWith("/")) {
      // relative path - prepend the cwd
      listDirectory = wd()+"/"+pathNumber(0);
    } else {
      // absolute path is simple
      listDirectory = pathNumber(0);
    }
    listPath = new Path(listDirectory);
    try {
      if (!fs().exists(listPath)) {
        // The requested path doesn't exist, so abort
        environment.stderr(listDirectory+": no such file or directory");
        return 1;
      }
    } catch(IOException e) {
      // This should really only happen if there is a problem with the 
      // file system, most likely the remote file system.
      environment.stderr(e.toString());
      return 2;
    }
    return 0;
  }

  @Override
  protected int runCommand() {
    try {
      FileStatus[] files = fs().listStatus(listPath);
      for (int i=0; i<files.length; i++) {
        environment.stdout(files[i].getPath().getName());
      }
    } catch (FileNotFoundException e) {
      environment.stderr(e.toString());
      return 1;
    } catch (IOException e) {
      environment.stderr(e.toString());
      return 3;
    }
    return 0;
  }
  
  @Override
  public FileSystem getFileSystemForPathNumber(ExecutionEnvironment env, int num) {
    return env.localfs();
  }
  
  @Override
  public String getWorkingDirectoryForPathNumber(ExecutionEnvironment env, int num) {
    return env.localwd();
  }
  
  private FileSystem fs() {
    return environment.localfs();
  }

  private String wd() {
    return environment.localwd();
  }
  
}
