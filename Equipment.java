public abstract class Equipment {
    private int space_taken;
    private int cost;
    private String type;
    private String name;

    public Equipment(int space_taken, int cost, String type, String name) {
        this.space_taken = space_taken;
        this.cost = cost;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public int getSpace_taken() {
        return space_taken;
    }

    public void setSpace_taken(int space_taken) {
        this.space_taken = space_taken;
    }

    public String toString(){
        return ("'" + this.name + "', " + "cost: " + this.cost + "\tspace: " + this.space_taken);
    }
}
