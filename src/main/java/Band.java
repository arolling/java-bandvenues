import org.sql2o.*;
import java.util.List;

public class Band {
  private int id;
  private String band_name;
  private int fanbase;

  public Band(String name) {
    band_name = name;
    fanbase = 0;
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId() &&
             this.getFanbase() == newBand.getFanbase();
    }
  }

  //Getters

  public String getName() {
    return band_name;
  }

  public int getFanbase() {
    return fanbase;
  }

  public int getId() {
    return id;
  }


  //Database interaction below
  public static List<Band> all() {
    String sql = "SELECT * FROM bands ORDER BY band_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Band.class);
    }
  }

  public static void deleteAll() {
    for(Band band : Band.all()) {
      band.delete();
    }
    // String sql = "DELETE FROM bands";
    // try(Connection con = DB.sql2o.open()) {
    //   con.createQuery(sql)
    //     .executeUpdate();
    // }
  }

  public void save() {
    String sql = "INSERT INTO bands (band_name, fanbase) VALUES (:band_name, :fanbase)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("band_name", band_name)
        .addParameter("fanbase", fanbase)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int band_id) {
    String sql = "SELECT * FROM bands WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", band_id)
        .executeAndFetchFirst(Band.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM bands WHERE id=:id";
    String venuesql = "DELETE FROM bands_venues WHERE band_id=:id";
    String genresql = "DELETE FROM bands_genres WHERE band_id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

      con.createQuery(venuesql)
        .addParameter("id", id)
        .executeUpdate();

      con.createQuery(genresql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateFans(int newFans) {
    fanbase = newFans;
    String sql = "UPDATE bands SET fanbase=:fanbase WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("fanbase", fanbase)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateName(String newName) {
    band_name = newName;
    String sql = "UPDATE bands SET band_name=:band_name WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("band_name", band_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  //JOIN TABLE interaction
  public void addVenue(Venue newVenue) {
    //ADD CONDITIONAL CHECKING OF VENUE SIZE HERE
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("venue_id", newVenue.getId())
        .addParameter("band_id", id)
        .executeUpdate();
    }
  }

  public List<Venue> allVenues() {
    String sql = "SELECT venues.* FROM bands JOIN bands_venues ON (bands.id = bands_venues.band_id) JOIN venues ON (bands_venues.venue_id = venues.id) WHERE bands.id = :id ORDER BY venue_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Venue.class);
    }
  }

  public void addGenre(int genreId) {
    String sql = "INSERT INTO bands_genres (band_id, genre_id) VALUES (:band_id, :genre_id)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("genre_id", genreId)
        .addParameter("band_id", id)
        .executeUpdate();
    }
  }

  public List<Genre> allGenres() {
    String sql = "SELECT genres.* FROM bands JOIN bands_genres ON (bands.id = bands_genres.band_id) JOIN genres ON (bands_genres.genre_id = genres.id) WHERE bands.id = :id ORDER BY genre_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Genre.class);
    }
  }

}
