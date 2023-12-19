package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGame;
import com.mygdx.game.ServernayaChast.UDPClient;
import com.mygdx.game.entity.Enemy;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.Skeleton;
import com.mygdx.game.input.input;
import com.mygdx.game.map.AnimatedMap;
import com.mygdx.game.map.LvlManager;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class GameScreen extends ScreenAdapter {


    private Player player;
    private Player player1;

    private input inputProcessor;

    public int TileSize;
    private Texture PlayerText;
    private LvlManager lvlManager;
    public static final int WIDTH = 400, HEIGHT = 300;

    private static GameState PreviousGS;
    private static GameState CurrentGS;

    private UDPClient client;


    public OrthographicCamera camera;

    private LinkedList<Vector2> PredictInput;
    private Vector2 CurrentPlayerPos;
    private LinkedList<Vector2> PredictPlayerPos;
    private LinkedList<String> Message;
    private Vector2 dir;

    private AnimatedMap animatedMap;

    private Enemy[] Entities;

    private MyGame game;
    public GameScreen(MyGame game){
        this.game = game;
        Message = new LinkedList<>();
        CurrentPlayerPos = new Vector2();
        dir = new Vector2();
        PredictInput = new LinkedList<>();
        PredictPlayerPos = new LinkedList<>();
        PreviousGS = new GameState(new Timestamp(System.currentTimeMillis()), new Vector2(0, 0), new Vector2(0, 0), 0, 6, 6);
        CurrentGS = new GameState(new Timestamp(System.currentTimeMillis()), new Vector2(0, 0), new Vector2(0, 0), 0, 6 , 6);
        PlayerText = new Texture("character.png");
        lvlManager = new LvlManager();
        TileSize = 32;
        player = new Player(0, 0, 6, 0, 0.3F, 1, PlayerText);
        inputProcessor = new input(player);

        camera = new OrthographicCamera(WIDTH, HEIGHT);    // 6
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        player1 = new Player(0, 0, 6, 0, 0.3F, 1, PlayerText);
        Skeleton first = new Skeleton(100,100,0,0,2,0);
        Entities = new Enemy[10];
        Entities[0] = first;
        client = game.getUdpClient();
        Update();

        animatedMap = new AnimatedMap(lvlManager.map);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void UpdatePlayerPos() {
        if (player.getNumber() == 1) {
        /*    PredictPlayerPos.set(CurrentGS.LastZap, CurrentGS.Pl1Pos);
            for (int i = CurrentGS.LastZap; i < PredictInput.size(); i++) {
                PredictPlayerPos.set(i, PredictPlayerPos.get(i).add(PredictInput.get(i)));
            }
            player.SetPos(PredictPlayerPos.getLast());*/
        }
        if (player.getNumber() == 2) {
           /* PredictPlayerPos.set(CurrentGS.LastZap, CurrentGS.Pl2Pos);
            for (int i = CurrentGS.LastZap; i < PredictInput.size(); i++) {
                PredictPlayerPos.set(i, PredictPlayerPos.get(i).add(PredictInput.get(i)));
            }
            player.SetPos(PredictPlayerPos.getLast());*/
        }
    }
    public void UpdateInfo(){
        if (player.getNumber() == 1){
            if(CurrentGS.HPPL1 != player.GetHP()){
                player.SetHp(CurrentGS.HPPL1);
            }
        }
        if (player.getNumber() == 2){
            if(CurrentGS.HPPL2 != player.GetHP()){
                player.SetHp(CurrentGS.HPPL2);
            }
        }
    }

    public void render(){



        ScreenUtils.clear(1, 1, 1, 1);
        dir.set(inputProcessor.getDir());
        player.MoveTo(dir);
        PredictInput.add(dir);
        PredictPlayerPos.add(player.getPos());

        try {
            Message.add(client.NewMessage(String.valueOf(dir)));
            client.SendMessage(String.valueOf(dir));
            client.IncZap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();


        if (player.getNumber() == 1) {
            synchronized (CurrentGS) {
                synchronized (PreviousGS) {
                    player1.InterPolation(PreviousGS.Pl2Pos.x, PreviousGS.Pl2Pos.y, CurrentGS.Pl2Pos.x, CurrentGS.Pl2Pos.y, PreviousGS.getTimestamp(), CurrentGS.getTimestamp());
                    if(player.getPos()!=CurrentGS.Pl1Pos) {
                        UpdatePlayerPos();
                        UpdateInfo();
                    }
                }
            }
        }
        if (player.getNumber() == 2) {
            synchronized (CurrentGS) {
                synchronized (PreviousGS) {
                    player1.InterPolation(PreviousGS.Pl1Pos.x, PreviousGS.Pl1Pos.y, CurrentGS.Pl1Pos.x, CurrentGS.Pl1Pos.y, PreviousGS.getTimestamp(), CurrentGS.getTimestamp());
                    if(player.getPos()!=CurrentGS.Pl2Pos) {
                        UpdatePlayerPos();
                        UpdateInfo();
                    }
                }
            }
        }




        lvlManager.RenderMap(camera,player,player1, Entities);

    }

    @Override
    public void render(float delta) {
        render();
    }

    public void Update() {
        Thread udpClientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = client.ReceiveMessage();
                        if (message.equals(null)) {
                            CurrentGS = PreviousGS;
                            continue;
                        }
                        synchronized (PreviousGS) {
                            synchronized (CurrentGS) {
                                String[] Words = message.split(" ");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                                Date parsedDate = null;
                                try {
                                    parsedDate = dateFormat.parse(Words[1] + " " + Words[2]);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                PreviousGS = CurrentGS;
                                CurrentGS = UpdateGameState(message);
                            }
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        udpClientThread.start();
    }

    private GameState UpdateGameState(String message) throws IOException {
        String[] InputWords = message.split(" ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        float HPPL1;
        float HPPL2;
        try {
            parsedDate = dateFormat.parse(InputWords[1] + " " + InputWords[2]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        player.setNumber(Integer.parseInt(InputWords[0]));
        if (Integer.parseInt(InputWords[0]) == 1) {
            player1.setNumber(2);
        } else {
            player1.setNumber(1);
        }

        String PosX1 = "";
        String PosY1 = "";
        int i = 1;
        while (InputWords[3].charAt(i) != ',') {
            PosX1 += String.valueOf(InputWords[3].charAt(i));
            i++;
        }
        i++;
        while (InputWords[3].charAt(i) != ')') {
            PosY1 += String.valueOf(InputWords[3].charAt(i));
            i++;
        }

        String PosX2 = "";
        String PosY2 = "";
        int y = 1;
        while (InputWords[4].charAt(y) != ',') {
            PosX2 += String.valueOf(InputWords[4].charAt(y));
            y++;
        }
        y++;
        while (InputWords[4].charAt(y) != ')') {
            PosY2 += String.valueOf(InputWords[4].charAt(y));
            y++;
        }
        int LastServerZap = Integer.parseInt(InputWords[5]);

        HPPL1 = Float.valueOf(String.valueOf(InputWords[3].charAt(InputWords[3].length()-1)));

        HPPL2 =  Float.valueOf(String.valueOf(InputWords[4].charAt(InputWords[4].length()-1)));

        return new GameState(new Timestamp(parsedDate.getTime()), new Vector2(Float.valueOf(PosX1), Float.valueOf(PosY1)), new Vector2(Float.valueOf(PosX2), Float.valueOf(PosY2)), LastServerZap, HPPL1, HPPL2);
    }

    private class GameState {
        Timestamp timestamp;
        Vector2 Pl1Pos;
        Vector2 Pl2Pos;
        int LastZap;

        float HPPL1;
        float HPPL2;

        public GameState(Timestamp timestamp, Vector2 Pl1Pos, Vector2 Pl2Pos, int LastZap, float HPPL1, float HPPL2) {
            this.timestamp = timestamp;
            this.Pl1Pos = Pl1Pos;
            this.Pl2Pos = Pl2Pos;
            this.LastZap = LastZap;
            this.HPPL1 = HPPL1;
            this.HPPL2 = HPPL2;
        }

        public Vector2 getPl2Pos() {
            return Pl2Pos;
        }

        public Vector2 getPl1Pos() {
            return Pl1Pos;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }
    }
}

