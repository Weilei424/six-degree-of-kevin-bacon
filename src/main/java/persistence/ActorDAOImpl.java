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
		// TODO Auto-generated method stub
		nb.addNode(actor.getActorId(), actor.getName(), Actor.class);
	}

	@Override
	public void addRelationship(String actorId, String movieId) {
		nb.addRelationship(actorId, movieId);
	}

	@Override
	public List<Actor> getBaconPath(String actorId) throws EntityNotFoundException {
		return null;
	}

	@Override
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		return nb.getBaconNumber(actorId);
	}
	
	
}