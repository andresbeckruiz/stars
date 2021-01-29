package edu.brown.cs.abeckrui.stars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

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
                    String[] command = curr_line.split(" ");
                    if (_actions.containsKey(command[0])){
                        _actions.get(command[0]).run(command);
                    }
                    //command doesn't exist, throw error
                    else {
                        System.err.println("Error: Command does not exist");
                    }
                }
                //this happens when we reach EOD, terminate loop
                else {
                    break;
                }
            }
            //handle error for readLine method
            catch(IOException e){
                System.err.println("Error: IOException in REPL" + e);
            }
        }
    }

}
