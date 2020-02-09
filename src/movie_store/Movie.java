package movie_store;

public class Movie {

	private int movieId;
	private String title;
	private String genre;
	private String imagePath;
	
	public Movie() {}
	
	public Movie(int movieId, String title, String genre, String imagePath) {
		this.movieId = movieId;
		this.title = title;
		this.genre = genre;
		this.imagePath = imagePath;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieID(int movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	
}
