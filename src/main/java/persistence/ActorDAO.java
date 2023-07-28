package persistence;

import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query);
}
