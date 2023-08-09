package persistence;

import java.util.List;

import org.neo4j.driver.v1.types.Path;

import exceptions.EntityNotFoundException;
import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query) throws EntityNotFoundException;
	void addActor(Actor actor);
	void addRelationship(String actorId, String movieId) throws EntityNotFoundException;
	List<Actor> getBaconPath(String actorId) throws EntityNotFoundException;
	int getBaconNumber(String actorId) throws EntityNotFoundException;
	boolean hasRelationship(String actorId, String movieId) throws EntityNotFoundException;
}
