package com.appsintheopen.hadoopcli.commands;

import java.util.ArrayList;
import java.util.HashMap;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public abstract class AbstractCommand {
  
  public String commandString;
  public HashMap<Character, String> switches = new HashMap<Character, String>();
  public ArrayList<CommandPath>          paths    = new ArrayList<CommandPath>();
  protected ExecutionEnvironment      environment;
  
  public AbstractCommand(String cmd) {
    commandString = cmd;
    tokenize();
  }
  
  // Implement any validation checks
  abstract protected int validate();
  // Implement the actual logic to run the command
  abstract protected int runCommand();
  
  protected String pathNumber(int i) {
    return paths.get(i).path;
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
  
  private void tokenize() {
    Boolean isKeyword = true;
    int     i         = 0;
    int     len       = commandString.length();

    while (i < len) {
      char c = commandString.charAt(i);
      if (c == ' ') {
        // just consume spaces, ie do nothing
        i++;
        continue;
      }
      if (isKeyword) {
        // Consume the keyword (ie the command name) and throw away
        // as it is not required.
        while(i < len && commandString.charAt(i) != ' ') {
          i++;
        }
        isKeyword = false;
        continue;
      }
      if (c == '-') {
        // Its a switch until we see a space or end of string
        i++;
        while ( i < len && commandString.charAt(i) != ' ') {
          switches.put(commandString.charAt(i), "");
          i++;
        }
        continue;
      }
      if (c != ' ') {
        // Its not a keyword or a switch then it has to be a path
        int pathStart = i;
        i++;
        while(i<len && commandString.charAt(i) != ' ') {
          i++;
        }
        paths.add(new CommandPath(pathStart, i-1, commandString.substring(pathStart, i)));
        continue;
      }
    }
  }

}