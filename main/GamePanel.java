package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.*;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol;
    public final int screenHeight = tileSize*maxScreenRow;

    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    

    int fps = 60;

    TileManager tileM = new TileManager(this);
    Key keyH = new Key(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public UI ui = new UI(this);
    public Entity npc[] = new Entity[10];
    public Shooting shooting = new Shooting(this);

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int statusState = 4;
    public final int shootingState = 5;
    
    int playerX= 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    public GamePanel(){
	this.setPreferredSize(new Dimension(screenWidth,screenHeight));
	this.setDoubleBuffered(true);
	this.addKeyListener(keyH);
	this.setFocusable(true);

	
    }

    public void setupGame(){
	aSetter.setObject();
	aSetter.setNPC();
	gameState = titleState;
    }
    
    public void startGameThread(){

	gameThread = new Thread(this);
	gameThread.start();//calls run method automatically
    }

    public void run(){

	double drawInterval = 1000000000/fps;
	double nextDrawTime = System.nanoTime() + drawInterval;
	
	while(gameThread != null){
	  	    
	    update();
	    repaint();//calls paintcomponent
	   
	    try{
		 double remainingTime = nextDrawTime - System.nanoTime();
		 remainingTime = remainingTime/1000000;

		 if(remainingTime <0) remainingTime=0;
		Thread.sleep((long)remainingTime);
		nextDrawTime += drawInterval;
	    }catch (InterruptedException e){
		e.printStackTrace();
	    }
	}

    }

    public void update(){

	if(gameState == playState){
	    player.update();
	    for(int i = 0; i<npc.length; i++){
		if(npc[i]!=null){
		    if(npc[i].alive == true){
			npc[i].update();
		    }
		    if(npc[i].alive==false){
			
			npc[i]=null;
			
		    }
		}
	    }
	}
	    
	
	    if(gameState == pauseState){
		//nil
	    }
	    if(gameState == dialogueState){}
	    if(gameState==shootingState){
		shooting.update();
        
	    }
	
    }
	public void paintComponent(Graphics g){
	super.paintComponent(g);

	Graphics2D g2 = (Graphics2D)g;

	if(gameState == titleState){
	    ui.draw(g2);
	}

	if(gameState == playState || gameState == pauseState|| gameState == dialogueState||gameState == statusState){
	    //tile
	    tileM.draw(g2);

	    //obj
	    for(int i = 0; i<obj.length; i++){
		if(obj[i]!= null){
		    obj[i].draw(g2, this);
		}
	    }

	    //npc
	    for(int i = 0; i<npc.length; i++){
		if(npc[i]!=null){
		    npc[i].draw(g2);
		}
	    }

	    //player
	    player.draw(g2);

	    //ui
	    ui.draw(g2);
	    g2.dispose();
	}
	if(gameState == shootingState){
	    shooting.draw(g2);
	}
    }
}
