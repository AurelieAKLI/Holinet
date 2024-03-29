package io.github.aurelieakli;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.PropertyContainer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BackEnd {
    public final Driver driver;
    private final String database;
    private Session session;


    public BackEnd(String database, String url, String user, String password) {
        driver = GraphDatabase.driver( url, AuthTokens.basic( user, password ) );
        this.database = database;
    }
    public static void main(String[] args){
        String[] tab={"Det", "Fem", "SG", "Def"};
        //System.out.println(nouvelleRequette("Det:Fem+SG+Def", tab));
    }


    public String retourAvantfusion(String... arguments){
        String request = "match (m:n_pos) where m.name=\"";
        request+=arguments[0]+"::" ;
        for (int i=1; i<arguments.length; ++i){
            if (arguments[i].equals("Sing")){
                request+="+SG";
            }
            else if (arguments[i].equals("Plur")){
                request += "+PL";
            }
            else {
                request+="+"+arguments[i];
            }
        }
        request+="\" AND NOT EXISTS (m.jdmType) DETACH DELETE m return *";
        request=request.replace(":+", "");
        return request;
    }

    public String[] getStringPremierePartieWithLastRelationship(String request, String[] r, String m, String[] arguments){
        for(int i = 0; i< arguments.length; ++i){
            if (i==0){
                r[i]="r";
            }
            else{
                r[i]= r[i-1]+"r";
            }
            m +="m";
            //System.out.println(r[i]+"\n");
            if (arguments[i].equals("PL")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Plur:'}) ";
            }
            else if (arguments[i].equals("SG")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Sing:'}) ";
            }
            else if (arguments[i].equals("Def")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Defini:'}) ";
            }
            else if (arguments[i].equals("Indef")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Indefini:'}) ";
            }
            else{
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'"+ arguments[i]+":'}) ";

            }
        }
        return r;
    }

    public String fusionEtiquettesDeuxiemeRequete(String... arguments){
        String request="MATCH";
        String[] r= new String[arguments.length];

        for (int i=0; i<r.length;++i){
            r[i]="";
        }

        String m="";
        request = getStringPremierePartie(request, r, m, arguments);
        r = getStringPremierePartieWithLastRelationship(request, r, m, arguments);
        request+=" SET (CASE WHEN NOT exists("+r[r.length-1]+".weight) THEN "+r[r.length-1]+" END).weight= (";
        for (int i=0; i<r.length; ++i){
            if (i!=r.length-1){
                request+=r[i]+".weight+ ";
            }
            else {
                request+=r[i]+".weight)/3 ";

            }
        }
        request=request.replaceFirst(",","");

        return request+"  RETURN DISTINCT PROPERTIES(n)";
    }

    public String fusionEtiquettes(String... arguments){
        //trait : nombre genre sous categ : def/indef
        //structure de trait : après le 1er arg du vararg
        String request="MATCH";
        String[] r= new String[arguments.length];

        for (int i=0; i<r.length;++i){
            r[i]="";
        }

        String m="";
        request = getStringPremierePartie(request, r, m, arguments);
        request+=" WHEREE ";

        for(int i=0; i<arguments.length; ++i){
            request+="AND "+ r[i]+".weight>0 ";
        }
        int x = (arguments.length)-1 ;
        request = finalite(request, x, arguments);
        request=request.replace("E AND", "");
        String reponse = request.replace("+}", "'");
        //return request.replace("+}", "'");
        return reponse;

        //System.out.println(request);
        //executeSet(request);
    }
    /*
    public void fusionEtiquettes(String... arguments){
        String request="MATCH";
        String[] r= new String[arguments.length];

        for (int i=0; i<r.length;++i){
            r[i]="";
        }

        String m="";
        request = getStringPremierePartie(request, r, m, arguments);
        request+=" WHERE ";

        for(int i=0; i<arguments.length; ++i){
            request+="AND "+ r[i]+".weight>0 ";
        }

        for (int i=0; i<arguments.length; ++i){
            System.out.println(arguments[i]);

        }

        int x = (arguments.length)-1 ;
        System.out.println("x vaut : "+x);
        request = finalite(request, x, arguments);


        System.out.println(request);
    }
    */

    public String retouverLiensAvec(String... arguments){
        String request = "match (n:n_term)-[r:r_pos]->(m:n_pos) where m.name=\"";
        request+=arguments[0]+"::" ;
        for (int i=1; i<arguments.length; ++i){
            if (arguments[i].equals("Sing")){
                request+="+SG";
            }
            else if (arguments[i].equals("Plur")){
                request += "+PL";
            }
            else if (arguments[i].equals("Indefini")){
                request += "+Indef";
            }
            else if (arguments[i].equals("Defini")){
                request += "+Def";
            }

            else {
                request+="+"+arguments[i];
            }
        }
        request+="\" ";
        request+="RETURN DISTINCT PROPERTIES(n)";
        request=request.replace("+}", "'");
        return request;
    }

    private String finalite(String request, int x, String... arguments) {
        //System.out.println(request);
        //request +=" MERGE (n)-["+ arguments[x]+"r:r_pos]->(nouveau:n_pos{name:'"; ERREUR GRAVE WSH
        request +=" MERGE (n)-[:r_pos]->(nouveau:n_pos{name:'";
        //System.out.println(request);
        request = getStringDeuxiemePartie(request, arguments);

        request +="RRETURN DISTINCT PROPERTIES(n)";


        //System.out.println(request +"\n");
        request = request.replaceFirst(",", " ");
        //request = request.replaceFirst("AND,", " ");
        request = request.replace("RR", "R");


        //System.out.println(request);
        return request;
    }

    public String requeteVerification(String etiquette){
        String request = "MATCH (n:n_term)-[r:r_pos]->(m:n_pos{name:'"+etiquette;
        return request+"'}) return DISTINCT PROPERTIES(n)";

    }

    public  String nouvelleRequette(String etiquetteJDMdepart, String... arguments){
        String s = "MATCH (n:n_term)-[r:r_pos]->(m:n_pos), (n:n_term)-[rr:r_pos]->(mm:n_pos{name:'";
        if (arguments[arguments.length-1].equals("Indef")){
            s+="Indefini:";
        }
        else if(arguments[arguments.length-1].equals("Def")){
            s+="Defini:";
        }
        else{
            s+=arguments[arguments.length-1];
        }
        s+="'}) WHERE r.weight>0  AND rr.weight>0 AND m.name STARTS WITH ";
        s+="'"+arguments[0]+":";
        for (int i=1; i<arguments.length-1; ++i){
            if (i==arguments.length-2){
                s+=arguments[i];
            }
            else{
                s+=arguments[i]+"+";
            }
        }

        s+="' MERGE (n)-[:r_pos]->(nouveau:n_pos{name:'"+etiquetteJDMdepart+"'}) RETURN DISTINCT PROPERTIES(n)";

        return s;
    }


    /*
    String y="MATCH (n:n_term)-[r:r_pos]->(m:n_pos{name:'Det:'})," +
                "(n:n_term)-[rr:r_pos]->(l:n_pos{name:'Number:Sing'})," +
                "(n:n_term)-[rrr:r_pos]->(o:n_pos{name:'Gender:Mas'}) " +
                "WHERE r.weight>0 AND rr.weight>0 AND rrr.weight>0 " +
                "MERGE (n)-[rrrr:r_pos]->(nouveau:n_pos{name:'Det:Mas+SG'}) " +
                //"return *");
                "RETURN DISTINCT PROPERTIES(n)";
     */

    private String getStringPremierePartie(String request, String[] r, String m, String[] arguments) {
        for(int i = 0; i< arguments.length; ++i){
            if (i==0){
                r[i]="r";
            }
            else{
                r[i]= r[i-1]+"r";
            }
            m +="m";
            //System.out.println(r[i]+"\n");
            if (arguments[i].equals("PL")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Plur:'}) ";
            }
            else if (arguments[i].equals("SG")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Sing:'}) ";
            }
            else if (arguments[i].equals("Def")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Defini:'}) ";
            }
            else if (arguments[i].equals("Indef")){
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'Indefini:'}) ";
            }
            else{
                request +=", (n:n_term)-["+ r[i]+":r_pos]->("+ m +":n_pos{name:'"+ arguments[i]+":'}) ";

            }

        }
        return request;
    }

    private String getStringDeuxiemePartie(String request, String[] arguments) {
        for (int i = 0; i< arguments.length; ++i){
            //System.out.println((arguments[i]));
            if (i==0){
                String etiquette= arguments[i].split(":")[0];
                request +=etiquette+":";
            }
            else{
                //String etiquette= arguments[i].split(":")[1];
                String etiquette=arguments[i];
                if (etiquette.equals("Sing")){
                    etiquette="SG";
                }
                if (etiquette.equals("Plur")){
                    etiquette="PL";
                }

                request +=etiquette+"+";
            }
        }
        request+="}}) ";
        return request;
    }


    public Session getSession() {
        if (database == null || database.isBlank()){
            return driver.session();
        }
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

    public LinkedList<String> executeSet(String cypher) {
        List<String> res = new LinkedList<>();
        try ( Session session = driver.session() ){
            session.writeTransaction(tx -> {
                Result result = tx.run(cypher);
                System.out.println("Task succeeded!");

                while (result.hasNext())
                {
                    //les  prochaines lignes permettent de récupérer les labels / propriétés du noeud
                    //backEnd.executeSet(" MATCH (n) RETURN DISTINCT LABELS(n)");         backEnd.executeSet(" MATCH (n) RETURN DISTINCT PROPERTIES(n)"); dans App.java
                    Record record = result.next();
                    String rts=record.toString();
                    //System.out.println(rts); //pour afficher tout le resultat de distincts properties
                    if (rts.contains("LABELS")){
                        //System.out.println(getLabels(record.toString()));
                    }
                    if (rts.contains("PROPERTIES")){
                        //System.out.println(getProperties(record.toString()));
                    }

                    //System.out.println(getNameOfNode(record.toString()));
                    res.add(getNameOfNode(record.toString()));

                    // Values can be extracted from a record by index or name.
                    //Node n = (Node) record.get(0).asNode();
                    //System.out.println(n);
                    //System.out.println(record.get(0).asNode().get("name").toString());
                    //System.out.println((record.get(0).asNode()).getClass().getName());
                    //System.out.println(n.getId());
                    //record.get(0).asNode();



                }
                return res;
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return (LinkedList<String>) res;
    }




    public Result executeGet( final String cypher ) throws Neo4jException{
        return session.run( cypher );
    }

    public String getNameOfNode(String noeud){
        String support=getProperties(noeud);
        support=support.split(":")[1];
        support=support.split(",")[0];
        return support.replaceFirst(" ","");
    }

    public String getLabels(String noeud){
        String x = (noeud.split("\\["))[1];
        x=x.replace("\"","");
        return "le label est : "+x.split("\\]")[0];
    }

    public String getProperties(String noeud){
        String x = (noeud.split("\\{"))[2];
        x=x.replace("\"","");
        return "les proprietes sont = "+x.split("\\}\\}")[0];

    }

}
