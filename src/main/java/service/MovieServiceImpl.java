package service;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.EntityNotFoundException;
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
		Movie movie;
		movie = new Movie(jsonObject.getString("movieId"), jsonObject.getString("name"));
		movieDAO.addMovie(movie);
	}

	@Override
	public JSONObject getMovie(String query) throws EntityNotFoundException {
		return new JSONObject(movieDAO.getMovie(query));
	}
}
