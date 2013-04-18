package minesweeper;

public class DatabaseSetting {
    //public static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    public static final String USER = "system";
    public static final String PASSWORD = "password";

    public static final String QUERY_CREATE_BEST_TIMES = "CREATE TABLE player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL, dificult VARCHAR(128) NOT NULL)";
    public static final String QUERY_ADD_BEST_TIME = "INSERT INTO player_time (name, best_time, dificult) VALUES (?, ?, ?)";
    public static final String QUERY_SELECT_BEST_TIMES = "SELECT name, best_time, dificult FROM player_time";

    private DatabaseSetting() {}
}
