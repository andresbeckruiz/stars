package edu.brown.cs.abeckrui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains all the logic for the REPL.
 */
public class Repl {

  private HashMap<String, Method> actions;
  private BufferedReader bufferedReader;


  /**
   * This constructor initializes our acitions HashMap and bufferedReader.
   * @param acts HashMap that maps commands to their respective method
   */
  public Repl(HashMap<String, Method> acts) {
    actions = acts;
    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  }


  /**
   * This method contains the REPL loop.
   */
  public void read() {
    //allows loop to run infinitely
    while (true) {
      try {
        String currLine = bufferedReader.readLine();
        //check to see that theres a valid command
        if (currLine != null) {
          //we don't want to do anything if line is empty, move to next line
          if (currLine.equals("")) {
            continue;
          }
          String[] command = this.splitString(currLine);
          if (actions.containsKey(command[0])) {
            actions.get(command[0]).run(command);
            //command doesn't exist, throw ERROR
          } else {
            System.err.println("ERROR: Command does not exist.");
          }
          //this happens when we reach EOD, terminate loop
        } else {
          break;
        }
      //handle ERROR for readLine method
      } catch (IOException e) {
        System.err.println("ERROR: IOException in REPL");
      }
    }
  }

  /**
   * Helper method for splitting string. This method is static so it can be used by
   * frontend handlers.
   * @param currLine representing String you want to parse
   * @return String array representing parsed String
   */
  public static String[] splitString(String currLine) {
    List<String> matchList = new ArrayList<String>();
    /**
     * regex for splitting string at spaces excluding quotes
     * see this link: https://stackoverflow.com/questions/366202/regex-for-splitting-a
     * -string-using-space-when-not-surrounded-by-single-or-double/366532
     */
    Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
    Matcher regexMatcher = regex.matcher(currLine);
    while (regexMatcher.find()) {
      matchList.add(regexMatcher.group());
    }
    int size = matchList.size();
    String[] command = new String[size];
    return matchList.toArray(command);
  }

}
