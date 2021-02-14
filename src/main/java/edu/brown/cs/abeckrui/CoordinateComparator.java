package edu.brown.cs.abeckrui;

import java.util.Comparator;

/**
 * This class is a custom comparator for objects that implement the CordComparable
 * interface.
 */
public class CoordinateComparator implements Comparator<Node> {

  private int level;

  /**
   * In the constructor, I initialize my level instance variable.
   * @param levelVal representing current level being searched in KD tree.
   */
  public CoordinateComparator(int levelVal) {
    level = levelVal;
  }

  @Override
  public int compare(Node node1, Node node2) {
    CordComparable comparable1 = node1.getCompObject();
    CordComparable comparable2 = node2.getCompObject();
    if (comparable1.getCoordinate(level) > comparable2.getCoordinate(level)) {
      return 1;
    } else if (comparable1.getCoordinate(level) < comparable2.getCoordinate(level)) {
      return -1;
    }
    return 0;
  }

}
