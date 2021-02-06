package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Kdtree;
import edu.brown.cs.abeckrui.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class Stars2Test {

  /**
   * Tests that a proper KD tree is built
   */
  @Test
  public void testKDBuild() {
    Star A = new Star(0,"A",3.2,6.5,2.9);
    Node nodeA = new Node(A);
    Star B = new Star(1,"B", 0,0,0);
    Node nodeB = new Node(B);
    Star C = new Star(2,"C", 5,-5,3);
    Node nodeC = new Node(C);
    Star D = new Star(3,"D", 2,4, -89);
    Node nodeD = new Node(D);
    Star E = new Star(4,"E", -8,50,8);
    Node nodeE = new Node(E);
    Star F = new Star(5,"F", 5.9,-2.54,34.2);
    Node nodeF = new Node(F);
    Star G = new Star(6,"G",-1,3,4);
    Node nodeG = new Node(G);
    Star H = new Star(7,"H",3,9,1);
    Node nodeH = new Node(H);
    List<Node> nodeList = new ArrayList<>();
    nodeList.add(nodeA);
    nodeList.add(nodeB);
    nodeList.add(nodeC);
    nodeList.add(nodeD);
    nodeList.add(nodeE);
    nodeList.add(nodeF);
    nodeList.add(nodeG);
    nodeList.add(nodeH);
    Kdtree tree = new Kdtree(nodeList);
    assertTrue(tree.getRoot() == nodeD);
    assertTrue(nodeD.getLeft() == nodeG && nodeD.getRight() == nodeF);
    assertTrue(nodeG.getLeft() == nodeB && nodeG.getRight() == nodeE);
    assertTrue(nodeF.getLeft() == nodeC && nodeF.getRight() == nodeH);
    assertTrue(nodeB.getLeft() == null && nodeB.getRight() == null);
    assertTrue(nodeE.getLeft() == null && nodeE.getRight() == null);
    assertTrue(nodeA.getLeft() == null && nodeA.getRight() == null);
    assertTrue(nodeC.getLeft() == null && nodeC.getRight() == null);
    assertTrue(nodeH.getLeft() == null && nodeH.getRight() == nodeA);
  }

  /**
   * Checks if KD build with empty list makes root null
   */
  @Test
  public void testKDBuildOne() {
    Star A = new Star(0,"A",3.2,6.5,2.9);
    Node node = new Node(A);
    List<Node> nodeList = new ArrayList<>();
    nodeList.add(node);
    Kdtree tree = new Kdtree(nodeList);
    assertTrue(tree.getRoot() == node);
  }

  /**
   * Checks if KD build with empty list makes root null
   */
  @Test
  public void setTestKDBuildZero() {
    List<Node> nodeList = new ArrayList<>();
    Kdtree tree = new Kdtree(nodeList);
    assertTrue(tree.getRoot() == null);
  }

  @Test
  public void testKDTie(){
    Star A = new Star(0,"A",3.2,6.5,2.9);
    Node nodeA = new Node(A);
    Star B = new Star(1,"B", 0,0,0);
    Node nodeB = new Node(B);
    Star C = new Star(2,"C", 3.2,-5,3);
    Node nodeC = new Node(C);
    Star D = new Star(3,"D", 8,4, -89);
    Node nodeD = new Node(D);
    Star E = new Star(4,"E", -4,50,8);
    Node nodeE = new Node(E);
    Star F = new Star(5,"F", 7,-2.54,34.2);
    Node nodeF = new Node(F);
    Star G = new Star(6,"G",-1,3,4);
    Node nodeG = new Node(G);
    Star H = new Star(7,"H",9,9,1);
    Node nodeH = new Node(H);
    List<Node> nodeList = new ArrayList<>();
    nodeList.add(nodeA);
    nodeList.add(nodeB);
    nodeList.add(nodeC);
    nodeList.add(nodeD);
    nodeList.add(nodeE);
    nodeList.add(nodeF);
    nodeList.add(nodeG);
    nodeList.add(nodeH);
    Kdtree tree = new Kdtree(nodeList);
    assertTrue(tree.getRoot() == nodeA || tree.getRoot() == nodeC);
  }

}
