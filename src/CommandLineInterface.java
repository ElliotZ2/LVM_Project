import java.io.*;
import java.util.Scanner;

public class CommandLineInterface {

    public void start() {
        load();
        System.out.println("Welcome to the Logical Volume Manager System! Enter your commands:");
        for(int i = 0; i < 10; i++) {
            System.out.println();
        }
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input.toLowerCase().equals("exit") == false) {
            input = scanner.nextLine();
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
                        System.out.println("Volume group " + vgName + " has been extended to include " + pvName + ".");
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
                        System.out.println("A volume group with the name " + vgName + " doesn't exist.");
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
                            if(lv.getVolumeGroup().getName().equals(vg.getName())) {
                                System.out.println(lv.getName() + ": [" + lv.getSize() + "G] [" + lv.getUuid() + "]");
                            }
                        }
                    }
                }
            }
        save();
        System.out.println("Saving data.");
    }

    public void save() {
        int items = PhysicalHardDrive.getAllHardDrives().size() + PhysicalVolume.getAllPhysicalVolumes().size() + VolumeGroup.getAllVolumeGroups().size() + LogicalVolume.getAllLogicalVolumes().size();
        try{
            ObjectOutputStream output1 = new ObjectOutputStream(new FileOutputStream("hdsave.dat"));
            output1.writeObject(PhysicalHardDrive.getAllHardDrives().size());
            for(PhysicalHardDrive hd : PhysicalHardDrive.getAllHardDrives()) {
                output1.writeObject(hd);
            }
            ObjectOutputStream output2 = new ObjectOutputStream(new FileOutputStream("pvsave.dat"));
            output2.writeObject(PhysicalVolume.getAllPhysicalVolumes().size());
            for(PhysicalVolume pv : PhysicalVolume.getAllPhysicalVolumes()) {
                output2.writeObject(pv);
            }
            ObjectOutputStream output3 = new ObjectOutputStream(new FileOutputStream("vgsave.dat"));
            output3.writeObject(VolumeGroup.getAllVolumeGroups().size());
            for(VolumeGroup vg : VolumeGroup.getAllVolumeGroups()) {
                output3.writeObject(vg);
            }
            ObjectOutputStream output4 = new ObjectOutputStream(new FileOutputStream("lvsave.dat"));
            output4.writeObject(LogicalVolume.getAllLogicalVolumes().size());
            for(LogicalVolume lv : LogicalVolume.getAllLogicalVolumes()) {
                output4.writeObject(lv);
            }
            output1.close();
            output2.close();
            output3.close();
            output4.close();
        }
        catch(IOException ioe) {
            System.out.println("Error saving data.");
        }
    }

    /*
    public void load() {
        try{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("save.dat"));
            int items = (int) input.readObject();
            for(int i = 0; i < items; i++) {
                System.out.println(i);
                Object o = input.readObject();
                System.out.println(o instanceof PhysicalHardDrive);
                if(o instanceof PhysicalHardDrive) {
                    PhysicalHardDrive.installHardDrive((PhysicalHardDrive) o);
                }
                else if(o instanceof PhysicalVolume) {
                    PhysicalVolume.installPhysicalVolume((PhysicalVolume) o);
                }
                else if(o instanceof VolumeGroup) {
                    VolumeGroup.installVolumeGroup((VolumeGroup) o);
                }
                else if(o instanceof LogicalVolume) {
                    LogicalVolume.installLogicalVolume((LogicalVolume) o);
                }
            }
        }
        catch(IOException ioe) {
            System.out.println("Error loading data.");
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("another problem");
        }
        for(int i = 0; i < 20; i++) {
            System.out.println();
        }
    }*/
    public void load() {
        try{
            ObjectInputStream input1 = new ObjectInputStream(new FileInputStream("hdsave.dat"));
            int hdCount = (int) input1.readObject();
            for(int i = 0; i < hdCount; i++) {
                PhysicalHardDrive.installHardDrive((PhysicalHardDrive) input1.readObject());
            }

            ObjectInputStream input2 = new ObjectInputStream(new FileInputStream("pvsave.dat"));
            int pvCount = (int) input2.readObject();
            for(int i = 0; i < pvCount; i++) {
                PhysicalVolume.installPhysicalVolume((PhysicalVolume) input2.readObject());
            }

            ObjectInputStream input3 = new ObjectInputStream(new FileInputStream("vgsave.dat"));
            int vgCount = (int) input3.readObject();
            for(int i = 0; i < vgCount; i++) {
                VolumeGroup.installVolumeGroup((VolumeGroup) input3.readObject());
            }

            ObjectInputStream input4 = new ObjectInputStream(new FileInputStream("lvsave.dat"));
            int lvCount = (int) input4.readObject();
            for(int i = 0; i < lvCount; i++) {
                LogicalVolume.installLogicalVolume((LogicalVolume) input4.readObject());
            }
        }
        catch(IOException ioe) {
            System.out.println("Error loading data.");
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("another problem");
        }
        for(int i = 0; i < 20; i++) {
            System.out.println();
        }
    }


}
