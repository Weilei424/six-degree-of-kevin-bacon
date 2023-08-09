package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ca.yorku.eecs.Utils;
import constants.HttpStatus;
import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;
import persistence.Neo4jBooks;
import service.*;

public class Controller implements HttpHandler {

	private static Controller instance;
	private ActorService actorService;
	private MovieService movieService;

	private Controller() {
		actorService = ActorServiceImpl.getInstance();
		movieService = MovieServiceImpl.getInstance();
	}
	
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	@Override
	public void handle(HttpExchange request) throws IOException {
		String path;

		try {
			path = request.getRequestURI().getPath().replaceFirst("^/api/v1/", "");;
			
			switch (path) {
			case "addActor":
				addActor(request);
				break;
			case "addMovie":
				addMovie(request);
				break;
			case "addRelationship":
				addRelationship(request);
				break;
			case "getActor":
				getActor(request);
				break;
			case "getMovie":
				getMovie(request);
				break;
			case "hasRelationship":
				hasRelationship(request);
				break;
			case "computeBaconNumber":
				computeBaconNumber(request);
				break;
			case "computeBaconPath":
				computeBaconPath(request);
				break;
			default:
				throw new InvalidRequestException("Invalid request");
			}
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (JSONException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void addActor(HttpExchange request) throws JSONException, IOException, EntityNotFoundException, InvalidRequestException {
		JSONObject jsonObject = JSONObjectParser(request.getRequestBody());
		actorService.addActor(jsonObject);
		response(request, "poggers addActor success", HttpStatus.OK);
	}

	private void addMovie(HttpExchange request) throws JSONException, IOException, EntityNotFoundException, InvalidRequestException {
		JSONObject jsonObject = JSONObjectParser(request.getRequestBody());
		movieService.addMovie(jsonObject);
		response(request, "addMovie successful", HttpStatus.OK);
	}

	private void addRelationship(HttpExchange request) throws IOException, JSONException, EntityNotFoundException {
		JSONObject json = JSONObjectParser(request.getRequestBody());
		String response = actorService.addRelationship(json);
		response(request, response, HttpStatus.OK);
	}

	private void getActor(HttpExchange request) throws IOException, EntityNotFoundException, JSONException {
		 // Get the query parameter from the request URI
	    String query = request.getRequestURI().getRawQuery();
	    String endpoint = query.split("=")[0];
		if (!endpoint.equals("actorId")) throw new JSONException("Invalid path");
		query = query.split("=")[1];
	    // Call the ActorService to get the actor data
	    String response = actorService.getActor(query).toString();
	    // Set the response headers and send the response to the client by using helper method
	    response(request, response, HttpStatus.OK);
	    
	}

	private void getMovie(HttpExchange request) throws IOException, EntityNotFoundException, JSONException {
		String query = request.getRequestURI().getRawQuery();
		String endpoint = query.split("=")[0];
		if (!endpoint.equals("movieId")) throw new JSONException("Invalid path");
		query = query.split("=")[1];
		String response = movieService.getMovie(query).toString();
		response(request, response, HttpStatus.OK);
	}

	private void hasRelationship(HttpExchange request) throws IOException, JSONException, EntityNotFoundException {
	    // Parse the JSON request body to get the movieId and actorId
	    JSONObject json = JSONObjectParser(request.getRequestBody());
	    String response = actorService.hasRelationship(json).toString();
	    response(request, response, HttpStatus.OK);

	}

	private void computeBaconNumber(HttpExchange request) {

	}

	private void computeBaconPath(HttpExchange request) {

	}
	
	private void response(HttpExchange request, String response, int httpCode) throws IOException {
		request.sendResponseHeaders(httpCode, response.length());
		OutputStream os = request.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	private JSONObject JSONObjectParser(InputStream requestBody) throws JSONException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
		String jsonString = reader.lines().collect(Collectors.joining());
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return jsonObject;
	}

}