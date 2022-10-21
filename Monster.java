public class Monster extends Mob{
    int money_drop;
    public Monster(int health, int x, int y, int money_drop, char type) {
        super(999, health, x, y, type + "Monster");
        this.money_drop = money_drop;
    }

    public int getMoney_drop() {
        return money_drop;
    }

    public String toString(){
        return("\nHealth: " + super.getHealth() + "\n" +
                "Weapon: " + this.getSword() + "\n" +
                "Armor: " + this.getArmor() + "\n" +
                "Shield: " + this.getShield() + "\n" +
                "Shoes: " + this.getShoes() + "\n");
    }
}
