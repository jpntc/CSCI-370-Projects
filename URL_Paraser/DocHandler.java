// Class that is responsible for handling all of the operations for docx and pdf files.
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocHandler {

    // Method to establish a connection to the pdf / docx link, and copy all the information from the file byte by byte.
    public void downloadDocument(String url, String saveLocation) throws IOException {
        // Make a url object with the url passed to the method.
        URL documentUrl = new URL(url);
        // Establish a connection to the url and specify the request type.
        HttpURLConnection connection = (HttpURLConnection) documentUrl.openConnection();
        connection.setRequestMethod("GET");
        
        //Condition to check the response code sent back, and if it is ok, proceed to copy the information from the pdf/docx file
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream();
                 BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(saveLocation))) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            throw new IOException("Failed to download document. HTTP response code: " + responseCode);
        }
    }

}
