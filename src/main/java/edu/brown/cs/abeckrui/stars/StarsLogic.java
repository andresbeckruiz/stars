package edu.brown.cs.abeckrui.stars;


import edu.brown.cs.abeckrui.CordComparable;
import edu.brown.cs.abeckrui.Csv;
import edu.brown.cs.abeckrui.Kdtree;
import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.Node;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This class handles all the search methods, data checking, and star logic. It also builds my
 * KD tree and stores all the stardata.
 */
public class StarsLogic implements Method {

  private List<Star> stars;
  private Kdtree kdTree;
  private static final double RANDOM_BOUND = 0.5;

  /**
   * This constructor initializes starData, which holds all the star data.
   */
  public StarsLogic() {
    stars = new ArrayList<>();
  }


  @Override
  public List<String> run(String[] line) {
    //toPrint is what is printed on the front end, representing the search results
    List<String> toPrint = new ArrayList<>();
    switch (line[0]) {
      case "stars":
        this.parseCSV(line);
        break;
      case "naive_neighbors":
        toPrint = this.naiveNeighbors(line);
        break;
      case "naive_radius":
        toPrint = this.naiveRadius(line);
        break;
      case "neighbors":
        toPrint = this.neighbors(line);
        break;
      case "radius":
        toPrint = this.radius(line);
        break;
      default:
        //shouldn't reach this, but handle error just in case
        System.err.println("ERROR: Command for stars not found.");
        toPrint.add("ERROR: Command for stars not found.");
        break;
    }
    return toPrint;
  }

  /**
   * This helper method instantiates an instance of the CSV class to parse a CSV file
   * and stores the data in the stars instance variable.
   * @param line representing command parsed from REPL
   */
  public void parseCSV(String[] line) {
    if (line.length != 2) {
      System.err.println("ERROR: Incorrect number of args for command. 2 are expected");
    } else {
      Csv testParser = new Csv(new File(line[1]));
      //first check if data is invalid. if not, set it equal to starData
      if (testParser.parse() != null) {
        Csv parser = new Csv(new File(line[1]));
        List<List<String>> starData = new ArrayList<>();
        starData = parser.parse();
        //clear old star data
        if (!stars.isEmpty()) {
          stars.clear();
        }
        //loop through starData, create list of star objects
        for (int i = 0; i < starData.size(); i++) {
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
  public static double calculateDistance(double x1, double y1, double z1, double x2,
                                   double y2, double z2) {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2));
  }

  /**
   * This helper method checks for valid commands for both neighbor methods.
   * @param line a String array representing the user input line parsed by the REPL
   * @return a string representing if command is valid ("" if valid, error message if not)
   */
  private String checkCommandNeighbors(String[] line) {
    //checking for correct command length
    if (line.length == 3 || line.length == 5) {
      try {
        Integer.parseInt(line[1]);
      } catch (NumberFormatException e) {
        System.err.println("ERROR: Number of neighbors must be an int");
        return "ERROR: Number of neighbors must be an int";
      }
      //checking if star data is null or empty
      if (stars.size() == 0 || stars == null) {
        System.err.println("ERROR: Please load star data and try again");
        return "ERROR: Please load star data and try again";
      }
      //checking whether named was queried
      if (line.length == 3) {
        //checking that third argument is a nonempty string
        if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
          System.err.println("ERROR: Name must be a nonempty string");
          return "ERROR: Name must be a nonempty string";
        }
        String name = line[2].replace("\"", "");
        boolean starFound = false;
        //searching for star with matching name
        for (int i = 0; i < stars.size(); i++) {
          if (stars.get(i).getName().equals(name)) {
            starFound = true;
          }
        }
        //print ERROR and exit method if no star found with name provided
        if (!starFound) {
          System.err.println("ERROR: Star not found. Please check name entered");
          return "ERROR: Star not found. Please check name entered";
        }
      } else {
        //checking that rest of arguments are doubles
        for (int i = 2; i < 5; i++) {
          try {
            Double.parseDouble(line[i]);
          } catch (NumberFormatException e) {
            System.err.println("ERROR: Coordinates must be int or double");
            return "ERROR: Coordinates must be int or double";
          }
        }
      }
      //represents command is valid
      return "";
    } else {
      System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected for "
              + "neighbors methods");
      return "ERROR: Incorrect number or args provided. 3 or 5 expected for neighbors methods";
    }
  }

