/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listtextfile;

// Imports
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A list in a text file called list.txt that can be viewed and edited
 * @author cogid4775
 */
public class ListTextFile {
    
    // ArrayList that holds the list from the text file in order to make edits
    // Edits to this ArrayList can then be saved to the file
    public static ArrayList<String> items = new ArrayList<String>();

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Build the ArrayList based on the contents in the file
        buildList();
        
        // Create a String to hold the option the user chooses
        String menuOption = "";
        // Unless the user chooses option 4, don't end the program
        while (!menuOption.equals("4")) {
            menuOption = JOptionPane.showInputDialog("Choose from the following menu:\n\n"
                + "1 - Print list\n"
                + "2 - Add to list\n"
                + "3 - Remove from list\n"
                + "4 - Exit");
            // Call the respective method for what the user chose to do
            if (menuOption.equals("1")) {
                printList();
            }
            if (menuOption.equals("2")) {
                addToList();
            }
            if (menuOption.equals("3")) {
                removeFromList();
            }
        }
    }
    
    /**
     * Builds the ArrayList based on contents in the file
     */
    public static void buildList() {
        // Clear the ArrayList so it is empty before it gets populated
        items.clear();
        // Try reading the file
        try {
            // Get the file
            BufferedReader readFile = new BufferedReader(new FileReader("list.txt"));
            // Create an empty String to hold the contents of the current line
            String myLine;
            // Loop through each line in the text file until there are none left
            while ((myLine = readFile.readLine()) != null) {
                // If the line has no contents in it, break from the loop
                // as there is said to be no more items left. This is to
                // account for random empty lines at the end of the text file,
                // which when manually editing it, can accidentally be left in.
                if (myLine.equals("")) {
                    break;
                }
                // Add the contents of this line to a new element of the ArrayList
                items.add(myLine);
            }
            // Close the file now that we are done with it
            readFile.close();
        } catch (IOException ioe) {
            // If something goes wrong, print this
            System.err.println("File Crashed!");
        }
    }
    
    /**
    * Prints the contents of the list, with the item number (not ArrayList index) out front.
    */
    public static void printList() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i));
        }
        // Add a blank line after the list to make room for whatever is printed next
        System.out.println("");
    }
    
    /**
     * Adds an new item to the list
     * @throws IOException
     */
    public static void addToList() throws IOException {
        String addThis = JOptionPane.showInputDialog("What would you like to add to the list?");
        
        String toHere = JOptionPane.showInputDialog("What number do you want to add the item to?");
        
        items.add(Integer.parseInt(toHere)-1, addThis);
        
        saveFile();
    }
    
    public static void removeFromList() throws IOException {
        String toHere = JOptionPane.showInputDialog("What number do you want to remove from the list?");
        
        items.remove(Integer.parseInt(toHere)-1);
        
        saveFile();
    }
    
    public static String currentList() {
        String currentList = "";
        for (int i = 0; i < items.size(); i++) {
            currentList += (items.get(i) + "\n");
        }
        return currentList;
    }
    
    public static void saveFile() throws IOException {
        String newFile = (currentList());
        
        PrintWriter fileOut = new PrintWriter(new FileWriter("list.txt"));
        fileOut.println(newFile);
        fileOut.close();
        buildList();
    }
    
}