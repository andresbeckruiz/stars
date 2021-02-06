package edu.brown.cs.abeckrui;

public class Node {

  private Node left;
  private Node right;
  //this is the comparable object the Node will store
  private Comparable compObject;

  public Node(Comparable comparableObject){
    left = null;
    right = null;
    compObject = comparableObject;
  }

  public Node getLeft(){
    return left;
  }

  public Node getRight(){
    return right;
  }

  public void setLeft(Node leftChild){
    left = leftChild;
  }

  public void setRight(Node rightChild){
    right = rightChild;
  }

  public Comparable getCompObject(){
    return compObject;
  }
}
