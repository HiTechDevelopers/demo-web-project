//package test;
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
//import java.util.Scanner;
//import java.net.URL;
//import java.net.URLConnection;
//
///**
// * @author Chantal
// * For assignment 6
// */
//public class ChantalTest extends TestCase
//{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public ChantalTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Test whether we get data back from upcoming launches
//     */
//    public void testUpcomingLaunchesGetData()
//    {
//        String content = null;
//        URLConnection connection = null;
//
//        try {
//          connection =  new URL("http://localhost:8080/cs580/getupcominglaunches").openConnection();
//          Scanner scanner = new Scanner(connection.getInputStream());
//          scanner.useDelimiter("\\Z");
//          content = scanner.next();
//        }catch ( Exception ex ) {}
//
//        assertTrue(content.length() == 0);
//    }
//}
