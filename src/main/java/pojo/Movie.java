package pojo;

import java.util.ArrayList;
import java.util.List;

public class Movie {
	private String movieId;
	private String name;
	private List<String> actors;
	
	public Movie() {
		super();
		actors = new ArrayList<>();
	}

	public Movie(String id, String name) {
		this.movieId = id;
		this.name = name;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String id) {
		this.movieId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getActors() {
		return actors;
	}
}
