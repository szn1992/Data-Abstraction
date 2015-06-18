// Zhuonan Sun && Jiahui Xu
// 3/10/2014
// CSE332 Project3 PhaseB
// test file of PopulationQuery

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TestPopulationQuery {
	
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	String[] version = {"-v3", "-v4", "-v5"};
	private String[] args =  {"CenPop2010.txt", "20", "25", "-v1"};
	
	@Before
	public void setup() {
		args[0] = "CenPop2010.txt";
		args[1]	= "20";
		args[2]	= "25"; 
		args[3] = "-v1";
	}
	
	/** Test Command Line Checking =================================================================================================== **/
	@Test(timeout = TIMEOUT)
	public void test_correct_command() {
		assertTrue(PopulationQuery.checkCommand(args));
	}
	
	@Test(timeout = TIMEOUT)
	public void test_x_or_y_is_too_small() {
		args[1] = "0";
		assertFalse(PopulationQuery.checkCommand(args));
		args[2] = "-8";
		assertFalse(PopulationQuery.checkCommand(args));
	}
	
	@Test(timeout = TIMEOUT)
	public void test_short_argument() {
		args = new String[2];
		assertFalse(PopulationQuery.checkCommand(args));
	}
	
	@Test(timeout = TIMEOUT)
	public void test_invalid_version() {
		args[3] = "8";
		assertFalse(PopulationQuery.checkCommand(args));
	}
	
	/** Test User Input Checking ========================================================================================= **/
	@Test(timeout = TIMEOUT)
	public void test_correct_input() {
		String input = "1 1 20 4";
		assertTrue(PopulationQuery.checkUserInput(input, 20, 25));
	}
	
	public void test_short_input() {
		String input = "1 1 3";
		assertFalse(PopulationQuery.checkUserInput(input, 20, 25));
	}
	
	public void test_out_of_bound() {
		String input = "0 1 25 20";
		assertFalse(PopulationQuery.checkUserInput(input, 20, 25));
		input = "1 1 25 21";
		assertFalse(PopulationQuery.checkUserInput(input, 20, 25));
	}
	
	public void test_irregular_but_valid_input() {
		String input = "   1              1        20          4";
		assertTrue(PopulationQuery.checkUserInput(input, 20, 25));
	}
}
