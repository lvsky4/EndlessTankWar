import java.util.Vector;

public class PlayerTank extends Tank {

    Shot shot = null;
    int ShotNum = 4;

    public int getShotNum() {
        return ShotNum;
    }


    public void setShotNum(int shotNum) {
        ShotNum = shotNum;
    }

    Vector<Shot> shots = new Vector<>();

    public PlayerTank(int x,int y,int direct,int speed,int shotNum){
        super(x,y);
        this.setSpeed(speed);
        this.setShotNum(shotNum);
        this.setDirect(direct);
    }

    public PlayerTank(int x,int y){
        super(x,y);
    }
    public Shot getShot() {
        return shot;
    }

    public void shotFire(){
        if( shots.size()>=ShotNum )
            return;
        switch (getDirect()){
            case 0:
                shot = new Shot(getX()+20,getY(),0);
                shots.add(shot);
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,1);
                shots.add(shot);
                break;
            case 2:
                shot = new Shot(getX() +20,getY() +60,2);
                shots.add(shot);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                shots.add(shot);
                break;
        }
        new Thread(shot).start();
    }

}
