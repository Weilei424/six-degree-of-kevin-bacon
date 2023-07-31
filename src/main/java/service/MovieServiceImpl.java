package service;

import org.json.JSONObject;

import exceptions.EntityNotFoundException;
import persistence.MovieDAO;
import persistence.MovieDAOImpl;
import persistence.MovieStub;
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
	public void addMovie(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getMovie(String query) throws EntityNotFoundException {
		
		return new JSONObject(stub.getMovie(query));
	}
}
