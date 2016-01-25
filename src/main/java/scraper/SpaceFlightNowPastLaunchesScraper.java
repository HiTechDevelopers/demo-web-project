package scraper;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;

public class SpaceFlightNowPastLaunchesScraper implements SpaceFlightNowScraper {

	private Document d;

	public SpaceFlightNowPastLaunchesScraper(String string) {
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
		Elements dates = d.select("td[colspan=2]");
		return dates;
	}

	@Override
	public Elements getRockets() {
		Elements launchVehicles = d.select("td[bgcolor=#000066]");
		return launchVehicles;
	}

	@Override
	public Elements getPayloads() {
		return getRockets();
	}

	@Override
	public Elements getLaunchLocations() {
		return getDates();
	}

	@Override
	public Elements getLaunchDescriptions() {
		return getDates();
	}

	@Override
	public String writeToJson(Elements dates, Elements launchVehicles,
			Elements payloads, Elements locations, Elements descriptions) {
		JSONObject parent = new JSONObject();
		JSONArray a = new JSONArray();
		for (int i = 0; i < launchVehicles.size(); i++) {
			for (String s : SpaceFlightNowScraper.SPACEX_ROCKETS) {
				if ((launchVehicles.get(i).text().toLowerCase()).contains(s)) {
					JSONObject o = new JSONObject();
					o.put("date", dates.get((i*2)%2).text());
					o.put("launchVehicle", launchVehicles.get(i).text().split("    ")[0]);
					o.put("payload", payloads.get(i).text().split("    ")[1]);
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
