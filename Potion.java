public class Potion extends Mob{
    int health_gain;

    public Potion(int health_gain, int x, int y) {
        super(100, 0, x, y, "Potion");
        this.health_gain = health_gain;
    }

    public int getHealth_gain() {
        return health_gain;
    }

    public String toString(){
        return("Health Gain: " + this.health_gain);
    }

}
