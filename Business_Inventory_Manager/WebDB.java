import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebDB {
    public static void main(String[] args) {
        Storage storage = null;
        String outputFile = "";
        BufferedWriter operationsLogger = null;
        Scanner operationsReader = new Scanner(System.in);

        // A switch statement block to read the arguments and initialize the storage and
        // output file.
        // If there are no file names given upon initial start of the program in
        // terminal, then it uses default files.
        switch (args.length) {
            case 1:
                try {
                    storage = new Storage(args[0]);
                    operationsLogger = new BufferedWriter(new FileWriter("Output.txt"));
                } catch (FileNotFoundException e) {
                    System.out.print("Error the datafile cannot be found.");
                } catch (IOException e) {
                    System.out.print(
                            "Error reading data from input file. Check to make sure items in the file have the correct format.");
                }
                break;
            case 2:
                try {
                    storage = new Storage(args[0]);
                } catch (FileNotFoundException e) {
                    System.out.print("FILE NOT FOUND ERROR: The datafile cannot be found.");
                } catch (IOException e) {
                    System.out.print(
                            "READ ERROR:Check to make sure items in the DATA file given have the correct format.");
                }
                try {
                    operationsLogger = new BufferedWriter(new FileWriter("Output"));
                } catch (FileNotFoundException e) {
                    System.out.print("FILE NOT FOUND ERROR: The output file cannot be found.");
                }catch( Exception except){
                    System.out.println("Error");
                }
                break;
            default:
                try {
                    storage = new Storage("DataA.txt");
                    operationsLogger = new BufferedWriter(new FileWriter("Output.txt"));
                } catch (FileNotFoundException e) {
                    System.out.print("Error the default datafile cannot be found.");
                } catch (IOException e) {
                    System.out.print(
                            "Error reading data from default data file. Check to make sure items in the file have the correct format.");
                }
                break;
        }

        // Code block responsible for processing the operations given by the user.
        String operation = "";

        while (!(operation.equals("QUIT")) && !(operation.equals("EXIT"))) {
            operation = operationsReader.nextLine();
        
            String[] operations = operation.split(" ");

            if (operations[0].equals("INSERT")) {
                try {
                    Logger(operationsLogger, operations, "");
                    ArrayList<String> ItemData = new ArrayList<>();
                    for (int i = 1; i < operations.length; i++) {
                        ItemData.add(operations[i]);
                    }
                    if (ItemData.size() <= 8) {
                        for (int i = ItemData.size(); i < 8; i++) {
                            ItemData.add("-1");
                        }
                    }
                    Item createdItem = new Item(Integer.parseInt(ItemData.get(0)), ItemData.get(1), ItemData.get(2),
                            ItemData.get(3), ItemData.get(4), Integer.parseInt(ItemData.get(5)), ItemData.get(6),
                            Integer.parseInt(ItemData.get(7)));
                    storage.INSERT(createdItem);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }

            } else if (operations[0].equals("DELETE")) {
                try {
                    Logger(operationsLogger, operations, "");
                    storage.DELETE(Integer.parseInt(operations[1]));
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            } else if (operations[0].equals("MODIFY")) {
                try {
                    Logger(operationsLogger, operations, "");
                    String[] params = new String[operations.length - 1];
                    for (int i = 0; i < operations.length - 1; i++) {
                        params[i] = operations[i + 1];
                    }
                    Item tempItem = storage.SEARCH(Integer.parseInt(params[0]));
                    for (int i = 1; i < params.length; i++) {
                        int parameterExp = Integer.parseInt("" + params[i].charAt(0));
                        if (parameterExp == 2) {
                            tempItem.Update_Item_Name(params[i].substring(1, params[i].length()));
                        } else if (parameterExp == 3) {
                            tempItem.Update_Item_Category(params[i].substring(1, params[i].length()));
                        } else if (parameterExp == 4) {
                            tempItem.Update_Item_Description(params[i].substring(1, params[i].length()));
                        } else if (parameterExp == 5) {
                            tempItem.Update_Item_Discount(params[i].substring(1, params[i].length() ));
                        } else if (parameterExp == 6) {
                            tempItem.Update_Item_Cost(Integer.parseInt(params[i].substring(1, params[i].length())));
                        } else if (parameterExp == 7) {
                            tempItem.Update_Item_Count(Integer.parseInt(params[i].substring(1, params[i].length())));
                        } else{
                            tempItem.Update_Item_Seller(params[i].substring(1, params[i].length()));
                        }
                    }
                    storage.INSERT(tempItem);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            } else if (operations[0].equals("SEARCH")) {
            
                try {
                    Item Temp = storage.SEARCH(Integer.parseInt(operations[1]));
                    String data = Temp.printData();
                    Logger(operationsLogger, operations, data);
                    System.out.println(data);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            } else {
                System.out.print("Invalid operation");
                operationsReader.nextLine();
            }
        }
        try{
        operationsReader.close();
        operationsLogger.close();
        }catch(Exception e){
            System.out.println("An error occurred while closing the file I/O streams");
        }
    }

    static void Logger(BufferedWriter operationslogger, String[] operations, String search) {
        // Code to get the current time the operation for which we started performing
        // the operation.
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy HH:mm:ss");
        String formattedDateTime = time.format(formatter);
        String operationToLog = formattedDateTime;

        for (int i = 0; i < operations.length; i++) {
            operationToLog += " | " + operations[i];
        }
        operationToLog += " | " + search;
        try {
            operationslogger.write(operationToLog + "\n");
            operationslogger.flush();
        } catch (IOException e) {
            System.out.print("Error writing to the log file");
        }
    }
}
