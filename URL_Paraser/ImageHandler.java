//Class that is used to get and save the image from a url.
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.URL;

class ImageHandler {
   public void fetchImageFromURL(String url, String saveLocation) throws IOException {
      //Instantiate a url object with the url passed to the method.
      URL imageUrl = new URL(url);
      //Instantiating a BufferedImage object and calling the read method to get and assign the image from the url.
      BufferedImage image = ImageIO.read(imageUrl);
      if (image != null) {
         File outputFile = new File(saveLocation);
         ImageIO.write(image, "jpg", outputFile); // Writing the image to the specified path which is passed to the method.
      } else {
         throw new IOException("Failed to download image from URL: " + url);
      }
   }
}
