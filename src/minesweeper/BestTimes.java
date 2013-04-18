package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time, String dificult) {
		// playerTimes.add(new PlayerTime(name, time));
		insertToDB(new PlayerTime(name, time, dificult));
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		selectFromDB();
		Formatter f = new Formatter();
		// for (PlayerTime playerTime : playerTimes) {

		for (int i = 0; i < playerTimes.size(); i++) {

			f.format("%d. 	%d sec	%s %s\n", i + 1,
					playerTimes.get(i).getTime(), playerTimes.get(i).getName(),
					playerTimes.get(i).getDificult());
		}
		return f.toString();
	}

	public void reset() {
		playerTimes.clear();
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		private final String dificult;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time, String dificult) {
			this.name = name;
			this.time = time;
			this.dificult = dificult;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		public String getDificult() {
			return dificult;
		}

		@Override
		public int compareTo(PlayerTime o) {
			return this.time - o.getTime();
		}
	}

	private void insertToDB(PlayerTime playerTime) {
		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(DatabaseSetting.URL,
					DatabaseSetting.USER, DatabaseSetting.PASSWORD);
			statement = connection.createStatement();
			try {
				statement
						.executeUpdate(DatabaseSetting.QUERY_CREATE_BEST_TIMES);
			} catch (Exception e) {

			} finally {
				statement.close();
			}
			try {
				ps = connection
						.prepareStatement(DatabaseSetting.QUERY_ADD_BEST_TIME);
				ps.setString(1, playerTime.getName());
				ps.setInt(2, playerTime.getTime());
				ps.setString(3, playerTime.getDificult());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					ps.close();
				}
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out
						.println("Exception occured during saving high score to database: "
								+ e.getMessage());
			}
		}
	}

	private void selectFromDB() {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(DatabaseSetting.URL,
					DatabaseSetting.USER, DatabaseSetting.PASSWORD);
			statement = connection.createStatement();
			rs = statement
					.executeQuery(DatabaseSetting.QUERY_SELECT_BEST_TIMES);
			playerTimes.clear();
			while (rs.next()) {
				PlayerTime pt = new PlayerTime(rs.getString(1), rs.getInt(2),
						"");
				if (rs.getString(3).matches(
						Minesweeper.getInstance().getSetting().toString())) {
					playerTimes.add(pt);
				}
				Collections.sort(playerTimes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				System.out
						.println("Exception occured during loading high score to database: "
								+ e.getMessage());
			}
		}

	}
}
