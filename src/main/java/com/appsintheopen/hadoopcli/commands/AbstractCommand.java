package com.appsintheopen.hadoopcli.commands;

import java.util.ArrayList;
import java.util.HashMap;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public abstract class AbstractCommand {
  
  public String commandString;
  public HashMap<Character, Token> switches = new HashMap<Character, Token>();
  public ArrayList<Token>          paths    = new ArrayList<Token>();
  protected ExecutionEnvironment      environment;
  
  public AbstractCommand(String cmd) {
    this(new CommandTokenizer().tokenize(cmd).tokens);
  }
  
  public AbstractCommand(ArrayList<Token> tokens) {
    for (Token t : tokens) {
      if (t.type == "switch" && t.value != "-") {
        switches.put(t.value.charAt(0), t);
      }
      if (t.type == "path") {
        paths.add(t);
      }
    }
  }
  
  // Implement any validation checks
  abstract protected int validate();
  // Implement the actual logic to run the command
  abstract protected int runCommand();
  
  protected String pathNumber(int i) {
    return paths.get(i).value;
  }
  
  public int execute(ExecutionEnvironment env) {
    environment = env;
    int status = validate();
    if (0 == status) {
      return runCommand();
    } else {
      return status;
    }
  }
 
}