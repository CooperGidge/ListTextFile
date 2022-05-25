/*
 * ListTextFile.java
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
        while (!menuOption.equals("5")) {
            menuOption = JOptionPane.showInputDialog("Choose from the following menu:\n\n"
                + "1 - Print list\n"
                + "2 - Add to list\n"
                + "3 - Remove from list\n"
                + "4 - Move list item\n"
                + "5 - Exit");
            // Check if the option provided is null. If it is, end the program
            // The option will be null if the user presses "Cancel" or the x in the corner
            // The reason we end the program is to prevent errors from the
            // if statements below this (null is not a String)
            if (menuOption == null) {
                return;
            }
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
            if (menuOption.equals("4")) {
                moveListItem();
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
        // Get the String the user wants to add to the list
        String addThis = JOptionPane.showInputDialog("What would you like to add to the list?");
        // Get the position in the list the user wants to add the String to
        String toHere = JOptionPane.showInputDialog("What number do you want to add the item to?\n"
            + "Answer must be between 1 and " + (items.size()+1) + ", inclusive.");
        // Check that the input is an integer
        // Break from the method if it is not
        if (!checkIfInt(toHere)) {
            return;
        }
        // Now that we are sure the input is an integer, check it is within the boundaries
        if ( Integer.parseInt(toHere) <= 0 || Integer.parseInt(toHere) > (items.size()+1) ) {
            JOptionPane.showMessageDialog(null, "That's outside of the boundaries!");
            return;
        }
        // Add the String to the position in the list
        // (subtract 1 because arrays start from 0)
        items.add(Integer.parseInt(toHere)-1, addThis);
        // Save the ArrayList to the file
        saveFile();
    }
    
    /**
     * Removes an item from the list
     * @throws IOException
     */
    public static void removeFromList() throws IOException {
        // Get the position number of the item the user wants to remove from the list
        String toHere = JOptionPane.showInputDialog("What number do you want to remove from the list?\n"
            + "Answer must be between 1 and " + (items.size()) + ", inclusive.");
        // Check that the input is an integer
        // Break from the method if it is not
        if (!checkIfInt(toHere)) {
            return;
        }
        // Now that we are sure the input is an integer, check it is within the boundaries
        if ( Integer.parseInt(toHere) <= 0 || Integer.parseInt(toHere) > (items.size()) ) {
            JOptionPane.showMessageDialog(null, "That's outside of the boundaries!");
            return;
        }
        // Remove that item from the list
        // (subtract 1 because arrays start from 0)
        items.remove(Integer.parseInt(toHere)-1);
        // Save the ArrayList to the file
        saveFile();
    }
    
    /**
     * Moves the position of an item in the list
     * @throws IOException
     */
    public static void moveListItem() throws IOException {
        String fromHere = JOptionPane.showInputDialog("What item number do you want to move?\n"
            + "Answer must be between 1 and " + (items.size()) + ", inclusive.");
        // Check that the input is an integer
        // Break from the method if it is not
        if (!checkIfInt(fromHere)) {
            return;
        }
        String toHere = JOptionPane.showInputDialog("To what item number do you want to move it to?\n"
            + "Answer must be between 1 and " + (items.size()) + ", inclusive.");
        // Check that the input is an integer
        // Break from the method if it is not
        if (!checkIfInt(toHere)) {
            return;
        }
        // Now that we are sure the inputs are integers, check that both are within the boundaries
        if ( Integer.parseInt(toHere) <= 0 || Integer.parseInt(toHere) > (items.size()) ) {
            if ( Integer.parseInt(fromHere) <= 0 || Integer.parseInt(fromHere) > (items.size()) ) {
                JOptionPane.showMessageDialog(null, "That's outside of the boundaries!");
                return;
            }
        }
        // (Remember: subtracting 1 because arrays start from 0)
        // Temporarily store the item we want to move
        String temp = items.get(Integer.parseInt(fromHere)-1);
        // Remove the original location of the item
        items.remove(Integer.parseInt(fromHere)-1);
        // Add the item stored in the String temp to the position specified by the user
        items.add(Integer.parseInt(toHere)-1, temp);
        // Save the ArrayList to the file
        saveFile();
    }
    
    /**
     * Converts the list into a String that can be printed and saved to the file
     * @return returns a String representation of the list
     */
    public static String currentList() {
        // Create an empty String to start
        String currentList = "";
        // Add each item of the ArrayList to the String, adding a line break at the end of each
        for (int i = 0; i < items.size(); i++) {
            currentList += (items.get(i) + "\n");
        }
        // Return the String representation of the list
        return currentList;
    }
    
    /**
     * Saves the ArrayList to the file
     * @throws IOException
     */
    public static void saveFile() throws IOException {
        // Gets the ArrayList as a String representation
        String newFile = (currentList());
        // Open the file
        PrintWriter fileOut = new PrintWriter(new FileWriter("list.txt"));
        // Replace the contents of the file with the String representation of the ArrayList
        fileOut.println(newFile);
        // Close the file
        fileOut.close();
        // Rebuild the ArrayList using the new file
        buildList();
    }
    
    /**
     * Checks if a String can be an integer
     * @param String n String to check if it is an integer
     * @return boolean returns true if the String can be parsed as an int, false otherwise
     */
    public static boolean checkIfInt(String n) {
        try { 
            // Check - if it is an integer, do nothing so the return true below runs.
            Integer.parseInt(n); 
        } catch (NumberFormatException e) { 
            // If it is not an integer, return false
            JOptionPane.showMessageDialog(null, "That's not a number!");
            return false;
        }
        // If the method doesn't return false, return true
        return true;
    }
    
}