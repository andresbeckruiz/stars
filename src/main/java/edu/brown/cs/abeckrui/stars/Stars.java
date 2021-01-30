package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Method;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Stars implements Method {

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
                this.naive_neighbors(line);
                break;
            case "naive_radius":
                this.naive_radius(line);
                break;
            default:
                System.err.println("ERROR: Command for stars not found.");
                break;
        }
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
                _starData = parser.parse();
                //output message
                System.out.println("Read " + _starData.size() + " stars from " + line[1]);
            }
        }
    }

    /**
     * This method calculates 3D distance between two points
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @return distance as a double
     */
    private double calculateDistance(double x1, double y1, double z1, double x2, double y2, double z2){
        return Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2) + Math.pow((z1 - z2),2));
    }

    /**
     * Naive neighbors method
     * @param line, an array of Strings parsed by the REPL
     */
    private void naive_neighbors(String [] line){
        //boolean to let me know if a name has been queried
        boolean searchByName = false;
        String name = "";
        //checking that _starData is not empty or null
        if (_starData.size() == 0 || _starData == null){
            System.err.println("ERROR: Please load star data and try again");
            return;
        }
        if (line.length == 3 || line.length == 5){
            int neighbors = 0;
            double x = 0;
            double y = 0;
            double z = 0;
            if (line.length == 3){
                searchByName = true;
                //checking that second argument is an int
                try {
                    int test = Integer.parseInt(line[1]);
                } catch (NumberFormatException e){
                    System.err.println("ERROR: Number of neighbors must be an int");
                    return;
                }
                //checking that third argument is a nonempty string
                if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
                    System.err.println("ERROR: Name must be a nonempty string");
                    return;
                }
                name = line[2].replace("\"","");
                neighbors = Integer.parseInt(line[1]);
                boolean starFound = false;
                //searching for star with matching name
                for (int i = 0; i < _starData.size(); i++){
                    if (_starData.get(i).get(1).equals(name)){
                        starFound = true;
                        x = Double.parseDouble(_starData.get(i).get(2));
                        y = Double.parseDouble(_starData.get(i).get(3));
                        z = Double.parseDouble(_starData.get(i).get(4));
                    }
                }
                //print ERROR and exit method if no star found with name provided
                if (!starFound){
                    System.err.println("ERROR: Star not found. Please check name entered");
                    return;
                }
            }
            else{
                //checking that rest of arguments are integers
                try {
                    double test = Integer.parseInt(line[1]);
                } catch (NumberFormatException e){
                    System.err.println("ERROR: Neighbor number must be int");
                    return;
                }
                for (int i = 2; i < 5; i++){
                    try {
                        double test = Double.parseDouble(line[i]);
                    } catch (NumberFormatException e){
                        System.err.println("ERROR: Coordinates must be int or double");
                        return;
                    }
                }
                neighbors = Integer.parseInt(line[1]);
                x = Double.parseDouble(line[2]);
                y = Double.parseDouble(line[3]);
                z = Double.parseDouble(line[4]);
            }
            //return nothing if neighbors is 0
            if (neighbors == 0){
                return;
            }
            //checking to see if number of neighbors > 0
            if (neighbors < 0){
                System.err.println("ERROR: Number of neighbors cannot be negative");
                return;
            }
            List<List<Double>> neighborList = new ArrayList<>();
            /**
             * this shuffle method allows tied stars to randomly be picked. Since the shuffle is random,
             * and stars that are tied in distance are always placed in front in the order, each competing
             * star has a equal random chance to be included in the final list
             */
            Collections.shuffle(_starData);
            for (int i = 0; i < _starData.size(); i++){
                //don't want to include star in list if queried by name
                if (searchByName && _starData.get(i).get(1).equals(name)){
                    continue;
                }
                double ID = Integer.parseInt(_starData.get(i).get(0));
                double currentX = Double.parseDouble(_starData.get(i).get(2));
                double currentY = Double.parseDouble(_starData.get(i).get(3));
                double currentZ = Double.parseDouble(_starData.get(i).get(4));
                //see helper method
                double currentDistance = this.calculateDistance(x,y,z,currentX,currentY,currentZ);
                List<Double> currentStar = new ArrayList<>();
                currentStar.add(ID);
                currentStar.add(currentDistance);
                boolean added = false;
                //index to know where to add later
                int addIndex = 0;
                for (int j = 0; j < neighborList.size(); j++) {
                    if (neighborList.get(j).get(1) > currentDistance){
                        addIndex = j;
                        added = true;
                        break;
                    }
                }
                if (added) {
                    neighborList.add(addIndex, currentStar);
                }
                else if (neighborList.size() < neighbors){
                    neighborList.add(currentStar);
                }
                //want to keep list at size of number of neighbors
                if (neighborList.size() > neighbors){
                    neighborList.remove(neighborList.size() - 1);
                }
            }
            //printing out stars to console
            for (int k = 0; k < neighborList.size(); k++){
                double doubleID = neighborList.get(k).get(0);
                int toPrint = (int) doubleID ;
                System.out.println(String.valueOf(toPrint));
            }
        }
        else {
            System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected for naive_neighbors");
        }
    }

    /**
     * Naive radius method
     * @param line, an array of strings parsed by the REPL
     */
    private void naive_radius(String [] line){
        //boolean to let me know if a name has been queried
        boolean searchByName = false;
        String name = "";
        //checking that _starData is not empty or null
        if (_starData.size() == 0 || _starData == null){
            System.err.println("ERROR: Please load star data and try again");
            return;
        }
        if (line.length == 3 || line.length == 5){
            double radius = 0;
            double x = 0;
            double y = 0;
            double z = 0;
            if (line.length == 3){
                searchByName = true;
                //checking that second argument is an int
                try {
                    int test = Integer.parseInt(line[1]);
                } catch (NumberFormatException e){
                    System.err.println("ERROR: Radius must be an int or double");
                    return;
                }
                //checking that third argument is a nonempty string
                if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
                    System.err.println("ERROR: Name must be a nonempty string");
                    return;
                }
                name = line[2].replace("\"","");
                radius = Double.parseDouble(line[1]);
                boolean starFound = false;
                //searching for star with matching name
                for (int i = 0; i < _starData.size(); i++){
                    if (_starData.get(i).get(1).equals(name)){
                        starFound = true;
                        x = Double.parseDouble(_starData.get(i).get(2));
                        y = Double.parseDouble(_starData.get(i).get(3));
                        z = Double.parseDouble(_starData.get(i).get(4));
                    }
                }
                //print ERROR and exit method if no star found with name provided
                if (!starFound){
                    System.err.println("ERROR: Star not found. Please check name entered");
                    return;
                }
            }
            else{
                //checking that rest of arguments are integers
                try {
                    double test = Integer.parseInt(line[1]);
                } catch (NumberFormatException e){
                    System.err.println("ERROR: Neighbor number must be int");
                    return;
                }
                for (int i = 2; i < 5; i++){
                    try {
                        double test = Integer.parseInt(line[i]);
                    } catch (NumberFormatException e){
                        System.err.println("ERROR: Coordinates must be int or double");
                        return;
                    }
                }
                radius = Double.parseDouble(line[1]);
                x = Double.parseDouble(line[2]);
                y = Double.parseDouble(line[3]);
                z = Double.parseDouble(line[4]);
            }
            //checking to see if radius > 0
            if (radius < 0){
                System.err.println("ERROR: Radius cannot be negative");
                return;
            }
            List<List<Double>> radiusList = new ArrayList<>();
            /**
             * this shuffle method allows tied stars to randomly be picked. Since the shuffle is random,
             * and stars that are tied in distance are always placed in front in the order, each competing
             * star has a equal random chance to be included in the final list
             */
            Collections.shuffle(_starData);
            for (int i = 0; i < _starData.size(); i++){
                //don't want to include star in list if queried by name
                if (searchByName && _starData.get(i).get(1).equals(name)){
                    continue;
                }
                double ID = Integer.parseInt(_starData.get(i).get(0));
                double currentX = Double.parseDouble(_starData.get(i).get(2));
                double currentY = Double.parseDouble(_starData.get(i).get(3));
                double currentZ = Double.parseDouble(_starData.get(i).get(4));
                //see helper method
                double currentDistance = this.calculateDistance(x,y,z,currentX,currentY,currentZ);
                List<Double> currentStar = new ArrayList<>();
                currentStar.add(ID);
                currentStar.add(currentDistance);
                if (currentDistance <= radius) {
                    boolean added = false;
                    //index to know where to add later
                    for (int j = 0; j < radiusList.size(); j++) {
                        if (radiusList.get(j).get(1) > currentDistance) {
                            radiusList.add(j,currentStar);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        radiusList.add(currentStar);
                    }
                }
            }
            //printing out stars to console
            for (int k = 0; k < radiusList.size(); k++){
                double doubleID = radiusList.get(k).get(0);
                int toPrint = (int) doubleID;
                System.out.println(String.valueOf(toPrint));
            }
        }
        else {
            System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected for naive_radius");
        }
    }

    /**
     * This
     * @param data
     * @return boolean indicating true if data is valid and false if not
     */
    public static boolean checkData(String[] data) {
        if (data.length != 5) {
            System.err.println("ERROR: Incorrect number of data fields for line:" + data);
            return false;
        }
        for (int i = 0; i < data.length; i++) {
            //data type checking
            if (i == 0) {
                try {
                    int test = Integer.parseInt(data[i]);
                } catch (NumberFormatException e){
                    System.err.println("ERROR: Star ID must be an int ");
                    return false;
                }
            } else if (i == 1) {
                if (!(data[i] instanceof String)) {
                    System.err.println("ERROR: Starname must be a string" + data);
                    return false;
                }
            } else {
                try {
                    double test = Double.parseDouble(data[i]);
                } catch (NumberFormatException e) {
                    System.err.println("ERROR: Coordinates must be a double" + data);
                }
            }
        }
        return true;
    }
}
