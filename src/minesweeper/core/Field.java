package minesweeper.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

import javax.swing.Timer;

/**
 * Field represents playing field and game logic.
 */
public class Field implements java.io.Serializable{
	/** Playing field tiles. */
	private Tile[][] tiles;

	/** Field row count. Rows are indexed from 0 to (rowCount - 1). */
	private final int rowCount;

	/** Column count. Columns are indexed from 0 to (columnCount - 1). */
	private final int columnCount;

	/** Mine count. */
	private final int mineCount;

	/** Game state. */
	private GameState state = GameState.NEW;

	private long startMillis;

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	public int getPlayingSeconds() {
		if (state == GameState.NEW) {
			return 0;
		}
		return (int) (System.currentTimeMillis() - startMillis) / 1000;
	}

	private void startGame(int clickedRow, int clickedColumn) {
		//generate(clickedRow, clickedColumn);
		state = GameState.PLAYING;
		startMillis = System.currentTimeMillis();
	}

	/**
	 * Opens tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column numberF
	 */
	public void openTile(int row, int column) {
		if (state == GameState.NEW) {
			startGame(row, column);
		}
		Tile tile = tiles[row][column];

		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				setState(GameState.FAILED);
				return;
			} else if (tile instanceof Clue) {
				if (((Clue) tile).getValue() == 0) {
					openAdjacentTiles(row, column);
				}
			}
			if (isSolved()) {
				setState(GameState.SOLVED);
				return;
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.QUESTION);
		} else if (tile.getState() == Tile.State.QUESTION) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate(/*int clickedRow, int clickedColumn*/) {
		generateMines(/*clickedRow, clickedColumn*/);
		generateClues();
	}

	private void generateClues() {
		// generating clues
		for (int i = 0; i < this.rowCount; i++) {
			for (int j = 0; j < this.columnCount; j++) {
				if (tiles[i][j] == null) {
					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}
	}

	private void generateMines(/*int clickedRow, int clickedColumn*/) {
		int newMines = 0;

		// generating mines
		while (newMines < mineCount) {
			Random random = new Random();

			int randomRow = random.nextInt(this.rowCount);
			int randomColumn = random.nextInt(this.columnCount);
			
//			if(randomRow == clickedRow && randomColumn == clickedColumn){
//				continue;
//			}

			if (tiles[randomRow][randomColumn] == null) {
				tiles[randomRow][randomColumn] = new Mine();
				newMines++;
			}

		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 * 
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		if ((rowCount * columnCount) - getNumberOf(Tile.State.OPEN) == mineCount) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 * 
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int actRow = row + i;
				int actColumn = column + j;
				if (actRow >= 0 && actRow < getRowCount() && actColumn >= 0
						&& actColumn < getColumnCount()) {
					if (tiles[actRow][actColumn] instanceof Mine) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int i = 0; i < this.rowCount; i++) {
			for (int j = 0; j < this.columnCount; j++) {
				if (tiles[i][j].getState() == state) {
					count++;
				}
			}
		}
		return count;
	}

	public void openAdjacentTiles(int row, int column) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int actRow = row + i;
				int actColumn = column + j;
				if (actRow >= 0 && actRow < getRowCount() && actColumn >= 0
						&& actColumn < getColumnCount()) {

					openTile(actRow, actColumn);

				}
			}
		}
	}

	public int getRemainingMineCount() {
		return (getMineCount() - getNumberOf(Tile.State.MARKED));
	}
}
