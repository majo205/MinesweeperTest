package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;
	private Minesweeper minesweeper = Minesweeper.getInstance();

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field
	 * )
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();
			if((field.getState() == GameState.SOLVED)){
				System.out.println("YOU WIN!");
				System.exit(0);
			}else if((field.getState() == GameState.FAILED)){
				System.out.println("GAME OVER!");
				System.exit(0);
			}
		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		System.out
				.println("Time: " + field.getPlayingSeconds());
		System.out.println("Remaining mines: " + field.getRemainingMineCount());
		System.out.println();
		System.out.printf("%3s", " ");
		for (int i = 0; i < field.getRowCount(); i++) {
			System.out.printf("%3d", i);
		}
		System.out.println();
		
		for (int i = 0; i < field.getRowCount(); i++) {
			System.out.printf("%3s", (char) (i + 65));
			
			for (int j = 0; j < field.getColumnCount(); j++) {
				if (field.getTile(i, j).getState() == State.CLOSED) {
					System.out.printf("%3s", "-");
				} else if (field.getTile(i, j).getState() == State.MARKED) {
					System.out.printf("%3s", "M");		
				} else if (field.getTile(i, j).getState() == State.QUESTION) {
					System.out.printf("%3s", "?");
				} else if (field.getTile(i, j).getState() == State.OPEN) {
					if (field.getTile(i, j) instanceof Mine) {
						System.out.printf("%3s", "X");
					} else if (field.getTile(i, j) instanceof Clue) {
						System.out.printf("%3d",
								((Clue) field.getTile(i, j)).getValue());
					}
				}
			}
			System.out.println();
		}
	}
	
	private void handleInput(String input) throws WrongFormatException{
		Pattern pattern = Pattern
				.compile("(O([A-I])([0-9]))|(M([A-I])([0-9]))|(X)");

		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {
			if (matcher.group(1) == input) {
				char character = matcher.group(2).charAt(0);
				int integer = character - 'A';

				field.openTile(integer, Integer.parseInt(matcher.group(3)));

			} else if (matcher.group(4) == input) {
				char character = matcher.group(5).charAt(0);
				int integer = character - 'A';
						
				field.markTile(integer, Integer.parseInt(matcher.group(6)));		
				
			} else if (matcher.group(7) == input) {
				System.out.println("GAME OVER.");
				System.exit(0);
			}
		} else {
			throw new WrongFormatException("Wrong input format.");
		}
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		System.out
				.println("Please enter your selection <X> EXIT, <MA1> MARK, <OB4> OPEN :");
		String inputLine = readLine();
		String input = inputLine.toUpperCase();
		try {
			handleInput(input);
		} catch (WrongFormatException e) {
			System.out.println("CHYBA: "+e.getMessage());
		}
		

	}
	
	
}
