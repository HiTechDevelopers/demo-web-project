package scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SpaceFlightNowUpcomingLaunchesScraper implements SpaceFlightNowScraper {
	protected Document d;

	public SpaceFlightNowUpcomingLaunchesScraper() {
		try {
			this.d = Jsoup.connect(SpaceFlightNowScraper.futureURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	@Override
	public String getMissionInfo() {
		ArrayList<String> dates = getDates();
		ArrayList<String> launchVehicles = getRockets();
		ArrayList<String> payloads = getPayloads();
		ArrayList<String> locations = getLaunchLocations();
		ArrayList<String> descriptions = getLaunchDescriptions();
		return writeToJson(dates, launchVehicles, payloads, locations, descriptions);
	}

	@Override
	public ArrayList<String> getDates() {
		Elements dates = d.select(".launchdate");
		Elements times = d.select("div.missiondata");
		ArrayList<String> finalizedDates = new ArrayList<String>();
		for (Element e : dates) {
			finalizedDates.add(e.ownText() +
					(times.get(finalizedDates.size()).text()
							.split("window:|period:|time:"))[1]
									.split("Launch site:")[0]);
		}
		return finalizedDates;
	}

	@Override
	public ArrayList<String> getRockets() {
		Elements launchVehicles = d.select(".mission");
		ArrayList<String> finalizedLaunchVehicles = new ArrayList<String>();
		for (Element e : launchVehicles) {
			finalizedLaunchVehicles.add(e.ownText().split(" \u2022 ")[0]);
		}
		return finalizedLaunchVehicles;
	}

	@Override
	public ArrayList<String> getPayloads() {
		Elements payloads = d.select(".mission");
		ArrayList<String> finalizedPayloads = new ArrayList<String>();
		for (Element e : payloads) {
			finalizedPayloads.add(e.ownText().split(" \u2022 ")[1]);
		}
		return finalizedPayloads;
	}

	@Override
	public ArrayList<String> getLaunchLocations() {
		Elements locations = d.select("div.missiondata");
		ArrayList<String> finalizedLocations = new ArrayList<String>();
		for (Element e : locations) {
			finalizedLocations.add(e.text().split("Launch site:")[1]);
		}
		return finalizedLocations;
	}

	@Override
	public ArrayList<String> getLaunchDescriptions() {
		Elements descriptions = d.select("div.missdescrip");
		ArrayList<String> finalizedDescriptions = new ArrayList<String>();
		for (Element e : descriptions) {
			finalizedDescriptions.add(e.ownText());
		}
		return finalizedDescriptions;
	}

	@Override
	public String writeToJson(ArrayList<String> dates, ArrayList<String> launchVehicles,
			ArrayList<String> payloads, ArrayList<String> locations, ArrayList<String> descriptions) {
		JSONObject parent = new JSONObject();
		JSONArray a = new JSONArray();
		for (int i = 0; i < dates.size(); i++) {
			for (String s : SpaceFlightNowScraper.SPACEX_ROCKETS) {
				if ((launchVehicles.get(i).toLowerCase()).contains(s)) {
					JSONObject o = new JSONObject();
					o.put("date", dates.get(i));
					o.put("launchVehicle", launchVehicles.get(i));
					o.put("payload", payloads.get(i));
					o.put("launchLocation", locations.get(i));
					o.put("description", descriptions.get(i));
					a.add(o);
				}
			}
		}

		String jsonString = JSONValue.toJSONString(a);

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("./data/upcoming_launches.json"), "utf-8"))) {
			writer.write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}
}
