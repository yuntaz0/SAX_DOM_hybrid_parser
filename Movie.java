import java.util.ArrayList;

public class Movie {
    private final String id;
    private String title;
    private Integer year;
    private double price;
    private Integer count;
    private String director;
    private ArrayList<String> genres;
    private float rating;
    private Integer votes;

    public Movie(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList genres) {
        this.genres = genres;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Movie {" +
                "ID='" + (id != null ? id : "null") + '\'' +
                ", Title='" + (title != null ? title : "null") + '\'' +
                ", Year=" + (year != null ? year : "null") +
                ", Price=$" + price +
                ", Count=" + (count != null ? count : "null") +
                ", Director='" + (director != null ? director : "null") + '\'' +
                ", Genre='" + (genres != null ? genres : "null") + '\'' +
                ", Rating=" + rating + " / 10" +
                ", Votes=" + (votes != null ? votes : "null") +
                '}';
    }
}
