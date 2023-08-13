package service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.Constants;
import pojo.Actor;
import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;
import persistence.ActorDAO;
import persistence.ActorDAOImpl;
import persistence.MovieDAO;
import persistence.MovieDAOImpl;

public class ActorServiceImpl implements ActorService {
	
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
	public JSONObject hasRelationship(String actorId, String movieId) throws EntityNotFoundException, JSONException {
		if (actorId == null || movieId == null) {
			throw new InvalidRequestException("Invalid path");
		}
		
		JSONObject responseJson = new JSONObject();
		
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
		getActor(actorId);
		List<String> bp;
		if (actorId.equals(Constants.KEVIN_BACON_ID)) {
			bp = new ArrayList<>();
			bp.add(Constants.KEVIN_BACON_ID);
		} else {
			bp = actorDAO.getBaconPath(actorId);
		}
		JSONObject json = new JSONObject();
		JSONArray idArray = new JSONArray(bp);
		json.put("baconPath", idArray);

		return json;
	}

	@Override
	public JSONObject getBaconNumber(String actorId) throws EntityNotFoundException, JSONException {
		JSONObject json = new JSONObject();
		getActor(actorId);
		int baconNumber;
		if (actorId.equals(Constants.KEVIN_BACON_ID)) {
			baconNumber = 0;
		} else {
			baconNumber = actorDAO.getBaconNumber(actorId);
		}
		json.put("baconNumber", baconNumber);
		return json;
	}
}
