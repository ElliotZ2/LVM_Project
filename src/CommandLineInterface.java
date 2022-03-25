import java.util.Scanner;

public class CommandLineInterface {

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input.toLowerCase().equals("exit") == false) {
            input = scanner.nextLine();
            if(input.contains("install-drive")) {
                String driveName = input.substring(input.indexOf(" "), input.indexOf(" ", input.indexOf(" ") + 1));
                String size = input.substring(input.indexOf(" ", input.indexOf(" ") + 1));
                int s = Integer.parseInt(size.substring(1, size.indexOf("G")));
                PhysicalHardDrive.installHardDrive(new PhysicalHardDrive(driveName, s));
            }
            else if(input.contains("list-drives")) {
                PhysicalHardDrive.listHardDrives();
            }
        }
    }
}
