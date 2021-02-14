package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.CordComparable;
import edu.brown.cs.abeckrui.Kdtree;
import edu.brown.cs.abeckrui.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Stars2Test {

  /**
   * This method tests that a proper KD tree is built.
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
   * This method checks if KD build with empty list makes root null.
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
   * This method checks if KD build with empty list makes root null.
   */
  @Test
  public void setTestKDBuildZero() {
    List<Node> nodeList = new ArrayList<>();
    Kdtree tree = new Kdtree(nodeList);
    assertTrue(tree.getRoot() == null);
  }

  /**
   * This method tests a KDTree build where there are sorting ties.
   */
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

  /**
   * This method tests my PriorityComparator.
   */
  @Test
  public void testPriorityComparator(){
    Star A = new Star(0,"A",3.2,6.5,2.9);
    Star B = new Star(1,"B", 0,0,0);
    Star C = new Star(2,"C", 3.2,-5,3);
    Star D = new Star(3,"D", 8,4, -89);
    Star E = new Star(4,"E", -4,50,8);
    Star F = new Star(5,"F", 7,-2.54,34.2);
    Star G = new Star(6,"G",-1,3,4);
    Star H = new Star(7,"H",9,9,1);
    PriorityQueue<CordComparable> neighborQueue = new PriorityQueue<>(new PriorityComparator(0,0,0));
    neighborQueue.add(D);
    neighborQueue.add(B);
    neighborQueue.add(C);
    neighborQueue.add(F);
    assert(neighborQueue.poll() == D);
    neighborQueue.add(G);
    assert(neighborQueue.poll() == F);
    neighborQueue.add(A);
    assert(neighborQueue.peek() == A);
    neighborQueue.add(E);
    neighborQueue.add(H);
    assert(neighborQueue.poll() == E);
    assert(neighborQueue.poll() == H);
    assert(neighborQueue.poll() == A);
    assert(neighborQueue.poll() == C);
    assert(neighborQueue.poll() == G);
    assert(neighborQueue.poll() == B);
  }

  /**
   * This is the PBT for the neighbor methods.
   */
  @Test
  public void neighborPBT(){
    StarsLogic stars = new StarsLogic();
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    stars.parseCSV(file);
    String randomNeighborName = "";
    List<String> starData = new ArrayList<>();
    List<String> naiveStarData = new ArrayList<>();
    for (int i = 0; i < 100; i++){
      Random random = new Random();
      int randomNeighborNumber = random.nextInt(1000);
      if (Math.random() < 0.5) {
        //don't want name to be ""
        while(randomNeighborName.equals("")){
          randomNeighborName = stars.getStars().get(random.nextInt(stars.getStars().size())).getName();
        }
        randomNeighborName = '"' + randomNeighborName + '"';
        String[] command = new String[3];
        command[0] = "neighbors";
        command[1] = Integer.toString(randomNeighborNumber);
        command[2] = randomNeighborName;
        starData = stars.run(command);
        String[] naiveCommand = new String[3];
        naiveCommand[0] = "naive_neighbors";
        naiveCommand[1] = Integer.toString(randomNeighborNumber);
        naiveCommand[2] = randomNeighborName;
        naiveStarData = stars.run(naiveCommand);
      } else {
        //generates random doubles between -50 and 50
        double x = -50 + 100 * random.nextDouble();
        double y = -50 + 100 * random.nextDouble();
        double z = -50 + 100 * random.nextDouble();
        String[] command = new String[5];
        command[0] = "neighbors";
        command[1] = Integer.toString(randomNeighborNumber);
        command[2] = Double.toString(x);
        command[3] = Double.toString(y);
        command[4] = Double.toString(z);
        starData = stars.run(command);
        String[] naiveCommand = new String[5];
        naiveCommand[0] = "naive_neighbors";
        naiveCommand[1] = Integer.toString(randomNeighborNumber);
        naiveCommand[2] = Double.toString(x);
        naiveCommand[3] = Double.toString(y);
        naiveCommand[4] = Double.toString(z);
        naiveStarData = stars.run(naiveCommand);
      }
      assert(starData.size() == naiveStarData.size());
      for (int j = 0; j < starData.size(); j++){
        String currNeighbor = starData.get(j);
        int index = currNeighbor.indexOf("Coordinates:");
        String curr = currNeighbor.substring(index + 13);
        String[] currCords = curr.split(",");
        Double x = Double.parseDouble(currCords[0]);
        Double y = Double.parseDouble(currCords[1]);
        Double z = Double.parseDouble(currCords[2]);
        //checking distance from origin
        String naiveCurrNeighbor = naiveStarData.get(j);
        int naiveIndex = naiveCurrNeighbor.indexOf("Coordinates:");
        String naiveCurr = naiveCurrNeighbor.substring(naiveIndex + 13);
        String[] naiveCurrCords = naiveCurr.split(",");
        Double naiveX = Double.parseDouble(naiveCurrCords[0]);
        Double naiveY = Double.parseDouble(naiveCurrCords[1]);
        Double naiveZ = Double.parseDouble(naiveCurrCords[2]);
        //checking distance from origin
        assert(StarsLogic.calculateDistance(x,y,z,0,0,0) ==
                StarsLogic.calculateDistance(naiveX,naiveY,naiveZ,0,0,0));
      }
    }
  }

  /**
   * This is the PBT testing for the radius methods.
   */
  @Test
  public void radiusPBT(){
    StarsLogic stars = new StarsLogic();
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    stars.parseCSV(file);
    String randomRadiusName = "";
    List<String> starData = new ArrayList<>();
    List<String> naiveStarData = new ArrayList<>();
    for (int i = 0; i < 100; i++){
      Random random = new Random();
      Double randomRadius = 100 * random.nextDouble();
      if (Math.random() < 0.5) {
        //don't want name to be ""
        while(randomRadiusName.equals("")){
          randomRadiusName = stars.getStars().get(random.nextInt(stars.getStars().size())).getName();
        }
        randomRadiusName = '"' + randomRadiusName + '"';
        String[] command = new String[3];
        command[0] = "radius";
        command[1] = Double.toString(randomRadius);
        command[2] = randomRadiusName;
        starData = stars.run(command);
        String[] naiveCommand = new String[3];
        naiveCommand[0] = "naive_radius";
        naiveCommand[1] = Double.toString(randomRadius);
        naiveCommand[2] = randomRadiusName;
        naiveStarData = stars.run(naiveCommand);
      } else {
        //generates random doubles between -50 and 50
        double x = -50 + 100 * random.nextDouble();
        double y = -50 + 100 * random.nextDouble();
        double z = -50 + 100 * random.nextDouble();
        String[] command = new String[5];
        command[0] = "radius";
        command[1] = Double.toString(randomRadius);
        command[2] = Double.toString(x);
        command[3] = Double.toString(y);
        command[4] = Double.toString(z);
        starData = stars.run(command);
        String[] naiveCommand = new String[5];
        naiveCommand[0] = "naive_radius";
        naiveCommand[1] = Double.toString(randomRadius);
        naiveCommand[2] = Double.toString(x);
        naiveCommand[3] = Double.toString(y);
        naiveCommand[4] = Double.toString(z);
        naiveStarData = stars.run(naiveCommand);
      }
      assert(starData.size() == naiveStarData.size());
      for (int j = 0; j < starData.size(); j++){
        String currRadius = starData.get(j);
        int index = currRadius.indexOf("Coordinates:");
        String curr = currRadius.substring(index + 13);
        String[] currCords = curr.split(",");
        Double x = Double.parseDouble(currCords[0]);
        Double y = Double.parseDouble(currCords[1]);
        Double z = Double.parseDouble(currCords[2]);
        //checking distance from origin
        String naiveCurrRadius = naiveStarData.get(j);
        int naiveIndex = naiveCurrRadius.indexOf("Coordinates:");
        String naiveCurr = naiveCurrRadius.substring(naiveIndex + 13);
        String[] naiveCurrCords = naiveCurr.split(",");
        Double naiveX = Double.parseDouble(naiveCurrCords[0]);
        Double naiveY = Double.parseDouble(naiveCurrCords[1]);
        Double naiveZ = Double.parseDouble(naiveCurrCords[2]);
        //checking distance from origin
        assert(StarsLogic.calculateDistance(x,y,z,0,0,0) ==
                StarsLogic.calculateDistance(naiveX,naiveY,naiveZ,0,0,0));
      }
    }
  }

  /**
   * This method tests errors in the neighbors method if the number of neighbors is negative.
   */
  @Test
  public void testNeighborsErrors(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[3];
    fakeCommand[0] = "neighbors";
    fakeCommand[1] = "-4";
    fakeCommand[2] = "Sol";
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Number of neighbors cannot be negative");
  }

  /**
   * This method tests errors in the naive radius method if the radius is negative.
   */
  @Test
  public void testRadiusErrors() {
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[3];
    fakeCommand[0] = "radius";
    fakeCommand[1] = "-4";
    fakeCommand[2] = "Sol";
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Radius cannot be negative");
  }

}
