package edu.brown.cs.abeckrui.mockaroo;

import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.stars.Csv;
import java.util.Date;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MockPersonRunner implements Method {

    private List<List<String>> _personData;

    public MockPersonRunner(){
        _personData = new ArrayList<>();
    }

    public static boolean checkData(String [] data) {
        //First Name, Datetime, Email Address, and Street Address are required
        if (data.length != 6) {
            System.err.println("ERROR: Incorrect number of data fields for line:" + data[0]);
            return false;
        }
        if (!(data[0] instanceof String) || data[0].equals("")){
            System.err.println("ERROR: First name is required to be a nonempty string");
            return false;
        }
        if (!(data[1] instanceof String)){
            System.err.println("ERROR: Last name must be a string");
            return false;
        }
        if (!(data[2] instanceof String) || data[2].equals("")){
            System.err.println("ERROR: Date is required to be a nonempty string");
            return false;
        }
        //email must contain @ to be valid
        if (!(data[3] instanceof String) || data[3].equals("") || !data[3].contains("@")){
            System.err.println("ERROR: Email is required to be a nonempty, valid email");
            return false;
        }
        if (!(data[4] instanceof String)){
            System.err.println("ERROR: Gender must be a string");
            return false;
        }
        if (!(data[5] instanceof String)){
            System.err.println("ERROR: Street address must be a string");
            return false;
        }
        return true;
    }

    @Override
    public void run(String [] line){
        this.parseCSV(line);
    }

    private void parseCSV(String [] line){
        if (line.length != 2){
            System.err.println("ERROR: Incorrect number of args for command. 2 are expected");
        }
        else {
            Csv testParser = new Csv(new File(line[1]));
            //first check if data is invalid. if not, set it equal to _starData
            if (testParser.parse() != null) {
                Csv parser = new Csv(new File(line[1]));
                _personData = parser.parse();
                for (int i = 0; i < _personData.size(); i++){
                    String firstName = _personData.get(i).get(0);
                    String lastName = _personData.get(i).get(1);
                    String date = _personData.get(i).get(2);
                    String email = _personData.get(i).get(3);
                    String gender = _personData.get(i).get(4);
                    String address = _personData.get(i).get(5);
                    MockPerson person = new MockPerson(firstName,lastName,date,email,gender,address);
                    person.printFields();
                }
            }
        }
    }
}
