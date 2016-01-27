package scraper;

import java.util.ArrayList;

public interface SpaceFlightNowScraper {
	static String[] SPACEX_ROCKETS = { "falcon" };
	public static final String futureURL = "http://spaceflightnow.com/launch-schedule/";
	public static final String pastURL = "http://spaceflightnow.com/tracking/launchlog.html";

	abstract String getMissionInfo();

	abstract ArrayList<String> getDates();

	abstract ArrayList<String> getRockets();

	abstract ArrayList<String> getPayloads();

	abstract ArrayList<String> getLaunchLocations();

	abstract ArrayList<String> getLaunchDescriptions();

	abstract String writeToJson(ArrayList<String> dates, 
			ArrayList<String> launchVehicles, ArrayList<String> payloads, 
			ArrayList<String> locations, ArrayList<String> descriptions);
}
