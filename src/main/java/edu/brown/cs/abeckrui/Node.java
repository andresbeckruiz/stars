package edu.brown.cs.abeckrui;

public class Node {

  private Node left;
  private Node right;
  //this is the comparable object the Node will store
  private CordComparable compObject;

  public Node(CordComparable comparableObject){
    left = null;
    right = null;
    compObject = comparableObject;
  }

  public Node getLeft(){
    return left;
  }

  public boolean hasLeft(){
    if (left == null){
      return false;
    }
    return true;
  }

  public Node getRight(){
    return right;
  }

  public boolean hasRight(){
    if (right == null){
      return false;
    }
    return true;
  }

  public void setLeft(Node leftChild){
    if (left != null){
      System.err.println("ERROR: Left child is not null!");
    }
    left = leftChild;
  }

  public void setRight(Node rightChild){
    right = rightChild;
  }

  public CordComparable getCompObject(){
    return compObject;
  }
}
