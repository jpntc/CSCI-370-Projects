//Main system that takes arguments from the command line.

public class WebReaderApplication {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\judep\\OneDrive\\Documents\\School\\CSCI\\CSCI370\\Project\\Project 2\\input.txt";
        String outputFile = "C:\\Users\\judep\\OneDrive\\Documents\\School\\CSCI\\CSCI370\\Project\\Project 2\\output.txt";
        String saveLocation = "C:\\Users\\judep\\OneDrive\\Documents\\School\\CSCI\\CSCI370\\Project\\Project 2\\Saved_Data";

        // Loop through the arguments and set the input file, output file, and save
        // location.
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i")) {
                i++;
                inputFile = args[i];
            } else if (args[i].equals("-o")) {
                i++;
                outputFile = args[i];

            } else if (args[i].equals("-d")) {
                i++;
                saveLocation = args[i];
            }
        }

        WebReader WebScraper = new WebReader(inputFile, outputFile, saveLocation);
        WebScraper.readUrls();
    }
}
