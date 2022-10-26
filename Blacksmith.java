public class Blacksmith extends Mob{
    private String weaponNames[] = {"Flaming ", "Stronger ", "Weakening ", "Frosty ", "Ragefull "};
    private String weaponEffects[] = {"Burn ", "Armor Down ", "Attack Down ", "Freeze ", "Rage "};
    private String armorNames[] = {"Spiky ", "Weakening ", "Protective "};
    private String armorEffects[] = {"Thorns ", "Attack Down ", "Defence Up" };

    public Blacksmith(int x, int y) {
        super(999, 999, x, y, "Blacksmith");
    }

    public Weapon reforge(Weapon weapon){
        int num = (int)(Math.random() * weaponNames.length);
        boolean switched = false;
        for (String i : weaponNames){
            if(weapon.getName().contains(i)){
                weapon.setName(weaponNames[num] + weapon.getName().substring(i.length() + 1));
                switched = true;
            }
        }
        if(!switched){
            weapon.setName(weaponNames[num] + weapon.getName());
        }
        weapon.setInflicts(weaponEffects[num]);
        return weapon;
    }

    public Armor reforge(Armor armor){
        int num = (int)(Math.random() * armorNames.length);
        boolean switched = false;
        for (String i : armorNames){
            if(armor.getName().contains(i)){
                armor.setName(armorNames[num] + armor.getName().substring(i.length() + 1));
                switched = true;
            }
        }
        if(!switched){
            armor.setName(armorNames[num] + armor.getName());
        }
        armor.setEffect(armorEffects[num]);
        return armor;
    }
}
