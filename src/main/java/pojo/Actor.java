package pojo;

import java.util.List;

public class Actor {
	private String actorId;
	private String name;
	private List<String> movies;
	
	public Actor() {
		super();
	}

	public Actor(String id, String name) {
		this.actorId = id;
		this.name = name;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String id) {
		this.actorId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setMovies(List<String> movies) {
		this.movies = movies;
	}
	public List<String> getMovies() {
		return movies;
	}
	public void addMovie(String movie) {
		movies.add(movie);
	}
	
}
