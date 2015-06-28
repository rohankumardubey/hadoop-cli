package com.appsintheopen.hadoopcli.commands;

import java.util.ArrayList;

public class CommandFactory {

  public AbstractCommand getCommand(String cmd) throws UnknownCommandException {
    ArrayList<Token> tokens = new CommandTokenizer().tokenize(cmd).tokens;
    String potential = "";
    if (tokens.size() > 0) {
      potential = tokens.get(0).value;
    }
    
    switch(potential) {
      case "ls": return new LsCommand(cmd);
      default: throw new UnknownCommandException(potential);
    }
  }
  
}