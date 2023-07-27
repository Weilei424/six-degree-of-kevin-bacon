package persistence;

import java.util.*;
import pojo.Actor;

public class ActorStub {
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
}