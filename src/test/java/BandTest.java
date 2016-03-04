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

  @Test
  public void equals_checksNameIdAndFanbase_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band savedBand = Band.all().get(0);
    assertTrue(neo.equals(savedBand));
  }

  @Test
  public void find_retrievesSpecificInstanceOfBand_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    assertEquals(neo, Band.find(neo.getId()));
  }

  @Test
  public void delete_deletesSpecificBand_false() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    neo.delete();
    assertFalse(Band.all().contains(neo));
  }

  @Test
  public void deleteAll_deletesAllBands_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    Band.deleteAll();
    assertEquals(0, Band.all().size());
  }

  @Test
  public void updateFans_changesFanbaseSizeLocallyAndRemotely_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    old.updateFans(100000000);
    Band savedOld = Band.find(old.getId());
    assertEquals(old.getFanbase(), savedOld.getFanbase());
  }

  @Test
  public void updateName_updatesBandNameLocallyAndRemotely_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Ladybugs");
    old.save();
    old.updateName("The Beatles");
    Band savedOld = Band.find(old.getId());
    assertTrue(old.equals(savedOld));
  }

  @Test
  public void addVenue_addsPlayedVenueToBand_true() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    old.updateFans(100000000);
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    old.addVenue(msg);
    assertTrue(old.allVenues().contains(msg));
    assertFalse(old.allVenues().contains(carnegie));
  }

}
