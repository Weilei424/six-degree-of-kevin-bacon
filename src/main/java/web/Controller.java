package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
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
				if (request.getRequestMethod().equals("PUT")) {
					addActor(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "addMovie":
				if (request.getRequestMethod().equals("PUT")) {
					addMovie(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "addRelationship":
				if (request.getRequestMethod().equals("PUT")) {
					addRelationship(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "getActor":
				if (request.getRequestMethod().equals("GET")) {
					getActor(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "getMovie":
				if (request.getRequestMethod().equals("GET")) {
					getMovie(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "hasRelationship":
				if (request.getRequestMethod().equals("GET")) {
					hasRelationship(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "computeBaconNumber":
				if (request.getRequestMethod().equals("GET")) {
					computeBaconNumber(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "computeBaconPath":
				if (request.getRequestMethod().equals("GET")) {
					computeBaconPath(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "initDemoDb":
				if (request.getRequestMethod().equals("POST")) {
					initDemoDb(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
				break;
			case "nuke":
				if (request.getRequestMethod().equals("DELETE")) {
					deleteAll(request);
				} else {
					throw new InvalidRequestException("Invalid request");
				}
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
		response(request, "addActor successsful", HttpStatus.OK);
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
	    Map<String, String> mapping = Utils.splitQuery(request.getRequestURI().getQuery());
	    String response = actorService.hasRelationship(mapping.get("actorId"), mapping.get("movieId")).toString();
	    response(request, response, HttpStatus.OK);
	}

	private void computeBaconNumber(HttpExchange request) throws JSONException, EntityNotFoundException, IOException {
		String query = request.getRequestURI().getRawQuery();
		String endpoint = query.split("=")[0];
		if (!endpoint.equals("actorId")) throw new JSONException("Invalid path");
		query = query.split("=")[1];
		String response = actorService.getBaconNumber(query).toString();
		response(request, response, HttpStatus.OK);
	}

	private void computeBaconPath(HttpExchange request) throws JSONException, EntityNotFoundException, IOException {
		String query = request.getRequestURI().getRawQuery();
		String endpoint = query.split("=")[0];
		if (!endpoint.equals("actorId")) throw new JSONException("Invalid path");
		query = query.split("=")[1];
		String response = actorService.getBaconPath(query).toString();
		response(request, response, HttpStatus.OK);
	}
	
	private void initDemoDb(HttpExchange request) throws IOException {
		Neo4jBooks.getInstance().initDemo();
		response(request, "Demo database has been initialized.", HttpStatus.OK);
	}
	
	private void deleteAll(HttpExchange request) throws IOException {
		Neo4jBooks.getInstance().deleteAll();
		response(request, "All data has been removed.", HttpStatus.NO_CONTENT);
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