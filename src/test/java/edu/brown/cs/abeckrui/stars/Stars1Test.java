package edu.brown.cs.abeckrui.stars;

import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.Repl;
import edu.brown.cs.abeckrui.mockaroo.MockPersonLogic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;

import static org.junit.Assert.assertFalse;

public class Stars1Test {

  private Repl repl;

  @Before
  public void setUp() {
    Method stars = new StarsLogic();
    Method runner = new MockPersonLogic();
    HashMap<String, Method> actions = new HashMap<>();
    actions.put("stars", stars);
    actions.put("naive_neighbors", stars);
    actions.put("naive_radius", stars);
    actions.put("mock", runner);
    repl = new Repl(actions);
  }


  /**
   * Resets the Repl
   */
  @After
  public void tearDown() {
    repl = null;
  }

  /**
   *
   */
  @Test
  public void testRepl() {
    System.out.println("Working");
  }
}
