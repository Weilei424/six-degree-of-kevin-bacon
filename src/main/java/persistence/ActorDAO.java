package persistence;

import org.neo4j.driver.v1.types.Path;

import exceptions.EntityNotFoundException;
import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query) throws EntityNotFoundException;
	void addActor(Actor actor);
	void addRelationship(String actorId, String movieId);
	Path getBaconPath(String actorId);
	int getBaconNumber(String actorId);
}
