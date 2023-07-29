package persistence;

import exceptions.EntityNotFoundException;
import pojo.Actor;

public interface ActorDAO {
	Actor getActor(String query) throws EntityNotFoundException;
}
