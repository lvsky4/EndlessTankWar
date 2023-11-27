public class Node {
    int x;
    int y;
    int direct;

    int speed=1;
    int shotNum=3;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getShotNum() {
        return shotNum;
    }

    public void setShotNum(int shotNum) {
        this.shotNum = shotNum;
    }

    public Node(int x, int y, int direct, int speed, int shotNum) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
        this.shotNum = shotNum;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
