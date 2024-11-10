import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertCastBatch {
    private static final String INSERT_SQL_TEMPLATE = "INSERT IGNORE INTO stage_stars_in_movies (movieId, starId) VALUES ('%s', '%s');";

    public void batchInsertCasts(List<Cast> casts) {
        try (FileWriter writer = new FileWriter(Main.SQL_CAST, true)) {
            for (Cast cast : casts) {
                String fid = cast.getFid() != null ? cast.getFid().replace("'", "''") : "";
                String starId = cast.getStarId() != null ? cast.getStarId().replace("'", "''") : "";

                String insertSql = String.format(INSERT_SQL_TEMPLATE, fid, starId);

                writer.write(insertSql + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + Main.SQL_CAST);
            e.printStackTrace();
        }
    }
}
