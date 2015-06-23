package com.appsintheopen.hadoopcli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.appsintheopen.hadoopcli.commands.AbstractCommand;
import com.appsintheopen.hadoopcli.commands.CommandFactory;
import com.appsintheopen.hadoopcli.commands.CommandTokenizer;
import com.appsintheopen.hadoopcli.commands.Token;
import com.appsintheopen.hadoopcli.commands.UnknownCommandException;

import jline.console.completer.Completer;

public class PathCompleter implements Completer {
  
  protected String completionPath;
  protected int pathNumber;

  @Override
  public int complete(String buffer, int cursor, List<CharSequence> candidates) {
    // Treat a null buffer as empty string
    if (buffer == null) {
      buffer = "";
    }
    
    // For now, assume we have a command
    AbstractCommand cmd;
    try {
      cmd = new CommandFactory().getCommand(buffer); 
    } catch (UnknownCommandException e) {
      // TODO - what is the correct way to bail out early?
      return 0;
    }
    // If we get here, at least we have a command. Now we might have a path or not, 
    // eg "ls " or "ls" or "ls foo" only one really has a path, but the others have
    // a path of the cwd. Other commands could have two paths, eg "mv foo bar"
    // If there are any paths present, the command will have them stored.
    
    setCompletionPath(buffer, cursor);
    System.out.println(completionPath+pathNumber);
    return 1;
    
  }
  
  private boolean setCompletionPath(String buffer, int cursor) {
    CommandTokenizer tok = new CommandTokenizer();
    ArrayList<Token> tokens = tok.tokenize(buffer).tokens;
    
    int     tokenNumber = -1;
    completionPath = null;
    pathNumber  = -1;
    
    for (int i = tokens.size() - 1; i >= 0; i--) {
      Token t = tokens.get(i);
      if (cursor - 1 > t.endPos) {
        // it lies between two tokens or past the end of all tokens
        // should be able to complete
        tokenNumber = i;
        // Bump the pathNumber by 1 here, so that when we count the paths
        // later, it gets pushed to one higher than the total number of path tokens.
        pathNumber ++;
        completionPath = "";
        break;
      }
      if (cursor - 1 == t.endPos) {
        // the cursor is against this token - if its a path its the one we want
        if (t.type == "path") {
          tokenNumber    = i;
          completionPath = t.value;
          break;
        }
      }
      if (cursor >= t.startPos && cursor <= t.endPos) {
        // its within a token - cannot complete
        break;
      }
      if (t.type != "path") {
        // we have hit a switch or command, so cannot complete
        break;
      }
    }
    
    for (int i = 0; i <= tokenNumber; i++) {
      if (tokens.get(i).type == "path") {
        pathNumber ++;
      }
    }
  
    if (tokenNumber >= 0) {
      return true;
    } else {
      return false;
    }
    
  }
  

  
}
