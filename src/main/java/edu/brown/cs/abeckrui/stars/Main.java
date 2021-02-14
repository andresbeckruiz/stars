package edu.brown.cs.abeckrui.stars;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.brown.cs.abeckrui.Method;
import edu.brown.cs.abeckrui.Repl;
import edu.brown.cs.abeckrui.mockaroo.MockPersonLogic;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.QueryParamsMap;
import spark.ExceptionHandler;
import spark.template.freemarker.FreeMarkerEngine;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private static Method starLogic;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments...
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    //populating my method hashmap
    starLogic = new StarsLogic();
    Method mockPersonLogic = new MockPersonLogic();
    HashMap<String, Method> actions = new HashMap<>();
    actions.put("stars", starLogic);
    actions.put("naive_neighbors", starLogic);
    actions.put("naive_radius", starLogic);
    actions.put("neighbors", starLogic);
    actions.put("radius", starLogic);
    actions.put("mock", mockPersonLogic);
    Repl repl = new Repl(actions);
    repl.read();
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
              templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.post("/stars", new NeighborsSubmitHandler(), freeMarker);
    Spark.post("/stars", new RadiusSubmitHandler(), freeMarker);
  }

  /**
   * Handle requests to the front page of our Stars website.
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
              "Stars: Query the database", "results", "");
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * This class handles neighbors query submissions.
   */
  private static class NeighborsSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String textFromTextField = qm.value("text");
      //check if checkbox is checked for naive method
      String checked = qm.value("method");
      String stringToParse = "";
      //checking which method to run
      if (checked == null) {
        stringToParse = "neighbors " + textFromTextField;
        System.out.println("Yes");
      } else {
        stringToParse = "naive_neighbors " + textFromTextField;
      }
      String[] command = Repl.splitString(stringToParse);
      List<String> neighborData = starLogic.run(command);
      String toPrint = "";
      //formating star data when getting print to front end
      for (int i = 0; i < neighborData.size(); i++) {
        toPrint = toPrint + "<br />" + "<br />" + neighborData.get(i);
      }
      if (neighborData.isEmpty()) {
        toPrint = "No stars found";
      }
      Map<String, String> variables = ImmutableMap.of("title",
              "Stars: Query the database", "results", toPrint);
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /**
   * This class handles radius query submissions.
   */
  private static class RadiusSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String textFromTextField = qm.value("text");
      //check if checkbox is checked for naive method
      String checked = qm.value("method");
      String stringToParse = "";
      //checking which method to run
      if (checked == null) {
        stringToParse = "radius " + textFromTextField;
        System.out.println("Yes");
      } else {
        stringToParse = "naive_radius " + textFromTextField;
      }
      String[] command = Repl.splitString(stringToParse);
      List<String> radiusData = starLogic.run(command);
      String toPrint = "";
      //formating star data when getting print to front end
      for (int i = 0; i < radiusData.size(); i++) {
        toPrint = toPrint + "<br />" + "<br />" + radiusData.get(i);
      }
      if (radiusData.isEmpty()) {
        toPrint = "No stars found";
      }
      Map<String, String> variables = ImmutableMap.of("title",
              "Stars: Query the database", "results", toPrint);
      return new ModelAndView(variables, "query.ftl");
    }
  }


  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

}
