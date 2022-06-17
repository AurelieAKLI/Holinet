package io.github.aurelieakli;

import org.neo4j.driver.*;
import org.neo4j.graphdb.Node;

import java.util.List;

public class Acces implements AutoCloseable {
    private static Driver driver;

    public Acces(){
    }

    public void connection(String uri, AuthToken username){
        Config conf= Config.defaultConfig();
        //driver = GraphDatabase.driver( "neo4j://localhost:7687", AuthTokens.basic( "neo4j", "0000" ) );
        //driver = GraphDatabase.driver(uri, username, conf);
        //driver = GraphDatabase.driver(uri, username);
        driver = GraphDatabase.driver(uri);
        //var session=driver.session();
        System.out.println(driver);
    }

    public void accessDatabase(String nameDatabase){
        try (Session session = driver.session(SessionConfig.forDatabase("Project 1"))) {
            session.writeTransaction(tx -> tx.run(":use " + nameDatabase));
        }
        catch(Exception e){
            System.out.println("nooon");
            System.out.println(e);
        }
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

    public void createNode(List<String> labels, List<String> imposedProperties, List<String> properties  ) {
        if (imposedProperties.size() != properties.size()){
            System.out.println("pas le même nombre de proprietes fixé que celui donné.\nVeuillez renouveler l'operation ultérieurement.\n");
        }

        try (Session session = driver.session()) {
            String insertNode = "CREATE n";
            for (String i: labels ){
                insertNode+=":"+i;
            }
            if (properties.size()!=0){
                insertNode+=" {";
                for (int i=0; i<properties.size(); ++i){
                    insertNode+=" ,"+imposedProperties.get(i)+" : \""+properties.get(i)+"\"";
                }
                insertNode+="}";
            }
            insertNode+=") return n";

            String finalInsertNode = insertNode;
            session.writeTransaction(tx -> tx.run(finalInsertNode));

        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public void freeRequest(String freeRequest){
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(freeRequest));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }



    private Node getNodeById(long id) {
        //return this.readTransaction(tx -> {
        //TODO
        return null;
    }

}
