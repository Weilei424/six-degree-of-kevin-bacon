package service;

import org.json.JSONObject;

import exceptions.EntityNotFoundException;

public interface MovieService {
	void addMovie(JSONObject jsonObject);
	JSONObject getMovie(String query) throws EntityNotFoundException;
}
