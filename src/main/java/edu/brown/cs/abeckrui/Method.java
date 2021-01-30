package edu.brown.cs.abeckrui;

/**
 * This interface allows the REPL to run commands, letting the REPL be more extensible.
 */
public interface Method {

  /**
   * This method allows each class that implements the interface to use their on run command.
   * @param line Command line parsed from REPL
   */
  void run(String[] line);

}
