public abstract class Mob {
    private int holding_space;
    private Equipment holding[];
    private int health;
    private int max_health;
    private int x;
    private int y;
    private String type;
    private String statusEffect;
    private int statusCounter;
    private boolean interacted;
    //temp vars
    private double prevArmor;
    private double prevSpeed;

    public Mob(int holding_space, int health, int x, int y, String type) {
        this.holding_space = holding_space;
        this.holding = new Equipment[4];
        this.health = health;
        this.max_health = health;
        this.x = x;
        this.y = y;
        this.type = type;
        this.interacted = false;
        this.statusEffect = "None";
    }

    public boolean setStatusEffect(String statusEffect) {
        if(!statusEffect.equals("None")) {
            if (this.statusEffect.equals("None")) {
                this.statusEffect = statusEffect;
                this.statusCounter = 0;
            } else {
                return false;
            }
        }else{
            this.statusCounter = 100;
            this.update();
            this.statusEffect = statusEffect;
            this.statusCounter = 0;
        }
        return true;
    }

    public String getStatusEffect() {
        return statusEffect;
    }

    public void update(){
        this.statusCounter++;
        if(this.statusEffect.equals("Burn")){
            this.loseHealth(5);
            System.out.println("\nYou took 5 damage from Burn\n");
            if(this.statusCounter > 5){
                this.statusEffect = "None";
            }
        }else if(this.statusEffect.equals("Intense Burn")){
            this.loseHealth(5);
            System.out.println("\nYou took 15 damage from Burn\n");
            if(this.statusCounter > 5){
                this.statusEffect = "None";
            }
        }else if (this.statusEffect.equals("Armor Down")){
            if(this.statusCounter == 1) {
                prevArmor = this.getArmor().getDamage_reduction();
                this.getArmor().setDamage_reduction(1);
                System.out.println("\nYour Armor has been weakened\n");
            }else if (this.statusCounter >= 3){
                this.statusEffect = "None";
                this.getArmor().setDamage_reduction(prevArmor);
                System.out.println("\nJK is back\n");
            }
        }else if (this.statusEffect.equals("Armor Up")){
            if(this.statusCounter == 1) {
                prevArmor = this.getArmor().getDamage_reduction();
                this.getArmor().setDamage_reduction(this.getArmor().getDamage_reduction() * 0.5);
                System.out.println("\nYour Armor has been strengthened\n");
            }else if (this.statusCounter >= 3){
                this.statusEffect = "None";
                this.getArmor().setDamage_reduction(this.getArmor().getDamage_reduction() * 1.5);
                System.out.println("\nJK is back\n");
            }
        }else if (this.statusEffect.equals("Attack Down")){
            if(this.statusCounter == 1) {
                this.getSword().setMaxdamage((int)(this.getSword().getMaxdamage() / 1.2));
                this.getSword().setMaxdamage((int)(this.getSword().getMindamage() / 1.2));
                System.out.println("\nYour Weapon has been weakened\n");
            }else if (this.statusCounter >= 3){
                this.statusEffect = "None";
                this.getSword().setMaxdamage((int)(this.getSword().getMaxdamage() * 1.2));
                this.getSword().setMaxdamage((int)(this.getSword().getMindamage() * 1.2));
                System.out.println("\nJK its back\n");
            }
        }else if (this.statusEffect.equals("Attack Up")){
            if(this.statusCounter == 1) {
                this.getSword().setMaxdamage((int)(this.getSword().getMaxdamage() * 1.2));
                this.getSword().setMaxdamage((int)(this.getSword().getMindamage() * 1.2));
                System.out.println("\nYour Weapon has been strengthened\n");
            }else if (this.statusCounter >= 3){
                this.statusEffect = "None";
                this.getSword().setMaxdamage((int)(this.getSword().getMaxdamage() / 1.2));
                this.getSword().setMaxdamage((int)(this.getSword().getMindamage() / 1.2));
                System.out.println("\nJK its back\n");
            }
        }else if (this.statusEffect.equals("Epic Speed")){
            if(this.statusCounter == 1) {
                prevSpeed = this.getShoes().getSpeed();
                this.getShoes().setSpeed(100000);
                System.out.println("\nYou can now dodge\n");
            }else if (this.statusCounter >= 3){
                this.statusEffect = "None";
                this.getShoes().setSpeed((int)prevSpeed);
                System.out.println("\nJK is back\n");
            }
        }else if (this.statusEffect.equals("Rage")){
            if(this.statusCounter < 5){
                this.statusCounter++;
                this.getSword().setMindamage(this.getSword().getMindamage() + 5);
                this.getSword().setMaxdamage(this.getSword().getMaxdamage() + 5);
            }else{
                this.getSword().setMindamage(this.getSword().getMindamage() - 25);
                this.getSword().setMaxdamage(this.getSword().getMaxdamage() - 25);
                this.statusEffect = "None";
            }
        }
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMax_health() {
        return max_health;
    }

    public boolean isInteracted() {
        return interacted;
    }

    public void setInteracted(boolean interacted) {
        this.interacted = interacted;
    }

    public boolean loseHealth(int health){
        if(this.getArmor() == null || this.getArmor().getDamage_reduction() == 1.0) {
            this.health -= health;
        }else{
            this.health -= health * this.getArmor().getDamage_reduction();
            System.out.print("\nBut the armor reduced it to: " + health * this.getArmor().getDamage_reduction() + "\n\n");
        }
        return this.health <= 0;
    }

    public void gainHealth(int health){
        this.health += health;
        this.health = Math.min(this.health, this.max_health);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHolding_space(int holding_space) {
        this.holding_space = holding_space;
    }

    public int getHolding_space() {
        return holding_space;
    }

    public int space_taken(){
        int sum = 0;
        for(Equipment i : this.holding){
            if (i != null) {
                sum += i.getSpace_taken();
            }
        }
        return sum;
    }

    public boolean equip(Weapon sword){
        if(this.getSword()!= null) {
            if (space_taken() - this.getSword().getSpace_taken() + sword.getSpace_taken() <= holding_space) {
                this.holding[0] = sword;
                return true;
            }
            return false;
        }else{
            if (space_taken() + sword.getSpace_taken() <= holding_space) {
                this.holding[0] = sword;
                return true;
            }
            return false;
        }
    }
    public boolean equip(Armor armor){
        if(this.getArmor()!= null) {
            if (space_taken() - this.getArmor().getSpace_taken() + armor.getSpace_taken() <= holding_space) {
                this.holding[1] = armor;
                return true;
            }
            return false;
        }else{
            if (space_taken() + armor.getSpace_taken() <= holding_space) {
                this.holding[1] = armor;
                return true;
            }
            return false;
        }
    }
    public boolean equip(Shield shield){
        if(this.getShield()!= null) {
            if (space_taken() - this.getShield().getSpace_taken() + shield.getSpace_taken() <= holding_space) {
                this.holding[2] = shield;
                return true;
            }
            return false;
        }else{
            if (space_taken() + shield.getSpace_taken() <= holding_space) {
                this.holding[2] = shield;
                return true;
            }
            return false;
        }
    }
    public boolean equip(Shoes shoes){
        if(this.getShoes()!= null) {
            if (space_taken() - this.getShoes().getSpace_taken() + shoes.getSpace_taken() <= holding_space) {
                this.holding[3] = shoes;
                return true;
            }
            return false;
        }else{
            if (space_taken() + shoes.getSpace_taken() <= holding_space) {
                this.holding[3] = shoes;
                return true;
            }
            return false;
        }
    }

    public Weapon getSword(){
        return (Weapon)this.holding[0];
    }
    public Armor getArmor(){
        return (Armor) this.holding[1];
    }
    public Shield getShield(){
        return (Shield)this.holding[2];
    }
    public Shoes getShoes(){
        return (Shoes)this.holding[3];
    }
    public void shieldGone(){
        holding[2] = null;
    }
    public void shoesGone(){
        holding[3] = null;
    }
}
