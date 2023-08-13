package persistence;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

import exceptions.EntityNotFoundException;

import pojo.Actor;

public class ActorDAOImpl implements ActorDAO {
	
	private static ActorDAOImpl instance;
	private Neo4jBacon nb;

	private ActorDAOImpl() {
		nb = Neo4jBacon.getInstance();
	}
	
	public static ActorDAOImpl getInstance() {
		if (instance == null) {
			instance = new ActorDAOImpl();
		}
		
		return instance;
	}
	
	@Override
	public Actor getActor(String query) throws EntityNotFoundException {
		Actor actor = getActorHelper(query);
		return actor;
	}
	
	public Actor getActorHelper(String id) throws EntityNotFoundException {
		StatementResult sr = nb.getNode(id, Actor.class);
		Actor actor = new Actor();

		if (sr.hasNext()) {
			Record r = sr.next();
			actor.setActorId(r.get("id").asString());
			actor.setName(r.get("name").asString());
			actor.setMovies(r.get("movies").asList(Value::asString));
			return actor;
		} else {
			throw new EntityNotFoundException();
		}
	}

	public void addActor(Actor actor) {
		nb.addNode(actor.getActorId(), actor.getName(), Actor.class);
	}
	
	@Override
	public boolean hasRelationship(String actorId, String movieId) throws EntityNotFoundException {
		return nb.hasRelationship(actorId, movieId);
	}

	@Override
	public void addRelationship(String actorId, String movieId) throws EntityNotFoundException {
		nb.hasRelationship(actorId, movieId);
		nb.addRelationship(actorId, movieId);
	}

	@Override
	public List<String> getBaconPath(String actorId) throws EntityNotFoundException {
		List<Object> objs = nb.bfs(actorId).get("idList").asList();
		List<String> result = new ArrayList<>();
		
		for(Object o : objs) {
			result.add(o.toString());
		}
		return result;
	}

	@Override
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		return nb.getBaconNumber(actorId);
	}
	
	
}