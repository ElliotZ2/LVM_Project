import java.util.ArrayList;

public class LogicalVolume extends Storage{
    private int size;
    private VolumeGroup volumeGroup;
    public static ArrayList<LogicalVolume> allLogicalVolumes = new ArrayList<LogicalVolume>();

    public LogicalVolume(String name, int size) {
        super(name);
        this.size = size;
    }

    public static void installLogicalVolume(LogicalVolume lv, VolumeGroup vg) {
        allLogicalVolumes.add(lv);
        vg.useStorage(lv.getSize());
    }

    public int getSize() {
        return size;
    }

    public VolumeGroup getVolumeGroup() {
        return volumeGroup;
    }

    public static ArrayList<LogicalVolume> getAllLogicalVolumes() {
        return allLogicalVolumes;
    }

    public static boolean containsLVWithName(String n) {
        for(LogicalVolume lv : allLogicalVolumes) {
            if(lv.getName().equals(n)) {
                return true;
            }
        }
        return false;
    }
}
