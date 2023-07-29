package persistence;

import java.util.*;

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
			if (a.getId().equals(id)) return a;
		}
		throw new EntityNotFoundException("No such ID");
	}
}