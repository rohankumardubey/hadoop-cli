package com.appsintheopen.hadoopcli.commands;

public class Token {

  public String type;
  public String value;
  public int    startPos;
  public int    endPos;
  
  public Token(String t, String v, int s, int e) {
    type = t;
    value = v;
    startPos = s;
    endPos = e;
  }
  
}
