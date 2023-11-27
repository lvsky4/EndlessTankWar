import java.util.Map;
import java.util.Vector;

public class enemyTank extends Tank implements Runnable{
    public enemyTank(int x,int y ){
        super(x, y);
    }
    Vector<enemyTank> Tanks;

    static int state = 1;

    public Vector<enemyTank> getTanks() {
        return Tanks;
    }

    public void setTanks(Vector<enemyTank> tanks) {
        Tanks = tanks;
    }

    Vector<Shot> shots = new Vector<>();

    public Vector<Shot> getShots() {
        return shots;
    }

    public boolean isTouch(Vector<enemyTank> tanks){
        if(tanks==null) return false;
        for(int i=0;i<tanks.size();i++){
            enemyTank tank = tanks.get(i);
            if(tank==this)
                continue;
            switch (getDirect()) {
                case 0 -> {
                    if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 40 && getY() >= tank.getY() && getY() <= tank.getY() + 60)
                            return true;
                        if (getX() + 40 >= getX() && getX() + 40 <= tank.getX() + 40 && getY() >= tank.getY() && getY() <= tank.getY() + 60)
                            return true;
                    }
                    if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 60 && getY() >= getY() && getY() <= tank.getY() + 40)
                            return true;
                        if (getX() + 40 >= tank.getX() && getX() + 40 <= tank.getX() + 60 && getY() >= tank.getY() && getY() <= tank.getY() + 40)
                            return true;
                    }
                }
                case 1 -> {
                    if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                        if (getX() + 60 >= tank.getX() && getX() + 60 <= tank.getX() + 40 && getY() >= tank.getY() && getY() <= tank.getY() + 60)
                            return true;
                        if (getX() + 40 >= getX() && getX() + 40 <= tank.getX() + 40 && getY() + 40 >= tank.getY() && getY() + 40 <= tank.getY() + 60)
                            return true;
                    }
                    if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                        if (getX() + 60 >= tank.getX() && getX() + 60 <= tank.getX() + 60 && getY() >= getY() && getY() <= tank.getY() + 40)
                            return true;
                        if (getX() + 60 >= tank.getX() && getX() + 60 <= tank.getX() + 60 && getY() + 40 >= tank.getY() && 40 + getY() <= tank.getY() + 40)
                            return true;
                    }
                }
                case 3 -> {
                    if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 40 && getY() + 60 >= tank.getY() && 60 + getY() <= tank.getY() + 60)
                            return true;
                        if (getX() + 40 >= getX() && getX() + 40 <= tank.getX() + 40 && getY() + 60 >= tank.getY() && getY() + 60 <= tank.getY() + 60)
                            return true;
                    }
                    if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 60 && getY() + 60 >= getY() && getY() + 60 <= tank.getY() + 40)
                            return true;
                        if (getX() + 40 >= tank.getX() && getX() + 40 <= tank.getX() + 60 && getY() + 60 >= tank.getY() && 60 + getY() <= tank.getY() + 40)
                            return true;
                    }
                }
                case 4 -> {
                    if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 40 && getY() >= tank.getY() && getY() <= tank.getY() + 60)
                            return true;
                        if (getX() >= getX() && getX() <= tank.getX() + 40 && getY() + 40 >= tank.getY() && getY() + 40 <= tank.getY() + 60)
                            return true;
                    }
                    if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 60 && getY() >= getY() && getY() <= tank.getY() + 40)
                            return true;
                        if (getX() >= tank.getX() && getX() <= tank.getX() + 60 && getY() + 40 >= tank.getY() && 40 + getY() <= tank.getY() + 40)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public AwardBall addAward(){
        int rate = (int) (Math.random() *100);
        AwardBall ball = new AwardBall(getX()+10,getY() +20,20,0);
        if(rate<40){
            ball.setKind(1);
        }
        if(rate>=40 && rate<80){
            ball.setKind(2);
        }
        return ball;
    }



    @Override
    public void run() {

        while (true){
            System.out.println(state);
            if(state==1){
                //System.out.println("go");
                Shot s;
                switch (getDirect()) {
                    case 0 -> {
                        s = new Shot(getX() + 20, getY(), 0);
                        new Thread(s).start();
                        shots.add(s);
                        for (int i = 0; i < 20; i++) {
                            if (getY() - getSpeed() < 0) {
                                break;
                            }
                            if (Tanks != null && !isTouch(Tanks))
                                moveUp();
                            else break;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                    case 1 -> {
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        new Thread(s).start();
                        shots.add(s);
                        for (int i = 0; i < 20; i++) {
                            if (getX() + 60 + getSpeed() > 1000) {
                                break;
                            }
                            if (Tanks != null && !isTouch(Tanks))
                                moveRight();
                            else break;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                    case 2 -> {
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        new Thread(s).start();
                        shots.add(s);
                        for (int i = 0; i < 20; i++) {
                            if (getY() + getSpeed() + 60 > 750) {
                                break;
                            }
                            if (Tanks != null && !isTouch(Tanks))
                                moveDOwn();
                            else break;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                    case 3 -> {
                        s = new Shot(getX(), getY() + 20, 3);
                        new Thread(s).start();
                        shots.add(s);
                        for (int i = 0; i < 20; i++) {
                            if (getX() - getSpeed() < 0) {
                                break;
                            }
                            if (Tanks != null && !isTouch(Tanks))
                                moveLeft();
                            else break;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                }
                setDirect((int) (Math.random() * 4));
                if (!isLive)
                    break;
            }
        }

    }
}
