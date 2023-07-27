package persistence;

import java.util.*;
import pojo.Movie;

public class MovieStub implements MovieDAO {
	List<Movie> list = new ArrayList<>();
	
	public MovieStub() {
		list = Arrays.asList(
				new Movie(),
				new Movie(),
				new Movie(),
				new Movie(),
				new Movie(),
				new Movie()
				);
	}
}
