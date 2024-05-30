import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

public class test {

    protected URL url = null;
    protected File outputImageFile = null;
    protected BufferedImage image = null;

    test() {
    }

    public void fetchImageFromURL(String url, String saveLocation) {
        try {
            this.url = new URL(url);
            this.outputImageFile = new File(saveLocation);
            this.image = ImageIO.read(this.url);
            ImageIO.write(this.image, "jpg", this.outputImageFile);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException error when fetching the image from the URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException error when fetching the image from the URL: " + e);
        }
    }

}
