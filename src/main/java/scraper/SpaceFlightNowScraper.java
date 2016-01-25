package scraper;

import org.jsoup.select.Elements;

public interface SpaceFlightNowScraper {
	static String[] SPACEX_ROCKETS = { "falcon" };

	abstract String getMissionInfo();

	abstract Elements getDates();

	abstract Elements getRockets();

	abstract Elements getPayloads();

	abstract Elements getLaunchLocations();

	abstract Elements getLaunchDescriptions();

	abstract String writeToJson(Elements dates, Elements launchVehicles, 
			Elements payloads, Elements locations, Elements descriptions);
}
