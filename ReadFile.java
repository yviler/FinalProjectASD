import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
 
public class ReadFile {
    private FileReader fr;
    private String array[];
    List<String> listOfStrings = new ArrayList<String>();
    public void OpenFile(String name) {
        try {
            fr = new FileReader(name);
        }catch(Exception e) {
            System.out.println("Cant read from file!");
        }
    }

    public void readFile() {
        char ch;
        String s = new String();
        try {
            while(fr.ready()) {
                ch = (char)fr.read();
                   
                // Used to specify the delimiters
                if (ch == '\n' || ch == ',') {
                   
                    listOfStrings.add(s.toString());
                   
                    s = new String();
                }
                else {
                    s += ch;
                }
            }
            array = listOfStrings.toArray(new String[0]);
        }catch(Exception e) {
            System.out.println("Cant read from file!");
        }
    }

    public String[] getFile() {
        return array;
    }
}