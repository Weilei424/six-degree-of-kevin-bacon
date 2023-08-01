package service;

import org.json.JSONException;
import org.json.JSONObject;

import pojo.Actor;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import static org.neo4j.driver.v1.Values.parameters;

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
