import java.util.ArrayList;

public class VolumeGroup extends Storage{
    private static ArrayList<VolumeGroup> allVolumeGroups = new ArrayList<VolumeGroup>();
    private ArrayList<PhysicalVolume> physicalVolumes;
    private int occupiedStorage;

    public VolumeGroup(String name) {
        super(name);
        physicalVolumes = new ArrayList<PhysicalVolume>();
        occupiedStorage = 0;
    }

    public static ArrayList<VolumeGroup> getAllVolumeGroups() {
        return allVolumeGroups;
    }

    public static boolean containsVGWithName(String n) {
        for(VolumeGroup vg : allVolumeGroups) {
            if(vg.getName().equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static void installVolumeGroup(VolumeGroup vg, PhysicalVolume pv) {
        allVolumeGroups.add(vg);
        vg.extend(pv);
        pv.associateWithVG(vg);
        System.out.println(vg.getName() + " created");
    }

    public void extend(PhysicalVolume pv) {
        physicalVolumes.add(pv);
    }

    public int getTotalSize() {
        int totalSize = 0;
        for(PhysicalVolume pv : physicalVolumes) {
            totalSize += pv.getSize();
        }
        return totalSize;
    }

    public int getAvailableStorage() {
        return getTotalSize() - occupiedStorage;
    }

    public void useStorage(int storageToUse) {
        if(getAvailableStorage() < storageToUse) {
            System.out.println(storageToUse + "G of storage cannot be used in volume group " + getName() + " when there is only " + getAvailableStorage() + "G of space available.");
        }
        else{
            occupiedStorage += storageToUse;
        }
    }

    public static VolumeGroup getVGWithName(String name) {
        for(VolumeGroup vg : allVolumeGroups) {
            if(vg.getName().equals(name)) {
                return vg;
            }
        }
        return null;
    }

    public ArrayList<PhysicalVolume> getPhysicalVolumes() {
        return physicalVolumes;
    }

    public static void listVG() {
        for(VolumeGroup vg : allVolumeGroups) {
            String str = vg.getName() + ": ";
            str += "total:[" + vg.getTotalSize() + "G] ";
            str += "available:[" + vg.getAvailableStorage() + "G] [";
            str += vg.getPhysicalVolumes().get(0).getName();
            for(int i = 1; i < vg.physicalVolumes.size(); i++) {
                str += "," + vg.physicalVolumes.get(i).getName();
            }
            str += "] [" + vg.getUuid() + "]";
            System.out.println(str);
        }
    }
}
