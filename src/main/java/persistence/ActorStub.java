package persistence;

import java.util.*;
import pojo.Actor;

public class ActorStub implements ActorDAO {
	List<Actor> list = new ArrayList<>();
	
	public ActorStub() {
		list = Arrays.asList(
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
				new Actor(),
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
	public Actor getActor(String query) {
		// TODO Auto-generated method stub
		return null;
	}
}