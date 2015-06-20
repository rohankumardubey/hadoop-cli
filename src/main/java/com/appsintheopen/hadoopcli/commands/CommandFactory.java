package com.appsintheopen.hadoopcli.commands;

public class CommandFactory {

  public AbstractCommand getCommand(String cmd) throws UnknownCommandException {
    String[] parts = cmd.trim().split("\\s+", 2);
    String potential = parts[0];
    // This handles the case where the command has no parameters. In that case
    // the parts array will not have an element at index [1] (arrayOutOfBounds)
    String params = "";
    if (parts.length == 2) {
      params = parts[1];
    }
    switch(potential) {
      case "ls": return new LsCommand(params);
      default: throw new UnknownCommandException(potential);
    }
  }
  
}