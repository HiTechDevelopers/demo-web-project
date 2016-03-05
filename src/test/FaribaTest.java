//package test;
//
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Scanner;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import scraper.SpaceFlightNowPastLaunchesScraper;
//
//public class FaribaTest extends TestCase {
//	public FaribaTest(String testName) {
//		super(testName);
//	}
//
//	/**
//	 * @return the suite of tests being tested
//	 */
//	public static Test suite() {
//		return new TestSuite(FaribaTest.class);
//	}
//
//	public void testPastLaunchesScraper() {
//		String content = null;
//        URLConnection connection = null;
//
//        try {
//          connection =  new URL("http://localhost:8080/cs580/getpastlaunches").openConnection();
//          Scanner scanner = new Scanner(connection.getInputStream());
//          scanner.useDelimiter("\\Z");
//          content = scanner.next();
//        }catch ( Exception ex ) {}
//
//        assertTrue(content.length() == 0);
//
//	}
//}
