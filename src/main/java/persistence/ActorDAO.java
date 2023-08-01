package persistence;

import exceptions.EntityNotFoundException;
import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query) throws EntityNotFoundException;
	void addActor(Actor actor);
	void addRelationship(String actorId, String movieId);
}
