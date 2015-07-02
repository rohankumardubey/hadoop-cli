package com.appsintheopen.hadoopcli.commands;

import org.apache.hadoop.fs.FileSystem;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class PwdCommand extends AbstractCommand {
  
  public PwdCommand(String cmd) {
    super(cmd);
  }

  @Override
  protected int validate() {
    return 0;
  }

  @Override
  protected int runCommand() {
    environment.stdout(environment.remotewd());
    return 0;
  }

  @Override
  public FileSystem getFileSystemForPathNumber(ExecutionEnvironment env, int num) {
    return null;
  }

  @Override
  public String getWorkingDirectoryForPathNumber(ExecutionEnvironment env, int num) {
    return null;
  }

}