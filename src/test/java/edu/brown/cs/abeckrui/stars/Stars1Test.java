package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.mockaroo.MockPersonLogic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Stars1Test {

  /**
   * This method tests the MockPersonLogic check data method.
   */
  @Test
  public void testMockTestData(){
    MockPersonLogic mock = new MockPersonLogic();
    String[] mockData = new String[6];
    assert(!mock.checkData(mockData));
    mockData[0] = "Adam";
    mockData[1] = "";
    mockData[2] = "2/3/2";
    mockData[3] = "someemail.com";
    mockData[4] = "";
    mockData[5] = "";
    assert(!mock.checkData(mockData));
    mockData[2] = "02/03/2020";
    assert(!mock.checkData(mockData));
    mockData[3] = "someemail@somedomain.com";
    assert(mock.checkData(mockData));
  }

  /**
   * This method tests errors in checkCommandNeighbors in StarLogic class.
   */
  @Test
  public void testCheckCommandNeighbors(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[2];
    fakeCommand[0] = "naive_neighbors";
    fakeCommand[1] = "Blah";
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Incorrect number or args provided." +
            " 3 or 5 expected for neighbors methods");
    String[] realNameCommand = new String[3];
    String[] realCordCommand = new String[5];
    realNameCommand[0] = "naive_neighbors";
    realNameCommand[1] = "2.3";
    realNameCommand[2] = "\"\"";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Number of neighbors must be an int");
    realNameCommand[1] = "2";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Please load star data and try again");
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Name must be a nonempty string");
    realNameCommand[2] = "NOT A REAL STAR";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Star not found. Please check name entered");
    realCordCommand[0] = "naive_neighbors";
    realCordCommand[1] = "1";
    realCordCommand[2] = "NOT_A_DOUBLE";
    realCordCommand[3] = "NOT_A_DOUBLE";
    realCordCommand[4] = "NOT_A_DOUBLE";
    assertEquals(starLogic.run(realCordCommand).get(0),"ERROR: Coordinates must be int or double");
  }

  /**
   * This method tests errors in checkCommandRadius in StarLogic class.
   */
  @Test
  public void testCheckCommandRadius(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[2];
    fakeCommand[0] = "naive_neighbors";
    fakeCommand[1] = "Blah";
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Incorrect number or args provided." +
            " 3 or 5 expected for neighbors methods");
    String[] realNameCommand = new String[3];
    String[] realCordCommand = new String[5];
    realNameCommand[0] = "naive_radius";
    realNameCommand[1] = "NOT_AN_INT_OR_DOUBLE";
    realNameCommand[2] = "\"\"";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Radius must be an int or double");
    realNameCommand[1] = "2";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Please load star data and try again");
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Name must be a nonempty string");
    realNameCommand[2] = "NOT A REAL STAR";
    assertEquals(starLogic.run(realNameCommand).get(0),"ERROR: Star not found. Please check name entered");
    realCordCommand[0] = "naive_neighbors";
    realCordCommand[1] = "1";
    realCordCommand[2] = "NOT_A_DOUBLE";
    realCordCommand[3] = "NOT_A_DOUBLE";
    realCordCommand[4] = "NOT_A_DOUBLE";
    assertEquals(starLogic.run(realCordCommand).get(0),"ERROR: Coordinates must be int or double");
  }

  /**
   * This method tests an error in the naive neighbors method if number of neighbors is negative.
   */
  @Test
  public void testNaiveNeighborsErrors(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[3];
    fakeCommand[0] = "naive_neighbors";
    fakeCommand[1] = "-4";
    fakeCommand[2] = "Sol";
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Number of neighbors cannot be negative");
  }

  /**
   * This method tests an error in the naive radius method if radius is negative.
   */
  @Test
  public void testNaiveRadiusErrors(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[3];
    fakeCommand[0] = "naive_radius";
    fakeCommand[1] = "-4";
    fakeCommand[2] = "Sol";
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Radius cannot be negative");
  }


  /**
   * This method tests an error in the run method for StarLogic
   */
  @Test
  public void testCommandNotFound(){
    StarsLogic starLogic = new StarsLogic();
    String[] fakeCommand = new String[2];
    fakeCommand[0] = "not_a_command";
    fakeCommand[1] = "Blah";
    String[] file = new String[2];
    file[0] = "stars";
    file[1] = "data/stars/stardata.csv";
    starLogic.parseCSV(file);
    assertEquals(starLogic.run(fakeCommand).get(0),"ERROR: Command for stars not found.");
  }

}
