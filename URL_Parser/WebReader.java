//Class that is responsible for handling all of the operations.

//Used GetURLInfo from the provided class code.
//Packages with the apis used for reading and writing data to and from files.
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

//Packages responsible for getting the information of the file.
import java.net.*;
import java.util.*;

public class WebReader implements Saving {

    public String inputFile;
    public String outputFile;
    public String saveLocation;
    protected DocHandler docReader;
    protected ImageHandler imageHandler;
    protected MarkupHandler markupHandler;
    protected BufferedWriter bufferedWriter;
    protected BufferedReader bufferedReader;

    // Only Constructor which is assuming that all of the files inputted are present
    // and correct.
    WebReader(String inputFile, String outputFile, String saveLocation) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.saveLocation = saveLocation;
        try {
            this.bufferedReader = new BufferedReader(new FileReader(this.inputFile));
        } catch (FileNotFoundException E) {
            System.out.println("Input file not found");
        }
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(this.outputFile));
        } catch (FileNotFoundException E) {
            System.out.println("Output file not found, making a new outputfile");
        } catch (IOException E) {
            System.out.println("IO Exception with the output file");
        }
        if (this.saveLocation == null) {
            System.out.println("No save location provided");
        }

        this.docReader = new DocHandler();
        this.imageHandler = new ImageHandler();
        this.markupHandler = new MarkupHandler();
        readUrls();
    }

    // Method that reads the urls from the input file.

    public void readUrls() {
        String url;
        try {
            while ((url = this.bufferedReader.readLine()) != null) {
                processUrl(url);
            }
        } catch (MalformedURLException e) {
            System.out.println("Error in URL");
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Error in processing the URL");

        }
    }

    // Method that processes the URL and gets the information from it.
    public void processUrl(String link) throws MalformedURLException, IOException {
        try {
            URL url = new URL(link);
            URLConnection uc = url.openConnection();
            ArrayList<String> urlInfo = new ArrayList<String>();
            String contentType = uc.getContentType();
            String[] linkParts = link.split("/");

            String path = this.saveLocation + "\\" + linkParts[linkParts.length - 1];

            urlInfo.add(uc.getURL().toExternalForm() + ":" + "\n");
            urlInfo.add("Content Type: " + uc.getContentType() + "\n");
            urlInfo.add("Content Length: " + uc.getContentLength() + "\n");
            urlInfo.add("Last Modified: " + new Date(uc.getLastModified()) + "\n");
            urlInfo.add("Expiration: " + uc.getExpiration() + "\n");
            urlInfo.add("Content Encoding: " + uc.getContentEncoding() + "\n");

            int numberOfLines = 0;

            if (contentType.contains("text") || contentType.contains("html") || contentType.contains("htm")) {
                try {
                    numberOfLines = this.markupHandler.scrapePage(link, path);
                } catch (Exception e) {
                    System.out.println("Error in processing the URL");
                }
                urlInfo.add("Number of Lines: " + numberOfLines + "\n");

            } else if (contentType.contains("jpg") || contentType.contains("jpeg") || contentType.contains("gif")) {
                try {
                    this.imageHandler.fetchImageFromURL(link, path);
                } catch (Exception e) {
                    System.out.println("Error in processing the URL");
                }
            } else if (contentType.contains("pdf") || contentType.contains("docx")) {
                try {
                    this.docReader.downloadDocument(link, path);
                } catch (Exception e) {
                    System.out.println("Error in processing the URL");
                }
            }

            urlInfo.add("File Size (bytes): " + getFileSize(path) + "\n\n");
            saveOutput(urlInfo);

        } catch (MalformedURLException e) {
            System.out.println("Error in processing the URL:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error in processing the URL:" + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occured when trying to get information from the url:" + e.getMessage());
        }
    }

    // Method that saves the output to the output file.
    public void saveOutput(ArrayList<String> urlInfo) {
        try {
            for (int i = 0; i < urlInfo.size(); i++) {
                try {

                    this.bufferedWriter.write(urlInfo.get(i));
                } catch (IOException e) {
                    System.out.println("Error in writing to the output file");
                } catch (Exception e) {
                    System.out.println("Error in writing to the output file");
                }
            }
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Error in writing to the output file");
        } catch (Exception e) {
            System.out.println("Error in writing to the output file");
        }
    }

    // Credits go to mkyong.com for the code to get the file size.
    public String getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            // size of a file (in bytes)
            long bytes = file.length();
            return "" + bytes + " " + "Bytes";
        }
        return "0";
    }

}
