package io.github.aurelieakli;

import org.neo4j.driver.*;
import org.neo4j.driver.Config;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.internal.BoltServerAddress;
import org.neo4j.driver.internal.ConnectionSettings;
import org.neo4j.driver.internal.security.SecurityPlan;

import java.net.URI;

public class BackEnd {
    public final Driver driver;
    private final String database;
    private Session session;


    public BackEnd(String database, String url, String user, String password) {
        driver = GraphDatabase.driver( url, AuthTokens.basic( user, password ) );
        this.database = database;
    }

    public Session getSession() {
        if (database == null || database.isBlank()) return driver.session();
        return driver.session(SessionConfig.forDatabase(database));
    }


    public void createSession(){
        session = driver.session();
        System.out.println("driver : "+driver+"\t session : "+session);
    }

    public void close() throws Neo4jException {
        driver.close();
    }

    public void executeSet(String cypher) {
        try ( Session session = driver.session() ){
            session.writeTransaction(tx -> {
                Result result = tx.run(cypher);
                System.out.println("Task succeeded!");
                return "Task succeeded!";
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Result executeGet( final String cypher ) throws Neo4jException{
        return session.run( cypher );
    }

}
