package minesweeper.test;

import static org.junit.Assert.*;

import minesweeper.BestTimes;

import org.junit.Test;

public class BestTimesTest {

	@Test
	public void testToStringEmpty() {
		BestTimes bt = new BestTimes();
		
		assertEquals("", bt.toString());
	}
	
	@Test
	public void testToStringOne() {
		BestTimes bt = new BestTimes();
		//bt.addPlayerTime("jano", 1);
		assertEquals("1 1 jano \n", bt.toString());
	}
	
	@Test
	public void testToStringTwo() {
		BestTimes bt = new BestTimes();
		//bt.addPlayerTime("jano", 1);
		//bt.addPlayerTime("jano2", 22);
		assertEquals("1 1 jano \n2 22 jano2 \n", bt.toString());
	}
}
