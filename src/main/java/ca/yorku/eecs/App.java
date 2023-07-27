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
        // please refer to the HTML server example 
        
        String url = ""; //not sure what url
        
        String user = "neo4j";
        String password = "12345678"; 
        neo4j database = new neo4j(url, user, password);
        
        
        server.createContext("/api/v1/addActor", new addActor(database));// not sure if we are allowed to create more context
   
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
        
        
         }
}
