package MonkeyTest;

import javax.swing.JOptionPane;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;

/**
 * Runs full suite of tests, reports results
 * 
 * @author Ben
 *
 */

public class MonkeyTest {
	static int i;
	static int k = 0;
	
	
	public static void main(String[] args) {
	
		TestSuite test = new TestSuite();
		
		try {
	 i = Integer.parseInt(JOptionPane.showInputDialog(null, "How many random commands to send?\n"));
		} catch(Exception e) { JOptionPane.showMessageDialog(null, "You must input an integer");}
		
		while(k < i) {test.randomMovement(); k++;}
		
		
		System.out.println();
	}
	
}

