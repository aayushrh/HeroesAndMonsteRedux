public class TownsPerson extends Mob{
    private Weapon weaponlist[] = {
            new Weapon(1, 10, 10, 20, ("Flaming " + Constants.RED_BOLD_BRIGHT +  "Short Dagger"), "Burn"),
            new Weapon(2, 20, 20, 30, (Constants.YELLOW_BOLD_BRIGHT + "Longsword"), "None"),
            new Weapon(4, 30, 30, 40, ("Stronger " + Constants.YELLOW_BOLD_BRIGHT +  "Longersword"), "Armor Down"),
            new Weapon(4, 40, 40, 50, (Constants.GREEN_BOLD_BRIGHT + "Greatsword"), "None"),
    };
    private Weapon weaponlist2[] = {
            new Weapon(1, 70, 50, 60, (Constants.RED_BOLD_BRIGHT + "Bronze Short Dagger"), "None"),
            new Weapon(2, 80, 70, 80, ("Flaming " + Constants.YELLOW_BOLD_BRIGHT +  "Iron Longsword"), "Burn"),
            new Weapon(4, 90, 90, 100, (Constants.YELLOW_BOLD_BRIGHT + "Weakening Ruby Longersword"), "Attack Down"),
            new Weapon(4, 100, 110, 120, ("Stronger " + Constants.GREEN_BOLD_BRIGHT +  "Platinum Greatsword"), "Armor Down"),
    };
    private Armor armorlist[] = {
            new Armor(4, 40, 7.0/8.0, (Constants.RED_BOLD_BRIGHT + "Copper Armor"), "None", 0),
            new Armor(5, 50, 6.0/8.0, (Constants.YELLOW_BOLD_BRIGHT + "Iron Armor"), "None", 10),
            new Armor(7, 60, 1.0/2.0, (Constants.YELLOW_BOLD_BRIGHT + "Diamond Armor"), "None", 20),
            new Armor(7, 70, 3.0/8.0, (Constants.GREEN_BOLD_BRIGHT + "Platinum Armor"), "None", 30),
            new Armor(4, 40, 1.0, (Constants.RED_BOLD_BRIGHT + "Wizard Cloak"), "None", 40),
            new Armor(5, 50, 7.0/8.0, (Constants.YELLOW_BOLD_BRIGHT + "Wizard Hat"), "None", 50),
            new Armor(7, 60, 3.0/4.0, (Constants.YELLOW_BOLD_BRIGHT + "Wizard Socks"), "None", 60),
            new Armor(7, 70, 5.0/8.0, ("Spiky " + Constants.GREEN_BOLD_BRIGHT + "Wizard Beard"), "Thorns", 70)
    };
    private Shoes shoelist[] = {
            new Shoes(2, 10, 2, (Constants.RED_BOLD_BRIGHT + "Sandals")),
            new Shoes(3, 20, 3, (Constants.YELLOW_BOLD_BRIGHT + "Tennis shoes")),
            new Shoes(2, 30, 3, (Constants.YELLOW_BOLD_BRIGHT + "Running shoes")),
            new Shoes(3, 40, 4, (Constants.GREEN_BOLD_BRIGHT + "Soccer shoes")),
    };
    private Shoes shoelist2[] = {
            new Shoes(2, 60, 6, (Constants.RED_BOLD_BRIGHT + "Bouncy Sandals")),
            new Shoes(3, 70, 7, (Constants.YELLOW_BOLD_BRIGHT + "Bouncy Tennis shoes")),
            new Shoes(2, 80, 8, (Constants.YELLOW_BOLD_BRIGHT + "Bouncy Running shoes")),
            new Shoes(3, 90, 8, (Constants.GREEN_BOLD_BRIGHT + "Speedy Soccer shoes")),
    };
    private Shield shieldlist[] = {
            new Shield(3, 20, 5, (Constants.RED_BOLD_BRIGHT + "Wooden shield")),
            new Shield(4, 30, 10, (Constants.YELLOW_BOLD_BRIGHT + "Reinforced shield")),
            new Shield(4, 40, 15, (Constants.YELLOW_BOLD_BRIGHT + "Iron Shield")),
            new Shield(4, 50, 20, (Constants.GREEN_BOLD_BRIGHT + "Diamond Shield")),
    };
    private Shield shieldlist2[] = {
            new Shield(3, 80, 40, (Constants.RED_BOLD_BRIGHT + "Reinforced Wooden shield")),
            new Shield(4, 95, 50, (Constants.YELLOW_BOLD_BRIGHT + "Double Reinforced shield")),
            new Shield(4, 110, 60, (Constants.YELLOW_BOLD_BRIGHT + "Reinforced Iron Shield")),
            new Shield(4, 125, 70, (Constants.GREEN_BOLD_BRIGHT + "Reinforced Diamond Shield")),
    };

    public TownsPerson(int holding_space, int x, int y) {
        super(holding_space, 999, x, y, "TownsPerson");
    }

    public String toString(){
        return("Today I am selling:\n" +
        "Weapon: " + super.getSword() + Constants.ANSI_RESET +
        "\nArmor: " + super.getArmor() + Constants.ANSI_RESET +
        "\nShield: " + super.getShield() + Constants.ANSI_RESET +
        "\nShoes: " + super.getShoes() + Constants.ANSI_RESET);
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
