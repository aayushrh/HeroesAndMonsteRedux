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

    public String saveText(){
        String s = (super.getHealth() + "  " +
                this.money + "  " +
                this.getSword().saveText() +
                this.getArmor().saveText());

        if(this.getShield() != null){
                s += this.getShield().saveText();
        }
        else{
                s += ("NONE" + "  " +
                    "NONE" + "  " +
                    "NONE" + "  ");
        }

        if(this.getShoes() != null){
                s += this.getShoes().saveText();
        }
        else{
                s += ("NONE" + "  " +
                "NONE" + "  " +
                "NONE" + "  ");
        }

        s += (super.getHolding_space() + "  " +
        this.monstersdefeated + "  " +
        this.getX() + "  " +
        this.getY() + "  ");

        if(potionList.size() > 0) {
            for (Potion i : potionList) {
                s += (i.getHealth_gain() + "  " +
                      i.getX() + "  " +
                      i.getY() + "  ");
            }
        }
        return s;
    }

    public int importSave(String save){
        int place = 0;
        String a[] = save.split("  ");
        super.setHolding_space(Integer.parseInt(a[16]));
        this.money = Integer.parseInt(a[1]);
        super.setX(Integer.parseInt(a[18]));
        super.setY(Integer.parseInt(a[19]));
        super.setHealth(Integer.parseInt(a[0]));
        super.equip(new Weapon(Integer.parseInt(a[6]), 0, Integer.parseInt(a[3]), Integer.parseInt(a[4]), a[2], a[5]));
        super.equip(new Armor(Integer.parseInt(a[9]), 0, Double.parseDouble(a[8]), a[7]));
        if(!a[10].equals("NONE")){
            super.equip(new Shield(Integer.parseInt(a[12]), 0, Integer.parseInt(a[11]), a[10]));
        }
        if(!a[13].equals("NONE")){
            super.equip(new Shield(Integer.parseInt(a[15]), 0, Integer.parseInt(a[14]), a[13]));
        }
        this.monstersdefeated = Integer.parseInt(a[17]);
        place = 20;
        for (int i = 20; i < a.length; i += 3) {
            if(a[i].charAt(0) - 48 < 9) {
                Potion p = new Potion(Integer.parseInt(a[i]), Integer.parseInt(a[i + 1]), Integer.parseInt(a[i + 2]));
                potionList.add(p);
                place = i + 3;
            }else{
                break;
            }
        }
        return place;
    }

}
