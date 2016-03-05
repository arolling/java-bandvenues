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

  @Test
  public void allBands_retrievesListOfAllBandsInGenre_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    old.updateFans(100000000);
    old.addGenre(2);
    old.addGenre(1);
    old.addGenre(7);
    neo.addGenre(2);
    assertTrue(Genre.find(1).allBands().contains(old));
    assertEquals(2, Genre.find(2).allBands().size());
  }

  @Test public void genreFilter_retrievesBandsThatMatchAnyOfSeveralGenres() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    Band old2 = new Band("The Ladybugs");
    old2.save();
    old.updateFans(100000000);
    old.addGenre(2);
    old.addGenre(1);
    old.addGenre(7);
    old2.addGenre(7);
    neo.addGenre(2);
    String [] testids = {"1", "7"};
    assertTrue(Genre.genreFilter(testids).contains(old2));
    assertEquals(2, Genre.genreFilter(testids).size());
  }
}
