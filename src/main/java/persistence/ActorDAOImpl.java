package persistence;

import org.neo4j.driver.v1.types.Path;

import pojo.Actor;

public class ActorDAOImpl implements ActorDAO {
	
	private static ActorDAOImpl instance;
	private Neo4jBooks nb;
	private ActorStub stub;

	private ActorDAOImpl() {
		nb = Neo4jBooks.getInstance();
	}
	
	public static ActorDAOImpl getInstance() {
		if (instance == null) {
			instance = new ActorDAOImpl();
		}
		
		return instance;
	}
	
	@Override
	public Actor getActor(String query) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public void addActor(Actor actor) {
		// TODO Auto-generated method stub
		nb.addNode(actor.getActorId(), actor.getName(), Actor.class);
	}

	@Override
	public void addRelationship(String actorId, String movieId) {
		nb.addRelationship(actorId, movieId);
	}

	@Override
	public Path getBaconPath(String actorId) {
		// TODO 
		return null;
	}

	@Override
	public int getBaconNumber(String actorId) {
		// TODO 
		return 0;
	}
	
	
}
