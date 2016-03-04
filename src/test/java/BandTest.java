import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class BandTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  // UNIT TESTING

  @Test
  public void all_returnsEmptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void save_savesBandToDBAndSetsID_true() {
    Band neo = new Band("Neo");
    neo.save();
    assertTrue(Band.all().get(0) instanceof Band);
    assertEquals(neo.getId(), Band.all().get(0).getId());
  }


}
