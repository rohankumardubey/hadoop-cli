package com.appsintheopen.hadoopcli.commands;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.appsintheopen.hadoopcli.ExecutionEnvironment;

public class TestExecutionEnvironment extends ExecutionEnvironment {

  public ArrayList<String> stdout = new ArrayList<String>();
  public ArrayList<String> stderr = new ArrayList<String>();
  protected Boolean enableOutput = false;
  
  public TestExecutionEnvironment() throws IOException {
    Configuration localConf = new Configuration();
    setLocalfs(FileSystem.get(localConf));
    setLocalwd(System.getProperty("user.dir"));
  }
  
  public void enableOutput() {
    enableOutput = true;
  }
  
  @Override
  public void stdout(String buf) {
    stdout.add(buf);
    if (enableOutput)
      System.out.print(buf+"\n");
  }

  @Override
  public void stderr(String buf) {
    stderr.add(buf);
    if (enableOutput)
      System.err.print(buf+"\n");
  }
  
}
