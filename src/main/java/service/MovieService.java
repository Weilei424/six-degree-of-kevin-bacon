package service;

import org.json.JSONObject;

public interface MovieService {
	void addMovie(JSONObject jsonObject);
	JSONObject getMovie(String query);
}
