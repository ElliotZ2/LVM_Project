import java.util.ArrayList;

public class VolumeGroup extends Storage{
    private ArrayList<PhysicalHardDrive> hardDrives;

    public VolumeGroup(String name) {
        super(name);
        hardDrives = new ArrayList<PhysicalHardDrive>();
    }

    public void addHardDrive(String name, int size) {
        PhysicalHardDrive h = new PhysicalHardDrive(name, size);
        hardDrives.add(h);
    }

    public void listHardDrives() {
        for(PhysicalHardDrive h : hardDrives) {
            System.out.println(h.getName());
        }
    }
}
