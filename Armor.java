public class Armor extends Equipment{
    public static final String ANSI_RESET = "\u001B[0m";
    private double damage_reduction;

    public Armor(int space_taken, int cost, double damage_reduction, String name) {
        super(space_taken, cost, "Armor", name);
        this.damage_reduction = damage_reduction;
    }

    public double getDamage_reduction() {
        return damage_reduction;
    }

    public void setDamage_reduction(double damage_reduction) {
        this.damage_reduction = damage_reduction;
    }

    public String toString(){
        return(super.toString() + "\tDamage Reduction: " + Math.round((1.0 - this.damage_reduction) * 10)/10 + ANSI_RESET);
    }
}
