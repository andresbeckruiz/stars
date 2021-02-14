package edu.brown.cs.abeckrui;

import edu.brown.cs.abeckrui.mockaroo.MockPersonLogic;
import edu.brown.cs.abeckrui.stars.StarsLogic;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all the CSV logic and parsing.
 */
public class Csv {

  private BufferedReader bufferedReader;

  /**
   * This instantiates the CSV object by passing in a filereader into our bufferedReader.
   * @param file representing the file to parse
   */
  public Csv(File file) {
    try {
      bufferedReader = new BufferedReader(new FileReader(file));
     //catch exception from initializing filereader
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: File not found");
    }
  }

  /**
   * This method parses the CSV file.
   * @return a list of string lists representing the parse data. Null is returned if data invalid
   */
  public List<List<String>> parse() {
    //checks if bufferedReader was able to be initialized or not (if file is invalid, it is not)
    if (bufferedReader == null) {
      return null;
    }
    //boolean representing if we are reading the mock data or not
    boolean mock = false;
    List<List<String>> data = new ArrayList<>();
    //have to read first line to make sure it matches StarID,ProperName,X,Y,Z format
    try {
      String firstLine = bufferedReader.readLine();
      if (firstLine == null) {
        System.out.println("ERROR: File is empty");
        return null;
      }
      String[] first = firstLine.split(",");
      if (first.length == 5 || first.length == 6) {
        if (first.length == 5) {
          //checking first line of file
          if (!first[0].equals("StarID") || !first[1].equals("ProperName")
                  || !first[2].equals("X") || !first[3].equals("Y")
                  || !first[4].equals("Z")) {
            System.err.println("ERROR: First line of data does not match 'StarID,ProperName,"
                    + "X,Y,Z'");
            return null;
          }
        } else {
          mock = true;
          //checking first line of file
          if (!first[0].equals("First Name") || !first[1].equals("Last Name")
                  || !first[2].equals("Datetime") || !first[3].equals("Email Address")
                  || !first[4].equals("Gender") || !first[5].equals("Street Address")) {
            System.err.println("ERROR: First line of data does not match 'First Name"
                    + ",Last Name,Datetime,Email Address,Gender,Street Address'");
            return null;
          }
        }
      } else {
        System.err.println("ERROR: First line of data is invalid");
        return null;
      }
    } catch (IOException e) {
      System.err.println("ERROR: IOException in CSV");
      return null;
    }
    while (true) {
      try {
        String currLine = bufferedReader.readLine();
        //check to see if there are lines left to read
        if (currLine != null) {
          String[] currData = currLine.split(",");
          List<String> currDataList = new ArrayList<String>();
          //see stars class for specific
          if (mock) {
            if (MockPersonLogic.checkData(currData)) {
              //converting array to list
              for (int i = 0; i < currData.length; i++) {
                currDataList.add(currData[i]);
              }
              data.add(currDataList);
            } else {
              return null;
            }
          } else {
            if (StarsLogic.checkData(currData)) {
              //converting array to list
              for (int i = 0; i < currData.length; i++) {
                currDataList.add(currData[i]);
              }
              data.add(currDataList);
            } else {
              return null;
            }
          } //this happens when we reach end of file, break
        } else {
          break;
        }
       //handle ERROR for readLine method
      } catch (IOException e) {
        System.err.println("ERROR: IOException in CSV" + e);
        return null;
      }
    }
    return data;
  }
}
