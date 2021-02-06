package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Csv;
import edu.brown.cs.abeckrui.Kdtree;
import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.Node;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * This class handles all the search methods, data checking, and star logic.
 */
public class StarsLogic implements Method {

  private List<Star> stars;
  private Kdtree kdTree;

  /**
   * This constructor initializes starData, which holds all the star data.
   */
  public StarsLogic() {
    stars = new ArrayList<>();
  }


  @Override
  public void run(String[] line) {
    switch (line[0]) {
      case "stars":
        this.parseCSV(line);
        break;
      case "naive_neighbors":
        this.naiveNeighbors(line);
        break;
      case "naive_radius":
        this.naiveRadius(line);
        break;
      case "neighbors":
        this.neighbors(line);
        break;
      case "radius":
        this.radius(line);
        break;
      default:
        //shouldn't reach this, but handle error just in case
        System.err.println("ERROR: Command for stars not found.");
        break;
    }
  }

  private void parseCSV(String[] line) {
    if (line.length != 2) {
      System.err.println("ERROR: Incorrect number of args for command. 2 are expected");
    } else {
      Csv testParser = new Csv(new File(line[1]));
      //first check if data is invalid. if not, set it equal to starData
      if (testParser.parse() != null) {
        Csv parser = new Csv(new File(line[1]));
        List<List<String>> starData = new ArrayList<>();
        starData = parser.parse();
        //loop through starData, create list of star objects
        for (int i = 0; i < starData.size(); i++){
          int id = Integer.parseInt(starData.get(i).get(0));
          String name = starData.get(i).get(1);
          double x = Double.parseDouble(starData.get(i).get(2));
          double y = Double.parseDouble(starData.get(i).get(3));
          double z = Double.parseDouble(starData.get(i).get(4));
          stars.add(new Star(id, name, x, y, z));
        }
        //builds KD tree
        this.buildTree();
        //output message
        System.out.println("Read " + starData.size() + " stars from " + line[1]);
      }
    }
  }

  /**
   * This method calculates 3D distance between two points.
   *
   * @param x1 double x1
   * @param y1 double y1
   * @param z1 double z1
   * @param x2 double x2
   * @param y2 double y2
   * @param z2 double z2
   * @return distance as a double
   */
  private double calculateDistance(double x1, double y1, double z1, double x2,
                                   double y2, double z2) {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
  }

  private boolean checkCommand(String[] line){
    return true;
  }


