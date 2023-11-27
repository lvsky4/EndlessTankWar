public class AwardBall {
    int x;
    int y;
    int radius;
    int kind; //1:加速 2：加子弹

    boolean isLive = true;

    boolean isGetBall(PlayerTank tank){
        if(tank.getDirect()==0 || tank.getDirect()==2){
            if(tank.getX()+40>=x-20 && tank.getX()<=x+20 && tank.getY()+60>=y-20 && tank.getY()<= y+20 ){
                return true;
            }
        } else {
            if(tank.getX()+60>=x-20 && tank.getX()<=x+20 && tank.getY()+40>=y-20 && tank.getY()<= y+20 ){
                return true;
            }
        }
        return false;
    }

    public AwardBall(int x, int y, int radius, int kind) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.kind = kind;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
