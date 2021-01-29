package edu.brown.cs.abeckrui.stars;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Stars implements Method{

    private List<List<String>> _starData;

    public Stars() {
        _starData = new ArrayList<>();
    }


    @Override
    public void run(String [] line){
        switch(line[0]){
            case "stars":
                this.parseCSV(line);
                break;
            case "naive_neighbors":
                //System.out.println("naive_neighbors");
                break;
            case "naive_radius":
                //System.out.println("naive_radius");
                break;
            default:
                //System.out.println("mock");
        }
    }

    private void parseCSV(String [] line){
        if (line.length != 2){
            System.err.println("Error: Incorrect number of args for command. 2 are expected");
        }
        else {
            Csv testParser = new Csv(new File(line[1]));
            //first check if data is invalid. if not, set it equal to _starData
            if (testParser.parse() != null) {
                Csv parser = new Csv(new File(line[1]));
                _starData = parser.parse();
                //output message
                System.out.println("Read " + _starData.size() + " stars from " + line[1]);
            }
        }
    }

    private void naive_neighbors(String [] line){
        if (line.length == 3){
            //checking that second argument is an int
            try {
                int test = Integer.parseInt(line[1]);
            } catch (NumberFormatException e){
                System.err.println("Error: Number of neighbors must be an int");
                return;
            }
            //checking that third argument is a nonempty string
            if (!(line[2] instanceof String) && (line[2] != "")) {
                System.err.println("Error: Name must be a nonempty string");
                return;
            }


        }
        else if (line.length == 5){
            //checking that rest of arguments are integers
            for (int i = 1; i < 5; i++){
                try {
                    int test = Integer.parseInt(line[i]);
                } catch (NumberFormatException e){
                    System.err.println("Error: Coordinates or neighbor number must be int");
                    return;
                }
            }
        }
        else {
            System.err.println("Error: Incorrect number or args provided. 3 or 5 expected for naive_neighbors");
        }
    }

    private void naive_radius(String [] line){
        if (line.length == 3){
            //checking that second argument is an int
            try {
                int test = Integer.parseInt(line[1]);
            } catch (NumberFormatException e){
                System.err.println("Error: Number of neighbors must be an int");
                return;
            }
            //checking that third argument is a nonempty string
            if (!(line[2] instanceof String) && (line[2] != "")) {
                System.err.println("Error: Name must be a nonempty string");
                return;
            }


        }
        else if (line.length == 5){
            //checking that rest of arguments are integers
            for (int i = 1; i < 5; i++){
                try {
                    int test = Integer.parseInt(line[i]);
                } catch (NumberFormatException e){
                    System.err.println("Error: Coordinates or neighbor number must be int");
                    return;
                }
            }
        }
        else {
            System.err.println("Error: Incorrect number or args provided. 3 or 5 expected for naive_neighbors");
        }
    }


    /**
     * This
     * @param data
     * @return boolean indicating true if data is valid and false if not
     */
    public static boolean checkData(String[] data) {
        if (data.length != 5) {
            System.err.println("Error: Incorrect number of data fields for line:" + data);
        }
        for (int i = 0; i < data.length; i++) {
            //data type checking
            if (i == 0) {
                try {
                    int test = Integer.parseInt(data[i]);
                } catch (NumberFormatException e){
                    System.err.println("Error: Star ID must be an int ");
                    return false;
                }
            } else if (i == 1) {
                if (!(data[i] instanceof String)) {
                    System.err.println("Error: Starname must be a string" + data);
                    return false;
                }
            } else {
                try {
                    double test = Double.parseDouble(data[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Coordinates must be a double" + data);
                }
            }
        }
        return true;
    }
}
