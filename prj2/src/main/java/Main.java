import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        List<String> pathList = new ArrayList<String>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains(":/") || args[i].contains(":\\")) {
                pathList.add(args[i]);
            } else {
                System.out.println("You have enter incorrect path");
            }
        }
        for (String path: pathList){
            fileManager.findFiles(path, fileManager.EXTENSION);
            try {
//                fileManager.printCatalog();
                fileManager.searchDublicants();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
