public class TownsPerson extends Mob{
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String ANSI_RESET = "\u001B[0m";
    private Weapon weaponlist[] = {
            new Weapon(1, 10, 10, 20, (RED_BOLD_BRIGHT + "Short Dagger")),
            new Weapon(2, 20, 20, 30, (YELLOW_BOLD_BRIGHT + "Longsword")),
            new Weapon(4, 30, 30, 40, (YELLOW_BOLD_BRIGHT + "Longersword")),
            new Weapon(4, 40, 40, 50, (GREEN_BOLD_BRIGHT + "Greatsword")),
    };
    private Weapon weaponlist2[] = {
            new Weapon(1, 70, 50, 60, (RED_BOLD_BRIGHT + "Bronze Short Dagger")),
            new Weapon(2, 80, 70, 80, (YELLOW_BOLD_BRIGHT + "Iron Longsword")),
            new Weapon(4, 90, 90, 100, (YELLOW_BOLD_BRIGHT + "Ruby Longersword")),
            new Weapon(4, 100, 110, 120, (GREEN_BOLD_BRIGHT + "Platinum Greatsword")),
    };
    private Armor armorlist[] = {
            new Armor(4, 40, 7.0/8.0, (RED_BOLD_BRIGHT + "Copper Armor")),
            new Armor(5, 50, 6.0/8.0, (YELLOW_BOLD_BRIGHT + "Iron Armor")),
            new Armor(7, 60, 1.0/2.0, (YELLOW_BOLD_BRIGHT + "Diamond Armor")),
            new Armor(7, 70, 3.0/8.0, (GREEN_BOLD_BRIGHT + "Platinum Armor")),
    };
    private Shoes shoelist[] = {
            new Shoes(2, 10, 2, (RED_BOLD_BRIGHT + "Sandals")),
            new Shoes(3, 20, 3, (YELLOW_BOLD_BRIGHT + "Tennis shoes")),
            new Shoes(2, 30, 3, (YELLOW_BOLD_BRIGHT + "Running shoes")),
            new Shoes(3, 40, 4, (GREEN_BOLD_BRIGHT + "Soccer shoes")),
    };
    private Shoes shoelist2[] = {
            new Shoes(2, 60, 6, (RED_BOLD_BRIGHT + "Bouncy Sandals")),
            new Shoes(3, 70, 7, (YELLOW_BOLD_BRIGHT + "Bouncy Tennis shoes")),
            new Shoes(2, 80, 8, (YELLOW_BOLD_BRIGHT + "Bouncy Running shoes")),
            new Shoes(3, 90, 8, (GREEN_BOLD_BRIGHT + "Speedy Soccer shoes")),
    };
    private Shield shieldlist[] = {
            new Shield(3, 20, 5, (RED_BOLD_BRIGHT + "Wooden shield")),
            new Shield(4, 30, 10, (YELLOW_BOLD_BRIGHT + "Reinforced shield")),
            new Shield(4, 40, 15, (YELLOW_BOLD_BRIGHT + "Iron Shield")),
            new Shield(4, 50, 20, (GREEN_BOLD_BRIGHT + "Diamond Shield")),
    };
    private Shield shieldlist2[] = {
            new Shield(3, 80, 40, (RED_BOLD_BRIGHT + "Reinforced Wooden shield")),
            new Shield(4, 95, 50, (YELLOW_BOLD_BRIGHT + "Double Reinforced shield")),
            new Shield(4, 110, 60, (YELLOW_BOLD_BRIGHT + "Reinforced Iron Shield")),
            new Shield(4, 125, 70, (GREEN_BOLD_BRIGHT + "Reinforced Diamond Shield")),
    };

    public TownsPerson(int holding_space, int x, int y) {
        super(holding_space, 999, x, y, "TownsPerson");
    }

    public String toString(){
        return("Today I am selling:\n" +
        "Weapon: " + super.getSword() + ANSI_RESET +
        "\nArmor: " + super.getArmor() + ANSI_RESET +
        "\nShield: " + super.getShield() + ANSI_RESET +
        "\nShoes: " + super.getShoes() + ANSI_RESET);
    }

    public void restock(int numb){
        if(numb == 1) {
            int num = (int) (Math.random() * 4);
            super.equip(weaponlist[num]);
            num = (int) (Math.random() * 4);
            super.equip(armorlist[num]);
            num = (int) (Math.random() * 4);
            super.equip(shieldlist[num]);
            num = (int) (Math.random() * 4);
            super.equip(shoelist[num]);
        }else{
            int num = (int) (Math.random() * 4);
            super.equip(weaponlist2[num]);
            num = (int) (Math.random() * 4);
            super.equip(armorlist[num]);
            num = (int) (Math.random() * 4);
            super.equip(shieldlist2[num]);
            num = (int) (Math.random() * 4);
            super.equip(shoelist2[num]);
        }
    }

}
