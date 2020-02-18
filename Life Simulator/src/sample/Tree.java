package sample;

public class Tree {
    private int lumberAmountLeft;
    private int x;
    private int y;
    private boolean isPlanted = true;
    private long startTime;


    public Tree(int lumberAmountLeft,int x,int y){
        this.lumberAmountLeft = lumberAmountLeft;
        this.x= x;
        this.y = y;
        startTime = System.nanoTime();


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

    public boolean isPlanted() {
        return isPlanted;
    }
    public void setPlanted() {
        isPlanted = !isPlanted;
    }

    public void setLumberAmountLeft(int lumberAmountLeft) {
        this.lumberAmountLeft = lumberAmountLeft;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = System.nanoTime();
    }
}
