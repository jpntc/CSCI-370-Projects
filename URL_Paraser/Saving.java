//Interface to define the methods that a class needs to write to the output file. 
import java.util.ArrayList; 
interface Saving {
    void saveOutput(ArrayList<String> urlInfo);
    String getFileSize(String path);
}