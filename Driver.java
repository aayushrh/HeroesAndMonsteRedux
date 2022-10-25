import java.util.*;
public class Driver{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String IDK = "\\u001b[41m";
    public static int mapw = 30;
    public static int maph = 30;
    public static double looking_distance = 2.5;
    public static Object check(int x, int y, List<Object> entities){
        for(Object i : entities){
            Mob m = (Mob)(i);
            if (m.getX() == x && m.getY() == y){
                if(!m.getType().equals("Hero")) {
                    return i;
                }
            }
        }
        return null;
    }

    public static boolean fight(Hero hero, Monster mon){
        int num = (int) (Math.random() * 50);
        if (num > mon.getShoes().getSpeed()) {
            int dmg = hero.getSword().getDamage();
            System.out.println("You dealt " + dmg + " damage");
            mon.setStatusEffect(hero.getSword().getInflicts());
            if (mon.loseHealth(dmg)) {
                System.out.println("You defeated the monster\n");
                System.out.println("You gained " + mon.getMoney_drop() + " coins\n");
                hero.setMoney(hero.getMoney() + mon.getMoney_drop());
                hero.setMonstersdefeated(hero.getMonstersdefeated() + 1);
                return true;
            }
        } else {
            System.out.println("\nYou missed\n");
        }
        return false;
    }

