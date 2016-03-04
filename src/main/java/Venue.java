import org.sql2o.*;
import java.util.List;

public class Venue{
  private int id;
  private String venue_name;
  private int venue_size;

  public Venue(String name, int capacity) {
    venue_name = name;
    venue_size = capacity;
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
             this.getId() == newVenue.getId() &&
             this.getSize() == newVenue.getSize();
    }
  }

  //Getters

  public String getName() {
    return venue_name;
  }

  public int getSize() {
    return venue_size;
  }

  public int getId() {
    return id;
  }


  //Database interaction below
  public static List<Venue> all() {
    String sql = "SELECT * FROM venues ORDER BY venue_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Venue.class);
    }
  }

  public static void deleteAll() {
    String sql = "DELETE FROM venues";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .executeUpdate();
    }
  }

  public void save() {
    String sql = "INSERT INTO venues (venue_name, venue_size) VALUES (:venue_name, :venue_size)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("venue_name", venue_name)
        .addParameter("venue_size", venue_size)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int venue_id) {
    String sql = "SELECT * FROM venues WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", venue_id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM venues WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateCapacity(int newCapacity) {
    venue_size = newCapacity;
    String sql = "UPDATE venues SET venue_size=:venue_size WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("venue_size", venue_size)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateName(String newName) {
    venue_name = newName;
    String sql = "UPDATE venues SET venue_name=:venue_name WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("venue_name", venue_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public List<Band> allBands() {
    String sql = "SELECT bands.* FROM venues JOIN bands_venues ON (bands_venues.venue_id = venues.id) JOIN bands ON (bands.id = bands_venues.band_id) WHERE venues.id = :id ORDER BY band_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Band.class);
    }
  }

}
