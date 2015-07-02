package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PwdCommandTest {
  
  TestExecutionEnvironment env;
  
  @Before
  public void setup() throws IOException {
    env = new TestExecutionEnvironment();
    env.setLocalwd("/home/foobar");
    env.setRemotewd("/hadoop/foobar");
  }
  
  @Test
  public void testLocalCurrentWorkingDirectoryPrinted() {
    LPwdCommand cmd = new LPwdCommand("lpwd");
    cmd.execute(env);
    assertEquals(env.stdout.get(0), "/home/foobar");
  }

  @Test
  public void testRemoteCurrentWorkingDirectoryPrinted() {
    PwdCommand cmd = new PwdCommand("pwd");
    cmd.execute(env);
    assertEquals(env.stdout.get(0), "/hadoop/foobar");
  }

  
}
