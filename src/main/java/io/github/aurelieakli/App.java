package io.github.aurelieakli;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


import static org.neo4j.driver.Values.parameters;



public class App implements AutoCloseable {
    private final Driver driver;

    public App(String uri, String user, String password){
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    public void createDatabase(String database) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run("CREATE DATABASE " + database));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void dropDatabase(String database) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run("DROP DATABASE " + database + " IF EXISTS"));

        }
        catch(Exception e){
            System.out.println(e);
        }
    }



    @Override
    public void close() throws Exception {
        driver.close();
    }

    public Node createNode(String... type) {
        try (Session session = driver.session()) {
            Node n = null;
            return n;
        }
        catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    private Node getNodeById(long id) {
        //return this.readTransaction(tx -> {
        //TODO
        return null;
    }

    public static void main(String[] args) {
        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
        System.out.println("saluuuut");

        Driver driver = GraphDatabase.driver("localhost");
    }

}
