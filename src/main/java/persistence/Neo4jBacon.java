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
			String label = "", property = "", list = "";
			if (c == Movie.class) {
				label = "x:movie";
				property = "movieId";
				list = ", actors:[]";
			} else if (c == Actor.class) {
				label = "x:actor";
				property = "actorId";
				list = ", movies:[]";
			} else {
				throw new InvalidRequestException();
			}
			String query = String.format("CREATE (%s {%s:$i, name:$n%s})", label, property, list);
            session.writeTransaction(tx -> tx.run(query,
                    parameters("i", id, "n", name)));
		}
	}
	
	public <T> StatementResult getNode(String id, Class<T> c) throws EntityNotFoundException {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				String label = "", property = "", list = "";
				if (c == Movie.class) {
					label = "x:movie";
					property = "movieId";
					list = ", x.actors AS actors";
				} else if (c == Actor.class) {
					label = "x:actor";
					property = "actorId";
					list = ", x.movies AS movies";
				} else {
					throw new EntityNotFoundException("No such type of record in our database.");
				}
				StatementResult sr = tx.run("MATCH (" + label + ")\n"
						+ "WHERE x." + property + " = $i\n"
						+ "RETURN x." + property + " AS id, x.name AS name" + list,
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
	
	public boolean initDemo() {
		if (!dbIsEmpty()) throw new InvalidRequestException(
				"Current database is not empty, please delete all data then try again."
				);
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("CREATE (m1:movie {name: \"M1\", movieId: \"0001\", actors: []}),\n"
					+ "       (m2:movie {name: \"M2\", movieId: \"0002\", actors: []}),\n"
					+ "       (m3:movie {name: \"M3\", movieId: \"0003\", actors: []}),\n"
					+ "       (m4:movie {name: \"M4\", movieId: \"0004\", actors: []}),\n"
					+ "       (m5:movie {name: \"M5\", movieId: \"0005\", actors: []}),\n"
					+ "       (m6:movie {name: \"M6\", movieId: \"0006\", actors: []}),\n"
					+ "       (m7:movie {name: \"M7\", movieId: \"0007\", actors: []}),\n"
					+ "       (m8:movie {name: \"M8\", movieId: \"0008\", actors: []}),\n"
					+ "       (m9:movie {name: \"M9\", movieId: \"0009\", actors: []}),\n"
					+ "       (m10:movie {name: \"M10\", movieId: \"0010\", actors: []}),\n"
					+ "       (m11:movie {name: \"M11\", movieId: \"0011\", actors: []}),\n"
					+ "       (m12:movie {name: \"M12\", movieId: \"0012\", actors: []}),\n"
					+ "       (m13:movie {name: \"M13\", movieId: \"0013\", actors: []}),\n"
					+ "       (ak:actor {name: \"Kevin Bacon\", actorId: \"nm1001231\"}),\n"
					+ "       (a1:actor {name: \"A1\", actorId: \"0001\", movies: []}),\n"
					+ "       (a2:actor {name: \"A2\", actorId: \"0002\", movies: []}),\n"
					+ "       (a3:actor {name: \"A3\", actorId: \"0003\", movies: []}),\n"
					+ "       (a4:actor {name: \"A4\", actorId: \"0004\", movies: []}),\n"
					+ "       (a5:actor {name: \"A5\", actorId: \"0005\", movies: []}),\n"
					+ "       (a6:actor {name: \"A6\", actorId: \"0006\", movies: []}),\n"
					+ "       (a7:actor {name: \"A7\", actorId: \"0007\", movies: []}),\n"
					+ "       (a8:actor {name: \"A8\", actorId: \"0008\", movies: []}),\n"
					+ "       (a9:actor {name: \"A9\", actorId: \"0009\", movies: []}),\n"
					+ "       (a10:actor {name: \"A10\", actorId: \"0010\", movies: []}),\n"
					+ "       (a11:actor {name: \"A11\", actorId: \"0011\", movies: []}),\n"
					+ "       (a12:actor {name: \"A12\", actorId: \"0012\", movies: []}),\n"
					+ "       (a13:actor {name: \"A13\", actorId: \"0013\", movies: []}),\n"
					+ "       (a14:actor {name: \"A14\", actorId: \"0014\", movies: []}),\n"
					+ "       (a15:actor {name: \"A15\", actorId: \"0015\", movies: []}),\n"
					+ "       (a16:actor {name: \"A16\", actorId: \"0016\", movies: []}), \n"
					+ "       (a17:actor {name: \"A17\", actorId: \"0017\", movies: []}),\n"
					+ "       (a18:actor {name: \"A18\", actorId: \"0018\", movies: []}),\n"
					+ "       (a19:actor {name: \"A19\", actorId: \"0019\", movies: []}),\n"
					+ "       (a20:actor {name: \"A20\", actorId: \"0020\", movies: []}),\n"
					+ "       (a21:actor {name: \"A21\", actorId: \"0021\", movies: []}),\n"
					+ "       (a22:actor {name: \"A22\", actorId: \"0022\", movies: []}),\n"
					+ "       (a23:actor {name: \"A23\", actorId: \"0023\", movies: []}),\n"
					+ "       (a24:actor {name: \"A24\", actorId: \"0024\", movies: []}),\n"
					+ "       (a25:actor {name: \"A25\", actorId: \"0025\", movies: []}),\n"
					+ "       (a26:actor {name: \"A26\", actorId: \"0026\", movies: []}), \n"
					+ "       (a27:actor {name: \"A27\", actorId: \"0027\", movies: []}),\n"
					+ "       (a28:actor {name: \"A28\", actorId: \"0028\", movies: []}),\n"
					+ "       (a29:actor {name: \"A29\", actorId: \"0029\", movies: []}),\n"
					+ "       (a30:actor {name: \"A30\", actorId: \"0030\", movies: []})\n"
					+ "WITH m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, ak, a1, a2, a3, a4, "
					+ "a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, "
					+ "a23, a24, a25, a26, a27, a28, a29, a30\n"
					+ "CREATE (ak)-[:ACTED_IN]->(m1)\n"
					+ "SET m1.actors = m1.actors + ak.actorId, ak.movies = ak.movies + m1.movieId\n"
					+ "CREATE (a1)-[:ACTED_IN]->(m1)\n"
					+ "SET m1.actors = m1.actors + a1.actorId, a1.movies = a1.movies + m1.movieId\n"
					+ "CREATE (a2)-[:ACTED_IN]->(m1)\n"
					+ "SET m1.actors = m1.actors + a2.actorId, a2.movies = a2.movies + m1.movieId\n"
					+ "CREATE (a2)-[:ACTED_IN]->(m2)\n"
					+ "SET m2.actors = m2.actors + a2.actorId, a2.movies = a2.movies + m2.movieId\n"
					+ "CREATE (a3)-[:ACTED_IN]->(m2)\n"
					+ "SET m2.actors = m2.actors + a3.actorId, a3.movies = a3.movies + m2.movieId\n"
					+ "CREATE (a4)-[:ACTED_IN]->(m2)\n"
					+ "SET m2.actors = m2.actors + a4.actorId, a4.movies = a4.movies + m2.movieId\n"
					+ "CREATE (a4)-[:ACTED_IN]->(m3)\n"
					+ "SET m3.actors = m3.actors + a4.actorId, a4.movies = a4.movies + m3.movieId\n"
					+ "CREATE (a4)-[:ACTED_IN]->(m4)\n"
					+ "SET m4.actors = m4.actors + a4.actorId, a4.movies = a4.movies + m4.movieId\n"
					+ "CREATE (a5)-[:ACTED_IN]->(m3)\n"
					+ "SET m3.actors = m3.actors + a5.actorId, a5.movies = a5.movies + m3.movieId\n"
					+ "CREATE (a5)-[:ACTED_IN]->(m5)\n"
					+ "SET m5.actors = m5.actors + a5.actorId, a5.movies = a5.movies + m5.movieId\n"
					+ "CREATE (a6)-[:ACTED_IN]->(m4)\n"
					+ "SET m4.actors = m4.actors + a6.actorId, a6.movies = a6.movies + m4.movieId\n"
					+ "CREATE (a6)-[:ACTED_IN]->(m6)\n"
					+ "SET m6.actors = m6.actors + a6.actorId, a6.movies = a6.movies + m6.movieId\n"
					+ "CREATE (a7)-[:ACTED_IN]->(m6)\n"
					+ "SET m6.actors = m6.actors + a7.actorId, a7.movies = a7.movies + m6.movieId\n"
					+ "CREATE (a7)-[:ACTED_IN]->(m7)\n"
					+ "SET m7.actors = m7.actors + a7.actorId, a7.movies = a7.movies + m7.movieId\n"
					+ "CREATE (a8)-[:ACTED_IN]->(m7)\n"
					+ "SET m7.actors = m7.actors + a8.actorId, a8.movies = a8.movies + m7.movieId\n"
					+ "CREATE (a8)-[:ACTED_IN]->(m8)\n"
					+ "SET m8.actors = m8.actors + a8.actorId, a8.movies = a8.movies + m8.movieId\n"
					+ "CREATE (a9)-[:ACTED_IN]->(m8)\n"
					+ "SET m8.actors = m8.actors + a9.actorId, a9.movies = a9.movies + m8.movieId\n"
					+ "CREATE (a9)-[:ACTED_IN]->(m9)\n"
					+ "SET m9.actors = m9.actors + a9.actorId, a9.movies = a9.movies + m9.movieId\n"
					+ "CREATE (a1)-[:ACTED_IN]->(m9)\n"
					+ "SET m9.actors = m9.actors + a1.actorId, a1.movies = a1.movies + m9.movieId\n"
					+ "CREATE (a10)-[:ACTED_IN]->(m2)\n"
					+ "SET m2.actors = m2.actors + a10.actorId, a10.movies = a10.movies + m2.movieId\n"
					+ "CREATE (a10)-[:ACTED_IN]->(m10)\n"
					+ "SET m10.actors = m10.actors + a10.actorId, a10.movies = a10.movies + m10.movieId\n"
					+ "CREATE (a10)-[:ACTED_IN]->(m9)\n"
					+ "SET m9.actors = m9.actors + a10.actorId, a10.movies = a10.movies + m9.movieId\n"
					+ "CREATE (a11)-[:ACTED_IN]->(m10)\n"
					+ "SET m10.actors = m10.actors + a11.actorId, a11.movies = a11.movies + m10.movieId\n"
					+ "CREATE (a12)-[:ACTED_IN]->(m10)\n"
					+ "SET m10.actors = m10.actors + a12.actorId, a12.movies = a12.movies + m10.movieId\n"
					+ "CREATE (a13)-[:ACTED_IN]->(m10)\n"
					+ "SET m10.actors = m10.actors + a13.actorId, a13.movies = a13.movies + m10.movieId\n"
					+ "CREATE (a14)-[:ACTED_IN]->(m5)\n"
					+ "SET m5.actors = m5.actors + a14.actorId, a14.movies = a14.movies + m5.movieId\n"
					+ "CREATE (a15)-[:ACTED_IN]->(m10)\n"
					+ "SET m10.actors = m10.actors + a15.actorId, a15.movies = a15.movies + m10.movieId\n"
					+ "CREATE (a16)-[:ACTED_IN]->(m7)\n"
					+ "SET m7.actors = m7.actors + a16.actorId, a16.movies = a16.movies + m7.movieId\n"
					+ "CREATE (a17)-[:ACTED_IN]->(m7)\n"
					+ "SET m7.actors = m7.actors + a17.actorId, a17.movies = a17.movies + m7.movieId\n"
					+ "CREATE (a18)-[:ACTED_IN]->(m6)\n"
					+ "SET m6.actors = m6.actors + a18.actorId, a18.movies = a18.movies + m6.movieId\n"
					+ "CREATE (a19)-[:ACTED_IN]->(m5)\n"
					+ "SET m5.actors = m5.actors + a19.actorId, a19.movies = a19.movies + m5.movieId\n"
					+ "CREATE (a20)-[:ACTED_IN]->(m3)\n"
					+ "SET m3.actors = m3.actors + a20.actorId, ak.movies = a20.movies + m3.movieId\n"
					+ "CREATE (a21)-[:ACTED_IN]->(m11)\n"
					+ "SET m11.actors = m11.actors + a21.actorId, a21.movies = a21.movies + m11.movieId\n"
					+ "CREATE (a22)-[:ACTED_IN]->(m11)\n"
					+ "SET m11.actors = m11.actors + a22.actorId, a22.movies = a22.movies + m11.movieId\n"
					+ "CREATE (a23)-[:ACTED_IN]->(m12)\n"
					+ "SET m12.actors = m12.actors + a23.actorId, a23.movies = a23.movies + m12.movieId\n"
					+ "CREATE (a24)-[:ACTED_IN]->(m4)\n"
					+ "SET m4.actors = m4.actors + a24.actorId, a24.movies = a24.movies + m4.movieId\n"
					+ "CREATE (a25)-[:ACTED_IN]->(m2)\n"
					+ "SET m2.actors = m2.actors + a25.actorId, a25.movies = a25.movies + m2.movieId\n"
					+ "CREATE (a26)-[:ACTED_IN]->(m8)\n"
					+ "SET m8.actors = m8.actors + a26.actorId, a26.movies = a26.movies + m8.movieId\n"
					+ "CREATE (a27)-[:ACTED_IN]->(m11)\n"
					+ "SET m11.actors = m11.actors + a27.actorId, a27.movies = a27.movies + m11.movieId\n"
					+ "CREATE (a28)-[:ACTED_IN]->(m4)\n"
					+ "SET m4.actors = m4.actors + a28.actorId, a28.movies = a28.movies + m4.movieId\n"
					+ "CREATE (a29)-[:ACTED_IN]->(m5)\n"
					+ "SET m5.actors = m5.actors + a29.actorId, a29.movies = a29.movies + m5.movieId\n"
					+ "RETURN m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, ak, a1, a2, a3, a4, "
					+ "a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, "
					+ "a23, a24, a25, a26, a27, a28, a29, a30;"
						));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean dbIsEmpty() {
		try (Session session = driver.session()) {
			try (Transaction tx = session.beginTransaction()) {
				StatementResult sr = tx.run("MATCH (n) RETURN count(n) AS size");
				return sr.next().get("size").asInt() == 0;
			}
		}
	}
	
	public void deleteAll() {
		try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run("MATCH (n) DETACH DELETE n"));
		}
	}
}
