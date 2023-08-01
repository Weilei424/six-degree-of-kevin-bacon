package pojo;

public class Movie {
	private String movieId;
	private String name;
	
	public Movie() {
		super();
	}

	public Movie(String id, String name) {
		this.movieId = id;
		this.name = name;
	}

	public String getId() {
		return movieId;
	}

	public void setId(String id) {
		this.movieId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
