
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import scraper.SpaceFlightNowPastLaunchesScraper;

public class FaribaTest extends TestCase {
	public FaribaTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(FaribaTest.class);
	}

	public void testPastLaunchesScraper() {
		SpaceFlightNowPastLaunchesScraper s = new SpaceFlightNowPastLaunchesScraper();
		String jsonInfo = s.getMissionInfo();
		assertTrue(jsonInfo.length() != 0);

	}
}
