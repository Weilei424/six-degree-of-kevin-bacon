package service;

import org.json.JSONException;
import org.json.JSONObject;

import pojo.Actor;

import exceptions.EntityNotFoundException;
import persistence.ActorDAO;
import persistence.ActorDAOImpl;
import persistence.ActorStub;

public class ActorServiceImpl implements ActorService {
	
	private ActorDAO stub = new ActorStub(); // Using stub db before we have actual db setup
	private static ActorServiceImpl instance;
	private ActorDAO actorDAO;
	
	private ActorServiceImpl() {
		actorDAO = ActorDAOImpl.getInstance();
	}
	
	public static ActorServiceImpl getInstance() {
		if (instance == null) {
			instance = new ActorServiceImpl();
		}
		
		return instance;
	}
	
	@Override
	public void addActor(JSONObject jsonObject) throws JSONException {
		// TODO Auto-generated method stub
		Actor actor;
		actor = new Actor(jsonObject.getString("id"), jsonObject.getString("name"));
		actorDAO.addActor(actor);
	}

	@Override
	public JSONObject getActor(String query) throws EntityNotFoundException {
		
		return new JSONObject(stub.getActor(query));
	}

	@Override
	public String addRelationship(String request) {
		// TODO Auto-generated method stub
		return null;
	}
}
