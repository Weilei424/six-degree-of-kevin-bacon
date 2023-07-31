package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ca.yorku.eecs.Utils;
import constants.HttpStatus;
import exceptions.EntityNotFoundException;
import exceptions.InvalidRequestException;
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
				addRelationShip(request);
				break;
			case "getActor":
				getActor(request);
				break;
			case "getMovie":
				getMovie(request);
				break;
			case "hasRelationship":
				hasRelationShip(request);
				break;
			case "computeBaconNumber":
				computeBaconNumber(request);
				break;
			case "computeBaconPath":
				computeBaconPath(request);
				break;
			default:
				throw new RuntimeException("Invalid request");
			}
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.BAD_REQUEST);
		} /*catch (JSONException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.BAD_REQUEST);
		}*/ catch (EntityNotFoundException e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			response(request, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void addActor(HttpExchange request) {

	}

	private void addMovie(HttpExchange request) {

	}

	private void addRelationShip(HttpExchange request) throws IOException {
		String response = actorService.addRelationship(Utils.convert(request.getRequestBody()));
		response(request, response, HttpStatus.OK);
	}

	private void getActor(HttpExchange request) throws IOException, EntityNotFoundException {
		 // Get the query parameter from the request URI
	    String query = request.getRequestURI().getQuery();

	    // Call the ActorService to get the actor data
	    String response = actorService.getActor(query).toString();

	    // Set the response headers and send the response to the client by using helper method
	    response(request, response, HttpStatus.OK);
	    
	}

	private void getMovie(HttpExchange request) throws IOException, EntityNotFoundException {
		String query = request.getRequestURI().getQuery();
		String response = movieService.getMovie(query).toString();
		
		response(request, response, HttpStatus.OK);
	}

	private void hasRelationShip(HttpExchange request) {

	}

	private void computeBaconNumber(HttpExchange request) {

	}

	private void computeBaconPath(HttpExchange request) {

	}
	
	private void response(HttpExchange request, String response, int httpCode) throws IOException {
		request.sendResponseHeaders(HttpStatus.OK, response.length());
		OutputStream os = request.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	private JSONObject requestToJSONObjectParser(InputStream requestBody) throws JSONException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
		String jsonString = reader.lines().collect(Collectors.joining());
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return jsonObject;
	}

}
