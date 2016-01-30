package edu.cpp.cs580.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		return "Hello World from Fariba!";
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
}
