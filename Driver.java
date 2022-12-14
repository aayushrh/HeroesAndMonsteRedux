import java.util.*;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class Driver{
    public static int mapw = 30;
    public static int maph = 30;
    public static double looking_distance = 2.5;
    public static String key[] = { "Key", (Constants.RED_BOLD_BRIGHT + "R" + Constants.ANSI_RESET + " is Regular Monster"),
            (Constants.RED_BOLD_BRIGHT + "S" + Constants.ANSI_RESET + " is Super Monster"),
            (Constants.RED_BOLD_BRIGHT + "V" + Constants.ANSI_RESET + " is Boss"),
            (Constants.PURPLE_BOLD_BRIGHT + "P" + Constants.ANSI_RESET + " is Potion"),
            (Constants.BLUE_BOLD_BRIGHT + "T" + Constants.ANSI_RESET + " is Shopkeeper"),
            (Constants.BLUE_BOLD_BRIGHT + "B" + Constants.ANSI_RESET + " is Blacksmith"),
            (Constants.BLUE_BOLD_BRIGHT + "H" + Constants.ANSI_RESET + " is you"),
            "", "", "" };
    public static Mob check(int x, int y, List<Mob> entities){
        for(Mob m : entities){
            if (m.getX() == x && m.getY() == y){
                if(!m.getType().equals("Hero")) {
                    return m;
                }
            }
        }
        return null;
    }

    public static void createSaveFile(int num){
        try {
            File myObj = new File("save" + num + ".txt");
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void save(Hero hero, List<Mob> entities, int save_file_num){
        createSaveFile(save_file_num);
        try {
            File file = new File("save" + save_file_num + ".txt");
            file.delete();
            FileWriter myWriter = new FileWriter("save" + save_file_num + ".txt");
            myWriter.write(hero.saveText());
            for(int i = 0; i < entities.size(); i++){
                Mob m = entities.get(i);
                switch(m.getType().charAt(0)){
                    case 'R':
                    case 'S':
                    case 'V':
                        Monster mon = (Monster)(m);
                        myWriter.write(
                        m.getType().charAt(0) + "  " +
                        m.getX() + "  " +
                        m.getY() + "  " +
                        m.getHealth() + "  " +
                        mon.getMoney_drop() + "  " +
                        m.getSword().saveText() +
                        m.getArmor().saveText() +
                        m.getShoes().saveText());
                        break;
                    case 'T':
                        myWriter.write(
                        "T  " +
                        m.getX() + "  " +
                        m.getY() + "  " +
                        m.getHolding_space() + "  " +
                        m.getSword().saveText() +
                        m.getArmor().saveText() +
                        m.getShield().saveText() +
                        m.getShoes().saveText());
                    case 'B':
                        myWriter.write("B  " +
                        m.getX() + "  " +
                        m.getY() + "  ");
                        break;
                    case 'P':
                        Potion p = (Potion)(m);
                        myWriter.write("P  " +
                        m.getX() + "  " +
                        m.getY() + "  " +
                        p.getHealth_gain() + "  ");
                        break;
                }
            }
            myWriter.close();
            System.out.println("Successfully Saved.\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void importSave(Hero hero, List<Mob> entities, int num){
        List<String> input = new ArrayList<String>();
        try {
            File myObj = new File("save" + num + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nYou do not have a save file\n");
            e.printStackTrace();
        }
        int place = hero.importSave(input.get(0));
        String a[] = input.get(0).split("  ");
        while(true){
            if(place >= a.length){
                break;
            }
            char type = a[place].charAt(0);
            switch(type){
                case 'V':
                case 'S':
                case 'R':
                    Monster m = new Monster(Integer.parseInt(a[place + 3]), Integer.parseInt(a[place + 1]), Integer.parseInt(a[place + 2]),Integer.parseInt(a[place + 4]), type);
                    m.equip(new Weapon(Integer.parseInt(a[place + 9]), 0, Integer.parseInt(a[place + 6]), Integer.parseInt(a[place + 7]), a[place + 5], a[place + 8]));
                    m.equip(new Armor(Integer.parseInt(a[place + 12]), 0, Double.parseDouble(a[place + 11]), a[place + 10], a[place + 13], Integer.parseInt(a[place + 14])));
                    m.equip(new Shoes(Integer.parseInt(a[place + 17]), 0, Integer.parseInt(a[place + 16]), a[place + 15]));
                    entities.add(m);
                    place += 18;
                    break;
                case 'T':
                    TownsPerson v = new TownsPerson(Integer.parseInt(a[place + 3]), Integer.parseInt(a[place + 1]), Integer.parseInt(a[place + 2]));
                    v.equip(new Weapon(Integer.parseInt(a[place + 8]), 0, Integer.parseInt(a[place + 5]), Integer.parseInt(a[place + 6]), a[place + 4], a[place + 7]));
                    v.equip(new Armor(Integer.parseInt(a[place + 11]), 0, Double.parseDouble(a[place + 10]), a[place + 9], a[place + 12], Integer.parseInt(a[place + 13])));
                    v.equip(new Shield(Integer.parseInt(a[place + 16]), 0, Integer.parseInt(a[place + 15]), a[place + 14]));
                    v.equip(new Shoes(Integer.parseInt(a[place + 19]), 0, Integer.parseInt(a[place + 18]), a[place + 17]));
                    entities.add(v);
                    place += 20;
                    break;
                case 'P':
                    Potion p = new Potion(Integer.parseInt(a[place + 3]), Integer.parseInt(a[place + 1]), Integer.parseInt(a[place + 2]));
                    entities.add(p);
                    place += 4;
                    break;
                case 'B':
                    Blacksmith b = new Blacksmith(Integer.parseInt(a[place + 1]), Integer.parseInt(a[place + 2]));
                    entities.add(b);
                    place += 3;
                    break;
            }
        }
    }

    public static boolean fight(Hero hero, Monster mon){
        int num = (int) (Math.random() * 50);
        if (num > mon.getShoes().getSpeed()) {
            int dmg = hero.getSword().getDamage();
            System.out.println("You dealt " + dmg + " damage");
            if(!hero.getSword().getInflicts().equals("None") && !hero.getSword().getInflicts().equals("Rage") && !hero.getSword().getInflicts().equals("Freeze")) {
                mon.setStatusEffect(hero.getSword().getInflicts());
            }else if (hero.getSword().getInflicts().equals("Freeze")){
                int chance = (int)(Math.random() * 2);
                if(chance == 0){
                    mon.setStatusEffect(hero.getSword().getInflicts());
                }else{
                    System.out.println("\nYou failed to freeze the monster\n");
                }
            }
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

    public static void printMap(Hero hero, List<Mob> entities){
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int i = miny; i < maxy; i++){
            for(int j = minx; j < maxx; j++){
                boolean printed = false;
                for(Mob m : entities){
                    if(m.getX() == j && m.getY() == i){
                        if(m.isInteracted() || Math.sqrt(Math.pow(hero.getX() - m.getX(),2) + Math.pow(hero.getY() - m.getY(), 2)) <= looking_distance) {
                            if(m.getType().charAt(1) == 'M') {
                                System.out.print("\t" + Constants.RED_BOLD_BRIGHT + m.getType().charAt(0) + Constants.ANSI_RESET);
                            }else if (m.getType().charAt(0) == 'P'){
                                System.out.print("\t" + Constants.PURPLE_BOLD_BRIGHT + m.getType().charAt(0) + Constants.ANSI_RESET);
                            }else{
                                System.out.print("\t" + Constants.BLUE_BOLD_BRIGHT + m.getType().charAt(0) + Constants.ANSI_RESET);
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
                        System.out.print("\t" + Constants.GREEN_BOLD_BRIGHT + "-" + Constants.ANSI_RESET);
                    }
                }
            }
            System.out.println("\t" + key[i - miny]);
        }

    }

    public static void spawn(List<Mob> entities, int regs, int supers){
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
            m.equip(new Armor(4, 0, 1, "Tattered Armor", "None", 0));
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
            m.equip(new Armor(4, 0, 0.9, "Leather Armor", "None", 0));
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

    public static List<Mob> restockVillagers(List<Mob> entities, int num){
        for(Mob m : entities){
            if(m.getType().charAt(0) == 'T'){
                TownsPerson t = (TownsPerson)(m);
                t.restock(num);
            }
        }
        return entities;
    }

    public static void monfight(Monster mon, Hero hero){
        int dmg = mon.getSword().getDamage();
        hero.setStatusEffect(mon.getSword().getInflicts());
        if(hero.getArmor().getEffect().equals("Defence Up")){
            dmg -= 10;
        }else if (hero.getArmor().getEffect().equals("Thorns")){
            System.out.println("The Monster took 8 damage from your Spiky Armor");
            mon.loseHealth(8);
        }else if (hero.getArmor().getEffect().equals("Attack Down")){
            mon.setStatusEffect("Attack Down");
        }
        if (hero.loseHealth(dmg)) {
            System.out.println("\nThe monster killed you\n");
            System.exit(0);
        } else {
            System.out.println("\nThe Monster dealt " + dmg + " damage");
        }
    }

    public static boolean onlyContainsMove(String input){
        for(int i = 0; i < input.length(); i++){
            char a = input.toLowerCase().charAt(i);
            if(!(a == 'w' || a == 's' || a == 'a' || a == 'd')){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        List<Mob> entities = new ArrayList<Mob>();
        Hero hero = new Hero(0, 100, 10, (int)(mapw/2), (int)(maph/2));
        hero.equip(new Weapon(2, 0, 20, 30, (Constants.RED_BOLD_BRIGHT + "Dagger"), "None"));
        hero.equip(new Armor(4, 0, 1, ( Constants.RED_BOLD_BRIGHT + "Starter Armor"), "None", 0));
        entities.add(hero);
        boolean spawn = true;
        boolean boss_killed = false;
        System.out.println("**Heroes and Monsters**\n**Epilepsy Warning** ");
        int intinput_s = 0;
        while (true) {
            System.out.println("1. Play\n" +
                    "2. Go to last save state\n" +
                    "3. Info\n");
            String input_s = scan.nextLine();
            if(input_s.equals("3") || input_s.equals("2") || input_s.equals("1")){
                intinput_s = Integer.parseInt(input_s);
                break;
            }
        }
        if(intinput_s == 2){
            spawn = false;
            int intinput_file = 0;
            while(true){
                System.out.println("Which save file would you like to import from? (1 - 4)");
                String input_file = scan.nextLine();
                if(input_file.equals("1") || input_file.equals("2") || input_file.equals("3") || input_file.equals("4")){
                    intinput_file = Integer.parseInt(input_file);
                    break;
                }
            }
            importSave(hero, entities, intinput_file);
        } else if(intinput_s == 3){
            System.out.println("So you see his game has a thing called holding space\n" +
                    "Each item you have has an amount of space taken which you can see by looking at your stats\n" +
                    "Your hero also has a certain amount of holding space that it can withstand\n" +
                    "If you purchase an item that goes beyond the set holding limit it will drop on the ground\n" +
                    "You Holding space will gradually upgrade as you go along your journey killing monsters\n" +
                    "Have Fun!\n");
        }
        if(spawn) {
            spawn(entities, 20, 10);
            for (int j = 0; j < 5; j++) {
                int x = 0;
                int y = 0;
                while (true) {
                    x = (int) (Math.random() * mapw);
                    y = (int) (Math.random() * maph);
                    if (check(x, y, entities) == null) {
                        break;
                    }
                }
                TownsPerson v = new TownsPerson(20, x, y);
                v.restock(1);
                entities.add(v);
            }
            for (int k = 0; k < 2; k++) {
                int x = 0;
                int y = 0;
                while (true) {
                    x = (int) (Math.random() * mapw);
                    y = (int) (Math.random() * maph);
                    if (check(x, y, entities) == null) {
                        break;
                    }
                }
                Blacksmith b = new Blacksmith(x, y);
                entities.add(b);
            }
        }
        boolean finale_boss = true;
        boolean intro = false;

        while(true) {
            int intinput_one = 0;
            if(hero.getMonstersdefeated() == 0 && !intro){
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
            else if(hero.getMonstersdefeated() > 7 && hero.getHolding_space() == 10){
                System.out.println("\nYou bag got upgraded to 15 holding space\n");
                hero.setHolding_space(15);
            }else if (hero.getMonstersdefeated() >= 15 && hero.getHolding_space() == 15){
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
                int numx = (int)(Math.random() * 4) + 1;
                int numy = (int)(Math.random() * 4) + 1;
                System.out.println("\nThe boss is within: x: " + (x - numx) + " - " + (x + (10 - numx)) +
                        "; y: " + (16 -(y - numy)) + " - " + (16 - (y + (10 - numy))) + "\n");
                Monster m = new Monster(200, x, y, 0, 'V');
                m.equip(new Weapon(2, 0, 40, 60, "Gold Sword", "None"));
                m.equip(new Armor(4, 0, 1.0, "T-shirt", "None", 0));
                m.equip(new Shoes(2, 0, 20, "Sandals"));
                entities.add(m);
            }else if (hero.getMonstersdefeated() >= 16 && hero.getHolding_space() == 20 && boss_killed){
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
                spawn(entities, 10, 9);
                boss_killed = false;
            }else if (hero.getMonstersdefeated() >= 31 && hero.getHolding_space() == 25){
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
                int numx = (int)(Math.random() * 4) + 1;
                int numy = (int)(Math.random() * 4) + 1;
                System.out.println("\nThe boss is within: x: " + (x - numx) + " - " + (x + (10 - numx)) +
                        "; y: " + (16 -(y - numy)) + " - " + (16 - (y + (10 - numy))) + "\n");
                Monster m = new Monster(400, x, y, 0, 'V');
                m.equip(new Weapon(2, 0, 100, 110, "Platinum Blade", "Burn"));
                m.equip(new Armor(4, 0, 0.8, "Chainmail", "None", 0));
                m.equip(new Shoes(2, 0, 20, "Iron boots"));
                entities.add(m);
            }else if (hero.getMonstersdefeated() >= 32 && hero.getHolding_space() == 30 && boss_killed){
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
                boss_killed = false;
            }
            while(true) {
                Mob m = check(hero.getX(), hero.getY(), entities);
                if(m != null){
                    if(m.getType().substring(1).equals("Monster")){
                        Monster mon = (Monster)(m);
                        while(true){
                            hero.updateMana();
                            hero.update();
                            mon.update();
                            int int_input_f = 0;
                            while(true) {
                                System.out.println(mon);
                                System.out.println("Your Health: " + hero.getHealth() +
                                        "\n" + "Status Effect: " + hero.getStatusEffect() +
                                        "\n" + "Mana: " + hero.getMana());
                                System.out.println("\n1.Fight\n" +
                                        "2.Parry\n" +
                                        "3.Potion\n" +
                                        "4.Spell\n" +
                                        "5.Escape");
                                String input_f = scan.nextLine();
                                if (input_f.equals("1") || input_f.equals("2") || input_f.equals("3") || input_f.equals("4") || input_f.equals("5")) {
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
                                    entities.remove(m);
                                    if(m.getType().charAt(0) == 'V'){
                                        boss_killed = true;
                                    }
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
                                                entities.remove(m);
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
                                            entities.remove(m);
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
                                hit = false;
                                int_input_f = 0;
                                while(true) {
                                    if(hero.getMonstersdefeated() > 7) System.out.println("1. Intense Burn(40)"); else System.out.println("1. Locked until 7 Monsters Defeated");
                                    if(hero.getMonstersdefeated() > 12) System.out.println("2. Defense Up(30)"); else System.out.println("2. Locked until 12 Monsters Defeated");
                                    if(hero.getMonstersdefeated() > 16) System.out.println("3. Attack Up(30)"); else System.out.println("3. Locked until 16 Monsters Defeated");
                                    if(hero.getMonstersdefeated() > 23) System.out.println("4. Dodge(30)"); else System.out.println("4. Locked until 23 Monsters Defeated");
                                    System.out.println("5. Exit");
                                    String input_f = scan.nextLine();
                                    if (input_f.equals("1") || input_f.equals("2") || input_f.equals("3") || input_f.equals("4") || input_f.equals("5")) {
                                        int_input_f = Integer.parseInt(input_f);
                                    }
                                    if(int_input_f != 0){
                                        break;
                                    }else{
                                        System.out.println("\nPlease input the options you have\n");
                                    }
                                }
                                switch(int_input_f){
                                    case 1:
                                        if(hero.getMana() > 40 && hero.getMonstersdefeated() > 7){
                                            mon.setStatusEffect("Intense Burn");
                                            hero.setMana(hero.getMana() - 40);
                                        }
                                    case 2:
                                        if(hero.getMana() > 30 && hero.getMonstersdefeated() > 12){
                                            hero.setStatusEffect("Defense Up");
                                            hero.setMana(hero.getMana() - 30);
                                        }
                                    case 3:
                                        if(hero.getMana() > 30 && hero.getMonstersdefeated() > 16){
                                            hero.setStatusEffect("Attack Up");
                                            hero.setMana(hero.getMana() - 30);
                                        }
                                    case 4:
                                        if(hero.getMana() > 30 && hero.getMonstersdefeated() > 23){
                                            hero.setStatusEffect("Epic Speed");
                                            hero.setMana(hero.getMana() - 30);
                                        }
                                }
                            }else if (int_input_f == 5){
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
                            if (hit && mon.getStatusEffect() != "Freeze"){
                                int num = (int)(Math.random() * 50);
                                if (hero.getShoes() != null) {
                                    if (num > hero.getShoes().getSpeed()) {
                                        monfight(mon, hero);
                                    } else {
                                        System.out.println("\nThe Monster missed\n");
                                    }
                                }else{
                                    monfight(mon, hero);
                                }
                            }
                            if(mon.getStatusEffect().equals("Freeze")){
                                System.out.println("The monster is frozen");
                            }
                        }
                        hero.setStatusEffect("None");
                    }else if(m.getType().equals("Potion")){
                        hero.addPotion((Potion)(m));
                        System.out.println("\nYou found " + (Potion)(m) + "\n");
                        entities.remove(m);
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
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getSword()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getSword()))));
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
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getArmor()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getArmor()))));
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
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShield()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShield()))));
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
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShoes()))));
                                            }else if(check(hero.getX() - 1, hero.getY() - 1, entities) == null) {
                                                entities.add((Mob) (new Drops(hero.getX() + 1, hero.getY() + 1, (Equipment) (t.getShoes()))));
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
                                    entities.remove(m);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Shield")){
                                if(hero.equip((Shield)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the shield\n");
                                    entities.remove(m);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Shoes")){
                                if(hero.equip((Shoes)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the shoes\n");
                                    entities.remove(m);
                                }else{
                                    System.out.println("\nYou failed\n");
                                }
                            } else if (type.equals("Armor")){
                                if(hero.equip((Armor)(d.getEquipment_dropped()))){
                                    System.out.println("\nYou equipped the armor\n");
                                    entities.remove(m);
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
                            System.out.println("\nWhat do you want to reforge (it costs 10 for Weapon and 20 for Armor)\n1.Sword\n2.Armor\n3.Nothing");
                            String input_b = scan.nextLine();
                            if(input_b.equals("1") || input_b.equals("2") || input_b.equals("3")){
                                int_input_b = Integer.parseInt(input_b);
                                break;
                            }
                        }
                        if(int_input_b == 1){
                            if (hero.getMoney() >= 10) {
                                hero.equip(b.reforge(hero.getSword()));
                                System.out.println("\nYour armor got reforged\n");
                                hero.setMoney(hero.getMoney() - 10);
                            }
                            else{
                                System.out.println("\nInsufficient funds\n");
                            }
                        }else if (int_input_b == 2){
                            if (hero.getMoney() >= 20) {
                                hero.equip(b.reforge(hero.getArmor()));
                                System.out.println("\nYour armor got reforged\n");
                                hero.setMoney(hero.getMoney() - 20);
                            }
                            else{
                                System.out.println("\nInsufficient funds\n");
                            }
                        }
                    }
                }
                printMap(hero, entities);
                System.out.println("WASD to move\n" +
                        "Q for potion\n" +
                        "E to view stats\n" +
                        "R. to drop something\n" +
                        "F. Save\n");
                String input_one = scan.nextLine();
                if (input_one.toLowerCase().equals("w")) {
                    intinput_one = 1;
                } else if (input_one.toLowerCase().equals("s")) {
                    intinput_one = 2;
                } else if (input_one.toLowerCase().equals("a")) {
                    intinput_one = 3;
                } else if (input_one.toLowerCase().equals("d")) {
                    intinput_one = 4;
                } else if (input_one.toLowerCase().equals("q")) {
                    intinput_one = 5;
                } else if (input_one.toLowerCase().equals("e")) {
                    intinput_one = 6;
                } else if (input_one.toLowerCase().equals("r")){
                    intinput_one = 7;
                } else if (input_one.toLowerCase().equals("f")){
                    intinput_one = 8;
                }
                else if (onlyContainsMove(input_one)){
                    intinput_one = 99999;
                    boolean outtabounds = false;
                    for(int i = 0; i < input_one.length(); i++){
                        char a = input_one.toLowerCase().charAt(i);
                        switch(a){
                            case 'w':
                                if(hero.getY() > 0) {
                                    hero.move(0, -1);
                                }else{
                                    System.out.println("\nYou are going outta bounds\n");
                                    outtabounds = true;
                                }
                                break;
                            case 's':
                                if(hero.getY() < maph - 2) {
                                    hero.move(0, 1);
                                }else{
                                    System.out.println("\nYou are going outta bounds\n");
                                    outtabounds = true;
                                }
                                break;
                            case 'a':
                                if(hero.getX() > 0) {
                                    hero.move(-1, 0);
                                }else{
                                    System.out.println("\nYou are going outta bounds\n");
                                    outtabounds = true;
                                }
                                break;
                            case 'd':
                                if(hero.getX() < mapw - 2) {
                                    hero.move(1, 0);
                                }else{
                                    System.out.println("\nYou are going outta bounds\n");
                                    outtabounds = true;
                                }
                                break;
                        }
                        if(outtabounds){
                            break;
                        }
                    }
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
                        Monster finale_boss_mon = new Monster(1000, 5, 5, 0, 'V');
                        finale_boss_mon.equip(new Weapon(2, 0, 150, 175, "Ultimate Sword", "Attack Down"));
                        finale_boss_mon.equip(new Armor(4, 0, 0.75, "Ruby Armor", "None", 0));
                        finale_boss_mon.equip(new Shoes(2, 0, 20, "Crowned Shoes"));
                        entities.add(finale_boss_mon);
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
                            "2.Shoes\n" +
                            "3.Nothing\n");
                    String input_f = scan.nextLine();
                    if (input_f.equals("1") || input_f.equals("2") || input_f.equals("3")) {
                        int_input_f = Integer.parseInt(input_f);
                    }
                    if(int_input_f != 0){
                        break;
                    }else{
                        System.out.println("\nPlease input the options you have\n");
                    }
                }
                if (int_input_f == 1 && hero.getShield() != null){
                    entities.add((new Drops(hero.getX(), hero.getY(), (Equipment)(hero.getShield()))));
                    hero.shieldGone();
                } else if (int_input_f == 2 && hero.getShoes() != null){
                    entities.add((new Drops(hero.getX(), hero.getY(), (Equipment)(hero.getShoes()))));
                    hero.shoesGone();
                }else{
                    System.out.println("\nYou do not have that peice of equipment\n");
                }
            } else if (intinput_one == 8){
                int intinput_file = 0;
                while(true){
                    System.out.println("Which save file would you like to save to? (1 - 4");
                    String input_file = scan.nextLine();
                    if(input_file.equals("1") || input_file.equals("2") || input_file.equals("3") || input_file.equals("4")){
                        intinput_file = Integer.parseInt(input_file);
                        break;
                    }
                }
                save(hero, entities, intinput_file);
            }
        }
    }
}