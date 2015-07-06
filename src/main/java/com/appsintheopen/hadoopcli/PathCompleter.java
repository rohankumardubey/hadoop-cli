package com.appsintheopen.hadoopcli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.appsintheopen.hadoopcli.commands.AbstractCommand;
import com.appsintheopen.hadoopcli.commands.CommandFactory;
import com.appsintheopen.hadoopcli.commands.CommandTokenizer;
import com.appsintheopen.hadoopcli.commands.Token;
import com.appsintheopen.hadoopcli.commands.UnknownCommandException;

import jline.console.completer.Completer;

public class PathCompleter implements Completer {

  protected ExecutionEnvironment env;
  
  protected String originalPath;
  protected String completionPath;
  protected int    completionPathStartPosition;
  protected String decodedPath;
  protected int pathNumber;
  protected FileSystem fs;
  protected String cwd;
  
  public PathCompleter(ExecutionEnvironment e) {
    env = e;
  }

  @Override
  public int complete(String buffer, int cursor, List<CharSequence> candidates) {
    // Treat a null buffer as empty string
    if (buffer == null) {
      buffer = "";
    }
    
    // First see if we have a valid command. If not, silently fail
    AbstractCommand cmd;
    try {
      cmd = new CommandFactory().getCommand(buffer); 
    } catch (UnknownCommandException e) {
      return cursor;
    }

    // Figure out where in the buffer the cursor is, and if it is valid to complete
    // It if valid if the cursor is against a path or past the end of a path or between
    // two paths etc.
    if (!setCompletionPath(buffer, cursor)) {
      return cursor;
    }
    // Now we are pretty sure we have a valid completion scenario. Need to ask the command
    // which filesystem is valid for the given path number
    fs = cmd.getFileSystemForPathNumber(env, pathNumber);
    cwd = cmd.getWorkingDirectoryForPathNumber(env, pathNumber);
    
    if (fs == null || cwd == null) {
      // Its a command that doesn't work on paths, eg pwd
      return cursor;
    }
    
    FileStatus[] files;
    try {
      files = getFiles();
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
        
    if (files.length == 0) {
      // There are no valid paths under the given path, so nothing to complete.
      return cursor;
    }
        
    int matches = 0;
    
    for (int i = 0; i < files.length; i++) {
      if (removeScheme(files[i].getPath().toString()).startsWith(decodedPath)) {
        matches ++;
      }
    }

    for (int i = 0; i < files.length; i++) {
      String pathName = removeScheme(files[i].getPath().toString());
      if (pathName.startsWith(decodedPath)) {
        CharSequence name = files[i].getPath().getName() + (matches == 1 && files[i].isDirectory() ? "/" : " ");
        candidates.add(name);
      }
    }

    if (originalPath.isEmpty()) {
      return cursor;
    } else {
      final int index = originalPath.lastIndexOf("/");
      return completionPathStartPosition + index + 1;
    }
  }
  
  public FileStatus[] getFiles() throws IOException {
    
    Path   directory;
    Path thePath = new Path(completionPath);
    
    if (thePath.isRoot()) {
      directory = thePath;
    } else {
      // If the original path string has a trailing "/" then we know it was already
      // a directory. When the string is turned into a "path" the trailing / is lost.
      // This is why the following statement users both thePath and path :-/
      if (fs.isDirectory(thePath) && completionPath.endsWith("/")) {
        directory = thePath;
      } else {
        directory = thePath.getParent();
      }
    }
        
    try {
      if (fs.exists(directory)) {
        //Path[] paths = FileUtil.stat2Paths(fs.listStatus(directory));
        return fs.listStatus(directory);
      } else {
        return new FileStatus[0];
      }
    } catch (org.apache.hadoop.security.AccessControlException e) {
      // If you don't have permission, then the file 'doesn't exist'
      return new FileStatus[0];
    }
  }
    
  private String removeScheme(String path) {
    return Path.getPathWithoutSchemeAndAuthority(new Path(path)).toString();
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
        completionPathStartPosition = cursor;
        break;
      }
      if (cursor - 1 == t.endPos) {
        // the cursor is against this token - if its a path its the one we want
        if (t.type == "path") {
          tokenNumber    = i;
          completionPath = t.value;
          completionPathStartPosition = t.startPos;
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
      // The original path, which is not absolutized, is needed to calculate
      // the cursor position for completions.
      originalPath   = completionPath;
      completionPath = completionPath.isEmpty() ? cwd+"/" : completionPath.startsWith("/") ? completionPath : cwd+"/"+completionPath;
      // DecodedPath will not have an . or .. sections. They are removed when a string becomes a path
      // This complicates checking if returned strings match the original path.
      decodedPath    =  removeScheme(new Path(completionPath).toString());
      return true;
    } else {
      return false;
    }
    
  }
  
}