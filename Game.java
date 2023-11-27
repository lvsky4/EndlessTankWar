import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

/*
√1.对于玩家坦克，可以多个射击.
√2.改子弹颜色，增加敌我子弹显示。我方在子弹销毁前无法射出第二发。
√3.击败敌人随机掉落道具增加子弹数量，或者速度，或者增加血量。
√4.增加游戏分数，根据游戏分数增加难度。
(暂停)5.增加玩家血量，并显示。
√6.暂停游戏 和重新开始游戏，完善重新上一局功能，复活功能。
√7.增加记录速度，子弹数量，这波敌人数量。
√8.随机位置产生敌人
    */
public class Game extends JFrame {
    GamePanel gamePanel = null;
    public static void main(String[] args) throws IOException {
//        System.out.println("1:新游戏 2：继续上一局");
//        Scanner scanner = new Scanner(System.in);
       // String key = scanner.next();
        Game game = new Game();
    }
    public Game() throws IOException {

        gamePanel = new GamePanel();
        Thread thread = new Thread(gamePanel);
        thread.start();
        this.add(gamePanel);
        this.setSize(1400,950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(gamePanel);
        this.setVisible(true);
        this.setTitle("EndlessTankWar");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
