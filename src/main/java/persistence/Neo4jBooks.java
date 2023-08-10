package persistence;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.types.Path;

import constants.Constants;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.List;

import pojo.Actor;
import pojo.Movie;
import exceptions.EntityNotFoundException;
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
			String label = "", property = "", actorsList = "";
			if (c == Movie.class) {
				label = "x:movie";
				property = "movieId";
				actorsList = ", actors:[]";
			} else if (c == Actor.class) {
				label = "x:actor";
				property = "actorId";
			} else {
				throw new InvalidRequestException();
			}
			String query = String.format("CREATE (%s {%s:$i, name:$n%s})", label, property, actorsList);
            session.writeTransaction(tx -> tx.run(query,
                    parameters("i", id, "n", name)));
		}
	}
	
	public <T> StatementResult getNode(String id, Class<T> c) throws EntityNotFoundException {
		
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				String label = "", property = "", actorList = "";
				if (c == Movie.class) {
					label = "x:movie";
					property = "movieId";
					actorList = ", x.actors AS actors";
				} else if (c == Actor.class) {
					label = "x:actor";
					property = "actorId";
				} else {
					throw new EntityNotFoundException("No such type of record in our database.");
				}
				StatementResult sr = tx.run("MATCH (" + label + ")\n"
						+ "WHERE x." + property + " = $i\n"
						+ "RETURN x." + property + " AS id, x.name AS name" + actorList,
						parameters("i", id)
						);
				if (sr.hasNext()) {
					return sr;
				} else {
					throw new EntityNotFoundException("This " + label.split(":")[1] + " is not found in our database.");
				}
			}
		}
	}
	
	// use this method for checking relationships, it returns all movies this actor has acted in
	public StatementResult getMoviesActed(String actorId) throws EntityNotFoundException {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				StatementResult sr = tx.run("MATCH (a:actor)-[:ACTED_IN*1]->(m:movie)\n"
						+ "WHERE a.actorId = $i\n"
						+ "RETURN m.movieId AS movieId, m.name AS name", 
						parameters("i", actorId)
						);
				
				if (sr.hasNext()) {
					return sr;
				} else {
					throw new EntityNotFoundException("This Actor has not acted in any movie!");
				}
			}
		}
	}
	
	public void addRelationship(String actorId, String movieId) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MATCH (a:actor {actorId: $x}), (b:movie {movieId: $y})\n"
						+ "CREATE (a)-[r:ACTED_IN]->(b)\n"
						+ "SET b.actors = b.actors +  a.actorId\n"
						+ "SET a.movies = a.movies +  b.movieId\n"
						+ "RETURN type(r)",
						parameters("x", actorId, "y", movieId)
						));
		}
	}
	
	public boolean hasRelationship(String actorId, String movieId) throws EntityNotFoundException {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				StatementResult sr = tx.run("MATCH (a:actor {actorId: $x}), (b:movie {movieId: $y})\n"
						+ "RETURN EXISTS((a)-[:ACTED_IN]->(b)) AS result",
						parameters( "x", actorId, "y", movieId)
						);
				
				if (sr.hasNext()) {
					return sr.next().get("result").asBoolean();
				} else {
					throw new EntityNotFoundException("No such relationship.");
				}
			}
		}
	}
	
	public int getBaconNumber(String actorId) throws EntityNotFoundException {
		return bfs(actorId).get("path").asPath().length() / 2;
	}
	
	/*/
	 * Use bfs(actorId).get("idList").asList()
	 * to extract the path as a List, the list contains
	 */
	public Record bfs(String actorId) throws EntityNotFoundException {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				StatementResult sr = tx.run("MATCH p=shortestPath(\n"
						+ "(a:actor {actorId: $x})-[*]-(b:actor {actorId: $k})\n"
						+ ")\n"
						+ "RETURN p as path, [node in nodes(p) |\n"
						+ "          CASE\n"
						+ "             WHEN 'actor' IN labels(node) THEN node.actorId\n"
						+ "             WHEN 'movie' IN labels(node) THEN node.movieId\n"
						+ "             ELSE null\n"
						+ "          END\n"
						+ "       ] AS idList",
						parameters( "x", actorId, "k", Constants.KEVIN_BACON_ID)
						);
				if (sr.hasNext()) {
					return sr.next();
				} else {
					throw new EntityNotFoundException("There does not exist a path from this actor to Kevin Bacon!");
				}
			}
		}
	}
}
