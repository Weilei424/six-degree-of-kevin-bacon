package service;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.EntityNotFoundException;

public interface ActorService {
	void addActor(JSONObject jsonObject) throws JSONException;
	JSONObject getActor(String query) throws EntityNotFoundException;
	String addRelationship(JSONObject jsonObject) throws JSONException, EntityNotFoundException;
}
