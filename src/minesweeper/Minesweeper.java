package minesweeper;

import java.util.Scanner;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.swingui.SwingUI;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */
    private UserInterface userInterface;
    private long startMillis;
    private BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;
    private Settings setting; 
    private static final String DEFAULT_UI = "swing";
    
    /**
     * Constructor.
     */
    private Minesweeper(boolean swing) {
    	setting = Settings.load();
    	
    	instance = this;
    	if(swing){
    		userInterface = create("swing");
    	}else{
    		userInterface = create("console");
    	}
        newGame();
    }
    
    public static Minesweeper getInstance(){
//    	if(instance == null){
//    		instance = new Minesweeper();
//    	}
    	return instance;
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
    	boolean swing = args.length != 1 || !"-console".equals(args[0]);
        new Minesweeper(swing);
    }

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		this.setting = setting;
		setting.save();
	}
	
	public void newGame() {
		Field field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());

        userInterface.newGameStarted(field);
    }
	
	private UserInterface create(String name){
		if(DEFAULT_UI.equals("swing")){
			return new SwingUI();
		}else if(DEFAULT_UI.equals("console")){
			return new ConsoleUI();
		}
		
		new RuntimeException("No valid UI specified");
		return null;
	}
}
