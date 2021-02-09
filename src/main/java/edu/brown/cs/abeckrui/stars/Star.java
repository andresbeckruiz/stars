package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.CordComparable;

import java.util.ArrayList;
import java.util.List;

public class Star implements CordComparable {

  private int id;
  private String name;
  private double x;
  private double y;
  private double z;
  private static final int DIMENSION = 3;

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
    if (k % DIMENSION == 0){
      return x;
    }
    else if (k % DIMENSION == 1){
      return y;
    }
    else{
      return z;
    }
  }

  @Override
  public List<String> getInfo(){
    List<String> info = new ArrayList<>();
    info.add(Integer.toString(id));
    info.add(name);
    return info;
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
