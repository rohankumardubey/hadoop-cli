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
      case "lls": return new LlsCommand(cmd);
      case "pwd": return new PwdCommand(cmd);
      case "lpwd": return new LPwdCommand(cmd);
      default: throw new UnknownCommandException(potential);
    }
  }
  
}