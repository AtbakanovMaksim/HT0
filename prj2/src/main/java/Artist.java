import java.util.HashSet;
import java.util.Set;

public class Artist {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    Set<String> albumSet = new HashSet<String>();

    public Artist(String name) {
        this.name = name;
    }


}
