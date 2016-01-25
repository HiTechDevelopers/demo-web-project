package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SpaceFlightNowUpcomingLaunchesScraper implements SpaceFlightNowScraper {
	protected Document d;

	public SpaceFlightNowUpcomingLaunchesScraper(String string) {
		try {
			d = Jsoup.connect(string).get();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public String getMissionInfo() {
		Elements dates = getDates();
		Elements launchVehicles = getRockets();
		Elements payloads = getPayloads();
		Elements locations = getLaunchLocations();
		Elements descriptions = getLaunchDescriptions();
		return writeToJson(dates, launchVehicles, payloads, locations, descriptions);
	}

	@Override
	public Elements getDates() {
		Elements dates = d.select(".launchdate");
		return dates;
	}

	@Override
	public Elements getRockets() {
		Elements launchVehicles = d.select(".mission");
		return launchVehicles;
	}

	@Override
	public Elements getPayloads() {
		return getRockets();
	}

	@Override
	public Elements getLaunchLocations() {
		Elements locations = d.select("div.missiondata");
		return locations;
	}

	@Override
	public Elements getLaunchDescriptions() {
		Elements descriptions = d.select("div.missdescrip");
		return descriptions;
	}

	@Override
	public String writeToJson(Elements dates, Elements launchVehicles,
			Elements payloads, Elements locations, Elements descriptions) {
		JSONObject parent = new JSONObject();
		JSONArray a = new JSONArray();
		for (int i = 0; i < dates.size(); i++) {
			for (String s : SpaceFlightNowScraper.SPACEX_ROCKETS) {
				if ((launchVehicles.get(i).ownText().toLowerCase()).contains(s)) {
					JSONObject o = new JSONObject();
					o.put("date", dates.get(i).ownText());
					o.put("launchVehicle", launchVehicles.get(i).ownText().split(" \u2022 ")[0]);
					o.put("payload", payloads.get(i).ownText().split(" \u2022 ")[1]);
					o.put("launchLocation", locations.get(i).text());
					o.put("description", descriptions.get(i).ownText());
					a.add(o);
				}
			}
		}
		parent.put("upcomingLaunches", a);

		String jsonString = JSONValue.toJSONString(parent);

		return jsonString;
	}
}
