import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InsertMovieBatch {
    private static final String INSERT_MOVIE_SQL_TEMPLATE =
            "INSERT IGNORE INTO stage_movies (id, title, year, director) VALUES ('%s', '%s', %d, '%s');";
    private static final String INSERT_GENRE_SQL_TEMPLATE =
            "INSERT IGNORE INTO stage_genres (name) VALUES ('%s');";
    private static final String INSERT_GENRES_IN_MOVIES_SQL_TEMPLATE =
            "INSERT IGNORE INTO stage_genres_in_movies (movieId, genreId) " +
                    "VALUES ('%s', (SELECT id FROM stage_genres WHERE name = '%s'));";
    private static final String INSERT_RATING_SQL_TEMPLATE =
            "INSERT IGNORE INTO stage_ratings (movieId, rating, numVotes) VALUES ('%s', %.1f, %d);";

    public void batchInsertMovies(List<Movie> movies) {
        StringBuilder sqlStatements = new StringBuilder();

        for (Movie movie : movies) {
            String title = movie.getTitle() != null ? movie.getTitle().replace("'", "''") : "";
            String director = movie.getDirector() != null ? movie.getDirector().replace("'", "''") : "";
            String movieInsertSql = String.format(INSERT_MOVIE_SQL_TEMPLATE,
                    movie.getId(),
                    title,
                    movie.getYear(),
                    director);

            sqlStatements.append(movieInsertSql).append(System.lineSeparator());

//            if (movie.getGenres() != null) {
//                for (String genre : movie.getGenres()) {
//                    String genreEscaped = genre.replace("'", "''");
//                    String genreInsertSql = String.format(INSERT_GENRE_SQL_TEMPLATE, genreEscaped);
//                    sqlStatements.append(genreInsertSql).append(System.lineSeparator());
//
//                    String genreInMoviesSql = String.format(INSERT_GENRES_IN_MOVIES_SQL_TEMPLATE,
//                            movie.getId(), genreEscaped);
//                    sqlStatements.append(genreInMoviesSql).append(System.lineSeparator());
//                }
//            }


            String ratingInsertSql = String.format(INSERT_RATING_SQL_TEMPLATE,
                    movie.getId(),
                    movie.getRating(),
                    movie.getVotes());
            sqlStatements.append(ratingInsertSql).append(System.lineSeparator());
        }

        try (FileWriter writer = new FileWriter(Main.SQL_MOVIE, true)) {
            writer.write(sqlStatements.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + Main.SQL_MOVIE);
            e.printStackTrace();
        }
    }
}
