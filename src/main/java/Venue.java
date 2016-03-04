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

  // @Override
  // public boolean equals(Object otherBand){
  //   if (!(otherBand instanceof Band)) {
  //     return false;
  //   } else {
  //     Band newBand = (Band) otherBand;
  //     return this.getName().equals(newBand.getName()) &&
  //            this.getId() == newBand.getId() &&
  //            this.getFanbase() == newBand.getFanbase();
  //   }
  // }

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

}
