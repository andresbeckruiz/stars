package edu.brown.cs.abeckrui;

/**
 * This node class contains CordComparable objects that can be used to build the KDTree.
 * It holds CordComparable objects so that it can be more extensible. All that is required
 * is that the objects are able to be compared coordinate wise and has some number of
 * dimensions.
 */
public class Node {

  private Node left;
  private Node right;
  //this is the comparable object the Node will store
  private CordComparable compObject;

  /**
   * In the constructor, I set the children initially to null. I also store the CordComparable
   * object passed in as an instance variable.
   * @param comparableObject representing CordComparable object to store in node.
   */
  public Node(CordComparable comparableObject) {
    left = null;
    right = null;
    compObject = comparableObject;
  }

  /**
   * Returns left child.
   * @return Node representing left child.
   */
  public Node getLeft() {
    return left;
  }

  /**
   * Checks if node has left child.
   * @return true if node has left child.
   */
  public boolean hasLeft() {
    if (left == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns right child.
   * @return Node representing right child.
   */
  public Node getRight() {
    return right;
  }

  /**
   * Checks if node has right child.
   * @return true if node has right child.
   */
  public boolean hasRight() {
    if (right == null) {
      return false;
    }
    return true;
  }

  /**
   * Sets left child.
   * @param leftChild node to be set as child.
   */
  public void setLeft(Node leftChild) {
    if (left != null) {
      System.err.println("ERROR: Left child is not null!");
    }
    left = leftChild;
  }

  /**
   * Sets right child.
   * @param rightChild node to be set as child.
   */
  public void setRight(Node rightChild) {
    right = rightChild;
  }

  /**
   * Getter method to return the object stored in the Node.
   * @return CordComprable object contained within the Node
   */
  public CordComparable getCompObject() {
    return compObject;
  }
}
