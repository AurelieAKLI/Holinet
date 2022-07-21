package io.github.aurelieakli;
import scala.Char;
import scala.util.parsing.combinator.testing.Str;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class lecturePTB {

    //public String lecture(String fichier){
    public List<String> lecture(File dossier){
        List<String> liste = new ArrayList<>();
        try
        {
            for (File file : dossier.listFiles()){
                if (!file.isDirectory()){
                    // Le fichier d'entrée
                    //file = new File(fichier);
                    // Créer l'objet File Reader
                    FileReader fr = new FileReader(file);
                    // Créer l'objet BufferedReader
                    BufferedReader br = new BufferedReader(fr);
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while((line = br.readLine()) != null)
                    {
                        String det=listeDeterminantDansFTB(line.toString());
                        //String det=listeDeterminantDansFTB(line.toString());

                        //if (det!=null ){
                        if (det!=null && !liste.contains((det))){
                            liste.add(det);

                        }
                        //System.out.println(listeDeterminantDansFTB(line.toString()));
                        //System.out.println(traitement(line.toString()));

                    }
                    fr.close();
                    //System.out.println("Contenu du fichier: ");
                    //System.out.println(sb.toString());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(liste);
        System.out.println(liste.size());
        return liste;
    }

    public HashMap traitement(String ligne){
        HashMap<String,Integer> hmp = new HashMap<String,Integer> ();

        //public String traitement(String ligne, HashMap<String,Integer> hmp){
        String premiereParenth=null;
        String secondeParenth=null;
        String seq="";
        String solution="";

        for (int i = 0; i < ligne.length()-1; i++){
            //System.out.println(ligne.charAt(i)+"\n");
            if (Character.compare('\n',ligne.charAt(i))==0){

            }
            else if (Character.compare('(',ligne.charAt(i))==0 && Character.compare('(',ligne.charAt(i+2))!=0 ){
                premiereParenth="(";
            }

            else if(Character.compare(')',ligne.charAt(i))==0 && Character.compare(')',ligne.charAt(i+1))!=0){
                secondeParenth=")";
                String[] parties=seq.split("##");
                parties[0]=parties[0].replace("(","");
                parties[2]=parties[2].replace(")","");

                premiereParenth=null;
                secondeParenth=null;
                    solution=solution+"!"+parties[0]+";"+parties[2];
                    seq="";
                    System.out.println("!"+parties[0]+";"+parties[2]+"\n");




            }

            else if (premiereParenth!=null && secondeParenth==null){
                seq+=ligne.charAt(i);
            }

        }

        //return solution;
        return hmp;
    }

    public String listeDeterminantDansFTB(String ligne){
        //List<String> liste = new ArrayList<>();

        //public String traitement(String ligne, HashMap<String,Integer> hmp){
        String premiereParenth=null;
        String secondeParenth=null;
        String seq="";
        String solution="";

        for (int i = 0; i < ligne.length()-1; i++){
            //System.out.println(ligne.charAt(i)+"\n");
            if (Character.compare('\n',ligne.charAt(i))==0){

            }
            else if (Character.compare('(',ligne.charAt(i))==0 && Character.compare('(',ligne.charAt(i+2))!=0 ){
                premiereParenth="(";
            }

            else if(Character.compare(')',ligne.charAt(i))==0 && Character.compare(')',ligne.charAt(i+1))!=0){
                secondeParenth=")";
                String[] parties=seq.split("##");
                parties[0]=parties[0].replace("(","");
                parties[2]=parties[2].replace(")","");

                premiereParenth=null;
                secondeParenth=null;
                if (parties[0].contains("DET")) {
                    solution=solution+"!"+parties[0]+";"+parties[2];
                    seq="";
                    //System.out.println("!"+parties[0]+";"+parties[2]+"\n");
                    //liste.add(parties[2]);
                    parties[1]=parties[1].replace(",","");
                    return("\n"+parties[1]+" - "+parties[2]);

                }

            }

            else if (premiereParenth!=null && secondeParenth==null){
                seq+=ligne.charAt(i);
            }

        }

        //return solution;
        return null;
    }

    public static void main(String[] args) throws Exception {
        lecturePTB lecture= new lecturePTB();
        File dossier = new File("\\\\Filer\\home\\Invites\\akli\\Mes documents\\fichiersPTBaLire");
        lecture.lecture(dossier);
        //String ligne=lecture.lecture("C:\\Users\\akli\\IdeaProjects\\Holinet\\src\\main\\java\\io\\github\\aurelieakli\\dev.French.gold.ptb");
        //premiere version avec un seul fichier lu lecture.lecture("C:\\Users\\akli\\IdeaProjects\\Holinet\\src\\main\\java\\io\\github\\aurelieakli\\dev.French.gold.ptb");
        //Une valeur peut avoir plusieurs clés ->  les catg gram des valeurs assoc à différents mots
        //une valer=catg et le mot en valeur
        //System.out.println(ligne);
        HashMap<String,Integer> h = new HashMap<String,Integer> ();
        h.put(null,null);
        h.put("det -  la",1);
        h.put("det -  la",2);

        //System.out.println(h+"\t"+h.size());
        //System.out.println(h.get("det -  la")+10);
    }

}
