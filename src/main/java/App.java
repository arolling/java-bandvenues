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

    get("/bands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band thisBand = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", thisBand);
      model.put("allGenres", Genre.all());
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/addGigs", (request, response) -> {
      Band thisBand = Band.find(Integer.parseInt(request.params("id")));
      String[] newGigs = request.queryParamsValues("checkVenue");
      for(String gigId : newGigs) {
        Venue venue = Venue.find(Integer.parseInt(gigId));
        thisBand.addVenue(venue);
      }
      response.redirect("/bands/" + thisBand.getId());
      return null;
    });

    post("/bands/:id/addCategories", (request, response) -> {
      Band thisBand = Band.find(Integer.parseInt(request.params("id")));
      String[] newGenres = request.queryParamsValues("checkGenre");
      for(String genreId : newGenres) {
        thisBand.addGenre(Integer.parseInt(genreId));
      }
      response.redirect("/bands/" + thisBand.getId());
      return null;
    });

    post("/bands/:id/edit", (request, response) -> {
      Band thisBand = Band.find(Integer.parseInt(request.params("id")));
      String newName = request.queryParams("editName");
      int newFans = Integer.parseInt(request.queryParams("editFans"));
      thisBand.updateFans(newFans);
      thisBand.updateName(newName);
      response.redirect("/bands/" + thisBand.getId());
      return null;
    });

    get("/bands/:id/delete", (request, response) -> {
      Band thisBand = Band.find(Integer.parseInt(request.params("id")));
      thisBand.delete();
      response.redirect("/");
      return null;
    });

  }
}
