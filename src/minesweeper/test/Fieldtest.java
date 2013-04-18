package minesweeper.test;

import static org.junit.Assert.*;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

import org.junit.Test;

public class Fieldtest {

	private static int ROWS = 8;
	private static int COLUMNS = 8;
	private static int MINES = 3;

	@Test
	public void testOpenTile() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		field.openTile(1, 1);
		Tile tile = field.getTile(1, 1);
		assertEquals(Tile.State.OPEN, tile.getState());
	}

	@Test
	public void testMarkTile() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		field.markTile(2, 2);
		Tile tile = field.getTile(2, 2);
		assertEquals(Tile.State.MARKED, tile.getState());
	}

	@Test
	public void isFailed() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Mine) {
					field.openTile(row, column);
					assertEquals(GameState.FAILED, field.getState());
				}
			}
		}
	}

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field
						.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		int mineCount = 0;
		int clueCount = 0;
		
		Field field = new Field(ROWS, COLUMNS, MINES);
		
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount()); 
		assertEquals(MINES, field.getMineCount());

		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if(field.getTile(row, column) instanceof Mine){
					mineCount++;
				}else if(field.getTile(row, column) instanceof Clue){
					clueCount++;
				}
				assertNotNull(field.getTile(row, column));
			}
		}
		
		assertEquals(MINES, mineCount);
		assertEquals(ROWS * COLUMNS - MINES, clueCount);
	}

}
