package edu.cpp.cs580.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.math.IntMath;

import edu.cpp.cs580.App;
import scraper.SpaceFlightNowPastLaunchesScraper;
import scraper.SpaceFlightNowUpcomingLaunchesScraper;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/******************************************/
	/**
	 * For Assign 3
	 * @author: Chantal Fry
	 */
	@RequestMapping(value = "/cs580/hello", method = RequestMethod.GET)
	String helloWorld() {
		return "Hello World!";
	}

	/******************************************/
	/**
	 * For Assign 3
	 * @author: Fariba
	 */
	@RequestMapping(value = "/cs580/fariba", method = RequestMethod.GET)
	String helloWorldFariba() {
		return "Hello world from Fariba!";
	}

	/******************************************/
	/**
	 * For Assign 4
	 * @author: Fariba
	 */
	@RequestMapping(value = "/cs580/gcd", method = RequestMethod.GET)
	String helloWorldGcd() {
		int a = 18;
		int b = 24;
		int gcd = IntMath.gcd(a, b);

		return "IntMath::gcd() example: GCD of "+a+" and "+b+" is "+gcd;
	}

	/******************************************/
	/**
	 * For Assign 4
	 * @author: Chantal Fry
	 */
	@RequestMapping(value = "/cs580/getupcominglaunches", method = RequestMethod.GET)
	String getUpcomingLaunches() {
		SpaceFlightNowUpcomingLaunchesScraper s =
				new SpaceFlightNowUpcomingLaunchesScraper();
		return s.getMissionInfo();
	}

	/******************************************/
	/**
	 * @author: Chantal Fry
	 */
	@RequestMapping(value = "/cs580/getpastlaunches", method = RequestMethod.GET)
	String getPastLaunches() {
		SpaceFlightNowPastLaunchesScraper s =
				new SpaceFlightNowPastLaunchesScraper();
		return s.getMissionInfo();
	}
	
	@RequestMapping(value = "/data/upcoming_launches", method = RequestMethod.GET)
	String getUpcomingLaunchesJson() throws IOException {
		BufferedReader fr = new BufferedReader(new FileReader("upcoming_launches.json"));
		return fr.readLine();
	}
	
	@RequestMapping(value = "/data/past_launches", method = RequestMethod.GET)
	String getPastLaunchesJson() throws IOException {
		BufferedReader fr = new BufferedReader(new FileReader("past_launches.json"));
		return fr.readLine();
	}
}