    public static void printMap(Hero hero, List<Object> entities){
        int minx = 0, maxx = 0, miny = 0, maxy = 0;

        if(hero.getX() < 5){
            minx = 0;
            maxx = 10;
        }else if (hero.getX() > (mapw - 5)){
            minx = mapw - 11;
            maxx = mapw - 1;
        }else{
            minx = hero.getX() - 5;
            maxx = hero.getX() + 5;
        }

        if(hero.getY() < 5){
            miny = 0;
            maxy = 10;
        }else if (hero.getY() > (maph - 5)){
            miny = maph - 11;
            maxy = maph - 1;
        }else{
            miny = hero.getY() - 5;
            maxy = hero.getY() + 5;
        }

        for(int i = miny; i < maxy; i++){
            for(int j = minx; j < maxx; j++){
                boolean printed = false;
                for(Object o : entities){
                    Mob m = ((Mob)(o));
                    if(m.getX() == j && m.getY() == i){
                        if(m.isInteracted() || Math.sqrt(Math.pow(hero.getX() - m.getX(),2) + Math.pow(hero.getY() - m.getY(), 2)) <= looking_distance) {
                            if(m.getType().charAt(1) == 'M') {
                                System.out.print("\t" + RED_BOLD_BRIGHT + m.getType().charAt(0) + ANSI_RESET);
                            }else if (m.getType().charAt(0) == 'P'){
                                System.out.print("\t" + PURPLE_BOLD_BRIGHT + m.getType().charAt(0) + ANSI_RESET);
                            }else{
                                System.out.print("\t" + BLUE_BOLD_BRIGHT + m.getType().charAt(0) + ANSI_RESET);
                            }
                            printed = true;
                            break;
                        }
                    }
                }
                if(!printed){
                    if(Math.sqrt(Math.pow(hero.getX() - j,2) + Math.pow(hero.getY() - i, 2)) >= looking_distance){
                        System.out.print("\t" + "?");
                    }else{
                        System.out.print("\t" + GREEN_BOLD_BRIGHT + "-" + ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }

    }

    public static void spawn(List<Object> entities, int regs, int supers){
        for (int i = 0; i < regs; i++){
            int x = 0;
            int y = 0;
            while(true) {
                x = (int) (Math.random() * (mapw - 1));
                y = (int) (Math.random() * (maph - 1));
                if(check(x, y, entities) == null){
                    break;
                }
            }
            Monster m = new Monster(50, x, y, (int)(Math.random() * 20) + 10, 'R');
            int n = (int)(Math.random() * 4);
            if(n == 0) {
                m.equip(new Weapon(2, 0, 10, 20, "Flaming Beat-up sword", "Burn"));
            }else{
                m.equip(new Weapon(2, 0, 10, 20, "Beat-up sword", "None"));
            }
            m.equip(new Armor(4, 0, 1, "Tattered Armor"));
            m.equip(new Shoes(2, 0, (int) (Math.random() * 3), "Leather Shoes"));
            entities.add(m);
        }
        for (int i = 0; i < supers; i++){
            int x = 0;
            int y = 0;
            while(true) {
                x = (int) (Math.random() * (mapw - 1));
                y = (int) (Math.random() * (maph - 1));
                if(check(x, y, entities) == null){
                    break;
                }
            }
            int n = (int)(Math.random() * 4);
            Monster m = new Monster(75, x, y, (int)(Math.random() * 30) + 20, 'S');
            if(n == 0) {
                m.equip(new Weapon(2, 0, 10, 20, "Flaming Monster sword", "Burn"));
            }else{
                m.equip(new Weapon(2, 0, 10, 20, "Monster sword", "None"));
            }
            m.equip(new Armor(4, 0, 0.9, "Leather Armor"));
            m.equip(new Shoes(2, 0, (int) (Math.random() * 5), "Leather Shoes"));
            entities.add(m);
        }
        for (int k = 0; k < 10; k++){
            int x = 0;
            int y = 0;
            while(true) {
                x = (int) (Math.random() * mapw);
                y = (int) (Math.random() * maph);
                if(check(x, y, entities) == null){
                    break;
                }
            }
            Potion p = new Potion((int)(Math.random() * 50) + 50, x, y);
            entities.add(p);
        }
    }

    public static List<Object> restockVillagers(List<Object> entities, int num){
        for(Object i : entities){
            Mob m = (Mob)(i);
            if(m.getType().charAt(0) == 'T'){
                TownsPerson t = (TownsPerson)(m);
                t.restock(num);
            }
        }
        return entities;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        List<Object> entities = new ArrayList<Object>();
        Hero hero = new Hero(0, 100, 10, (int)(mapw/2), (int)(maph/2));
        hero.equip(new Weapon(2, 0, 20, 30, (RED_BOLD_BRIGHT + "Dagger"), "None"));
        hero.equip(new Armor(4, 0, 1, (RED_BOLD_BRIGHT + "Starter Armor")));
        entities.add(hero);
        spawn(entities, 10, 5);
        for (int j = 0; j < 5; j++){
            int x = 0;
            int y = 0;
            while(true) {
                x = (int) (Math.random() * mapw);
                y = (int) (Math.random() * maph);
                if(check(x, y, entities) == null){
                    break;
                }
            }
            TownsPerson v = new TownsPerson(20, x, y);
            v.restock(1);
            entities.add(v);
        }
        for (int k = 0; k < 2; k++){
            int x = 0;
            int y = 0;
            while(true) {
                x = (int) (Math.random() * mapw);
                y = (int) (Math.random() * maph);
                if(check(x, y, entities) == null){
                    break;
                }
            }
            Blacksmith b = new Blacksmith(x, y);
            entities.add(b);
        }
        boolean finale_boss = true;
        boolean intro = false;
        while(true) {
            int intinput_one = 0;
            if(hero.getMonstersdefeated() < 7 && !intro){
                System.out.println("You wake up one day in your beautiful village of Honeywood\n" +
                        "Its just that, well, it wasn't beautiful anymore\n" +
                        "Fire Everywhere\n" +
                        "Trees burning\n" +
                        "Women screaming\n" +
                        "Children dying\n" +
                        "What heartless, no, soulless, creature could do such a thing, after we have done nothing to them\n" +
                        "You will not have it\n" +
                        "You pick up a sword forged in the dying breaths of Bodge\n" +
                        "The prized Chainmail Armor of the Mayor\n" +
                        "and you FIGHT, to avenge, for Vengance's sake\n");
                intro = true;
            }
            else if(hero.getMonstersdefeated() > 7 && hero.getHolding_space() < 15){
                System.out.println("\nYou bag got upgraded to 15 holding space\n");
                hero.setHolding_space(15);
            }else if (hero.getMonstersdefeated() >= 15 && hero.getHolding_space() < 20){
                hero.setHolding_space(20);
                System.out.println("\nYou bag got upgraded to 20 holding space\n");
                System.out.println("You've done it\n" +
                        "You killed all the Monsters\n" +
                        "The soulless being the ravaged your village\n" +
                        "But Wait\n" +
                        "In the distance you see another orc\n" +
                        "It doesn't look like the other however\n" +
                        "It looks a little more ... distinguished\n");
                int x = 0;
                int y = 0;
                while(true) {
                    x = (int) (Math.random() * mapw);
                    y = (int) (Math.random() * maph);
                    if(check(x, y, entities) == null){
                        break;
                    }
                }
                Monster m = new Monster(200, x, y, 0, 'V');
                m.equip(new Weapon(2, 0, 40, 60, "Gold Sword", "None"));
                m.equip(new Armor(4, 0, 1, "T-shirt"));
                m.equip(new Shoes(2, 0, 20, "Sandals"));
                entities.add(m);
            }else if (hero.getMonstersdefeated() >= 16 && hero.getHolding_space() < 25){
                hero.setHolding_space(25);
                System.out.println("\nYou bag got upgraded to 25 holding space\n");
                System.out.println("'Argh', cried the Monster\n" +
                        "'You can speak!, Who are you', you ask\n" +
                        "'I am  lieutenant of the forces of Darksied'\n" +
                        "'Doesn't matter' you say, 'You are stopped now'\n" +
                        "it chuckles 'You will never stop us, I am but a pawn'\n" +
                        "Look like you job isn' done yet\n");
                System.out.println("All the TownsPeople have been restocked \n");
                hero.setMoney(hero.getMoney() + 100);
                entities = restockVillagers(entities, 2);
                spawn(entities, 8, 7);
            }else if (hero.getMonstersdefeated() >= 31 && hero.getHolding_space() < 30){
                hero.setHolding_space(30);
                System.out.println("\nYou bag got upgraded to 30 holding space\n");
                System.out.println("You find the King of the Goblin\n" +
                        "Now is your chance\n" +
                        "To end it all once and for all\n");
                int x = 0;
                int y = 0;
                while(true) {
                    x = (int) (Math.random() * mapw);
                    y = (int) (Math.random() * maph);
                    if(check(x, y, entities) == null){
                        break;
                    }
                }
                Monster m = new Monster(400, x, y, 0, 'V');
                m.equip(new Weapon(2, 0, 100, 110, "Platinum Blade", "Burn"));
                m.equip(new Armor(4, 0, 0.8, "Chainmail"));
                m.equip(new Shoes(2, 0, 20, "Iron boots"));
                entities.add(m);
            }else if (hero.getMonstersdefeated() >= 32 && hero.getHolding_space() < 35){
                hero.setHolding_space(35);
                System.out.println("\nYou bag got upgraded to 30 holding space\n");
                System.out.println("It's Dead\n" +
                        "You look around and realize you are in what seems ot be a plannign room\n" +
                        "but what were they planning\n" +
                        "You look around the walls and see .. Defense formations?\n" +
                        "That doesnt seem very Monster like\n" +
                        "You look more closely and you see they were fighting the forces of Darksied\n" +
                        "Wait aminutes, those were the forces you were going to fight.\n" +
                        "You look even moe and relize the the forces of Darksied are far greater\n" +
                        "than you could ever imagines\n" +
                        "'Oh no, what have I done?\n" +
                        "--- THE END ---");
                entities = restockVillagers(entities, 2);
                hero.setMoney(hero.getMoney() + 150);
                finale_boss = true;
                spawn(entities, 0, 0);
            }
            while(true) {
                Object o = check(hero.getX(), hero.getY(), entities);
                Mob m = (Mob)(o);
                if(m != null){
                    if(m.getType().substring(1).equals("Monster")){
                        Monster mon = (Monster)(m);
                        while(true){
                            int int_input_f = 0;
                            while(true) {
                                System.out.println(mon);
                                System.out.println("Your Health: " + hero.getHealth() +
                                        "\n" + "Status Effect: " + hero.getStatusEffect());
                                System.out.println("\n1.Fight\n" +
                                        "2.Parry\n" +
                                        "3.Potion\n" +
                                        "4.Escape");
                                String input_f = scan.nextLine();
                                if (input_f.equals("1") || input_f.equals("2") || input_f.equals("3") || input_f.equals("4")) {
                                    int_input_f = Integer.parseInt(input_f);
                                }
                                if(int_input_f != 0){
                                    break;
                                }else{
                                    System.out.println("\nPlease input the options you have\n");
                                }
                            }
                            boolean hit = true;
                            if (int_input_f == 1){
                                if(fight(hero, mon)){
                                    entities.remove(o);
                                    break;
                                }
                            }else if (int_input_f == 2){
                                hit = false;
                                if(hero.getShield() != null){
                                    int num_c = (int)(Math.random() * 5);
                                    if(num_c < 5) {
                                        if (hero.loseHealth(mon.getSword().getDamage() - hero.getShield().getBlock())) {
                                            System.out.println("\nYou died");
                                            System.exit(0);
                                        } else {
                                            System.out.println("\nYou blocked" + hero.getShield().getBlock() + " damage\n");
                                            if(fight(hero, mon)){
                                                entities.remove(o);
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        hit = true;
                                        System.out.println("\nYou missed the block and took 20 damage\n");
                                        if(hero.loseHealth(20)){
                                            System.out.println("\nYou died\n");
                                            System.exit(0);
                                        }
                                        if(fight(hero, mon)){
                                            entities.remove(o);
                                            break;
                                        }
                                    }
                                }else{

                                    System.out.println("\nYou do not have a shield\n");
                                }
                            }else if (int_input_f == 3){
                                hero.potionUse();
                                hit = false;
                            }else if (int_input_f == 4){
                                int num = (int)(Math.random() * 5) + 1;
                                System.out.println("Num: " + num + ", Shoes" + hero.getShoes());
                                if (hero.getShoes() == null) {
                                    if (num > (mon.getShoes().getSpeed())){
                                        System.out.println("\nYou escaped\n");
                                        break;
                                    }
                                    else{
                                        System.out.println("\nYou failed to escape");
                                    }
                                }else{
                                    if (num > (mon.getShoes().getSpeed() - hero.getShoes().getSpeed())){
                                        System.out.println("\nYou escaped\n");
                                        break;
                                    }
                                    else{
                                        System.out.println("\nYou failed to escape, you should consider getting shoes");
                                    }
                                }
                            }
                            if (hit){
                                int num = (int)(Math.random() * 50);
                                if (hero.getShoes() != null) {
                                    if (num > hero.getShoes().getSpeed()) {
                                        int dmg = mon.getSword().getDamage();
                                        hero.setStatusEffect(mon.getSword().getInflicts());
                                        if (hero.loseHealth(dmg)) {
                                            System.out.println("\nThe monster killed you\n");
                                            System.exit(0);
                                        } else {
                                            System.out.println("\nThe Monster dealt " + dmg + " damage\n");
                                        }
                                    } else {
                                        System.out.println("\nThe Monster missed\n");
                                    }
                                }else{
                                    int dmg = mon.getSword().getDamage();
                                    hero.setStatusEffect(mon.getSword().getInflicts());
                                    if (hero.loseHealth(dmg)) {
                                        System.out.println("\nThe monster killed you\n");
                                        System.exit(0);
                                    } else {
                                        System.out.println("\nThe Monster dealt " + dmg + " damage\n");
                                    }
                                }
                            }
                        }
                    }else if(m.getType().equals("Potion")){
                        hero.addPotion((Potion)(m));
                        System.out.println("\nYou found " + (Potion)(m) + "\n");
                        entities.remove(o);
                    }else if (m.getType().equals("TownsPerson")){
                        TownsPerson t = (TownsPerson)(m);
                        m.setInteracted(true);
                        int int_input_t = 0;
                        while(true) {
                            System.out.println(t + "\n5.Exit\n");
                            String input_t = scan.nextLine();
                            if (input_t.equals("1") || input_t.equals("2") || input_t.equals("3") || input_t.equals("4")|| input_t.equals("5")) {
                                int_input_t = Integer.parseInt(input_t);
                            }
                            if(int_input_t != 0){
                                break;
                            }else{
                                System.out.println("\nPlease input the options you have\n");
                            }
                        }
                        switch(int_input_t){
                            case 1:
                                if(t.getSword() != null){
                                    if (hero.getMoney() >= t.getSword().getCost()){
                                        hero.setMoney(hero.getMoney() - t.getSword().getCost());
                                        System.out.println("\nYou have bought the sword\n");
                                        if(hero.equip(t.getSword())){
                                            System.out.println("\nYou have equipped the sword\n");
                                        }else{
                                            System.out.println("\nyou did not have enough space and dropped it on the ground\n");
                                            if(check(hero.getX() + 1, hero.getY() + 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getSword()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getSword()))));
                                            }
                                        }
                                    }else{
                                        System.out.println("\nInsufficient funds\n");
                                    }
                                }else{
                                    System.out.println("This Shopkeeper does not have a sword");
                                }
                                break;
                            case 2:
                                if(t.getArmor() != null){
                                    if (hero.getMoney() >= t.getArmor().getCost()){
                                        hero.setMoney(hero.getMoney() - t.getArmor().getCost());
                                        System.out.println("\nYou have bought the armor\n");
                                        if(hero.equip(t.getArmor())){
                                            System.out.println("\nYou have equipped the armor\n");
                                        }else{
                                            System.out.println("\nyou did not have enough space and dropped it on the ground\n");
                                            if(check(hero.getX() + 1, hero.getY() + 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getArmor()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getArmor()))));
                                            }
                                        }
                                    }else{
                                        System.out.println("\nInsufficient funds\n");
                                    }
                                }else{
                                    System.out.println("This Shopkeeper does not have armor");
                                }
                                break;
                            case 3:
                                if(t.getShield() != null){
                                    if (hero.getMoney() >= t.getShield().getCost()){
                                        hero.setMoney(hero.getMoney() - t.getShield().getCost());
                                        System.out.println("\nYou have bought the shield\n");
                                        if(hero.equip(t.getShield())){
                                            System.out.println("\nYou have equipped the shield\n");
                                        }else{
                                            System.out.println("\nyou did not have enough space and dropped it on the ground\n");
                                            if(check(hero.getX() + 1, hero.getY() + 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShield()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShield()))));
                                            }
                                        }
                                    }else{
                                        System.out.println("\nInsufficient funds\n");
                                    }
                                }else{
                                    System.out.println("This Shopkeeper does not have a shield");
                                }
                                break;
                            case 4:
                                if(t.getShoes() != null){
                                    if (hero.getMoney() >= t.getShoes().getCost()){
                                        hero.setMoney(hero.getMoney() - t.getShoes().getCost());
                                        System.out.println("\nYou have bought the shoes\n");
                                        if(hero.equip(t.getShoes())){
                                            System.out.println("\nYou have equipped the shoes\n");
                                        }else{
                                            System.out.println("\nyou did not have enough space and dropped it on the ground\n");
                                            if(check(hero.getX() + 1, hero.getY() + 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShoes()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Object) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShoes()))));
                                            }
                                        }
                                    }else{
                                        System.out.println("\nInsufficient funds\n");
                                    }
                                }else{
                                    System.out.println("This Shopkeeper does not have shoes");
                                }
                                break;
                        }
                    }else if (m.getType().equals("Items")){
                        System.out.print("Would you like to pick up: ");
                        Drops d = ((Drops)(m));
                        String type = d.getEquipment_dropped().getType();
                        if (type.equals("Weapon")){
                            System.out.println((Weapon)(d.getEquipment_dropped()));
                        } else if (type.equals("Shield")){
                            System.out.println((Shield)(d.getEquipment_dropped()));
                        } else if (type.equals("Shoes")){
                            System.out.println((Shoes)(d.getEquipment_dropped()));
                        } else if (type.equals("Armor")){
                            System.out.println((Armor)(d.getEquipment_dropped()));
                        }
                        int int_input_t = 0;
                        while(true) {
                            System.out.println("1. Yes\n2. No\n");
                            String input_t = scan.nextLine();
                            if (input_t.equals("1") || input_t.equals("2")) {
                                int_input_t = Integer.parseInt(input_t);
                            }
                            if(int_input_t != 0){
                                break;
                            }else{
                                System.out.println("\nPlease input the options you have\n");
                            }
                        }
                        if(int_input_t == 1){
                            if (type.equals("Weapon")){
                                if(hero.equip((Weapon)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the sword\n");
                                    entities.remove(o);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Shield")){
                                if(hero.equip((Shield)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the shield\n");
                                    entities.remove(o);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Shoes")){
                                if(hero.equip((Shoes)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the shoes\n");
                                    entities.remove(o);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Armor")){
                                if(hero.equip((Armor)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the armor\n");
                                    entities.remove(o);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            }
                        }else{
                            System.out.println("\nYou did not pick it up\n");
                        }
                    }else if (m.getType().equals("Blacksmith")){
                        Blacksmith b = (Blacksmith)(m);
                        int int_input_b = 0;
                        while(true) {
                            System.out.println("\nDo you want to reforge your sword (it costs 10)\n1.Yes\n2.No\n");
                            String input_b = scan.nextLine();
                            if(input_b.equals("1") || input_b.equals("2")){
                                int_input_b = Integer.parseInt(input_b);
                                break;
                            }
                        }
                        if(int_input_b == 1){
                            if (hero.getMoney() > 10) {
                                hero.equip(b.reforge(hero.getSword()));
                                System.out.println("\nYou sword got reforged\n");
                            }
                            else{
                                System.out.println("\nInnsufficient funds\n");
                            }
                        }
                    }
                }
                printMap(hero, entities);
                System.out.println("WASD to move\n" +
                        "1 for potion\n" +
                        "2 to view stats\n" +
                        "3. to drop something\n");
                String input_one = scan.nextLine();
                if (input_one.toLowerCase().equals("w")) {
                    intinput_one = 1;
                } else if (input_one.toLowerCase().equals("s")) {
                    intinput_one = 2;
                } else if (input_one.toLowerCase().equals("a")) {
                    intinput_one = 3;
                } else if (input_one.toLowerCase().equals("d")) {
                    intinput_one = 4;
                } else if (input_one.equals("1")) {
                    intinput_one = 5;
                } else if (input_one.equals("2")) {
                    intinput_one = 6;
                } else if (input_one.equals("3")){
                    intinput_one = 7;
                }

                if(intinput_one != 0){
                    break;
                }
                else{
                    System.out.println("\nPlease choose an actual option\n");
                }
            }
            if(intinput_one == 1){
                if(hero.getY() > 0) {
                    hero.move(0, -1);
                }else{
                    if(!finale_boss) {
                        System.out.println("\nYou are going out of bounds\n");
                    }else{
                        System.out.println("\nYou have found the true Goblin King\n");
                        maph = 10;
                        mapw = 10;
                        hero.setX(5);
                        hero.setY(1);
                        entities = new ArrayList<Object>();
                        Monster finale_boss_mon = new Monster(1000, 5, 5, 0, 'V');
                        finale_boss_mon.equip(new Weapon(2, 0, 150, 175, "Ultimate Sword", "Attack Down"));
                        finale_boss_mon.equip(new Armor(4, 0, 0.75, "Ruby Armor"));
                        finale_boss_mon.equip(new Shoes(2, 0, 20, "Crowned Shoes"));
                        entities.add(finale_boss);
                    }
                }
            }else if (intinput_one == 2){
                if(hero.getY() < maph - 2) {
                    hero.move(0, 1);
                }else{
                    System.out.println("\nYou are going out of bounds\n");
                }
            }else if (intinput_one == 3){
                if(hero.getX() > 0){
                    hero.move(-1, 0);
                }else{
                    System.out.println("\nYou are going out of bounds\n");
                }
            }else if (intinput_one == 4){
                if(hero.getX() < mapw - 2){
                    hero.move(1, 0);
                }else{
                    System.out.println("\nYou are going out of bounds\n");
                }
            }else if (intinput_one == 5){
                hero.potionUse();
            } else if (intinput_one == 6){
                System.out.println(hero);
            } else if (intinput_one == 7){
                int int_input_f = 0;
                while(true) {
                    System.out.println("\n1.Shield\n" +
                            "2.Shoes\n");
                    String input_f = scan.nextLine();
                    if (input_f.equals("1") || input_f.equals("2")) {
                        int_input_f = Integer.parseInt(input_f);
                    }
                    if(int_input_f != 0){
                        break;
                    }else{
                        System.out.println("\nPlease input the options you have\n");
                    }
                }
                if (int_input_f == 1 && hero.getShield() != null){
                    entities.add((Object)(new Drops(hero.getX(), hero.getY(), (Equipment)(hero.getShield()))));
                    hero.shieldGone();
                } else if (int_input_f == 2 && hero.getShoes() != null){
                    entities.add((Object)(new Drops(hero.getX(), hero.getY(), (Equipment)(hero.getShoes()))));
                    hero.shoesGone();
                }else{
                    System.out.println("\nYou do not have that peice of equipment\n");
                }
            }
        }
    }
}
