package io.github.aurelieakli;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.PropertyContainer;
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
    /*
    public void executeSet(String cypher) {
        try ( Session session = driver.session() ){
            session.writeTransaction(tx -> {
                Result result = tx.run(cypher);
                while (result.hasNext())
                {
                    Record record = result.next();
                    // Values can be extracted from a record by index or name.
                    System.out.println(record.get("name").toString());
                }                System.out.println("Task succeeded!");
                return "Task succeeded!";
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/

    public void executeSet(String cypher) {
        try ( Session session = driver.session() ){
            session.writeTransaction(tx -> {
                Result result = tx.run(cypher);
                System.out.println("Task succeeded!");
                while (result.hasNext())
                {
                    //les  prochaines lignes permettent de récupérer les labels / propriétés du noeud
                    //backEnd.executeSet(" MATCH (n) RETURN DISTINCT LABELS(n)");         backEnd.executeSet(" MATCH (n) RETURN DISTINCT PROPERTIES(n)"); dans App.java
                    Record record = result.next();
                    System.out.println(record.toString());

                    // Values can be extracted from a record by index or name.
                    /*Node n = (Node) record.get(0).asNode();
                    System.out.println(n);
                    System.out.println(record.get(0).asNode().get("name").toString());
                    System.out.println((record.get(0).asNode()).getClass().getName());
                    System.out.println(n.getId());
                    record.get(0).asNode();

                    */

                }
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