  /**
   * This helper method checks for valid commands for both radius methods.
   * @param line a String array representing the user input line parsed by the REPL
   * @return a boolean representing if command is valid (true if valid)
   */
  private String checkCommandRadius(String[] line) {
    //checking for correct command length
    if (line.length == 3 || line.length == 5) {
      try {
        Double.parseDouble(line[1]);
      } catch (NumberFormatException e) {
        System.err.println("ERROR: Radius must be an int or double");
        return "ERROR: Radius must be an int or double";
      }
      //checking if star data is null or empty
      if (stars.size() == 0 || stars == null) {
        System.err.println("ERROR: Please load star data and try again");
        return "ERROR: Please load star data and try again";
      }
      //checking for if name is queried
      if (line.length == 3) {
        //checking that third argument is a nonempty string
        if (!(line[2] instanceof String) || (line[2].equals("\"\""))) {
          System.err.println("ERROR: Name must be a nonempty string");
          return "ERROR: Name must be a nonempty string";
        }
        String name = line[2].replace("\"", "");
        boolean starFound = false;
        //searching for star with matching name
        for (int i = 0; i < stars.size(); i++) {
          if (stars.get(i).getName().equals(name)) {
            starFound = true;
          }
        }
        //print ERROR and exit method if no star found with name provided
        if (!starFound) {
          System.err.println("ERROR: Star not found. Please check name entered");
          return "ERROR: Star not found. Please check name entered";
        }
      } else {
        //checking that rest of arguments are doubles
        for (int i = 2; i < 5; i++) {
          try {
            Double.parseDouble(line[i]);
          } catch (NumberFormatException e) {
            System.err.println("ERROR: Coordinates must be int or double");
            return "ERROR: Coordinates must be int or double";
          }
        }
      }
      return "";
    } else {
      System.err.println("ERROR: Incorrect number or args provided. 3 or 5 expected for "
              + "radius methods");
      return "ERROR: Incorrect number or args provided. 3 or 5 expected for radius methods";
    }
  }
  /**
   * Naive neighbors method.
   * @param line, an array of Strings parsed by the REPL
   * @return List representing star data for front end
   */
  private List<String> naiveNeighbors(String[] line) {
    List<String> neighborData = new ArrayList<>();
    //see helper method- checks if whole command is valid
    String returnString = this.checkCommandNeighbors(line);
    if (!returnString.equals("")) {
      neighborData.add(returnString);
      return neighborData;
    }
    String name = "";
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    int neighbors = Integer.parseInt(line[1]);
    double x = 0;
    double y = 0;
    double z = 0;
    //finding star by name
    if (line.length == 3) {
      searchByName = true;
      name = line[2].replace("\"", "");
      for (int i = 0; i < stars.size(); i++) {
        if (stars.get(i).getName().equals(name)) {
          x = stars.get(i).getX();
          y = stars.get(i).getY();
          z = stars.get(i).getZ();
        }
      }
    } else {
      x = Double.parseDouble(line[2]);
      y = Double.parseDouble(line[3]);
      z = Double.parseDouble(line[4]);
    }
    //return nothing if neighbors is 0
    if (neighbors == 0) {
      return neighborData;
    }
    //checking to see if number of neighbors > 0
    if (neighbors < 0) {
      System.err.println("ERROR: Number of neighbors cannot be negative");
      neighborData.add("ERROR: Number of neighbors cannot be negative");
      return neighborData;
    }
    List<List<Double>> neighborList = new ArrayList<>();
    //this is the list I use to return the star data
    List<Star> neighborStarList = new ArrayList<>();
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
        neighborStarList.add(addIndex, stars.get(i));
        //want to include neighbors up to amount of neighbors asked for in command
      } else if (neighborList.size() < neighbors) {
        neighborList.add(currentStar);
        neighborStarList.add(stars.get(i));
      }
      //want to keep list at size of number of neighbors
      if (neighborList.size() > neighbors) {
        neighborList.remove(neighborList.size() - 1);
        neighborStarList.remove(neighborStarList.size() - 1);
      }
    }
    //printing out stars to console
    for (int k = 0; k < neighborList.size(); k++) {
      double iD = neighborList.get(k).get(0);
      int toPrint = (int) iD;
      //formatting string for front end
      System.out.println(String.valueOf(toPrint));
      neighborData.add("ID: " + neighborStarList.get(k).getID() + " | Name: "
              + neighborStarList.get(k).getName() + " | " + "Coordinates: "
              + neighborStarList.get(k).getX()
              + ", " + neighborStarList.get(k).getY()
              + ", " + neighborStarList.get(k).getZ());
    }
    return neighborData;
  }

  /**
   * Naive radius method.
   * @param line, an array of strings parsed by the REPL
   * @return List representing star data for front end
   */
  private List<String> naiveRadius(String[] line) {
    List<String> radiusData = new ArrayList<>();
    //see helper method- checks if whole command is valid
    String returnString = this.checkCommandRadius(line);
    if (!returnString.equals("")) {
      radiusData.add(returnString);
      return radiusData;
    }
    String name = "";
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    double radius = Double.parseDouble(line[1]);
    double x = 0;
    double y = 0;
    double z = 0;
    //finding star by name
    if (line.length == 3) {
      searchByName = true;
      name = line[2].replace("\"", "");
      for (int i = 0; i < stars.size(); i++) {
        if (stars.get(i).getName().equals(name)) {
          x = stars.get(i).getX();
          y = stars.get(i).getY();
          z = stars.get(i).getZ();
        }
      }
    } else {
      x = Double.parseDouble(line[2]);
      y = Double.parseDouble(line[3]);
      z = Double.parseDouble(line[4]);
    }
    //checking to see if radius > 0
    if (radius < 0) {
      System.err.println("ERROR: Radius cannot be negative");
      radiusData.add("ERROR: Radius cannot be negative");
      return radiusData;
    }
    List<List<Double>> radiusList = new ArrayList<>();
    //list for storing star data
    List<Star> radiusStarList = new ArrayList<>();
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
            radiusStarList.add(j, stars.get(i));
            added = true;
            break;
          }
        }
        //add at end of list if furthest away from cordinates given
        if (!added) {
          radiusList.add(currentStar);
          radiusStarList.add(stars.get(i));
        }
      }
    }
    //printing out stars to console
    for (int k = 0; k < radiusList.size(); k++) {
      double iD = radiusList.get(k).get(0);
      int toPrint = (int) iD;
      System.out.println(String.valueOf(toPrint));
      //formatting string to print on front end
      radiusData.add("ID: " + radiusStarList.get(k).getID() + " | Name: "
              + radiusStarList.get(k).getName() + " | " + "Coordinates: "
              + radiusStarList.get(k).getX() + ", "
              + radiusStarList.get(k).getY()
              + ", " + radiusStarList.get(k).getZ());
    }
    return radiusData;
  }

  /**
   * KDTree neighbors method.
   * @param line, an array of strings parsed by the REPL
   * @return List representing star data for front end
   */
  private List<String> neighbors(String[] line) {
    List<String> neighborData = new ArrayList<>();
    //see helper method- checks if whole command is valid
    String returnString = this.checkCommandNeighbors(line);
    if (!returnString.equals("")) {
      neighborData.add(returnString);
      return neighborData;
    }
    String name = "";
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    int neighbors = Integer.parseInt(line[1]);
    double x = 0;
    double y = 0;
    double z = 0;
    //finding star by name
    if (line.length == 3) {
      searchByName = true;
      name = line[2].replace("\"", "");
      for (int i = 0; i < stars.size(); i++) {
        if (stars.get(i).getName().equals(name)) {
          x = stars.get(i).getX();
          y = stars.get(i).getY();
          z = stars.get(i).getZ();
        }
      }
    } else {
      x = Double.parseDouble(line[2]);
      y = Double.parseDouble(line[3]);
      z = Double.parseDouble(line[4]);
    }
    //return nothing if neighbors is 0
    if (neighbors == 0) {
      return neighborData;
    }
    //checking to see if number of neighbors > 0
    if (neighbors < 0) {
      System.err.println("ERROR: Number of neighbors cannot be negative");
      neighborData.add("ERROR: Number of neighbors cannot be negative");
      return neighborData;
    }
    PriorityQueue<CordComparable> neighborQueue = new
            PriorityQueue<>(new PriorityComparator(x, y, z));
    Node rootNode = kdTree.getRoot();
    //our target star, or point in space, where we are trying to compare with
    CordComparable target = new Star(-1, "", x, y, z);
    this.neighborsHelper(searchByName, name, neighbors, target,
            0, neighborQueue, rootNode);
    List<CordComparable> print = new ArrayList<>();
    while (!neighborQueue.isEmpty()) {
      print.add(neighborQueue.poll());
    }
    for (int i = print.size() - 1; i >= 0; i--) {
      System.out.println(print.get(i).getInfo().get(0));
      neighborData.add("ID: " + print.get(i).getInfo().get(0) + " | Name: "
              + print.get(i).getInfo().get(1) + " | "
              + "Coordinates: " + print.get(i).getCoordinate(0)
              + ", " + print.get(i).getCoordinate(1)
              + ", " + print.get(i).getCoordinate(2));
    }
    return neighborData;
  }

  /**
   * This recursive helper method searches for neighbors in the KDTree.
   * @param searchByName boolean representing whether we are searching by name
   * @param name string representing the name of star being queried- "" if not
   *             queried by name
   * @param neighbors int number of neighbors we are searching for
   * @param target CordComparable representing the target star input by user
   * @param depth int representing current depth of node being checked
   * @param neighborQueue priority queue holding neighbors
   * @param currentNode Node representing current node being checked
   */
  private void neighborsHelper(boolean searchByName, String name, int neighbors,
                                CordComparable target, int depth,
                                PriorityQueue<CordComparable> neighborQueue, Node currentNode) {
    //getting coordinates
    double currX = currentNode.getCompObject().getCoordinate(0);
    double currY = currentNode.getCompObject().getCoordinate(1);
    double currZ = currentNode.getCompObject().getCoordinate(2);
    double targetX = target.getCoordinate(0);
    double targetY = target.getCoordinate(1);
    double targetZ = target.getCoordinate(2);
    double currentDistance = this.calculateDistance(currX, currY, currZ,
            targetX, targetY, targetZ);
    double furthestDistance = Double.MAX_VALUE;
    //want to add neighbors to queue if size is less than neighbors we are looking for
    if (neighborQueue.size() < neighbors) {
      //ensure we don't add name of node if querying by name
      if (!(searchByName && currentNode.getCompObject().getInfo().get(1).equals(name))) {
        neighborQueue.add(currentNode.getCompObject());
      } //check if we have found a closer node
    } else {
      CordComparable furthest = neighborQueue.peek();
      double furthestX = furthest.getCoordinate(0);
      double furthestY = furthest.getCoordinate(1);
      double furthestZ = furthest.getCoordinate(2);
      furthestDistance = this.calculateDistance(furthestX, furthestY, furthestZ,
              targetX, targetY, targetZ);
      if (currentDistance <= furthestDistance) {
        //don't want to add star if queried by name
        if (!(searchByName && currentNode.getCompObject().getInfo().get(1).equals(name))) {
          //check if we want to randomize tied star
          if (currentDistance == furthestDistance) {
            //this ensures that tied stars are randomly picked
            if (Math.random() < RANDOM_BOUND) {
              //remove furthest neighbor
              neighborQueue.poll();
              neighborQueue.add(currentNode.getCompObject());
            } //don't want to randomize in this case
          } else {
            //remove furthest neighbor
            neighborQueue.poll();
            neighborQueue.add(currentNode.getCompObject());
          }
        }
      }
    }
    if (neighborQueue.size() != 0) {
      //update new furthest distance after potentially updating list
      CordComparable furthest = neighborQueue.peek();
      double furthestX = furthest.getCoordinate(0);
      double furthestY = furthest.getCoordinate(1);
      double furthestZ = furthest.getCoordinate(2);
      furthestDistance = this.calculateDistance(targetX, targetY, targetZ, furthestX, furthestY,
              furthestZ);
    }
    //check if we need to recur on both children or just one
    if (neighborQueue.size() < neighbors || furthestDistance
            > Math.abs(currentNode.getCompObject().getCoordinate(depth)
            - target.getCoordinate(depth))) {
      if (currentNode.hasLeft()) {
        this.neighborsHelper(searchByName, name, neighbors,
                target, depth + 1, neighborQueue, currentNode.getLeft());
      }
      if (currentNode.hasRight()) {
        this.neighborsHelper(searchByName, name, neighbors, target,
                depth + 1, neighborQueue, currentNode.getRight());
      }
    } else {
      if (currentNode.getCompObject().getCoordinate(depth) <= target.getCoordinate(depth)) {
        if (currentNode.hasRight()) {
          this.neighborsHelper(searchByName, name, neighbors, target,
                  depth + 1, neighborQueue, currentNode.getRight());
        }
      } else {
        if (currentNode.hasLeft()) {
          this.neighborsHelper(searchByName, name, neighbors, target,
                  depth + 1, neighborQueue, currentNode.getLeft());
        }
      }
    }
  }

  /**
   * KDTree radius method.
   * @param line an array of strings parsed by the REPL
   * @return List representing star data for front end
   */
  public List<String> radius(String[] line) {
    List<String> radiusData = new ArrayList<>();
    //see helper method- checks if whole command is valid
    String returnString = this.checkCommandRadius(line);
    if (!returnString.equals("")) {
      radiusData.add(returnString);
      return radiusData;
    }
    String name = "";
    //boolean to let me know if a name has been queried
    boolean searchByName = false;
    double radius = Double.parseDouble(line[1]);
    double x = 0;
    double y = 0;
    double z = 0;
    //finding star by name
    if (line.length == 3) {
      searchByName = true;
      name = line[2].replace("\"", "");
      for (int i = 0; i < stars.size(); i++) {
        if (stars.get(i).getName().equals(name)) {
          x = stars.get(i).getX();
          y = stars.get(i).getY();
          z = stars.get(i).getZ();
        }
      }
    } else {
      x = Double.parseDouble(line[2]);
      y = Double.parseDouble(line[3]);
      z = Double.parseDouble(line[4]);
    }
    //checking to see if number of neighbors > 0
    if (radius < 0) {
      System.err.println("ERROR: Radius cannot be negative");
      radiusData.add("ERROR: Radius cannot be negative");
      return radiusData;
    }
    PriorityQueue<CordComparable> radiusQueue =
            new PriorityQueue<>(new PriorityComparator(x, y, z));
    Node rootNode = kdTree.getRoot();
    //our target star, or point in space, where we are trying to compare with
    CordComparable target = new Star(-1, "", x, y, z);
    this.radiusHelper(searchByName, name, radius, target,
            0, radiusQueue, rootNode);
    List<CordComparable> print = new ArrayList<>();
    while (!radiusQueue.isEmpty()) {
      print.add(radiusQueue.poll());
    }
    for (int i = print.size() - 1; i >= 0; i--) {
      System.out.println(print.get(i).getInfo().get(0));
      radiusData.add("ID: " + print.get(i).getInfo().get(0) + " | Name: "
              + print.get(i).getInfo().get(1) + " | " + "Coordinates: "
              + print.get(i).getCoordinate(0) + ", "
              + print.get(i).getCoordinate(1)
              + ", " + print.get(i).getCoordinate(2));
    }
    return radiusData;
  }


  /**
   * This recursive helper method searches for stars within raduis in the KDTree.
   * @param searchByName boolean representing whether we are searching by name
   * @param name string representing the name of star being queried- "" if not
   *             queried by name
   * @param radius double representing radius we are searching by
   * @param target CordComparable representing the target star input by user
   * @param depth int representing current depth of node being checked
   * @param radiusQueue priority queue holding stars
   * @param currentNode Node representing current node being checked
   */
  private void radiusHelper(boolean searchByName, String name, double radius,
                            CordComparable target, int depth,
                            PriorityQueue<CordComparable> radiusQueue, Node currentNode) {
    double currX = currentNode.getCompObject().getCoordinate(0);
    double currY = currentNode.getCompObject().getCoordinate(1);
    double currZ = currentNode.getCompObject().getCoordinate(2);
    double targetX = target.getCoordinate(0);
    double targetY = target.getCoordinate(1);
    double targetZ = target.getCoordinate(2);
    double currentDistance = this.calculateDistance(currX, currY, currZ, targetX, targetY, targetZ);
    //add node to queue if within radius
    if (currentDistance <= radius) {
      if (!(searchByName && currentNode.getCompObject().getInfo().get(1).equals(name))) {
        radiusQueue.add(currentNode.getCompObject());
      }
    }
    //check if we need to recur on both children or just one
    if (radius > Math.abs(currentNode.getCompObject().getCoordinate(depth)
            - target.getCoordinate(depth))) {
      if (currentNode.hasLeft()) {
        this.radiusHelper(searchByName, name, radius, target, depth + 1, radiusQueue,
                currentNode.getLeft());
      }
      if (currentNode.hasRight()) {
        this.radiusHelper(searchByName, name, radius, target, depth + 1, radiusQueue,
                currentNode.getRight());
      }
    } else {
      if (currentNode.getCompObject().getCoordinate(depth) <= target.getCoordinate(depth)) {
        if (currentNode.hasRight()) {
          this.radiusHelper(searchByName, name, radius, target, depth + 1, radiusQueue,
                  currentNode.getRight());
        }
      } else {
        if (currentNode.hasLeft()) {
          this.radiusHelper(searchByName, name, radius, target, depth + 1, radiusQueue,
                  currentNode.getLeft());
        }
      }
    }
  }

  /**
   * This method checks if the starData is valid or not.
   * @param data an array of strings representing the CSV star data
   * @return boolean indicating true if data is valid and false if not
   */
  public static boolean checkData(String[] data) {
    if (data.length != 5) {
      System.err.println("ERROR: Incorrect number of data fields for line in file");
      return false;
    }
    for (int i = 0; i < data.length; i++) {
      //data type checking
      if (i == 0) {
        try {
          Integer.parseInt(data[i]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Star ID must be an int ");
          return false;
        }
      } else if (i == 1) {
        if (!(data[i] instanceof String)) {
          System.err.println("ERROR: Starnames must be a string");
          return false;
        }
      } else {
        try {
          Double.parseDouble(data[i]);
        } catch (NumberFormatException e) {
          System.err.println("ERROR: Coordinates must be a double");
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Helper method that builds KD tree.
   */
  private void buildTree() {
    if (stars.size() == 0 || stars == null) {
      System.err.println("ERROR: Please load star data and try again");
      return;
    }
    List<Node> nodes = new ArrayList<>();
    //creating nodes for every star
    for (int i = 0; i < stars.size(); i++) {
      nodes.add(new Node(stars.get(i)));
    }
    kdTree = new Kdtree(nodes);
  }

  /**
   * Getter method to get stardata in test classes.
   * @return List representing the star data instance variable.
   */
  public List<Star> getStars() {
    return stars;
  }

}
