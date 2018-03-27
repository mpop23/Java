
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Levels {

    static final String DB_URL = "jdbc:mysql://localhost/histospot";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) throws SQLException {
        try (
                Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement st = con.createStatement()) {

            System.out.println("Starting");

            int novice = 15;
            int apprentice = 25;
            int adept = 50;
            int expert = 75;
            int master = 100;
            int legendary = 200;

            TreeMap<Integer, Integer> levels = new TreeMap<>();
            int exp = 10;
            levels.put(1, exp);

            for (int level = 2; level < novice; ++level) {
                exp += 10 + 1 * level;
                levels.put(level, exp);
            }

            for (int level = novice; level < apprentice; ++level) {
                exp += 15 + 3 * level;
                levels.put(level, exp);
            }

            for (int level = apprentice; level < adept; ++level) {
                exp += 25 + 5 * level;
                levels.put(level, exp);
            }

            for (int level = adept; level < expert; ++level) {
                exp += 50 + 10 * level;
                levels.put(level, exp);
            }

            for (int level = expert; level < master; ++level) {
                exp += 25 + 15 * level;
                levels.put(level, exp);
            }

            for (int level = master; level <= legendary; ++level) {
                exp += 500 + 50 * level;
                levels.put(level, exp);
            }

            levels.entrySet().forEach(e -> System.out.println("level " + e.getKey() + ": " + e.getValue()));

            st.executeUpdate("DELETE FROM level");

            int inserts = levels.entrySet().stream()
                    .map(e -> "INSERT INTO level VALUES (" + e.getKey() + ", " + e.getValue() + ")")
                    .mapToInt((sql) -> {
                        try {
                            return st.executeUpdate(sql);
                        } catch (SQLException ex) {
                            return 0;
                        }
                    })
                    .sum();
            
            if (inserts < levels.size()) {
                System.err.println("Something whent wrong!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
