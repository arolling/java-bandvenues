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
    String sql = "DELETE FROM bands";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .executeUpdate();
    }
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
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
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

}
