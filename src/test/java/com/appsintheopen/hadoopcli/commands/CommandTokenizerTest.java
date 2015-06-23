package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class CommandTokenizerTest {
  
//  @Before
//  public void setup() throws IOException {
//  }
  
  @Test
  public void testCommandTokenizes() {
    CommandTokenizer t = new CommandTokenizer();
    //          012345678901234567890
    t.tokenize("ls -abc -d /foo /bar");
    ArrayList<Token> tokens = t.tokens;
    assertEquals(9, tokens.size());
    assertEquals("ls", tokens.get(0).value);
    assertEquals("command", tokens.get(0).type);
    assertEquals(0, tokens.get(0).startPos);
    assertEquals(1, tokens.get(0).endPos);
    
    assertEquals("-", tokens.get(1).value);
    assertEquals("switch", tokens.get(1).type);
    assertEquals(3, tokens.get(1).startPos);
    assertEquals(3, tokens.get(1).endPos);

    assertEquals("a", tokens.get(2).value);
    assertEquals("switch", tokens.get(2).type);
    assertEquals(4, tokens.get(2).startPos);
    assertEquals(4, tokens.get(2).endPos);
    
    assertEquals("d", tokens.get(6).value);
    assertEquals("switch", tokens.get(6).type);
    assertEquals(9, tokens.get(6).startPos);
    assertEquals(9, tokens.get(6).endPos);

    assertEquals("/foo", tokens.get(7).value);
    assertEquals("path", tokens.get(7).type);
    assertEquals(11, tokens.get(7).startPos);
    assertEquals(14, tokens.get(7).endPos);
    
    assertEquals("/bar", tokens.get(8).value);
    assertEquals("path", tokens.get(8).type);
    assertEquals(16, tokens.get(8).startPos);
    assertEquals(19, tokens.get(8).endPos);
    
  }


}
