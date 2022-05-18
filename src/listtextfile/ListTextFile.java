/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listtextfile;

// Imports
import javax.swing.*;
import java.io.*;

/**
 *
 * @author cogid4775
 */
public class ListTextFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        currentList();
                
        String menuOption = "";
        while (!menuOption.equals("4")) {
            menuOption = JOptionPane.showInputDialog("Choose from the following menu:\n\n"
                + "1 - Print list\n"
                + "2 - Add to list\n"
                + "3 - Remove from list\n"
                + "4 - Exit");
            if (menuOption.equals("1")) {
                printList();
            }
            if (menuOption.equals("2")) {
                addToList();
            }
        }
    }
    
    /**
    * Prints the contents of the list
    */
    public static void printList() {
        try {
            BufferedReader readFile = new BufferedReader(new FileReader("list.txt"));
            String myLine;
            int lineNum = 0;
            while ((myLine = readFile.readLine()) != null) {
                lineNum++;
                System.out.println(lineNum + ". " + myLine);
            }
            System.out.println("");
            readFile.close();
        } catch (IOException ioe) {
            System.err.println("File Crashed!");
        }
    }
    
    /**
     * 
     */
    public static void addToList() throws IOException {
        String addThis = JOptionPane.showInputDialog("What would you like to add to the list?");
        
        PrintWriter fileOut = new PrintWriter(new FileWriter("list.txt"));
        
        String currentList = currentList();
        
        System.out.println(currentList);
        
        fileOut.println(currentList + addThis);
        
        fileOut.close();
    }
    
    public static String currentList() {
        String currentList = "";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader("list.txt"));
            String myLine;
            while ((myLine = readFile.readLine()) != null) {
                currentList+= myLine + "\n";
            }
            readFile.close();
        } catch (IOException ioe) {
            System.err.println("File Crashed!");
        }
        System.out.println(currentList);
        return currentList;
    }
    
}
