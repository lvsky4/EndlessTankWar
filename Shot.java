public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0;
    int speed = 8;
    boolean isLive = true;

    static int state = 1;



    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirect() {
        return direct;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(state==1){
                switch (direct) {
                    case 0:
                        y -= speed;
                        break;
                    case 1:
                        x += speed;
                        break;
                    case 2:
                        y += speed;
                        break;
                    case 3:
                        x -= speed;
                        break;
                }
                if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
                    isLive = false;
                    break;
                }
            }
            else {
                continue;
            }
        }
    }


}
