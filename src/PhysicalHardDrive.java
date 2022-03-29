import java.util.ArrayList;

public class PhysicalHardDrive {
    private String name;
    private int size;
    private static ArrayList<PhysicalHardDrive> allHardDrives = new ArrayList<PhysicalHardDrive>();
    private boolean associatedWithPV;

    public PhysicalHardDrive(String name, int size) {
        this.name = name;
        this.size = size;
        associatedWithPV = false;
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
            System.out.println("Drive " + hd.getName() + " installed");
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

    public void associateWithPV() {
        associatedWithPV = true;
    }

    public boolean isAssociatedWithPV() {
        return associatedWithPV;
    }

    public String getName() {
        return name;
    }
}
