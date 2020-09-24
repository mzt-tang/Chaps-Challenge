package MonkeyTest;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;

/**
 * Runs full suite of tests, reports results
 * 
 * @author Ben
 *
 */

public class MonkeyTest {
	public static void main(String[] args) {
	
		Result result = JUnitCore.runClasses(ApplicationTests.class, MazeTests.class);
		
		for(Failure fail : result.getFailures()) { System.out.println(fail.toString());}
		
		System.out.println("Test Successful: " + result.wasSuccessful());
	}
	
}

