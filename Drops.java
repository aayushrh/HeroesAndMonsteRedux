public class Drops extends Mob{
    Equipment equipment_dropped;

    public Drops(int x, int y, Equipment equipment_dropped) {
        super(0, 0, x, y, "Items");
        this.equipment_dropped = equipment_dropped;
    }

    public Equipment getEquipment_dropped() {
        return equipment_dropped;
    }
}
