package io.github.aurelieakli;



//import static io.github.aurelieakli.GestionCSV.removeRecord;

import org.apache.lucene.search.FieldCache;
import scala.util.parsing.combinator.testing.Str;

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

        //csv.enteteCSV(csvOut, "Projet Holinet");

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
            String premiereRequete = backEnd.nouvelleRequette(listNodesNames.get(i), liste.toArray(new String[liste.size()]) );
            //String premiereRequete = backEnd.fusionEtiquettes(liste.toArray(new String[liste.size()]));
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

        int x= listePremiereRequetteEtiquettesFusionnees.size();

        //tableau contenant exactement la même chose que listePremiereRequetteEtiquettesFusionnees
        //String[] troisCol= listePremiereRequetteEtiquettesFusionnees.toArray(new String[listePremiereRequetteEtiquettesFusionnees.size()]);
        //troisCol: on stocke les requette permettant de vérifier létat
        //cinqCol : on stocke les 1ere requetes à afficher   //sixCol : on stocke les 2ere requetes à afficher
        String[] quatreCol= new String[x];
        String[] cinqCol= listePremiereRequetteEtiquettesFusionnees.toArray(new String[x]);
        String[] sixCol= listeDeuxiemeRequetteEtiquettesFusionnees.toArray(new String[x]);
        String[] septCol = new String[x];
        String[] huitCol = new String[x];
        String[] neufCol = new String[x]; // la colonne 9 contiendra la différence
        String[] dixCol = new String[x]; // nombre d'éléments dans la 8eme colonne
        String[] onzeCol = new String[x]; // nombre d'éléments dans la 9eme colonne
        String[] troisCol= new String[x];

        for(int i=0; i<troisCol.length; ++i){
            //backEnd.executeSet(listNodesNames.get(i));
            troisCol[i]= backEnd.requeteVerification(listNodesNames.get(i)); //3eme colonne : avant d'effectuer une requete on stocke la requete qui fait le point
            //septCol[i]= backEnd.requeteVerification(listNodesNames.get(i));    // on remplit le 7eme tableau avec la verification des resultats des requetes précédentes
            septCol[i]=troisCol[i];

            List<String> liste = backEnd.executeSet(troisCol[i]);    ///3eme colonne : avant d'effectuer une requete on fait le point
            quatreCol[i]="-  "; //quatreCol va stocker le résultat de l'état avant les requetes
            if (liste.size() != 0){
                for (int j=0; j<liste.size(); ++j){
                    quatreCol[i]+=liste.get(j)+ "  -  ";

                }
            }
            else {
                quatreCol[i]+= "     ";
            }

            dixCol[i]=Integer.toString(liste.size());

            backEnd.executeSet(cinqCol[i]);    //on execute les premieres requete
            backEnd.executeSet(sixCol[i]);      //on execute les deuxiemes requetes

            List<String> liste_bis = backEnd.executeSet(troisCol[i]);
            onzeCol[i]=Integer.toString(liste.size());
            huitCol[i]="-  "; //huitCol va stocker le résultat de l'état apres les requetes
            for (int j=0; j<liste_bis.size(); ++j){
                huitCol[i]+=liste_bis.get(j)+ "  -  ";
                neufCol[i]=" ";
                if (!liste.contains(liste_bis.get(j))){
                    neufCol[i]+=liste_bis.get(j)+ "  -  ";
                }
            }
        }

        csv.addColumn(csvOut, ";", 1, troisCol);
        csv.addColumn(csvOut, ";", 2, quatreCol);
        csv.addColumn(csvOut, ";", 3, cinqCol);
        csv.addColumn(csvOut, ";", 4, sixCol);
        csv.addColumn(csvOut, ";", 5, septCol);
        csv.addColumn(csvOut, ";", 6, huitCol);
        csv.addColumn(csvOut, ";", 7, neufCol);
        csv.addColumn(csvOut, ";", 8, dixCol);
        csv.addColumn(csvOut, ";", 9, onzeCol);

        backEnd.close();

        //TODO : normalement tout est fait; vérifier si tout juste avant d'executer;
        //TODO : écrire un programme permettant de différencier les résultats des colonnes 4 et 8 : c'est fait
        //TODO : ecrire une fonction qui permet d'ecrire un entete en prenant en entrée un varag qui contiendra toutes les case souhaitées



    }

}
