

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;

public class MarkupHandler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6";

    public MarkupHandler() {
    }

    // Methods that create the objects necessary to process the text on the webpage.
    public static InputStream getURLInputStream(String sURL) throws Exception {
        URLConnection oConnection = (new URL(sURL)).openConnection();
        oConnection.setRequestProperty("User-Agent", USER_AGENT);
        return oConnection.getInputStream();

    }

    public static BufferedReader read(String url) throws Exception {
        InputStream content = (InputStream) getURLInputStream(url);
        return new BufferedReader(new InputStreamReader(content));
    }

    // Main method that creates the reader and writer objects, and writes to a new file all the text scraped from the webpage (line by line).
    public int scrapePage(String url, String file) throws Exception {
        int numberOfLines = 0;
        try {
            BufferedReader reader = read(url);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
                line = reader.readLine();
                numberOfLines++;
            }
            writer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("An error occurred when scraping the page: " + e);
        }
        return numberOfLines;
    }}

