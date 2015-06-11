package com.appsintheopen.hadoopcli.commands;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class LsCommand extends AbstractCommand {
  
  public LsCommand(String cmd) {
    super(cmd);
  }

  @Override
  public int execute(ExecutionEnvironment env) {
    validate();
    return 0;
  }
  
  @Override
  protected int validate() {
    return 0;
  }

  @Override
  protected int runCommand() {
    return 0;
  }

}
