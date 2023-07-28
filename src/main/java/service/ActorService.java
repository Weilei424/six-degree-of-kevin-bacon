package service;

import org.json.JSONObject;

public interface ActorService {
	void addActor(JSONObject jsonObject);
	JSONObject getActor(String query);
}
