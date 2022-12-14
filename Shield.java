public class Shield extends Equipment{
    private int block;

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public Shield(int space_taken, int cost, int block, String name) {
        super(space_taken, cost, "Shield", name);
        this.block = block;
    }

    public String toString(){
        return(super.toString() + "\tBlock: " + this.block + Constants.ANSI_RESET);
    }

    public String saveText(){
        return (this.getName() + "  " +
                this.getBlock() + "  " +
                this.getSpace_taken() + "  ");
    }
}
