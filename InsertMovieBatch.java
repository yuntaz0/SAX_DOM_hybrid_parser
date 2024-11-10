import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertMovieBatch {
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO stage_movies (movie_id, title, year, director) VALUES ('%s', '%s', %d, '%s');";
    private static final String TEMP_FILE = "Test.txt";
    public void batchInsertMovies(List<Movie> movies) {
        try (FileWriter writer = new FileWriter(TEMP_FILE, true)) {
            for (Movie movie : movies) {
                String insertSql = String.format(INSERT_SQL_TEMPLATE,
                        movie.getId(),
                        movie.getTitle(), // Escape single quotes
                        movie.getYear(),
                        movie.getDirector()); // Escape single quotes

                writer.write(insertSql + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + TEMP_FILE);
            e.printStackTrace();
        }
    }
}
