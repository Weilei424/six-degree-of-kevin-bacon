package service;

import persistence.ActorDAO;
import persistence.ActorStub;

public class ActorServiceImpl implements ActorService {
	
	private ActorDAO actorDAO = new ActorStub(); // Using stub db before we have actual db setup
}
