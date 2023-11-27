import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener,Runnable {
    PlayerTank playerTank = null;
    int enemyTankSize = 0;

    int state = 1; //1：开始状态 2：正常游戏状态 3：死亡状态  4：暂停游戏
    String key;

    int go=0;

    Vector<enemyTank> enemyTanks = new Vector<>();
    Vector<AwardBall> balls = new Vector<>();

    Vector<Node> nodes = new Vector<>();
    public GamePanel() throws IOException {

        //选择是否重新开始


    }

    public enemyTank RandomTank(){
        int x = (int)(Math.random()*1000);
        int y = (int) (Math.random()*750);
        while(!(x>=0 && y>=0 && x+60<=1000 && y+60<=750) ){
             x = (int)(Math.random()*1000);
             y = (int) (Math.random()*750);
        }
        enemyTank tank = new enemyTank(x,y);
        if(enemyTanks==null)
            return tank;
        if(tank.isTouch(enemyTanks)){
            return null;
        }
        return tank;
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        if(state==1){
            drawStartInfo(g);
        }
        if(state==2 || state==4 ||state==3){
            drawNormal(g);
        }

    }

    public void drawNormal(Graphics g){
        showInfo(g);
        if(playerTank.isLive) //画玩家
            drawTank(playerTank.getX(),playerTank.getY(),g,playerTank.getDirect(),0);

        for (enemyTank tank:enemyTanks){ //画敌人以及子弹
            if(tank.isLive){
                drawTank(tank.getX(), tank.getY(), g, tank.getDirect(), 1);
                Vector<Shot> shots = tank.getShots();
                for (int i = 0; i < shots.size(); i++) {
                    Shot shot = shots.get(i);
                    if (shot.isLive) {
                        g.setColor(Color.yellow);
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        shots.remove(shot);
                        i--;
                    }
                }
            }
        }

        for(int i=0;i<playerTank.shots.size();i++){ //画我方子弹
            Shot s = playerTank.shots.get(i);
            if(s.isLive){
                g.setColor(Color.cyan);
                g.draw3DRect(s.getX(),s.getY(),1,1,false);
            }
            else {
                playerTank.shots.remove(s);
            }
        }
        drawBall(g);
    }

    public void drawTank(int x,int y,Graphics g,int direct,int type){ //画坦克

        switch (type){
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        switch (direct){ //0 refer up,1 refer right,2 refer down, 3 refer left
            case 0:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y +20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y +10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y +20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y +10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("keep");
        }

    }

    public void drawBall(Graphics g){
        for(int i=0;i<balls.size();i++){
            AwardBall ball = balls.get(i);
            if(ball.isLive){
                if(ball.getKind()==1) {
                    g.setColor(Color.cyan);
                    g.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
                } else {
                    g.setColor(Color.RED);
                    g.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
                }
            }
        }
    }

    public void hitTank(Shot s,Tank tank){ //评定是否击中坦克
        switch (tank.getDirect()){
            case 0:
            case 1:
            case 2:
                if(s.getX() > tank.getX() && s.x < tank.getX()+40
                        && s.getY() > tank.getY() && s.getY() < tank.getY() +60){
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    if(tank instanceof enemyTank) {
                        Recorder.addScore();
                        AwardBall ball = ((enemyTank) tank).addAward();
                        if(ball.getKind()!=0) balls.add(ball);
                    }
                    else state=3;
                }
                break;
            case 3:
                if(s.getX() > tank.getX() && s.x < tank.getX()+60
                        && s.getY() > tank.getY() && s.getY() < tank.getY() +40){
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    if(tank instanceof enemyTank) {
                        Recorder.addScore();
                        AwardBall ball = ((enemyTank) tank).addAward();
                        if(ball.getKind()!=0) balls.add(ball);
                    }
                    else state=3;
                }
                break;
        }
    }

    public void HitPlayer() {
        for(int i=0;i<enemyTanks.size();i++){
            enemyTank tank = enemyTanks.get(i);
            for(int j=0;j<tank.shots.size();j++){
                Shot s = tank.shots.get(j);
                if(playerTank.isLive && s.isLive){
                    hitTank(s,playerTank);
                }
            }
        }
    } //判定是否集中玩家

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(state==2){
            if (e.getKeyCode() == KeyEvent.VK_W) {
                playerTank.setDirect(0);
                playerTank.moveUp();
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                playerTank.setDirect(1);
                playerTank.moveRight();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                playerTank.setDirect(2);
                playerTank.moveDOwn();
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                playerTank.setDirect(3);
                playerTank.moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_J) {
                playerTank.shotFire();
            } else if(e.getKeyCode() == KeyEvent.VK_R ){
                System.out.println("zt");
                state=4;
                enemyTank.state=2;
                Shot.state=2;
            }
        }
        else if ((e.getKeyCode() == KeyEvent.VK_1 ) && state == 1) {
            state = 2;
            key = "1";
            NewGame();

        }
        else if (( e.getKeyCode()==KeyEvent.VK_2) && state == 1){
            try {
                continueGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            state = 2;
        }
        else if (e.getKeyCode() == KeyEvent.VK_J && state == 3) {
            enemyTankSize=0;
            go=0;
            NewGame();
            state=2;
        }
        else if(e.getKeyCode()==KeyEvent.VK_R && (state==4)){
            state=2;
            enemyTank.state=1;
            Shot.state=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_O && state == 3){
            playerTank.isLive=true;
            state=2;
            enemyTank.state=1;
            Shot.state=1;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }



    void continueGame() throws IOException {
        nodes = Recorder.getNodesAndEnemyTankRec();
        enemyTankSize = nodes.size()-1;
        System.out.println(enemyTankSize);
        if(nodes==null || nodes.size()==0){
            NewGame();
            return;
        }
        playerTank = new PlayerTank(nodes.get(0).getX(), nodes.get(0).getY(), nodes.get(0).getDirect(), nodes.get(0).getSpeed(), nodes.get(0).getShotNum());
        //playerTank.setX(3);
        for (int i = 1; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            enemyTank tank = new enemyTank(node.getX(), node.getY());
            tank.setDirect(node.getDirect());
            new Thread(tank).start();
            tank.setDirect(2);
            Shot shot = new Shot(tank.getX() + 20, tank.getY() + 60, tank.getDirect());
            tank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(tank);
        }
        Recorder.setEnemyTanks(enemyTanks);
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTank tank = enemyTanks.get(i);
            tank.setTanks(enemyTanks);
            enemyTanks.set(i, tank);
        }
        go=1;
    }



    void NewGame(){
        balls.clear();
        for(enemyTank tank:enemyTanks){
            tank.shots.clear();
        }
        enemyTanks.clear();
        playerTank = new PlayerTank(3,100);
        playerTank.isLive = true;
        //playerTank.setX(3);
        addEnemy();
        go=1;
        Recorder.setPlayer(playerTank);
        Recorder.setEnemyTanks(enemyTanks);
        for(int i=0;i<enemyTankSize;i++){
            enemyTank tank = enemyTanks.get(i);
            tank.setTanks(enemyTanks);
            enemyTanks.set(i,tank);
        }
    }

    void addEnemy(){
        if(!enemyTanks.isEmpty()) return;
        enemyTankSize++;

        for(int i=0;i<enemyTankSize;i++){
            enemyTank tank = RandomTank();
            if(tank==null){
                i--;
                continue;
            }
            enemyTanks.add(tank);
            new Thread(tank).start();
        }
        for(int i=0;i<enemyTankSize;i++){
            enemyTank tank = enemyTanks.get(i);
            tank.setTanks(enemyTanks);
            enemyTanks.set(i,tank);
        }

    }

    void GetAward(){
        for(int i=0;i<balls.size();i++){
            AwardBall ball = balls.get(i);
            if(ball.isGetBall(playerTank)){
                if(ball.getKind()==1) playerTank.setSpeed(playerTank.getSpeed()+1);
                if(ball.getKind()==2) playerTank.setShotNum(playerTank.getShotNum()+1);
                balls.remove(ball);
                ball.isLive=false;
                return;
            }
        }
    }

    @Override
    public void run() {
       while (true){
           if(state==3){
               enemyTank.state=2;
               Shot.state =2;
               this.repaint();
           }
           else if(state==1){
               this.repaint();
           }
           else if(state==2){
               if(go!=0)
                   addEnemy();
               enemyTank.state=1;
               Shot.state=1;
               try {
                   Thread.sleep(100);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               if(playerTank!=null && playerTank.shots!=null) {
                   for (int i = 0; i < playerTank.shots.size(); i++) { //判定是否击中敌人
                       Shot s = playerTank.shots.get(i);
                       for (int j = 0; j < enemyTanks.size(); j++) {
                           enemyTank tank = enemyTanks.get(j);
                           if (tank.isLive)
                               hitTank(s, tank);
                       }
                   }
               }
               HitPlayer(); //判定是否击中玩家
               GetAward();
               this.repaint();
           }
           else if(state==4){
                this.repaint();
           }
        }

    }

    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font= new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("您累计击败的敌方坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getScore()+"",1080,100);
        g.drawString("目前敌人的数量",1020,160);
        g.drawString(enemyTankSize+"",1250,160);
        g.drawString("目前玩家子弹的数量",1020,220);
        g.drawString(playerTank.getShotNum()+"",1300,220);
        g.drawString("目前玩家的速度",1020,280);
        g.drawString(playerTank.getSpeed()+"",1200,280);
        g.drawString("按R暂停/继续游戏",1020,360);
        g.drawString("死亡后按O复活 按J重新开始",1020,420);
        g.drawString("蓝球增加速度红球加子弹数",1020,480);
    }

    public void drawStartInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font= new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("按1开始新游戏 按2继续上一局",1020,30);
    }
}
