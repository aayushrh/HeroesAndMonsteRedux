public class Blacksmith extends Mob{
    private String names[] = {"Flaming", "Stronger", "Weakening", "Frosty", "Ragefull"};
    private String effects[] = {"Burn", "Armor Down", "Attack Down", "Freeze", "Rage"};

    public Blacksmith(int x, int y) {
        super(999, 999, x, y, "Blacksmith");
    }

    public Weapon reforge(Weapon weapon){
        int num = (int)(Math.random() * names.length);
        boolean switched = false;
        for (String i : names){
            if(weapon.getName().contains(i)){
                weapon.setName(names[num] + weapon.getName().substring(i.length() + 1));
                switched = true;
            }
        }
        if(!switched){
            weapon.setName(names[num] + weapon.getName());
        }
        weapon.setInflicts(effects[num]);
        return weapon;
    }
}
