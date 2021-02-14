package edu.brown.cs.abeckrui;

import java.util.List;

/**
 * This interface allows the KDTree to be more generic. It allows objects of different
 * dimensions to get their
 */
public interface CordComparable {

  /**
   * This method returns the current coordinate of the object that should be checked.
   * @param level representing current level in KDTree.
   * @return double representing current coordinate being checekd.
   */
  double getCoordinate(int level);

  /**
   * This method allows objects to return any important information, such as name,
   * ID, etc.
   * @return List representing any info about the object
   */
  List<String> getInfo();
}
