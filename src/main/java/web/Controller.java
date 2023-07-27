package web;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import service.ActorService;
import service.MovieService;

public class Controller implements HttpHandler {

	private ActorService actorService;
	private MovieService movieService;

	// TODO: singleton here and DI

	@Override
	public void handle(HttpExchange request) throws IOException {
		String requestName;

		try {
			requestName = URIParser(request.getRequestURI().toString());

			switch (requestName) {
			case "addActor":
				addActor(request);
				break;
			case "addMovie":
				addMovie(request);
				break;
			case "addRelationShip":
				addRelationShip(request);
				break;
			case "getActor":
				getActor(request);
				break;
			case "getMovie":
				getMovie(request);
				break;
			case "hasRelationShip":
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addActor(HttpExchange request) {

	}

	private void addMovie(HttpExchange request) {

	}

	private void addRelationShip(HttpExchange request) {

	}

	private void getActor(HttpExchange request) {

	}

	private void getMovie(HttpExchange request) {

	}

	private void hasRelationShip(HttpExchange request) {

	}

	private void computeBaconNumber(HttpExchange request) {

	}

	private void computeBaconPath(HttpExchange request) {

	}

	private String URIParser(String uri) {
		return uri.replaceFirst("^/api/v1/", "");
	}

}
