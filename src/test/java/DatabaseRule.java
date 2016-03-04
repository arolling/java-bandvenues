import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_venues_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBandsQuery = "DELETE FROM bands *;";
      String deleteVenuesQuery = "DELETE FROM venues *;";
      String deleteBandsVenuesQuery = "DELETE FROM bands_venues *;";
      String deleteGenresQuery = "DELETE FROM genres WHERE id > 14;";
      String deleteBandsGenresQuery = "DELETE FROM bands_genres *;";
      con.createQuery(deleteBandsQuery).executeUpdate();
      con.createQuery(deleteVenuesQuery).executeUpdate();
      con.createQuery(deleteBandsVenuesQuery).executeUpdate();
      con.createQuery(deleteGenresQuery).executeUpdate();
      con.createQuery(deleteBandsGenresQuery).executeUpdate();
    }
  }
}
