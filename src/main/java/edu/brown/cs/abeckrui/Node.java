package edu.brown.cs.abeckrui;

public class Node<T extends CordComparable> {

  private Node left;
  private Node right;
  //this is the comparable object the Node will store
  private T compObject;

  public Node(T comparableObject){
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

  public void setLeft(Node<T> leftChild){
    if (left != null){
      System.err.println("ERROR: Left child is not null!");
    }
    left = leftChild;
  }

  public void setRight(Node<T> rightChild){
    right = rightChild;
  }

  public T getCompObject(){
    return compObject;
  }
}
