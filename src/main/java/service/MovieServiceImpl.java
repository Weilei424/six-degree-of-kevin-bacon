package service;

import org.json.JSONObject;

import exceptions.EntityNotFoundException;
import persistence.MovieDAO;
import persistence.MovieStub;
import pojo.Movie;

public class MovieServiceImpl implements MovieService {

	private MovieDAO movieDAO = new MovieStub(); // Using stub db before we have actual db setup

	@Override
	public void addMovie(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getMovie(String query) throws EntityNotFoundException {
		
		return new JSONObject(movieDAO.getMovie(query));
	}
}
