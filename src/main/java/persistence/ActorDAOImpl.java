package persistence;

import org.neo4j.driver.v1.types.Path;

import constants.Constants;

import java.util.ArrayList;
import java.util.List;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Map;
import java.util.HashMap;

import java.util.Collections;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import exceptions.EntityNotFoundException;

import pojo.Actor;

public class ActorDAOImpl implements ActorDAO {
	
	private static ActorDAOImpl instance;
	private Neo4jBooks nb;
	private ActorStub stub;

	private ActorDAOImpl() {
		nb = Neo4jBooks.getInstance();
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
	public List<Actor> getBaconPath(String actorId) throws EntityNotFoundException {
		List<Actor> result = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		Map<String, String> parentage = new HashMap<>();
		List<String> visited = new LinkedList<>();
		
		visited.add(actorId);
		queue.add(actorId);
		String a = actorId;
		
		
		while(!queue.isEmpty()) {
			a = queue.remove();
			
			if(a.equals(Constants.KEVIN_BACON_ID)) {
				break;
			}
			
			List<String> adjacentActors = new LinkedList<>();
			//get movies v acted in
			//get actors (actorid) from those movies
			//these are adjacent actors
			
			for(String v : adjacentActors) {
				if(!visited.contains(v)) {
					visited.add(v);
					parentage.put(v, a);
					queue.add(v);
				}
			}
		}
		
		while(!a.equals(actorId)) {
			result.add(getActorHelper(a));
			a = parentage.get(a);
		}
		result.add(getActorHelper(a));
		Collections.reverse(result);
		
		return result;
	}

	@Override
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		if(actorId.equals(Constants.KEVIN_BACON_ID)) {
			return 0;
		}else {
			return getBaconPath(actorId).size();
		}
	}
	
	
}