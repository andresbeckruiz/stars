package edu.brown.cs.abeckrui;

import java.util.Collections;
import java.util.List;

public class Kdtree {

  private Node root;
  private List<Node> nodes;

  public Kdtree(List<Node> nodesList){
    nodes = nodesList;
    root = this.buildTree(nodes, 0);
  }

  /**
   * This function builds the KD tree recursively given a list of nodes
   * @return Node representing root node of current recursion
   */
  private Node buildTree(List<Node> nodes, int depth){
    //edge case if list is empty - this means the root will be null, or wont exist
    if (nodes.size() == 0){
      return null;
    }
    //base case if list is one, just return this as root node
    if (nodes.size() == 1){
      return nodes.get(0);
    }
    Collections.sort(nodes, new CoordinateComparator(depth));
    int medianIndex = (nodes.size() - 1) / 2;
    //variable to compare median coordinates with duplicates
    double medianCoordinate = nodes.get(medianIndex).getCompObject().getCoordinate(depth);
    //want to get furthest left median (if there are multiple objects with same value)
    while (medianIndex > 0) {
      if (nodes.get(medianIndex - 1).getCompObject().getCoordinate(depth) != medianCoordinate) {
        break;
      }
      medianIndex--;
    }
    Node median = nodes.get(medianIndex);
    List<Node> leftList = nodes.subList(0,medianIndex);
    List<Node> rightList = nodes.subList(medianIndex + 1, nodes.size());
    //recurse to set left and right children
    median.setLeft(this.buildTree(leftList, depth + 1));
    median.setRight(this.buildTree(rightList, depth + 1));
    return median;
  }

  /**
   *
   * @return Node representing the root node
   */
  public Node getRoot(){
    return root;
  }

  /**
   *
   * @return a list of nodes representing the Nodes in the KDtree
   */
  public List<Node> getNodeList(){
    return nodes;
  }
}
