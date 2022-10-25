public class Weapon extends Equipment{
    public static final String ANSI_RESET = "\u001B[0m";
    private int maxdamage;
    private int mindamage;
    private String inflicts;


    public Weapon(int space_taken, int cost, int mindamage, int maxdamage, String name, String inflicts) {
        super(space_taken, cost, "Weapon", name);
        this.mindamage = mindamage;
        this.maxdamage = maxdamage;
        this.inflicts = inflicts;
    }

    public String getInflicts() {
        return inflicts;
    }

    public void setInflicts(String inflicts) {
        this.inflicts = inflicts;
    }

    public int getDamage() {
        return (int)(Math.random() * (maxdamage - mindamage) + mindamage);
    }

    public int getMaxdamage() {
        return maxdamage;
    }

    public void setMaxdamage(int maxdamage) {
        this.maxdamage = maxdamage;
    }

    public int getMindamage() {
        return mindamage;
    }

    public void setMindamage(int mindamage) {
        this.mindamage = mindamage;
    }

    public String toString(){
        return(super.toString() + "\tMin Damage: " + this.mindamage + "\tMax Damage: " + this.maxdamage + ANSI_RESET);
    }
}
