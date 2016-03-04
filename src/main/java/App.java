import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("allBands", Band.all());
      model.put("allVenues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addBand", (request, response) -> {
      String newBandName = request.queryParams("bandName");
      Band newBand = new Band(newBandName);
      newBand.save();
      response.redirect("/");
      return null;
    });

    post("/addVenue", (request, response) -> {
      String newVenueName = request.queryParams("venueName");
      int capacity = Integer.parseInt(request.queryParams("capacity"));
      Venue newVenue = new Venue(newVenueName, capacity);
      newVenue.save();
      response.redirect("/");
      return null;
    });
  }
}
