package persistence;

import java.security.Policy.Parameters;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

import pojo.Actor;
import pojo.Movie;
import exceptions.InvalidRequestException;

public class Neo4jBooks {
	
	private static Neo4jBooks instance;
	private Driver driver;
	private String uri;
	
	private Neo4jBooks() {
		uri = "bolt://localhost:7687";
		Config config = Config.build().withoutEncryption().build();
		driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "12345678"), config);
	}
	
	public static Neo4jBooks getInstance() {
		if (instance == null) {
			instance = new Neo4jBooks();
		}
		return instance;
	}
	
	public <T> void addNode(String id, String name, Class<T> c) {
		
		try (Session session = driver.session()) {
			String label = "";
			if (c == Movie.class) {
				label = "m: movie";
			} else if (c == Actor.class) {
				label = "a: actor";
			} else {
				throw new InvalidRequestException();
			}
			String query = String.format("CREATE (:%s {id:", label);
            session.writeTransaction(tx -> tx.run(
            		query
            		+ id
            		+ "\", name:\""
            		+ name
            		+ "\"})"));
//            session.writeTransaction(tx -> tx.run(
//            		), parameters());
		}
	}
	
	public <T> StatementResult getNode(String id, Class<T> c) {
		
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				String label = "";
				if (c == Movie.class) {
					label = "x: movie";
				} else if (c == Actor.class) {
					label = "x: actor";
				} else {
					throw new InvalidRequestException();
				}
				StatementResult sr = tx.run(
						"MATCH ("
						+ label
						+ ")\nWHERE x.id"
						+ " = \""
						+ id 
						+ "\"\n"
						+ "RETURN x;\n"
						);
				return sr;
			}
		}
		
	}
}
