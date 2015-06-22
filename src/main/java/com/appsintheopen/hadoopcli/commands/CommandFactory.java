package com.appsintheopen.hadoopcli.commands;

public class CommandFactory {

  public AbstractCommand getCommand(String cmd) throws UnknownCommandException {
    String[] parts = cmd.trim().split("\\s+", 2);
    String potential = parts[0];

    switch(potential) {
      case "ls": return new LsCommand(cmd);
      default: throw new UnknownCommandException(potential);
    }
  }
  
}