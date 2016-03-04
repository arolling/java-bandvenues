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

  @Test
  public void save_savesVenueToDBAndSetsID_true() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    assertTrue(Venue.all().get(0) instanceof Venue);
    assertEquals(carnegie.getId(), Venue.all().get(0).getId());
  }

  @Test
  public void equals_checksNameIdAndCapacity_true() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue savedVenue = Venue.all().get(0);
    assertTrue(carnegie.equals(savedVenue));
  }

  @Test
  public void find_retrievesSpecificInstanceOfVenue_true() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    assertEquals(carnegie, Venue.find(carnegie.getId()));
  }

  @Test
  public void delete_deletesSpecificVenue_false() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    carnegie.delete();
    assertFalse(Venue.all().contains(carnegie));
  }

  @Test
  public void deleteAll_deletesAllVenues_true() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    Venue.deleteAll();
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void updateCapacity_changesVenueSizeLocallyAndRemotely_true() {
    Venue carnegie = new Venue("Carnegie Hall", 28040);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    carnegie.updateCapacity(2804);
    Venue savedVenue = Venue.find(carnegie.getId());
    assertEquals(carnegie.getSize(), savedVenue.getSize());
  }

  @Test
  public void updateName_updatesVenueNameLocallyAndRemotely_true() {
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    carnegie.updateName("Carnegie Concert Hall");
    Venue savedVenue = Venue.find(carnegie.getId());
    assertTrue(carnegie.equals(savedVenue));
  }

}
