import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class GenreTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  // UNIT TESTING

  @Test
  public void all_returns14PreSetsAtFirst() {
    assertEquals(Genre.all().size(), 14);
  }

  @Test
  public void find_retrievesSpecificInstanceOfGenre_true() {
    Genre folk = new Genre("Folk");
    folk.save();
    Genre metal = new Genre("Metal");
    metal.save();
    assertEquals(folk, Genre.find(folk.getId()));
  }

  @Test
  public void save_savesGenreToDBAndSetsID_true() {
    Genre folk = new Genre("Folk");
    folk.save();
    assertTrue(Genre.find(folk.getId()) instanceof Genre);
    assertEquals(folk, Genre.find(folk.getId()));
  }

  @Test
  public void equals_checksNameId_true() {
    Genre folk = new Genre("Folk");
    folk.save();
    Genre metal = new Genre("Metal");
    metal.save();
    Genre savedGenre = Genre.find(metal.getId());
    assertTrue(metal.equals(savedGenre));
  }

  @Test
  public void updateName_updatesGenreNameLocallyAndRemotely_true() {
    Genre folk = new Genre("Folk");
    folk.save();
    Genre metal = new Genre("Metal");
    metal.save();
    folk.updateName("Folk Rock");
    Genre savedGenre = Genre.find(folk.getId());
    assertTrue(folk.equals(savedGenre));
  }


}
