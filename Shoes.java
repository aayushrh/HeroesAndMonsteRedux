public class Shoes extends Equipment{
    public static final String ANSI_RESET = "\u001B[0m";
    private int speed;

    public Shoes(int space_taken, int cost, int speed, String name) {
        super(space_taken, cost, "Shoes", name);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String toString(){
        return(super.toString() + "\tSpeed: " + this.speed + ANSI_RESET);
    }
}
