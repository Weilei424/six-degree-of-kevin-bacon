package persistence;

import java.util.*;

import org.neo4j.driver.v1.types.Path;

import exceptions.EntityNotFoundException;
import pojo.Actor;
import pojo.Movie;


public class ActorStub implements ActorDAO {
	List<Actor> list = new ArrayList<>();
	
	public ActorStub() {
		list = Arrays.asList(
				new Actor("1", "test1"),
				new Actor("2", "test2"),
				new Actor("3", "test3"),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor()
				);
	}

	@Override
	public Actor getActor(String query) throws EntityNotFoundException {
		String id = query.replace("actorId=", "");
		
		for (Actor a : list) {
			if (a.getActorId().equals(id)) return a;
		}
		throw new EntityNotFoundException("No such ID");
	}

	@Override
	public void addActor(Actor actor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addActor'");
	}

	@Override
	public void addRelationship(String actorId, String movieId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getBaconPath(String actorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaconNumber(String actorId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasRelationship(String actorId, String movieId) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}
}