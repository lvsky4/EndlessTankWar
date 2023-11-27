import java.io.*;
import java.util.Objects;
import java.util.Vector;

public class Recorder {
    private static int score = 0;
    private static FileWriter fw = null;
    private static BufferedWriter bw =null;

    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";

    private static Vector<Node> nodes = new Vector<>();

    private static PlayerTank player = null;

    public static Vector<Node> getNodes() {
        return nodes;
    }

    public static void setNodes(Vector<Node> nodes) {
        Recorder.nodes = nodes;
    }

    public static Vector<Node> getNodesAndEnemyTankRec() throws IOException {
        br = new BufferedReader(new FileReader(recordFile));
        String s;
        if( (s= br.readLine())!=null )
            score = Integer.parseInt(s);
        String line = "";
        while( (line= br.readLine())!=null ){
            String[] xyd = line.split(" ");
            if(Objects.equals(xyd[0], ""))
                return nodes;
            Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]),Integer.parseInt(xyd[3]),Integer.parseInt(xyd[4]));
            nodes.add(node);
        }
        if(br !=null){
            br.close();
        }
        return nodes;
    }

    private static Vector<enemyTank> enemyTanks = null;

    public static Vector<enemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void setEnemyTanks(Vector<enemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Recorder.score = score;
    }

    public static void addScore(){
        Recorder.score++;
    }

    public static PlayerTank getPlayer() {
        return player;
    }

    public static void setPlayer(PlayerTank player) {
        Recorder.player = player;
    }

    public static void keepRecord() throws IOException {
        bw = new BufferedWriter(new FileWriter(recordFile));
        bw.write(score +"\r\n" );
        if(enemyTanks==null || enemyTanks.isEmpty()){
            return;
        }
        String record_p = "";
        if(player!=null)
            record_p = player.getX() +" "+ player.getY()+" "+player.getDirect()+" "+player.getSpeed()+" "+ player.getShotNum();
        bw.write(record_p+"\r\n");

        for(int i = 0;i<enemyTanks.size();i++){
            enemyTank tank = enemyTanks.get(i);
            if(tank.isLive){
                String record = tank.getX() + " " + tank.getY() + " "+ tank.getDirect()+" "+"0"+" "+"0";
                bw.write(record+ "\r\n");
            }
        }
        if(bw!=null)
            bw.close();
    }
}
