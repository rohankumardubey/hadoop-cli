package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LlsCommandTest {
  
  TestExecutionEnvironment env;
  
  @Before
  public void setup() throws IOException {
    env = new TestExecutionEnvironment();
  }
  
  @Test
  public void testSwitchesExtracted() {
    LlsCommand cmd = new LlsCommand("lls -l -t -abc /somepath other/path");
    assertTrue(cmd.switches.containsKey('l'));
    assertTrue(cmd.switches.containsKey('t'));
    assertTrue(cmd.switches.containsKey('a'));
    assertTrue(cmd.switches.containsKey('b'));
    assertTrue(cmd.switches.containsKey('c'));
  }

  @Test
  public void testPathsExtracted() {
    LlsCommand cmd = new LlsCommand("lls -l -t -abc /somepath other/path");
    assertEquals(cmd.paths.get(0).value, "/somepath");
    assertEquals(cmd.paths.get(0).startPos, 15);
    assertEquals(cmd.paths.get(0).endPos, 23);

    assertEquals(cmd.paths.get(1).value, "other/path");
    assertEquals(cmd.paths.get(1).startPos, 25);
    assertEquals(cmd.paths.get(1).endPos, 34);
    
  }
  
  @Test
  public void testPathPositionsCorrectWithLeadingWhiteSpace() {
    LlsCommand cmd = new LlsCommand("  lls -l -t -abc /somepath other/path");
    assertEquals(cmd.paths.get(0).value, "/somepath");
    assertEquals(cmd.paths.get(0).startPos, 17);
    assertEquals(cmd.paths.get(0).endPos, 25);

    assertEquals(cmd.paths.get(1).value, "other/path");
    assertEquals(cmd.paths.get(1).startPos, 27);
    assertEquals(cmd.paths.get(1).endPos, 36);
  }
    
  @Test
  public void testListingCurrentDirectory() throws IOException {
    LlsCommand cmd = new LlsCommand("lls -l -t");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingFileCurrentDirectory() throws IOException {
    LlsCommand cmd = new LlsCommand("lls -l -t .classpath");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingDirectoryThatDoesNotExist() throws IOException {
    LlsCommand cmd = new LlsCommand("lls -l -t /dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }

  @Test
  public void testListingRelativeDirectoryThatDoesNotExist() throws IOException {
    LlsCommand cmd = new LlsCommand("lls -l -t dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }
  
}
