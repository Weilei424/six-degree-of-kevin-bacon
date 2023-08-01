package service;

import org.json.JSONException;
import org.json.JSONObject;

import pojo.Actor;

import exceptions.EntityNotFoundException;
import persistence.ActorDAO;
import persistence.ActorDAOImpl;
import persistence.ActorStub;
import persistence.MovieDAO;
import persistence.MovieDAOImpl;

public class ActorServiceImpl implements ActorService {
	
	private ActorDAO stub = new ActorStub(); // Using stub db before we have actual db setup
	private static ActorServiceImpl instance;
	private ActorDAO actorDAO;
	private MovieDAO movieDAO;
	
	private ActorServiceImpl() {
		actorDAO = ActorDAOImpl.getInstance();
		movieDAO = MovieDAOImpl.getInstance();
	}
	
	public static ActorServiceImpl getInstance() {
		if (instance == null) {
			instance = new ActorServiceImpl();
		}
		
		return instance;
	}
	
	@Override
	public void addActor(JSONObject jsonObject) throws JSONException {
		Actor actor;
		actor = new Actor(jsonObject.getString("actorId"), jsonObject.getString("name"));
		actorDAO.addActor(actor);
	}

	@Override
	public JSONObject getActor(String query) throws EntityNotFoundException {
		
		return new JSONObject(stub.getActor(query));
	}

	@Override
	public String addRelationship(JSONObject jsonObject) throws JSONException, EntityNotFoundException {
		String movieId = jsonObject.getString("movieId");
		String actorId = jsonObject.getString("actorId");
		
		getActor(actorId);
		movieDAO.getMovie(movieId);
		actorDAO.addRelationship(actorId, movieId);
		
		return "relastionship actor:" + actorId + "->" + "movie:" + movieId + "has been added.";
	}
}
