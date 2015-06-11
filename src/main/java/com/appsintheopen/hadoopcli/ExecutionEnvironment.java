package com.appsintheopen.hadoopcli;

import org.apache.hadoop.fs.FileSystem;
import com.appsintheopen.hadoopcli.commands.AbstractCommand;

public class ExecutionEnvironment implements ExecuterInterface
{
  
  private String localwd;
  private String remotewd;
  private FileSystem localfs;
  private FileSystem remotefs;
  
  @Override
  public String localwd() {
    return localwd;
  }
  
  @Override
  public void setLocalwd(String path) {
    localwd = path;
  }
  
  @Override
  public String remotewd() {
    return remotewd;
  }
  
  @Override
  public void setRemotewd(String path) {
    remotewd = path;
  }
  
  @Override
  public FileSystem remotefs() {
    return remotefs;
  }
  
  public void setRemotefs(FileSystem fs) {
    remotefs = fs;
  }
   
  @Override
  public FileSystem localfs() {
    return localfs;
  }
    
  public void setLocalfs(FileSystem fs) {
    localfs = fs;
  }
  
  @Override
  public int execute(AbstractCommand cmd) {
    return cmd.execute(this);
  }

}
