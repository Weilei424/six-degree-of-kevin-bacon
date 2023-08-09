package service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;
import persistence.MovieDAO;
import persistence.MovieDAOImpl;
import persistence.MovieStub;
import pojo.Actor;
import pojo.Movie;

public class MovieServiceImpl implements MovieService {

	private MovieDAO stub = new MovieStub(); // Using stub db before we have actual db setup
	private static MovieServiceImpl instance;
	private MovieDAO movieDAO;

	private MovieServiceImpl() {
		movieDAO = MovieDAOImpl.getInstance();
	}

	public static MovieServiceImpl getInstance() {
		if (instance == null) {
			instance = new MovieServiceImpl();
		}
		return instance;
	}

	@Override
	public void addMovie(JSONObject jsonObject) throws JSONException {
		String movieId = jsonObject.getString("movieId");
		String name = jsonObject.getString("name");
		Movie movie;
		movie = new Movie(movieId, name);

		try {
			getMovie(movieId);
			throw new InvalidRequestException("movieId already exists");
		} catch (EntityNotFoundException e) {
			movieDAO.addMovie(movie);
		} catch (InvalidRequestException e) {
			throw e;
		}
	}

	@Override
	public JSONObject getMovie(String query) throws EntityNotFoundException, JSONException {
		Movie m = movieDAO.getMovie(query);
		JSONObject movieJson = new JSONObject();

		movieJson.put("movieId", m.getMovieId());
		movieJson.put("name", m.getName());
		JSONArray actorArray = new JSONArray(m.getActors());
		movieJson.put("actors", actorArray);

		return movieJson;
	}
}
