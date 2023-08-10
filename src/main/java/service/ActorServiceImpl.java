package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pojo.Actor;
import pojo.Movie;
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
		Actor a = actorDAO.getActor(query);
		JSONObject actorJson = new JSONObject();

		actorJson.put("actorId", a.getActorId());
		actorJson.put("name", a.getName());
		JSONArray movieArray = new JSONArray(a.getMovies());
		actorJson.put("movies", movieArray);

		return actorJson;
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
		if (actorDAO.hasRelationship(actorId, movieId)) {
			throw new InvalidRequestException("This relationship already exists.");
		}
		actorDAO.addRelationship(actorId, movieId);
		
		return "relationship actor:" + actorId + " ->" + " movie:" + movieId + " has been added.";
	}

	@Override
	public JSONObject getBaconPath(String actorId) throws JSONException, EntityNotFoundException {
		// TODO Convert Path object to JSONObject here
		List<String> bp = actorDAO.getBaconPath(actorId);
		JSONObject json = new JSONObject();

		JSONArray idArray = new JSONArray(bp);
		json.put("baconPath", idArray);

		return json;
	}

	@Override
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		return actorDAO.getBaconNumber(actorId);
	}
}
