package persistence;

import pojo.Movie;

public interface MovieDAO {
	Movie getMovie(String query);
}
