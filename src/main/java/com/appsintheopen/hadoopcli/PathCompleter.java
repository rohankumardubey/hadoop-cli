package com.appsintheopen.hadoopcli;

import java.util.List;

import com.appsintheopen.hadoopcli.commands.AbstractCommand;
import com.appsintheopen.hadoopcli.commands.CommandFactory;
import com.appsintheopen.hadoopcli.commands.UnknownCommandException;

import jline.console.completer.Completer;

public class PathCompleter implements Completer {

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
    
    // If there are no paths, then the previous character must be a space
    // If there are paths
    //    If current char is a space and prev is not, then complete that path
    //    Otherwise you are in the next path (cwd) or nothing if its a one path command etc.
    
    
    
    return 1;
    
  }
  

  
}
