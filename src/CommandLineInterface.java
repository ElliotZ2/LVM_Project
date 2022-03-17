import java.util.Scanner;

public class CommandLineInterface {

    public static void start(VolumeGroup vg) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input.toLowerCase().equals("exit") == false) {
            input = scanner.nextLine();
            if(input.contains("install-drive")) {
                String driveName = input.substring(input.indexOf(" "), input.indexOf(" ", input.indexOf(" ") + 1));
                String size = input.substring(input.indexOf(" ", input.indexOf(" ") + 1));
                int s = Integer.parseInt(size.substring(0, size.indexOf("G")));
                vg.addHardDrive(driveName, s);
            }
            else if(input.contains("list-drives")) {
                vg.listHardDrives();
            }
        }
    }
}
