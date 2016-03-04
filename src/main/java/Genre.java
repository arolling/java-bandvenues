import org.sql2o.*;
import java.util.List;

public class Genre{
  private int id;
  private String genre_name;

  public Genre(String name) {
    genre_name = name;
  }

  @Override
  public boolean equals(Object otherGenre){
    if (!(otherGenre instanceof Genre)) {
      return false;
    } else {
      Genre newGenre = (Genre) otherGenre;
      return this.getName().equals(newGenre.getName()) &&
             this.getId() == newGenre.getId();
    }
  }

  //Getters
  public String getName() {
    return genre_name;
  }

  public int getId() {
    return id;
  }

  //Database interaction below
  public static List<Genre> all() {
    String sql = "SELECT * FROM genres ORDER BY genre_name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Genre.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO genres (genre_name) VALUES (:genre_name)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("genre_name", genre_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Genre find(int genre_id) {
    String sql = "SELECT * FROM genres WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", genre_id)
        .executeAndFetchFirst(Genre.class);
    }
  }

  public void updateName(String newName) {
    genre_name = newName;
    String sql = "UPDATE genres SET genre_name=:genre_name WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("genre_name", genre_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
