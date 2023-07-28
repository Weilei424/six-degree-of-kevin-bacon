package service;

import org.json.JSONObject;

import persistence.ActorDAO;
import persistence.ActorStub;

public class ActorServiceImpl implements ActorService {
	
	private ActorDAO actorDAO = new ActorStub(); // Using stub db before we have actual db setup

	@Override
	public void addActor(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getActor(String query) {
		// TODO Auto-generated method stub
		return null;
	}
}
