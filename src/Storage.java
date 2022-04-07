import java.io.Serializable;
import java.util.UUID;

public class Storage implements Serializable {
    private String name;
    private String uuid;

    public Storage(String name) {
        this.name = name;
        uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
