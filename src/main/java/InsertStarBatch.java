import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertStarBatch {
    private static final String INSERT_SQL_TEMPLATE = "INSERT IGNORE INTO stars (id, name, birthYear) VALUES ('%s', '%s', %d);";

    public void batchInsertStars(List<Star> stars) {
        try (FileWriter writer = new FileWriter(Main.SQL_STAR, true)) {
            for (Star star : stars) {
                String name = star.getName() != null ? star.getName().replace("'", "''") : "";
                String id = star.getId() != null ? star.getId().replace("'", "''") : "";
                String insertSql = String.format(INSERT_SQL_TEMPLATE,
                        id,
                        name,
                        star.getBirthYear());

                writer.write(insertSql + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + Main.SQL_STAR);
            e.printStackTrace();
        }
    }
}
