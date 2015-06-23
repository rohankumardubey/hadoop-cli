package com.appsintheopen.hadoopcli.commands;

import java.util.ArrayList;

public class CommandTokenizer {
  
  public ArrayList<Token> tokens = new ArrayList<Token>(); 
  
  public CommandTokenizer tokenize(String str) {
    Boolean isKeyword = true;
    int     i         = 0;
    int     len       = str.length();

    while (i < len) {
      char c = str.charAt(i);
      if (c == ' ') {
        // just consume spaces, ie do nothing
        i++;
        continue;
      }
      if (isKeyword) {
        // Consume the keyword (ie the command name) and throw away
        // as it is not required.
        int keyStart = i;
        while(i < len && str.charAt(i) != ' ') {
          i++;
        }
        isKeyword = false;
        tokens.add(new Token("command", str.substring(keyStart, i), keyStart, i-1));
        continue;
      }
      if (c == '-') {
        // Its a switch until we see a space or end of string
        tokens.add(new Token("switch", str.substring(i, i+1), i, i));
        i++;
        while ( i < len && str.charAt(i) != ' ') {
          tokens.add(new Token("switch", str.substring(i, i+1), i, i));
          i++;
        }
        continue;
      }
      if (c != ' ') {
        // Its not a keyword or a switch then it has to be a path
        int pathStart = i;
        i++;
        while(i<len && str.charAt(i) != ' ') {
          i++;
        }
        tokens.add(new Token("path", str.substring(pathStart, i), pathStart, i-1));
        continue;
      }
    }
    return this;
  }
    
}
