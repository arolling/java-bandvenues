import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class VenueTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  // UNIT TESTING

  @Test
  public void all_returnsEmptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }


}
