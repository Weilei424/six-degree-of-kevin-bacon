package persistence;

import java.util.*;

import exceptions.EntityNotFoundException;
import pojo.Movie;

public class MovieStub implements MovieDAO {
	List<Movie> list = new ArrayList<>();
	
	public MovieStub() {
		list = Arrays.asList(
				new Movie("1", "test1"),
				new Movie("2", "test2"),
				new Movie("3", "test3"),
				new Movie("4", "test4"),
				new Movie("5", "test5"),
				new Movie("6", "test6")
				);
	}

	@Override
	public Movie getMovie(String query) throws EntityNotFoundException {
		String id = query.replace("movieId=", "");
		
		for (Movie m : list) {
			if (m.getId().equals(id)) return m;
		}
		throw new EntityNotFoundException("No such ID");
	}

	@Override
	public void addMovie(Movie movie) {
		throw new UnsupportedOperationException("Unimplemented method 'addMovie'");
	}
}
