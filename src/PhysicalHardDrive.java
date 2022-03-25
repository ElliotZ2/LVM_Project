import java.util.ArrayList;

public class PhysicalHardDrive extends Storage {
    private int size;
    private int occupiedSize;
    private static ArrayList<PhysicalHardDrive> allHardDrives = new ArrayList<PhysicalHardDrive>();

    public PhysicalHardDrive(String name, int size) {
        super(name);
        this.size = size;
        occupiedSize = 0;
    }

    public static ArrayList<PhysicalHardDrive> getAllHardDrives() {
        return allHardDrives;
    }

    public int getSize() {
        return size;
    }

    public static void installHardDrive(PhysicalHardDrive hd) {
        boolean sameName = false;
        for(PhysicalHardDrive h : allHardDrives) {
            if(h.getName().equals(hd.getName())) {
                sameName = true;
                break;
            }
        }
        if(sameName == false) {
            allHardDrives.add(hd);
        }
        else{
            System.out.println("A hard drive named " + hd.getName() + " already exists.");
        }
    }

    public static void listHardDrives() {
        for(PhysicalHardDrive hd : allHardDrives) {
            System.out.println(hd.getName() + " [" + hd.getSize() + "G]");
        }
    }
}
