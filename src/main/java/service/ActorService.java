package service;

import org.json.JSONObject;

import exceptions.EntityNotFoundException;

public interface ActorService {
	void addActor(JSONObject jsonObject);
	JSONObject getActor(String query) throws EntityNotFoundException;
	String addRelationship(String request);
}
