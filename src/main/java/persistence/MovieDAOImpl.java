package persistence;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

import exceptions.EntityNotFoundException;
import pojo.Actor;
import pojo.Movie;

public class MovieDAOImpl implements MovieDAO {
	
	private static MovieDAOImpl instance;
	private Neo4jBooks nb;
	
	private MovieDAOImpl() {
		nb = Neo4jBooks.getInstance();
	}
	
	public static MovieDAOImpl getInstance() {
		if (instance == null) {
			instance = new MovieDAOImpl();
		}
		
		return instance;
	}
	
	@Override
	public Movie getMovie(String query) throws EntityNotFoundException {
		StatementResult sr = nb.getNode(query, Movie.class);
		Movie movie = new Movie();
		
		if (sr.hasNext()) {
			Record r = sr.next();
			movie.setMovieId(r.get("id").asString());
			movie.setName(r.get("name").asString());
			movie.setActors(r.get("actors").asList(Value::asString));
			return movie;
		} else {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public void addMovie(Movie movie) {
		nb.addNode(movie.getMovieId(), movie.getName(), Movie.class);
	}
}
