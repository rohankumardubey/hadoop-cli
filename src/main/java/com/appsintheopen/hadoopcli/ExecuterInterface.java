package com.appsintheopen.hadoopcli;

import org.apache.hadoop.fs.FileSystem;

import com.appsintheopen.hadoopcli.commands.AbstractCommand;

public interface ExecuterInterface {
  
  public String localwd();
  public void setLocalwd(String path);
  public String remotewd();
  public void setRemotewd(String path);
  
  public FileSystem localfs();
  public FileSystem remotefs();
  public int execute(AbstractCommand cmd);
  
  
}
