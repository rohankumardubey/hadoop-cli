package com.appsintheopen.hadoopcli.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

public class LsCommand extends AbstractCommand {
  
  protected String listDirectory;
  protected Path   listPath;

  public LsCommand(String cmd) {
    super(cmd);
  }

  @Override
  protected int validate() {
    if (paths.size() == 0) {
      // If not path was passed, then list the cwd
      listDirectory = environment.localwd();
    } else if (!paths.get(0).startsWith("/")) {
      // relative path - prepend the cwd
      listDirectory = environment.localwd()+"/"+paths.get(0);
    } else {
      // absolute path is simple
      listDirectory = paths.get(0);
    }
    listPath = new Path(listDirectory);
    try {
      if (!environment.localfs().exists(listPath)) {
        // The requested path doesn't exist, so abort
        environment.stderr(listDirectory+" does not exist");
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
      FileStatus[] files = environment.localfs().listStatus(listPath);
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

}