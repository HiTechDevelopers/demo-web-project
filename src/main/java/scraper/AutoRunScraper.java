package scraper;

import java.util.concurrent.*;

public class AutoRunScraper {

  public AutoRunScraper() {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(scraperRunnable, 0, 86400, TimeUnit.SECONDS);
  }

  Runnable scraperRunnable = new Runnable() {
    public void run() {
      SpaceFlightNowUpcomingLaunchesScraper s1 =
          new SpaceFlightNowUpcomingLaunchesScraper();
      s1.getMissionInfo();
      SpaceFlightNowPastLaunchesScraper s2 =
  				new SpaceFlightNowPastLaunchesScraper();
  		s2.getMissionInfo();
    }
  };

}
