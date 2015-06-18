package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LsCommandTest {
  
  TestExecutionEnvironment env;
  
  @Before
  public void setup() throws IOException {
    env = new TestExecutionEnvironment();
  }
  
  @Test
  public void testSwitchesExtracted() {
    LsCommand cmd = new LsCommand("-l -t -abc /somepath other/path");
    assertTrue(cmd.switches.containsKey('l'));
    assertTrue(cmd.switches.containsKey('t'));
    assertTrue(cmd.switches.containsKey('a'));
    assertTrue(cmd.switches.containsKey('b'));
    assertTrue(cmd.switches.containsKey('c'));
  }

  @Test
  public void testPathsExtracted() {
    LsCommand cmd = new LsCommand("-l -t -abc /somepath other/path");
    assertEquals(cmd.paths.get(0), "/somepath");
    assertEquals(cmd.paths.get(1), "other/path");
  }
  
  @Test
  public void testListingCurrentDirectory() throws IOException {
    LsCommand cmd = new LsCommand("-l -t");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingFileCurrentDirectory() throws IOException {
    LsCommand cmd = new LsCommand("-l -t .classpath");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingDirectoryThatDoesNotExist() throws IOException {
    LsCommand cmd = new LsCommand("-l -t /dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }

  @Test
  public void testListingRelativeDirectoryThatDoesNotExist() throws IOException {
    LsCommand cmd = new LsCommand("-l -t dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }
  
}
