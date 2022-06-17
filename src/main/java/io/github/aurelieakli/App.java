package io.github.aurelieakli;

//import org.neo4j.driver.Transaction;
//import org.neo4j.graphdb.*;
import org.neo4j.driver.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.TopLevelTransaction;

import java.lang.Runtime.Version;
import java.util.HashSet;
import java.util.Set;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.RelationshipIndex;
import org.neo4j.helpers.collection.IterableWrapper;
//import org.neo4j.server.database.GraphDatabaseFactory;
import javax.naming.ServiceUnavailableException;

//import org.neo4j.kernel.EmbeddedGraphDatabase;

public class App {


    public enum NodeType implements Label {
        Person, Course;
    }

    public enum RelationType implements RelationshipType {
        Knows, BelongsTo;
    }


    public static void main(String[] args) throws Exception {

        BackEnd backEnd= new BackEnd( "Projet 1", "bolt://localhost:7687", "neo4j", "0000" );
        backEnd.createSession();
        backEnd.executeSet("CREATE (n:NOUVEAU)");
        backEnd.executeSet("match (n) return n");
        backEnd.close();













        /*
        1ère version
        System.out.println("saluuuut");
        Version version = Runtime.version();
        System.out.println(" java version : "+version);
        Acces acces = new Acces();
        String username = "neo4j";
        String password = "0000";
        acces.connection("neo4j://localhost:7687", AuthTokens.basic(username, password));
        //acces.connection( "bolt://localhost:7687/", AuthTokens.basic(username, password));
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        GraphDatabaseService dbService = dbFactory.newEmbeddedDatabase("C:\\Users\\akli\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-6281b6b9-686b-4d8e-8dcd-df2871cf9980");
        System.out.println("saluuuut2");

        //acces.accessDatabase("neo4j");
        //acces.freeRequest("match(n) return n");



        acces.close();
        2ème version

        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase("C:\\Users\\akli\\.Neo4jDesktop\\relate-data\\dbmss\\dbms-a6f054e5-0b12-414a-931f-c34babedc66e");
        Transaction transaction;
        transaction = (Transaction) graphDb.beginTx();
        try {
            Node bobNode  = graphDb.createNode();
            //bobNode.addLabel(label("Person"));
            bobNode.setProperty("PId", 5001);
            bobNode.setProperty("Name", "Bob");
            bobNode.setProperty("Age", 25);

            //Node aliceNode  = graphDb.createNode(NodeType.Person);
            Node aliceNode  = graphDb.createNode();
            aliceNode.setProperty("PId", 5002);
            aliceNode.setProperty("Name", "Alice");
            aliceNode.setProperty("Age", 25);
            System.out.println("youpi\t"+bobNode);
            bobNode.createRelationshipTo(aliceNode, RelationType.Knows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        */
        //TODO : tester un programme java sur une base de données inventée
        //TODO : comment ajouter une propriété/noeud manquant
        //TODO le faire à partir de java
        //TODO compléter la base de connaissance avec le leee
    }

}
