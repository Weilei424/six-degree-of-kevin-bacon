package persistence;

import exceptions.EntityNotFoundException;
import pojo.Movie;

public interface MovieDAO {
	Movie getMovie(String query) throws EntityNotFoundException;
}
