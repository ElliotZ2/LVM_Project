import java.util.ArrayList;

public class PhysicalVolume extends Storage{
    private PhysicalHardDrive hardDrive;
    private VolumeGroup associatedVolumeGroup;
    private static ArrayList<PhysicalVolume> allPhysicalVolumes = new ArrayList<PhysicalVolume>();

    public PhysicalVolume(String name, PhysicalHardDrive hardDrive) {
        super(name);
        this.hardDrive = hardDrive;
    }

    public static ArrayList<PhysicalVolume> getAllPhysicalVolumes() {
        return allPhysicalVolumes;
    }

    public static void installPhysicalVolume(PhysicalVolume pv, PhysicalHardDrive hd) {
        boolean shouldInstall = true;
        if (hd.isAssociatedWithPV()) {
            shouldInstall = false;
            System.out.println(hd.getName() + " is already associated with another Physical Volume");
        }
        for(PhysicalVolume x : allPhysicalVolumes) {
            if(x.getName().equals(pv.getName())) {
                shouldInstall = false;
                System.out.println("A physical volume named " + x.getName() + " already exists.");
            }
        }
        if(shouldInstall) {
            allPhysicalVolumes.add(pv);
            hd.associateWithPV();
            System.out.println(pv.getName() + " created");
        }
    }

    public void associateWithVG(VolumeGroup vg) {
        if(!isPartOfVolumeGroup()) {
            associatedVolumeGroup = vg;
        }
        else{
            System.out.println("Physical volume " + getName() + " is already associated with volume group " + associatedVolumeGroup.getName());
        }
    }

    public VolumeGroup getAssociatedVolumeGroup() {
        return associatedVolumeGroup;
    }

    public boolean isPartOfVolumeGroup() {
        if(associatedVolumeGroup == null) {
            return false;
        }
        else{
            return true;
        }
    }

    public PhysicalHardDrive getHardDrive() {
        return hardDrive;
    }

    public int getSize() {
        return hardDrive.getSize();
    }
}
