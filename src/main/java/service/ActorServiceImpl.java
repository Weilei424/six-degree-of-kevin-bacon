package service;

import org.json.JSONException;
import org.json.JSONObject;

import pojo.Actor;

import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;
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
	public void addActor(JSONObject jsonObject) throws JSONException, EntityNotFoundException, InvalidRequestException {
		String actorId = jsonObject.getString("actorId");
		String name = jsonObject.getString("name");
		Actor actor;
		actor = new Actor(actorId, name);

		try {
			getActor(actorId);
			throw new InvalidRequestException("actorId already exists");
		} catch (EntityNotFoundException e) {
			actorDAO.addActor(actor);
		} catch (InvalidRequestException e) {
			throw e;
		}
	}

	@Override
	public JSONObject getActor(String query) throws EntityNotFoundException, JSONException {
		return new JSONObject(actorDAO.getActor(query));
	}

	@Override
	public JSONObject hasRelationship(JSONObject jsonObject) throws EntityNotFoundException, JSONException {
		JSONObject responseJson = new JSONObject();
		String actorId = jsonObject.getString("actorId");
		String movieId = jsonObject.getString("movieId");
		actorDAO.getActor(actorId);
		movieDAO.getMovie(movieId);
		responseJson.put("actorId", actorId);
		responseJson.put("movieId", movieId);
		responseJson.put("hasRelationship", actorDAO.hasRelationship(actorId, movieId));
		return responseJson;
	}
	
	@Override
	public String addRelationship(JSONObject jsonObject) throws JSONException, EntityNotFoundException {
		String movieId = jsonObject.getString("movieId");
		String actorId = jsonObject.getString("actorId");
		
		getActor(actorId);
		movieDAO.getMovie(movieId);
		actorDAO.addRelationship(actorId, movieId);
		
		return "relastionship actor:" + actorId + " ->" + " movie:" + movieId + " has been added.";
	}

	@Override
	public JSONObject getBaconPath(String actorId) {
		// TODO Convert Path object to JSONObject here
		return null;
	}

	@Override
	public int getBaconNumber(String actorId) {
		// TODO 
		return 0;
	}
}
