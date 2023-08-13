package persistence;

import java.util.List;

import exceptions.EntityNotFoundException;
import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query) throws EntityNotFoundException;
	void addActor(Actor actor);
	void addRelationship(String actorId, String movieId) throws EntityNotFoundException;
	List<String> getBaconPath(String actorId) throws EntityNotFoundException;
	int getBaconNumber(String actorId) throws EntityNotFoundException;
	boolean hasRelationship(String actorId, String movieId) throws EntityNotFoundException;
}
