import java.util.Vector;

public class Tank {
    private int x;
    private int y;

    boolean isLive = true;
    private int speed = 1;
    private int direct; //0 refer up,1 refer right,2 refer down, 3 refer left

    public void moveUp(){
        if(y-speed>=0)
            y -=speed;
    }

    public void moveRight() {
        if(x+60 +speed <= 1000)
            x += speed;
    }
    public void moveDOwn() {
        if(y+speed+60 <= 750)
            y+= speed;
    }
    public void moveLeft() {
        if(x-speed >=0)
            x -= speed;
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
