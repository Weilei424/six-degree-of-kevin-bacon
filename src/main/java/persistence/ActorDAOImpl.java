package persistence;

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
}
