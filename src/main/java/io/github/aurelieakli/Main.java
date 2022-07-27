package io.github.aurelieakli;



//import static io.github.aurelieakli.GestionCSV.removeRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        GestionCSV csv = new GestionCSV();
        String csvIn = "ptb2jdm.lookup.csv";
        String csvOut = "resultat.csv";

        BackEnd backEnd = new BackEnd("stageEval", "bolt://localhost:7687", "neo4j", "0000");
        backEnd.createSession();

        //on ne retient quer les lignes du ptb2jdm.lookup.csv qui qui nous intéressent
        csv.removeRecord(csvIn, csvOut, "DET", 1, ";");


        //recupérer les étiquettes souhaitées (ici Det:*)
        //faire un foreach qui fera une requete sur chacune des ces étiquettes ;

        //liste étiquettes à traiter
        List<String> listNodesNames = csv.getNamePosInCSV(csvIn);
        System.out.println(listNodesNames.size()+"\n"+listNodesNames);
        for (String s : listNodesNames){
            System.out.println(s);
        }

        //initialisation de la future liste des premières requetes pour toutes les étiquettes de listNodes
        List<String> listePremiereRequetteEtiquettesFusionnees= new LinkedList<>();
        List<String> listeDeuxiemeRequetteEtiquettesFusionnees= new LinkedList<>();

        for (int i=0; i<listNodesNames.size(); ++i){
            //liste qui va contenir chaque élément d'une étiquette par ex [Det, Fem, SG, Def]
            List<String> liste =  new LinkedList<>();
            liste.add(listNodesNames.get(i).split(":")[0]);

            if (listNodesNames.get(i).split(":").length>1){
                //System.out.println("iciiii" + listNodesNames.get(i).split(":")[1]);
                String x = listNodesNames.get(i).split(":")[1];
                for (int j=0; j < x.split("[+]").length;  ++j){
                    liste.add(x.split("[+]")[j]);
                }
            }


            //on ajoute à listePremiereRequetteEtiquettesFusionnees la requête correpondant à l'étiquette dont on s'occupe
            //ceci à l'aide de la fonction fusionEtiquettes qui prend en entrée un vararg
            String premiereRequete = backEnd.fusionEtiquettes(liste.toArray(new String[liste.size()]));
            String deuxiemeRequete = backEnd.fusionEtiquettesDeuxiemeRequete(liste.toArray(new String[liste.size()]));
            listePremiereRequetteEtiquettesFusionnees.add(premiereRequete);
            listeDeuxiemeRequetteEtiquettesFusionnees.add(deuxiemeRequete);


            System.out.println(i+"\t"+listePremiereRequetteEtiquettesFusionnees.get(i));
            System.out.println(i+"\t"+listeDeuxiemeRequetteEtiquettesFusionnees.get(i));
        }
        listePremiereRequetteEtiquettesFusionnees.remove(55);
        listeDeuxiemeRequetteEtiquettesFusionnees.remove(55);
        listePremiereRequetteEtiquettesFusionnees.remove(54);
        listeDeuxiemeRequetteEtiquettesFusionnees.remove(54);

        //tableau contenant exactement la même chose que listePremiereRequetteEtiquettesFusionnees
        String[] troisCol= listePremiereRequetteEtiquettesFusionnees.toArray(new String[listePremiereRequetteEtiquettesFusionnees.size()]);
        String[] quatreCol= listeDeuxiemeRequetteEtiquettesFusionnees.toArray(new String[listePremiereRequetteEtiquettesFusionnees.size()]);

        //csv.addColumn(csvOut, ";", 1, listePremiereRequetteEtiquettesFusionnees.toArray(new String[listePremiereRequetteEtiquettesFusionnees.size()]));
        //csv.addColumn(csvOut, ";", 2, listeDeuxiemeRequetteEtiquettesFusionnees.toArray(new String[listePremiereRequetteEtiquettesFusionnees.size()]));
        csv.addColumn(csvOut, ";", 1, troisCol);
        csv.addColumn(csvOut, ";", 2, quatreCol);

        backEnd.close();


        //System.out.println("listePremiereRequetteEtiquettesFusionnees : "+listePremiereRequetteEtiquettesFusionnees);
        //---------------System.out.println(listePremiereRequetteEtiquettesFusionnees.size()+"\t iciiiii");
        //System.out.println(listePremiereRequetteEtiquettesFusionnees);



        //String[] tableau={"Det", "Fem", "Inv", "Plur"};
        //        System.out.println(backEnd.retouverLiensAvec(tableau));
        /*
        String[] tableau = {"Det", "Fem", "PL", "Def"};
        String request = backEnd.fusionEtiquettes(tableau);
        System.out.println(request);

        String[] tab = {"Det","Mas","PL","Def"} ;
        System.out.println("crash test "+backEnd.fusionEtiquettesDeuxiemeRequete(tab));
        for (int i=0; i<56; ++i){
            System.out.println(troisCol[i]+" fin");

        }
        */



        //System.out.println("Res final:"+backEnd.executeSet("MATCH (n) WHERE n.name=\"chat\" OR  n.name=\"chats\" RETURN DISTINCT PROPERTIES(n)"));


        //Det:Fem+SG+Def


    }

}
