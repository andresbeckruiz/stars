package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NEED TO COMMENT MORE (class comments as well) CHECK STYLE GUIDE!!!!!!!!!!!!!!!!
 */

public class Repl {

    private HashMap<String, Method> _actions;
    private BufferedReader _bufferedReader;


    /**
     *
     * @param actions
     *
     * HashMap that maps commands to their respective method
     */

    public Repl(HashMap<String, Method> actions){
        _actions = actions;
        _bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    /**
     * This method contains the REPL loop
     */
    public void read(){
        //allows loop to run infinitely
        while (true){
            try{
                String curr_line = _bufferedReader.readLine();
                //check to see that theres a valid command
                if (curr_line != null){
                    //we don't want to do anything if line is empty, move to next line
                    if (curr_line.equals("")) {
                        continue;
                    }
                    /**
                     * regex for splitting string at spaces excluding quotes
                     * see this link: https://stackoverflow.com/questions/366202/regex-for-splitting-a
                     * -string-using-space-when-not-surrounded-by-single-or-double/366532
                     */
                    List<String> matchList = new ArrayList<String>();
                    Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
                    Matcher regexMatcher = regex.matcher(curr_line);
                    while (regexMatcher.find()) {
                        matchList.add(regexMatcher.group());
                    }
                    int size = matchList.size();
                    String[] command = new String[size];
                    matchList.toArray(command);
                    if (_actions.containsKey(command[0])){
                        _actions.get(command[0]).run(command);
                    }
                    //command doesn't exist, throw ERROR
                    else {
                        System.err.println("ERROR: Command does not exist");
                    }
                }
                //this happens when we reach EOD, terminate loop
                else {
                    break;
                }
            }
            //handle ERROR for readLine method
            catch(IOException e){
                System.err.println("ERROR: IOException in REPL" + e);
            }
        }
    }

}
