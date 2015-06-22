package com.appsintheopen.hadoopcli.commands;

public class CommandPath {

  public int    startPosition;
  public int    endPosition;
  public String path;
  
  public CommandPath(int start, int end, String pth) {
    startPosition = start;
    endPosition   = end;
    path          = pth; 
  }
  
  public boolean isWithPath(int pos) {
    if (pos >= startPosition && pos <= endPosition) {
      return true;
    } else {
      return false;
    }
  }
  
}
