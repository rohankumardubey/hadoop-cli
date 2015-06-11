package com.appsintheopen.hadoopcli.commands;

import java.util.ArrayList;
import java.util.HashMap;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public abstract class AbstractCommand {
  
  public String commandString;
  public HashMap<Character, String> switches = new HashMap<Character, String>();
  public ArrayList<String>          paths    = new ArrayList<String>();
  private ExecutionEnvironment      environment;
  
  public AbstractCommand(String cmd) {
    commandString = cmd;
    tokenize();
  }
  
  // Implement any validation checks
  abstract protected int validate();
  // Implement the actual logic to run the command
  abstract protected int runCommand();
  
  
  public int execute(ExecutionEnvironment env) {
    environment = env;
    int status = validate();
    if (0 == status) {
      return runCommand();
    } else {
      return status;
    }
  }
  
  private void tokenize() {
    // This is a naive implementation - switches cannot have 
    // further options like "-a foo -b bar"
    String[] parts = commandString.split("\\s+");  
    for (String p : parts) {
      if (p.startsWith("-")) {
        tokenizeSwitch(p);
      } else {
        // its a path
        paths.add(p);
      }
    }
  }
  
  // Splits a series of switches into their single character
  // switches. Eg -ltr becomes 3 switches
  private void tokenizeSwitch(String str) {
    // Index from 1 to skip the leading dash
    for (int i=1; i<str.length(); i++) {
      switches.put(str.charAt(i), "");
    }
  }

}
