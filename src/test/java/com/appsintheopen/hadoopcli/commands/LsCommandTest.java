package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import org.junit.Test;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class LsCommandTest {
  
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
  public void testSimpleExecuteReturnsZero() {
    ExecutionEnvironment env = new ExecutionEnvironment();
    LsCommand cmd = new LsCommand("-l -t /somepath");
    assertEquals(cmd.execute(env), 0);
  }
  
}
