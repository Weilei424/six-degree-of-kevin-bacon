package service;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.EntityNotFoundException;

public interface MovieService {
	void addMovie(JSONObject jsonObject) throws JSONException;;
	JSONObject getMovie(String query) throws EntityNotFoundException;
}
