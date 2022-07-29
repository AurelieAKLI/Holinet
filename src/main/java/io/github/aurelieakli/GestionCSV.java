package io.github.aurelieakli;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GestionCSV {

    private static Scanner x;

    public static void main(String[] args){
        String s = "lem=40|cpos=D|mwehead=DET+|pred=y|s=card -  40, " ;
        verification(s,"");
        return ;
    }

    public static Boolean verification(String TupleEtiquetteFtbDet, String fichierCSV){ //à itérer sur la liste de tuple donner par lecture dans lecturePTB.java
        String[] s = TupleEtiquetteFtbDet.split("-", 2);
        String t = s[1];
        String etiquette = t.split("-",1)[0];
        String det = t.split("-",1)[0];
        det=det.replace("  ","-  ");
        System.out.println(s[0]+"    " + det);
        return true;
    }

    public void  enteteCSV(String filepath, String titreProposéparlUtilisateur, String... colonneSup){
        File file =  new File(filepath);
        try{
            FileWriter fw = new FileWriter(filepath, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(titreProposéparlUtilisateur+"\t ;");
            pw.print("étiquettes FTB\t\t;\t");//1
            pw.print("étiquettes JDM\t\t;\t");//2
            pw.print("requêtes verification avant traitement\t\t;\t");//3
            pw.print("résultat verification avant traitement\t\t;\t");//4
            pw.print("première requete du traitement : on vérifie si les relations souhaitées existent sinon on les crée\t\t;\t");//5
            pw.print("deuxième requete du traitement : on vérifie si les relations souhaitées ont un poids sinon on les pondère\t\t;\t");//6
            pw.print("requête pour avoir le résultat du traitement précédent\t\t;\t");//7
            pw.print("résultat du traitement précédent\t\t;\t");//8
            pw.print("mots nouveaux ajoutés grâce à ce traitement\t\t;\t");//9
            pw.print("nombre d'éléments dans la 8eme colonne\t\t;\t");//10
            pw.print("nombre d'éléments dans la 9eme colonne\t\t;\t");//11

            for (int i=0; i< colonneSup.length; ++i){
                pw.print(colonneSup[i]+"\t\t;\t");
            }
            pw.flush();
            pw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public  void  removeRecord(String filepath, String tempFile,  String keepTerm, int pos, String delimiter) {
        int position = pos-1;
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            writingInRemoveRecord(keepTerm, position, pw, br);
            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writingInRemoveRecord(String keepTerm, int position, PrintWriter pw, BufferedReader br) throws IOException {
        String currentLine;
        String[] data;
        while ((currentLine= br.readLine()) != null){
            data=currentLine.split("##");
            if ((data[position].equalsIgnoreCase(keepTerm))){
                pw.println(currentLine);
            }
        }
    }


    public void addColumn(String filepath, String delimiter, int colPos, String... newDataCol) {
        try{
            PrintWriter pw = getPrintWriterForAddColumn(filepath, delimiter, colPos, newDataCol);
            pw.close();
            System.out.println("Column added.");
        }
        catch(Exception e){
            System.out.println("Column NOT added.");
            e.printStackTrace();

        }
    }

    private PrintWriter getPrintWriterForAddColumn(String filepath, String delimiter, int colPos, String[] newDataCol) throws IOException {
        List<String> data = Files.readAllLines(Paths.get(filepath));
        PrintWriter pw = new PrintWriter(filepath);
        FileWriter fw = new FileWriter(filepath, true);
        pw = new PrintWriter(fw);

        writingInAddColumn(delimiter, colPos, newDataCol, data, pw);

        return pw;
    }

    private void writingInAddColumn(String delimiter, int colPos, String[] newDataCol, List<String> data, PrintWriter pw) {
        for (int i = 0; i< newDataCol.length; ++i){
            String[] line = data.get(i).split(delimiter);
            List<String> record = new LinkedList<>(Arrays.asList(line));
            record.add(colPos, newDataCol[i]);
            pw.println(String.join(delimiter, record));
        }
    }

    public void saveRecord(String name, String name2, String pk, String sk , String filepath){
        try{
            //ecrire dans csv
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(name+";"+pk+";"+name2+";"+sk);
            pw.flush();
            pw.close();
            JOptionPane.showMessageDialog(null, "Record saved");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Record not saved");
        }
    }

    public void readRecord(String searchTerm, String filepath){
        boolean found = false;
        String name="";
        String name2 = "";
        String pk = "";
        String sk = "";

        try{
            readingInReadRecord(searchTerm, filepath, found);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void readingInReadRecord(String searchTerm, String filepath, boolean found) throws FileNotFoundException {
        String name;
        x = new Scanner(new File(filepath));
        x.useDelimiter("[;\n]");
        while(x.hasNext() && !found){
            name=x.next();
            System.out.println(name);

            if (name.equals(searchTerm)){
                found =true;
            }
        }
    }

    public List<String> getNamePosInCSV(String filepath){
        String pos="";
        List<String> listNodesNames = new ArrayList<>();
        try{
            writingInGetNamePosInCSV(filepath, listNodesNames);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listNodesNames;
    }

    private void writingInGetNamePosInCSV(String filepath, List<String> listNodesNames) throws FileNotFoundException {
        String pos;
        x = new Scanner(new File(filepath));
        x.useDelimiter("[##\n]");
        while(x.hasNext()){
            pos = x.next();
            if (pos.contains("Det:") && !pos.contains("Pre") && !pos.contains("Intg")){  //A MODIFIER SELON LE POS QUE L'ON CHERCHE
                pos=pos.replace("\t","");
                pos=pos.replace("DET+","");
                listNodesNames.add(pos);
            }
        }
    }

}
