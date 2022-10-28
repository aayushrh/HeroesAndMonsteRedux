public class Armor extends Equipment{
    private double damage_reduction;
    private String effect;
    private int manaRegen;

    public Armor(int space_taken, int cost, double damage_reduction, String name, String effect, int manaRegen) {
        super(space_taken, cost, "Armor", name);
        this.damage_reduction = damage_reduction;
        this.effect = effect;
        this.manaRegen = manaRegen;
    }

    public double getDamage_reduction() {
        return damage_reduction;
    }

    public void setDamage_reduction(double damage_reduction) {
        this.damage_reduction = damage_reduction;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String toString(){
        return(super.toString() + "\tDamage Reduction: " + (Math.round((1.0 - this.damage_reduction) * 10)/10.0) +
                "\tMana regen: " + this.manaRegen + Constants.ANSI_RESET);
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public String saveText(){
        return(this.getName() + "  " +
                this.getDamage_reduction() + "  " +
                this.getSpace_taken() + "  " +
                this.effect + "  " +
                this.manaRegen + "  ");
    }
}
