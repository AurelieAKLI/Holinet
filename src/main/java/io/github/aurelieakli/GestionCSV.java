package io.github.aurelieakli;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GestionCSV {

    private static Scanner x;

    public static void main(String[] args){
        String name = "Bob";
        String name2 = "Alice";
        String pk = "00";
        String sk = "11";
        String filepath = "cake.csv";
        String searchTerm=name;

        String[] newDataCol =  {"newData1", "newData2", "newData3"};
        //AddColumn(newDataCol,"cake.csv",";",3);

        //saveRecord(name, name2, pk, sk , filepath);
        //readRecord(name2,"cake.csv");
        
        removeRecord(filepath, "Alice", 1, ";");

    }

    private static void removeRecord(String filepath, String removeTerm, int pos, String delimiter) {
        int position = pos-1;
        String tempFile="temp.txt"; //ecrit dans temp.txt les lignes qui contiennent pas Alice
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);

        String currentLine;
        String data[];

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine=br.readLine()) != null){
                data=currentLine.split(";");
                System.out.println(data[position]);
                if (!(data[position].equalsIgnoreCase(removeTerm))){
                    pw.println(currentLine);
                    System.out.println("youpimmm");

                }
            }
            pw.close();
            System.out.println("youpi");
        }
        catch (Exception e){

        }
    }

    private static void AddColumn(String[] newDataCol, String filepath, String delimiter, int colPos) {
        try{
            List<String> data = Files.readAllLines(Paths.get(filepath));
            PrintWriter pw = new PrintWriter(filepath);
            FileWriter fw = new FileWriter(filepath, true);
            pw = new PrintWriter(fw);
            for (int i = 0;  i<data.size(); ++i){
                String[] line = data.get(i).split(delimiter);
                List<String> record = new ArrayList<>(Arrays.asList(line));
                record.add(colPos, newDataCol[i]);
                pw.println(String.join(delimiter, record));
            }
            pw.close();
            System.out.println("Column added.");
        }
        catch(Exception e){

        }
    }

    public static void saveRecord(String name, String name2, String pk, String sk , String filepath){
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

    public static void readRecord(String searchTerm, String filepath){
        boolean found = false;
        String name="";
        String name2 = "";
        String pk = "";
        String sk = "";

        try{
            x = new Scanner(new File(filepath));
            x.useDelimiter("[;\n]");
            while(x.hasNext() && !found){
                name=x.next();
                System.out.println(name);

                if (name.equals(searchTerm)){
                    found=true;
                    System.out.println("youpii");
                }
            }

        }
        catch(Exception e){

        }
    }


}
