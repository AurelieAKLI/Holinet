package io.github.aurelieakli;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class App {
    
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("localhost");
    }
    
}
