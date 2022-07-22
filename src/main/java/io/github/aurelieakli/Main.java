package io.github.aurelieakli;



//import static io.github.aurelieakli.GestionCSV.removeRecord;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GestionCSV csv = new GestionCSV();
        String csvIn = "ptb2jdm.lookup.csv";
        String csvOut = "resultat.csv";

        csv.removeRecord(csvIn, csvOut, "DET", 1, ";");

        //recupérer les étiquettes souhaitées (ici Det:*)
        //faire un foreach qui fera une requete sur chacune des ces étiquettes ;

        List<String> listNodesNames = csv.getNamePosInCSV(csvIn);
        //System.out.println(listNodesNames);


        BackEnd backEnd = new BackEnd("stageEval", "bolt://localhost:7687", "neo4j", "0000");
        backEnd.createSession();

        //String[] tableau={"Det", "Fem", "Inv", "Plur"};
        //        System.out.println(backEnd.retouverLiensAvec(tableau));

        String[] tableau = {"Det", "Fem", "PL", "Def"};
        String request = backEnd.fusionEtiquettes(tableau);
        System.out.println(request);

        System.out.println("Res final:"+backEnd.executeSet("MATCH (n{name:'chat'}) RETURN  DISTINCT PROPERTIES(n)"));
        backEnd.close();
        //Det:Fem+SG+Def


    }

}
