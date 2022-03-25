import java.util.UUID;

public class Storage {
    private String name;
    private String uuid;

    public Storage(String name) {
        this.name = name;
        uuid = UUID.randomUUID().toString();
    }

    public void test() {
        System.out.println("yo");
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
    /*
    public boolean fill(int gigabytes) {
        if(gigabytes > getAvailableSpace()) {
            return false;
        }
        else{
            occupiedStorage += gigabytes;
            return true;
        }
    }

    public int getAvailableSpace() {
        return size - occupiedStorage;
    }*/
}
