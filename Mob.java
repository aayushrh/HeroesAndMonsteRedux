public abstract class Mob {
    private int holding_space;
    private Equipment holding[];
    private int health;
    private int max_health;
    private int x;
    private int y;
    private String type;

    private boolean interacted;

    public Mob(int holding_space, int health, int x, int y, String type) {
        this.holding_space = holding_space;
        this.holding = new Equipment[4];
        this.health = health;
        this.max_health = health;
        this.x = x;
        this.y = y;
        this.type = type;
        this.interacted = false;
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
        if(this.getArmor() == null || this.getArmor().getDamage_reduction() == 1) {
            this.health -= health;
        }else{
            this.health -= health * this.getArmor().getDamage_reduction();
            System.out.print("But the armor reduced it to: " + health * this.getArmor().getDamage_reduction() + "\n");
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

    public void armorGone(){
        holding[1] = null;
    }
    public void shieldGone(){
        holding[2] = null;
    }
    public void shoesGone(){
        holding[3] = null;
    }
}
