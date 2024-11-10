import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertMovieBatch {
    private static final String INSERT_SQL_TEMPLATE = "INSERT IGNORE INTO stage_movies (id, title, year, director) VALUES ('%s', '%s', %d, '%s');";

    public void batchInsertMovies(List<Movie> movies) {
        try (FileWriter writer = new FileWriter(Main.TEMP_FILE_2, true)) {
            for (Movie movie : movies) {
                String title = movie.getTitle() != null ? movie.getTitle().replace("'", "''") : "";
                String director = movie.getDirector() != null ? movie.getDirector().replace("'", "''") : "";

                String insertSql = String.format(INSERT_SQL_TEMPLATE,
                        movie.getId(),
                        title,
                        movie.getYear(),
                        director);

                writer.write(insertSql + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + Main.TEMP_FILE_2);
            e.printStackTrace();
        }
    }
}
