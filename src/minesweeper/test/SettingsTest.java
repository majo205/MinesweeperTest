package minesweeper.test;

import static org.junit.Assert.*;

import minesweeper.Settings;

import org.junit.Test;

public class SettingsTest {
	private static final int ROWS = 9;
	private static final int COLS = 9;
	private static final int MINES = 9;
	
	@Test
	public void saveLoad() {
		Settings s = new Settings(ROWS, COLS, MINES);
		s.save();
		
		Settings s2 = Settings.load();
		assertNotNull(s2);
		assertEquals(9, s2.getRowCount());
		assertEquals(9, s2.getColumnCount());
		assertEquals(9, s2.getMineCount());
	}
	
	@Test
	public void equals(){
		Settings s = new Settings(ROWS, COLS, MINES);
		Settings s2 = new Settings(ROWS, COLS, MINES);
		
		assertTrue(s.equals(s2));
	}

}
