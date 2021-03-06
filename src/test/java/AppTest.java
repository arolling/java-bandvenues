import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();


  //Integration testing

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void addNewBandandVenue() {
    goTo("http://localhost:4567/");
    fill("#bandName").with("The Beatles");
    submit("#addBand");
    fill("#venueName").with("Shea Stadium");
    fill("#capacity").with("80000");
    submit("#addVenue");
    assertThat(pageSource()).contains("The Beatles");
    assertThat(pageSource()).contains("capacity 80000");
  }

  @Test
  public void addVenueAndGenreToBand() {
    Band old = new Band("The Beatles");
    old.save();
    old.updateFans(100000000);
    Venue carnegie = new Venue("Carnegie Hall", 2804);
    carnegie.save();
    Venue msg = new Venue("Madison Square Gardens", 18200);
    msg.save();
    goTo("http://localhost:4567/bands/" + old.getId());
    click("#venue" + msg.getId());
    click("#venue" + carnegie.getId());
    submit("#addGigs");
    click("#genre5");
    click("#genre7");
    submit("#categorize");
    assertThat(pageSource()).contains("capacity 18200");
  }

  @Test
  public void editBandNameAndFanbase() {
    Band old = new Band("The Beeeetles");
    old.save();
    goTo("http://localhost:4567/bands/" + old.getId());
    fill("#editName").with("The Beatles");
    submit("#edit");
    fill("#editFans").with("10000000");
    submit("#edit");
    assertThat(pageSource()).contains("The Beatles");
    assertThat(pageSource()).contains("10000000");
  }

  @Test
  public void deleteBand() {
    Band neo = new Band("Neo");
    neo.save();
    Band old = new Band("The Beatles");
    old.save();
    goTo("http://localhost:4567/bands/" + old.getId());
    click("a", withText("Delete Band"));
    assertThat(pageSource()).doesNotContain("The Beatles");
    assertThat(pageSource()).contains("Neo");
  }
}
