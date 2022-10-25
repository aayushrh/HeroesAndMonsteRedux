import java.util.List;
import java.util.ArrayList;

public class Hero extends Mob{
    private int money;
    private List<Potion> potionList;
    private int monstersdefeated;
    public Hero(int money, int health, int holding_space, int x, int y) {
        super(holding_space, health, x, y, "Hero");
        this.money = money;
        this.potionList = new ArrayList<Potion>();
        this.monstersdefeated = 0;
    }

    public int getMonstersdefeated() {
        return monstersdefeated;
    }

    public void setMonstersdefeated(int monstersdefeated) {
        this.monstersdefeated = monstersdefeated;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addPotion(Potion potion){
        potionList.add(potion);
    }

    public void move(int x_speed, int y_speed){
        super.setX(super.getX() + x_speed);
        super.setY(super.getY() + y_speed);
    }

    public void potionUse(){
        if(potionList.size() > 0) {
            super.gainHealth(potionList.get(0).getHealth_gain());
            System.out.println("used potion and healed " + potionList.get(0).getHealth_gain() + " hp");
            potionList.remove(0);
        }else{
            System.out.println("you have no potions");
        }
    }

    public String toString(){
        String s = ("\nHealth: " + super.getHealth() + "\n" +
               "Money: " + this.money + "\n" +
               "Weapon: " + this.getSword() + "\n" +
               "Armor: " + this.getArmor() + "\n" +
               "Shield: " + this.getShield() + "\n" +
               "Shoes: " + this.getShoes() + "\n" +
               "Holding Space: " + super.getHolding_space() + "\n" +
               "Monsters Defeated: " + this.monstersdefeated + "\n" +
               "Potions: " );
        if(potionList.size() > 0) {
            for (Potion i : potionList) {
                s += (i + "\n");
            }
        }else{
            s += "You have no Potions\n";
        }
        return s;
    }
}
