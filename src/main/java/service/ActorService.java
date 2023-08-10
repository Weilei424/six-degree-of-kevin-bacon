package service;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;

public interface ActorService {
	void addActor(JSONObject jsonObject) throws JSONException, EntityNotFoundException, InvalidRequestException;
	JSONObject getActor(String query) throws EntityNotFoundException, JSONException;
	String addRelationship(JSONObject jsonObject) throws JSONException, EntityNotFoundException;
	JSONObject getBaconPath(String actorId) throws JSONException, EntityNotFoundException;
	int getBaconNumber(String actorId) throws EntityNotFoundException;
	JSONObject hasRelationship(JSONObject jsonObject) throws EntityNotFoundException, JSONException;
}
