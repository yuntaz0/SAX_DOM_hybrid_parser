import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertMovieBatch {
    private static final String TEMP_FILE = "sql_commands.sql";
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO stage_movies (movie_id, title, year, director) VALUES ('%s', '%s', %d, '%s');";

    public void batchInsertMovies(List<Movie> movies) {

        try (FileWriter writer = new FileWriter(TEMP_FILE, true)) {
            writer.write("SET GLOBAL autocommit = 0; USE moviedb;" + System.lineSeparator());

            for (Movie movie : movies) {
                String insertSql = String.format(INSERT_SQL_TEMPLATE,
                        movie.getId(),
                        movie.getTitle(),
                        movie.getYear(),
                        movie.getDirector());

                writer.write(insertSql + System.lineSeparator());
            }

            writer.write("SET GLOBAL autocommit = 1;" + System.lineSeparator());

        } catch (IOException e) {
            System.err.println("Error writing to file: " + TEMP_FILE);
            e.printStackTrace();
        }
    }
}
