public class Shoes extends Equipment{
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
        return(super.toString() + "\tSpeed: " + this.speed + Constants.ANSI_RESET);
    }

    public String saveText(){
        return (this.getName() + "  " +
                this.getSpeed() + "  " +
                this.getSpace_taken() + "  ");
    }
}
