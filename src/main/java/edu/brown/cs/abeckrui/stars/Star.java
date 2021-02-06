package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Comparable;

public class Star implements Comparable {

  private int id;
  private String name;
  private double x;
  private double y;
  private double z;

  public Star(int iD, String starName, double xcord, double ycord, double zcord){
    id = iD;
    name = starName;
    x = xcord;
    y = ycord;
    z = zcord;
  }

  /**
   * Returns coordinates of stars (method from Comparable interface).
   * @param k representing current level in tree
   * @return coordinate
   */
  @Override
  public double getCoordinate(int k){
    if (k % 3 == 0){
      return x;
    }
    else if (k % 3 == 1){
      return y;
    }
    else{
      return z;
    }
  }

  /**
   * Gets star ID.
   * @return int, representing id.
   */
  public int getID(){
    return id;
  }

  /**
   * Gets star name.
   * @return string, representing name.
   */
  public String getName(){
    return name;
  }

  /**
   * Gets x cord.
   * @return double, representing x cord.
   */
  public double getX(){
    return x;
  }

  /**
   * Gets y cord.
   * @return double, representing y cord.
   */
  public double getY(){
    return y;
  }

  /**
   * Gets z cord.
   * @return double, representing z cord.
   */
  public double getZ(){
    return z;
  }

}
