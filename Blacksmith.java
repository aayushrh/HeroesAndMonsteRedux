public class Blacksmith extends Mob{
    private String names[] = {"Flaming", "Stronger", "Weakening"};
    private String effects[] = {"Burn", "Armor Down", "Attack Down"};

    public Blacksmith(int x, int y) {
        super(999, 999, x, y, "Blacksmith");
    }

    public Weapon reforge(Weapon weapon){
        int num = (int)(Math.random() * 3);
        if(weapon.getName().contains("Flaming")){
            weapon.setName(names[num] + weapon.getName().substring(8));
        }else if (weapon.getName().contains("Weakening")){
            weapon.setName(names[num] + weapon.getName().substring(10));
        }else if (weapon.getName().contains("Stronger")){
            weapon.setName(names[num] + weapon.getName().substring(9));
        }else{
            weapon.setName(names[num] + weapon.getName());
        }
        weapon.setInflicts(effects[num]);
        return weapon;
    }
}
