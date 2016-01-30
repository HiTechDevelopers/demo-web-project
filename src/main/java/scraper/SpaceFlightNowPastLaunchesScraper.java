package scraper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpaceFlightNowPastLaunchesScraper implements SpaceFlightNowScraper {

	private Document d;

	public SpaceFlightNowPastLaunchesScraper() {
		try {
			this.d = Jsoup.connect(SpaceFlightNowScraper.pastURL).get();
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
		Elements dates = d.select("td[colspan=2]");
		ArrayList<String> finalizedDates = new ArrayList<String>();
		for (Element e : dates) {
			try {
				finalizedDates.add((e.text().split("time:")[1]).split("Launch")[0]);
			} catch (Exception exception) {
				continue;
			}
		}
		return finalizedDates;
	}

	@Override
	public ArrayList<String> getRockets() {
		Elements launchVehicles = d.select("td[bgcolor=#000066]");
		ArrayList<String> finalizedLaunchVehicles = new ArrayList<String>();
		for (Element e : launchVehicles) {
			finalizedLaunchVehicles.add(e.text().split("    ")[0]);
		}
		return finalizedLaunchVehicles;
	}

	@Override
	public ArrayList<String> getPayloads() {
		Elements payloads = d.select("td[bgcolor=#000066]");
		ArrayList<String> finalizedPayloads = new ArrayList<String>();
		for (Element e : payloads) {
			finalizedPayloads.add(e.text().split("    ")[1]);
		}
		return finalizedPayloads;
	}

	@Override
	public ArrayList<String> getLaunchLocations() {
		Elements locations = d.select("td[colspan=2]");
		ArrayList<String> finalizedLocations = new ArrayList<String>();
		for (Element e : locations) {
			try {
				finalizedLocations.add((e.toString().split("<\\/b>")[2]).split("<br>")[0]);
			} catch (Exception exception) {
				continue;
			}
		}
		return finalizedLocations;
	}

	@Override
	public ArrayList<String> getLaunchDescriptions() {
		Elements descriptions = d.select("td[colspan=2]");
		ArrayList<String> finalizedDescriptions = new ArrayList<String>();
		for (Element e : descriptions) {
			try {
				finalizedDescriptions.add((e.toString().split("<\\/b>")[2]).split("<br>")[2]);
			} catch (Exception exception) {
				continue;
			}
		}
		return finalizedDescriptions;
	}

	@Override
	public String writeToJson(ArrayList<String> dates, 
			ArrayList<String> launchVehicles, ArrayList<String> payloads, 
			ArrayList<String> locations, ArrayList<String> descriptions) {
		JSONObject parent = new JSONObject();
		JSONArray a = new JSONArray();
		for (int i = 0; i < launchVehicles.size(); i++) {
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
		parent.put("upcomingLaunches", a);

		String jsonString = JSONValue.toJSONString(parent);
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("./src/main/resources/static/data/past_launches.json"), "utf-8"))) {
			writer.write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}

}
