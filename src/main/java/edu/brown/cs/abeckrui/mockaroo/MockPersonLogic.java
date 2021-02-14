package edu.brown.cs.abeckrui.mockaroo;

import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.Csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the logic of checking and parsing data for each MockPerson.
 */
public class MockPersonLogic implements Method {

  private List<List<String>> personData;

  /**
   * Constructor initialized personData instance variable.
   */
  public MockPersonLogic() {
    personData = new ArrayList<>();
  }

  /**
   * This method recieves data and checks that each field is valid.
   * @param data an array of strings from the mock data file
   * @return boolean representing if data is valid or not (true if valid)
   */
  public static boolean checkData(String[] data) {
    //First Name, Datetime, Email Address, and Street Address are required
    if (data.length != 6) {
      System.err.println("ERROR: Incorrect number of data fields for line:" + data[0]);
      return false;
    }
    if (!(data[0] instanceof String) || data[0].equals("")) {
      System.err.println("ERROR: First name is required to be a nonempty string");
      return false;
    }
    if (!(data[1] instanceof String)) {
      System.err.println("ERROR: Last name must be a string");
      return false;
    }
    if (!(data[2] instanceof String) || data[2].equals("")) {
      System.err.println("ERROR: Date is required to be a nonempty string");
      return false;
    }
    /**
     * Link to this regex checker code: https://www.tutorialspoint.com
     * /how-to-validate-given-date-formats-like-mm-dd-yyyy-using-regex-in-java
     */
    String date = data[2];
    //checks for dd/mm/yyyy format
    String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    //Creating a pattern object
    Pattern pattern = Pattern.compile(regex);
    //Matching the compiled pattern in the String
    Matcher matcher = pattern.matcher(date);
    boolean bool = matcher.matches();
    if (!bool) {
      System.err.println("ERROR: Date is not valid. Please use dd/mm/yyyy format");
      return false;
    }
    //email must contain @ to be valid
    if (!(data[3] instanceof String) || data[3].equals("") || !data[3].contains("@")) {
      System.err.println("ERROR: Email is required to be a nonempty, valid email");
      return false;
    }
    if (!(data[4] instanceof String)) {
      System.err.println("ERROR: Gender must be a string");
      return false;
    }
    if (!(data[5] instanceof String)) {
      System.err.println("ERROR: Street address must be a string");
      return false;
    }
    return true;
  }

  @Override
  public List<String> run(String[] line) {
    List<String> toPrint = new ArrayList<>();
    this.parseCSV(line);
    return toPrint;
  }

  private void parseCSV(String[] line) {
    if (line.length != 2) {
      System.err.println("ERROR: Incorrect number of args for command. 2 are expected");
    } else {
      Csv testParser = new Csv(new File(line[1]));
      //first check if data is invalid. if not, set it equal to _starData
      if (testParser.parse() != null) {
        Csv parser = new Csv(new File(line[1]));
        personData = parser.parse();
        for (int i = 0; i < personData.size(); i++) {
          String firstName = personData.get(i).get(0);
          String lastName = personData.get(i).get(1);
          String date = personData.get(i).get(2);
          String email = personData.get(i).get(3);
          String gender = personData.get(i).get(4);
          String address = personData.get(i).get(5);
          MockPerson person = new MockPerson(firstName, lastName, date, email, gender, address);
          person.printFields();
        }
      }
    }
  }
}