  /**
   * Naive neighbors method.
   *
   * @param line, an array of Strings parsed by the REPL
   */
  private void naiveNeighbors(String[] line) {
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    String name = "";
    //checking that starData is not empty or null
    if (stars.size() == 0 || stars == null) {
      System.err.println("ERROR: Please load star data and try again");
      return;
    }
    if (line.length == 3 || line.length == 5) {
      int neighbors = 0;
      double x = 0;
      double y = 0;
      double z = 0;
      if (line.length == 3) {
        searchByName = true;
        //checking that second argument is an int
        try {
          int test = Integer.parseInt(line[1]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Number of neighbors must be an int");
          return;
        }
        //checking that third argument is a nonempty string
        if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
          System.err.println("ERROR: Name must be a nonempty string");
          return;
        }
        name = line[2].replace("\"", "");
        neighbors = Integer.parseInt(line[1]);
        boolean starFound = false;
        //searching for star with matching name
        for (int i = 0; i < stars.size(); i++) {
          if (stars.get(i).getName().equals(name)) {
            starFound = true;
            x = stars.get(i).getX();
            y = stars.get(i).getY();
            z = stars.get(i).getZ();
          }
        }
        //print ERROR and exit method if no star found with name provided
        if (!starFound) {
          System.err.println("ERROR: Star not found. Please check name entered");
          return;
        }
      } else {
        //checking that rest of arguments are integers
        try {
          double test = Integer.parseInt(line[1]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Neighbor number must be int");
          return;
        }
        for (int i = 2; i < 5; i++) {
          try {
            double test = Double.parseDouble(line[i]);
          } catch (NumberFormatException e) {
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
      if (neighbors == 0) {
        return;
      }
      //checking to see if number of neighbors > 0
      if (neighbors < 0) {
        System.err.println("ERROR: Number of neighbors cannot be negative");
        return;
      }
      List<List<Double>> neighborList = new ArrayList<>();
      /**
       * this shuffle method allows tied stars to randomly be picked. Since the shuffle is random,
       * and stars that are tied in distance are always placed in front in the order, each competing
       * star has a equal random chance to be included in the final list
       */
      Collections.shuffle(stars);
      for (int i = 0; i < stars.size(); i++) {
        //don't want to include star in list if queried by name
        if (searchByName && stars.get(i).getName().equals(name)) {
          continue;
        }
        double iD = stars.get(i).getID();
        double currentX = stars.get(i).getX();
        double currentY = stars.get(i).getY();
        double currentZ = stars.get(i).getZ();
        //see helper method
        double currentDistance = this.calculateDistance(x, y, z, currentX, currentY, currentZ);
        List<Double> currentStar = new ArrayList<>();
        currentStar.add(iD);
        currentStar.add(currentDistance);
        boolean added = false;
        //index to know where to add later
        int addIndex = 0;
        for (int j = 0; j < neighborList.size(); j++) {
          if (neighborList.get(j).get(1) > currentDistance) {
            addIndex = j;
            added = true;
            break;
          }
        }
        if (added) {
          neighborList.add(addIndex, currentStar);
          //want to include neighbors up to amount of neighbors asked for in command
        } else if (neighborList.size() < neighbors) {
          neighborList.add(currentStar);
        }
        //want to keep list at size of number of neighbors
        if (neighborList.size() > neighbors) {
          neighborList.remove(neighborList.size() - 1);
        }
      }
      //printing out stars to console
      for (int k = 0; k < neighborList.size(); k++) {
        double iD = neighborList.get(k).get(0);
        int toPrint = (int) iD;
        System.out.println(String.valueOf(toPrint));
      }
    } else {
      System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected for "
              + "naiveNeighbors");
    }
  }

  /**
   * Naive radius method.
   *
   * @param line, an array of strings parsed by the REPL
   */
  private void naiveRadius(String[] line) {
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    String name = "";
    //checking that starData is not empty or null
    if (stars.size() == 0 || stars == null) {
      System.err.println("ERROR: Please load star data and try again");
      return;
    }
    if (line.length == 3 || line.length == 5) {
      double radius = 0;
      double x = 0;
      double y = 0;
      double z = 0;
      if (line.length == 3) {
        searchByName = true;
        //checking that second argument is an int
        try {
          double test = Double.parseDouble(line[1]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Radius must be an int or double");
          return;
        }
        //checking that third argument is a nonempty string
        if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
          System.err.println("ERROR: Name must be a nonempty string");
          return;
        }
        name = line[2].replace("\"", "");
        radius = Double.parseDouble(line[1]);
        boolean starFound = false;
        //searching for star with matching name
        for (int i = 0; i < stars.size(); i++) {
          if (stars.get(i).getName().equals(name)) {
            starFound = true;
            x = stars.get(i).getX();
            y = stars.get(i).getY();
            z = stars.get(i).getZ();
          }
        }
        //print ERROR and exit method if no star found with name provided
        if (!starFound) {
          System.err.println("ERROR: Star not found. Please check name entered");
          return;
        }
      } else {
        //checking that rest of arguments are integers
        try {
          double test = Double.parseDouble(line[1]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Radius must be an int or double");
          return;
        }
        for (int i = 2; i < 5; i++) {
          try {
            double test = Double.parseDouble(line[i]);
          } catch (NumberFormatException e) {
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
      if (radius < 0) {
        System.err.println("ERROR: Radius cannot be negative");
        return;
      }
      List<List<Double>> radiusList = new ArrayList<>();
      /**
       * this shuffle method allows tied stars to randomly be picked. Since the shuffle is random,
       * and stars that are tied in distance are always placed in front in the order, each competing
       * star has a equal random chance to be included in the final list
       */
      Collections.shuffle(stars);
      for (int i = 0; i < stars.size(); i++) {
        //don't want to include star in list if queried by name
        if (searchByName && stars.get(i).getName().equals(name)) {
          continue;
        }
        double iD = stars.get(i).getID();
        double currentX = stars.get(i).getX();
        double currentY = stars.get(i).getY();
        double currentZ = stars.get(i).getZ();
        //see helper method
        double currentDistance = this.calculateDistance(x, y, z, currentX, currentY, currentZ);
        List<Double> currentStar = new ArrayList<>();
        currentStar.add(iD);
        currentStar.add(currentDistance);
        if (currentDistance <= radius) {
          boolean added = false;
          //index to know where to add later
          for (int j = 0; j < radiusList.size(); j++) {
            if (radiusList.get(j).get(1) > currentDistance) {
              radiusList.add(j, currentStar);
              added = true;
              break;
            }
          }
          //add at end of list if furthest away from cordinates given
          if (!added) {
            radiusList.add(currentStar);
          }
        }
      }
      //printing out stars to console
      for (int k = 0; k < radiusList.size(); k++) {
        double iD = radiusList.get(k).get(0);
        int toPrint = (int) iD;
        System.out.println(String.valueOf(toPrint));
      }
    } else {
      System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected "
              + "for naiveRadius");
    }
  }

  /**
   * This method checks if the starData is valid or not.
   *
   * @param data an array of strings representing the CSV star data
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
        } catch (NumberFormatException e) {
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

  /**
   * Helper method that builds KD tree
   */
  private void buildTree(){
    if (stars.size() == 0 || stars == null) {
      System.err.println("ERROR: Please load star data and try again");
      return;
    }
    List<Node> nodes = new ArrayList<>();
    //creating nodes for every
    for (int i = 0; i < stars.size(); i++){
      nodes.add(new Node(stars.get(i)));
    }
    kdTree = new Kdtree(nodes);
  }

  private void neighbors(String[] line){
    System.out.println("Neighbors Working");
  }

  private void radius(String[] line){
    System.out.println("Radius working");
  }



}
