import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Storage{
    //A hashmap storage for constant time search, insert, modify, and delete operations.
    protected HashMap<Integer, Item> storage;
    protected String fileName;

    //Constructor to initialize the storage with the data passed from a file.
    public Storage(String fileName)throws FileNotFoundException, IOException{
        storage = new HashMap<Integer, Item>();
        this.fileName = fileName;
        readFromFile(this.fileName);
    }


    //An algorithm to read data from a file. The complexity is O(n)^2 because I wanted to add a product description and give it the ability to save spaces for a real simulation.
    public void readFromFile(String fileName) throws FileNotFoundException, IOException{
            BufferedReader dataReader = new BufferedReader(new FileReader(fileName)); //Initialize a BufferedReader object to read each line of the file, where each line is one item.
            String line; // A temporary string to hold the current line that was read.
            Stack <Character> temp = new Stack(); // A stack to work with the algorithm that reads evey word in each ''.
            ArrayList <String> data = new ArrayList<String>(); // An arraylist to hold each word that will be passed to the paramaters of an item constructor.
            Item tempItem; // Our item object.
            while((line = dataReader.readLine()) != null){ // While there are still lines to read, keep reading.
                    String word = ""; // A temporary string to reconstruct each word.
                    for(int i =0 ; i < line.length(); i++){ // A for loop to read each character in the line.
                        char currChar = line.charAt(i); // A temporary character to hold the current character.
                        if(currChar == '\''&& temp.isEmpty()){ // If the current character is a ' and the stack is empty, then push it to the stack.
                            temp.push(currChar);
                        }else if(currChar == '\'' && !(temp.isEmpty())){ // If the current character is a ' and the stack is not empty, then pop it from the stack and add the word to the arraylist.
                            temp.pop();
                            data.add(word);
                            word = "";
                            i++;
                            continue;
                        }
                           if(currChar != '\'')
                            word += "" + currChar;
                    }
                    // Initialize the item object with the data from the arraylist and add it to the storage.
                    tempItem = new Item(Integer.parseInt(data.get(0)), data.get(1), data.get(2), data.get(3), data.get(4), Integer.parseInt(data.get(5)), data.get(6), Integer.parseInt(data.get(7)));
                    storage.put(tempItem.ItemKey, tempItem);
                    data.clear();
                    temp.clear();
            }
            dataReader.close(); // Close the reader.
    }


    //Method to add an item to the storage.
    public void INSERT(Item ItemToInsert)throws Exception{

        storage.put(ItemToInsert.ItemKey, ItemToInsert);
    }

    
    //Method to delete an item from the storage.
    public void DELETE(int ItemKey)throws Exception{
        if(storage.containsKey(ItemKey)){
            storage.remove(ItemKey);
        }
        else{
            throw new Exception("Item not found");
        }
    }
   
    //Method to update an item that is currently in storage.
    public void MODIFY(int key )throws Exception{

    }

    //Method to search for an item.
    public Item SEARCH(int key) throws Exception {
        Item tempItem;
        if(storage.containsKey(key)){
            tempItem = storage.get(key);
            return tempItem;
            
        }else{
            throw new Exception("Item not found");
        }
    }


    //A method to search for multiple items.
    public void SearchMultiple() throws Exception {
        for (Item tempItem : storage.values()) {
            tempItem.printData();
        }
    }


    //A method that will be used to print to the output file all the data searched.
    public void printToFile()throws Exception{
    }


    public void PrintItem(Item Item)throws Exception{
    }
}


