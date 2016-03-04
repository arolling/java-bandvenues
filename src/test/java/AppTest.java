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
}
