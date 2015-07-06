package com.appsintheopen.hadoopcli.commands;

import org.apache.hadoop.fs.FileSystem;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class LsCommand extends LlsCommand {
  
  public LsCommand(String cmd) {
    super(cmd);
  }
  
  @Override
  public FileSystem getFileSystemForPathNumber(ExecutionEnvironment env, int num) {
    return env.remotefs();
  }
  
  @Override
  public String getWorkingDirectoryForPathNumber(ExecutionEnvironment env, int num) {
    return env.remotewd();
  }
  
  @Override
  protected FileSystem fs() {
    return environment.remotefs();
  }

  @Override
  protected String wd() {
    return environment.remotewd();
  }

}
