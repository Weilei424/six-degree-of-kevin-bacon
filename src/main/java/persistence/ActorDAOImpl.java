package persistence;

import org.neo4j.driver.v1.types.Path;

import java.util.ArrayList;
import java.util.List;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Set;
import java.util.Stack;
import java.util.LinkedHashSet;

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
		String id = query.split("=")[1];
		Actor actor = getActorHelper(id);
		return actor;
	}
	
	public Actor getActorHelper(String id) throws EntityNotFoundException {
		StatementResult sr = nb.getNode(id, Actor.class);
		Actor actor = new Actor();

		if (sr.hasNext()) {
			Record r = sr.next();
			System.out.println(r);
			actor.setActorId(r.get("id").asString());
			actor.setName(r.get("name").asString());

			return actor;
		} else {
			throw new EntityNotFoundException();
		}
	}

	public void addActor(Actor actor) {
		// TODO Auto-generated method stub
		nb.addNode(actor.getActorId(), actor.getName(), Actor.class);
	}

	@Override
	public void addRelationship(String actorId, String movieId) {
		nb.addRelationship(actorId, movieId);
	}

	@Override
	public List<Actor> getBaconPath(String actorId) throws EntityNotFoundException {
		List<Actor> result = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		List<String> visited = new LinkedList<>();
		
		visited.add(actorId);
		queue.add(actorId);
		
		while(!queue.isEmpty()) {
			String a = queue.remove();
			
			if(a.equals("nm0000102")) {
				result.add(getActorHelper(a));
				return result;
			}
			
			List<String> adjacentActors = new LinkedList<>();
			//get movies v acted in
			//get actors (actorid) from those movies
			//these are adjacent actors
			
			for(String v : adjacentActors) {
				if(!visited.contains(v)) {
					visited.add(v);
					queue.add(v);
				}
			}
		}
		
		return null;
	}

	@Override
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		if(actorId.equals("nm0000102")) {
			return 0;
		}else {
			return getBaconPath(actorId).size();
		}
	}
	
	
}