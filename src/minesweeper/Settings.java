package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings implements java.io.Serializable {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	public static final Settings BEGINNER = new Settings(9, 9, 10);
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	public static final Settings EXPERT = new Settings(16, 30, 5);
	private static final String SETTING_FILE = System.getProperty("user.home")
			+ System.getProperty("file.separator") + "minesweeper.settings";

	public Settings(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
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

	@Override
	public int hashCode() {
		return rowCount * columnCount * mineCount;
	}

	@Override
	public boolean equals(Object obj) {
		Settings other = (Settings) obj;
		if (columnCount != other.columnCount)
			return false;
		if (mineCount != other.mineCount)
			return false;
		if (rowCount != other.rowCount)
			return false;
		return true;
	}

	public void save() {
		FileOutputStream fos = null;
		ObjectOutputStream outputSettings = null;
		try {
			fos = new FileOutputStream(SETTING_FILE);
			outputSettings = new ObjectOutputStream(fos);
			outputSettings.writeObject(this);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputSettings.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static Settings load() {
		Settings settings = EXPERT;
		try {
			FileInputStream fis = new FileInputStream(SETTING_FILE);
			ObjectInputStream inputSettings = new ObjectInputStream(fis);
			settings = (Settings) inputSettings.readObject();
		} catch (IOException e) {
			System.out.println("Settings not found.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return settings;
	}
}
