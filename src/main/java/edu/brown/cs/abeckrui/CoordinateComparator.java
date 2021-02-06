package edu.brown.cs.abeckrui;

import java.util.Comparator;

public class CoordinateComparator implements Comparator<Node>{

  private int dimension;

  public CoordinateComparator(int dimensionVal){
    dimension = dimensionVal;
  }

  @Override
  public int compare(Node node1,Node node2){
    CordComparable comparable1 = node1.getCompObject();
    CordComparable comparable2 = node2.getCompObject();
    if (comparable1.getCoordinate(dimension) > comparable2.getCoordinate(dimension)){
      return 1;
    } else if (comparable1.getCoordinate(dimension) < comparable2.getCoordinate(dimension)){
      return -1;
    }
    return 0;
  }

}
