package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.CordComparable;

import java.util.Comparator;

public class PriorityComparator implements Comparator<CordComparable> {

  private double x;
  private double y;
  private double z;

  public PriorityComparator(double xVal, double yVal, double zVal){
    x = xVal;
    y = yVal;
    z = zVal;
  }

  @Override
  public int compare(CordComparable a, CordComparable b){
    double ax = a.getCoordinate(0);
    double ay = a.getCoordinate(1);
    double az = a.getCoordinate(2);
    double aDist = StarsLogic.calculateDistance(ax,ay,az,x,y,z);
    double bx = b.getCoordinate(0);
    double by = b.getCoordinate(1);
    double bz = b.getCoordinate(2);
    double bDist = StarsLogic.calculateDistance(bx,by,bz,x,y,z);
    return (-1 * Double.compare(aDist,bDist));
  }

}

