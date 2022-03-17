public class PhysicalHardDrive extends Storage {
    private int size;
    private int occupiedSize;

    public PhysicalHardDrive(String name, int size) {
        super(name);
        this.size = size;
        occupiedSize = 0;
    }
}
