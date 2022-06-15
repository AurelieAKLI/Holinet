package io.github.aurelieakli;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


import static org.neo4j.driver.Values.parameters;



public class App {

    public static void main(String[] args) {
        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
        System.out.println("saluuuut");

        Driver driver = GraphDatabase.driver("localhost");

        //TODO : tester un programme java sur une base de données inventée
        //TODO : comment ajouter une propriété/noeud manquant
        //TODO le faire à partir de java
        //TODO compléter la base de connaissance avec le leee
    }

}
