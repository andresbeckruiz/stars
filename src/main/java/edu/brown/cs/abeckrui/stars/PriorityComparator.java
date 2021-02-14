package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.CordComparable;
import java.util.Comparator;

/**
 * This class is a custom comparator for my priority queue used in the StarsLogic class.
 */
public class PriorityComparator implements Comparator<CordComparable> {

  private double x;
  private double y;
  private double z;
  private static final double RANDOM_BOUND = 0.5;

  /**
   * This constructor stores the target coordinates to compare to as instance variables.
   * @param xVal representing the target x value
   * @param yVal representing the target y value
   * @param zVal representing the target z value
   */
  public PriorityComparator(double xVal, double yVal, double zVal) {
    x = xVal;
    y = yVal;
    z = zVal;
  }

  @Override
  public int compare(CordComparable a, CordComparable b) {
    double ax = a.getCoordinate(0);
    double ay = a.getCoordinate(1);
    double az = a.getCoordinate(2);
    double aDist = StarsLogic.calculateDistance(ax, ay, az, x, y, z);
    double bx = b.getCoordinate(0);
    double by = b.getCoordinate(1);
    double bz = b.getCoordinate(2);
    double bDist = StarsLogic.calculateDistance(bx, by, bz, x, y, z);
    //checking if we want to randomize ties or not
    if (Double.compare(aDist, bDist) == 0) {
      if (Math.random() < RANDOM_BOUND) {
        return 1;
      } else {
        return -1;
      } //multiplied by -1 to get furthest objects in the front of queue
    } else {
      return (-1 * Double.compare(aDist, bDist));
    }
  }

}

