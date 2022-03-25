import java.util.ArrayList;

public class PhysicalVolume{
    private PhysicalHardDrive hardDrive;
    private static ArrayList<PhysicalVolume> allPhysicalVolumes = new ArrayList<PhysicalVolume>();

    public PhysicalVolume(PhysicalHardDrive hardDrive) {
        this.hardDrive = hardDrive;
    }

    public PhysicalHardDrive getHardDrive() {
        return hardDrive;
    }

    public int getSize() {
        return hardDrive.getSize();
    }
}
