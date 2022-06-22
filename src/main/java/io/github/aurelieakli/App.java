package io.github.aurelieakli;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.dbms.api.DatabaseManagementService;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class App  {
    private static GraphDatabaseService graphDb;
    private   Node firstNode;
    private   Node secondNode;
    private Relationship relationship;
    private static DatabaseManagementService managementService;

    public enum NodeType implements Label {
        Person, Course;
    }

    public enum RelationType implements RelationshipType {
        Knows, BelongsTo;
    }


    public App(GraphDatabaseService graphDb, Node firstNode, Node secondNode, Relationship relationship, DatabaseManagementService managementService) {
        this.graphDb = graphDb;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.relationship = relationship;
        this.managementService = managementService;
    }

    public static void main(String[] args) throws Exception {

        BackEnd backEnd= new BackEnd( "Projet 1", "bolt://localhost:7687", "neo4j", "0000" );
        backEnd.createSession();



        //utiliser merge plutôt que contrainte d'unicité
        //backEnd.executeSet("MERGE (n:NOUVEAU {name:\"un nom quelconque\"})");
        //backEnd.executeSet("match (n) return n");
                /*"MATCH (n)\n" +
                "WHERE n.name=\"Morpheus\"\n" +
                "RETURN\n" +
                "  n.name AS name,\n" +
                "  exists((n)-[:CODED_BY]->()) AS is_coded_by");*/
        backEnd.executeSet("match (n) where n.name=\"Neo\" return n");
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

        /*
        PSEUDO-CODE
        fonctionFinale(catégorie_souhaitée) :
            catégorie_souhaitée catg=[]
            pour tous les noeuds dans holinet :
                si noeud.catégorie==catégorie_souhaitée
                     categorie+=noeud
                     applicationTravailSurLesEtiquettes()
        retourner liste
        demander au prof si il vaut mieux traverser holinet une fois et faire le
        travail sur les étiquettes tout d'un coup u bien retraverser holinet pour
        chaque nouvelle catg et renvoyer à chaque  fois une liste par catg par ex

         */
    }

}
