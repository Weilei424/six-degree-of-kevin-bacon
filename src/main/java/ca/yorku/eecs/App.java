package ca.yorku.eecs;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        // TODO: two lines of code are expected to be added here
        
        String url = ""; //not sure that the address for the database will be? somthing localhost:7687 though?
        String user = "neo4j";
        String password = "12345678";
        neo4j database = new neo4j(url, user, password);
        
        
        // please refer to the HTML server example 

        server.createContext("/api/v1/addActor", new addActor(database)); //they said that there can only be one context and any more will
        server.createContext("/api/v1/addMovie", new addMovie(database)); // cause mark lost. So not sure?

        
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
    }
}
