public class Weapon extends Equipment{
    public static final String ANSI_RESET = "\u001B[0m";
    private int maxdamage;
    private int mindamage;

    public int getDamage() {
        return (int)(Math.random() * (maxdamage - mindamage) + mindamage);
    }

    public Weapon(int space_taken, int cost, int mindamage, int maxdamage, String name) {
        super(space_taken, cost, "Weapon", name);
        this.mindamage = mindamage;
        this.maxdamage = maxdamage;
    }

    public String toString(){
        return(super.toString() + "\tMin Damage: " + this.mindamage + "\tMax Damage: " + this.maxdamage + ANSI_RESET);
    }
}
