import java.util.Scanner;

public class CommandLineInterface {

    public static void start() {
        System.out.println("Welcome to the Logical Volume Manager System! Enter your commands:");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input.toLowerCase().equals("exit") == false) {
            input = scanner.nextLine();
            if(input.length() >= 5 && input.substring(0, 5).equals("cmd# ")) {
                input = input.substring(5);
                if(input.contains("install-drive")) {
                    String driveName = input.substring(input.indexOf(" ") + 1, input.indexOf(" ", input.indexOf(" ") + 1));
                    String size = input.substring(input.indexOf(" ", input.indexOf(" ") + 1));
                    int s = Integer.parseInt(size.substring(1, size.indexOf("G")));
                    PhysicalHardDrive.installHardDrive(new PhysicalHardDrive(driveName, s));
                }
                else if(input.contains("list-drives")) {
                    PhysicalHardDrive.listHardDrives();
                }
                else if(input.contains("pvcreate")) {
                    String pvName = input.substring(input.indexOf(" ") + 1, input.indexOf(" ", input.indexOf(" ") + 1));
                    String hdName = input.substring(input.lastIndexOf(" ") + 1);
                    PhysicalHardDrive hd = null;
                    for(PhysicalHardDrive h : PhysicalHardDrive.getAllHardDrives()) {
                        if(h.getName().equals(hdName)) {
                            hd = h;
                        }
                    }
                    if(hd == null) {
                        System.out.println("A hard drive named " + hdName + " doesn't exist.");
                    }
                    else{
                        PhysicalVolume.installPhysicalVolume(new PhysicalVolume(pvName, hd), hd);
                    }
                }
                else if(input.contains("pvlist")) {
                    for(VolumeGroup vg : VolumeGroup.getAllVolumeGroups()) {
                        for(PhysicalVolume pv : vg.getPhysicalVolumes()) {
                            System.out.println(pv.getName() + ":[" + pv.getSize() + "G] [" + vg.getName() + "] [" + pv.getUuid() + "]");
                        }
                    }
                    for(PhysicalVolume pv : PhysicalVolume.getAllPhysicalVolumes()) {
                        if(!pv.isPartOfVolumeGroup()) {
                            System.out.println(pv.getName() + ":[" + pv.getSize() + "G] [" + pv.getUuid() + "]");
                        }
                    }
                }
                else if(input.contains("vgcreate")) {
                    String vgName = input.substring(input.indexOf(" ") + 1, input.indexOf(" ", input.indexOf(" ") + 1));
                    String pvName = input.substring(input.lastIndexOf(" ") + 1);
                    PhysicalVolume pv = null;
                    for(PhysicalVolume pv2 : PhysicalVolume.getAllPhysicalVolumes()) {
                        if(pv2.getName().equals(pvName)) {
                            pv = pv2;
                        }
                    }
                    if(VolumeGroup.containsVGWithName(vgName)) {
                        System.out.println("A volume group with the name " + vgName + " already exists.");
                    }
                    else if(pv == null) {
                        System.out.println("A physical volume with the name " + pvName + " does not exist.");
                    }
                    else if(pv.isPartOfVolumeGroup()) {
                        System.out.println("Physical volume " + pv.getName() + " is already associated with volume group " + pv.getAssociatedVolumeGroup().getName() + ".");
                    }
                    else{
                        VolumeGroup.installVolumeGroup(new VolumeGroup(vgName), pv);
                    }
                }
                else if(input.contains("vgextend")) {
                    String vgName = input.substring(input.indexOf(" ") + 1, input.indexOf(" ", input.indexOf(" ") + 1));
                    String pvName = input.substring(input.lastIndexOf(" ") + 1);
                    PhysicalVolume pv = null;
                    for(PhysicalVolume pv2 : PhysicalVolume.getAllPhysicalVolumes()) {
                        if(pv2.getName().equals(pvName)) {
                            pv = pv2;
                        }
                    }
                    if(!VolumeGroup.containsVGWithName(vgName)) {
                        System.out.println("A volume group with the name " + vgName + " doesn't exist.");
                    }
                    else if(pv == null) {
                        System.out.println("A physical volume with the name " + pvName + " does not exist.");
                    }
                    else if(pv.isPartOfVolumeGroup()) {
                        System.out.println("Physical volume " + pv.getName() + " is already associated with volume group " + pv.getAssociatedVolumeGroup().getName() + ".");
                    }
                    else{
                        VolumeGroup.getVGWithName(vgName).extend(pv);
                    }
                }
                else if(input.contains("vglist")) {
                    VolumeGroup.listVG();
                }
                else if(input.contains("lvcreate")) {
                    String lvName = input.substring(input.indexOf(" ") + 1, input.indexOf(" ", input.indexOf(" ") + 1));
                    String vgName = input.substring(input.lastIndexOf(" ") + 1);
                    input = input.substring(0, input.lastIndexOf(" "));
                    String s = input.substring(input.lastIndexOf(" ") + 1, input.lastIndexOf("G"));
                    int size = Integer.parseInt(s);
                    if(LogicalVolume.containsLVWithName(lvName)) {
                        System.out.println("A logical volume with the name " + lvName + " already exists.");
                    }
                    else if(!VolumeGroup.containsVGWithName(vgName)) {
                        System.out.println("A volume group with the name + " + vgName + " doesn't exist.");
                    }
                    else if(VolumeGroup.getVGWithName(vgName).getAvailableStorage() < size) {
                        System.out.println("Volume group " + vgName + " cannot allocate " + size + "G of space when it only has " + VolumeGroup.getVGWithName(vgName).getAvailableStorage() + "G available.");
                    }
                    else{
                        LogicalVolume.installLogicalVolume(new LogicalVolume(lvName, size), VolumeGroup.getVGWithName(vgName));
                        System.out.println("Logical volume " + lvName + " has been created inside the volume group " + vgName + ".");
                    }
                }
                else if(input.contains("lvlist")) {
                    for(VolumeGroup vg : VolumeGroup.getAllVolumeGroups()) {
                        for(LogicalVolume lv : LogicalVolume.getAllLogicalVolumes()) {
                            if(lv.getVolumeGroup().equals(vg)) {
                                System.out.println(lv.getName() + ": [" + lv.getSize() + "] [" + lv.getUuid() + "]");
                            }
                        }
                    }
                }
            }
        }
    }

    public void save() {

    }

    public void load() {

    }
}
