package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CommandFactoryTest {

  @Test
  public void testLsCommandCanBeCreated() throws UnknownCommandException {
    AbstractCommand cmd = new CommandFactory().getCommand("ls /foo/bar");
    assertEquals(LsCommand.class, cmd.getClass());
  }

  @Test
  public void testLsCommandCanBeCreatedWithNoPath() throws UnknownCommandException {
    AbstractCommand cmd = new CommandFactory().getCommand("ls");
    assertEquals(LsCommand.class, cmd.getClass());
  }
  
  @Test
  public void testLsCommandCanBeCreatedWithLeadingWhitespace() throws UnknownCommandException {
    AbstractCommand cmd = new CommandFactory().getCommand("   ls");
    assertEquals(LsCommand.class, cmd.getClass());
  }

  @Test(expected=UnknownCommandException.class)
  public void testUnknownCommandThrowsUnknownCommand() throws UnknownCommandException {
    AbstractCommand cmd = new CommandFactory().getCommand("notacommand  params");
  }
  
  @Test(expected=UnknownCommandException.class)
  public void testCommandWithEmptyStringThrowsUnknownCommand() throws UnknownCommandException {
    AbstractCommand cmd = new CommandFactory().getCommand("");
  }
  
}
