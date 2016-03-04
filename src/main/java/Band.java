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

  // @Override
  // public boolean equals(Object otherBook){
  //   if (!(otherBook instanceof Book)) {
  //     return false;
  //   } else {
  //     Book newBook = (Book) otherBook;
  //     return this.getTitle().equals(newBook.getTitle()) &&
  //            this.getId() == newBook.getId() && this.getCopies() == newBook.getCopies();
  //   }
  // }

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

}
