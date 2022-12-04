package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.util.Random;

public class Slowbro extends Entity{
    public Slowbro(GamePanel gp){
	super(gp);
	direction = "down";
	speed = 2;

	maxLife = 10;
	life = maxLife;

	getnpcImage();
	setDialogue();

    }
     public void getnpcImage(){
	try{

	    up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_up_1.png"));
	    up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_up_2.png"));
	    down1  = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_down_1.png"));
	    down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_down_2.png"));
	    left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_left_1.png"));
	    left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_left_2.png"));
	    right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_right_1.png"));
	    right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/slowbro_right_2.png"));
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    public void setDialogue(){
	dialogue[0] = "slowwwwww";

    }
    public void setAction(){
	counter++;
	if(counter ==60){
	Random random = new Random();
	int i = random.nextInt(100);//picks 0-99
	if(i<=25)direction = "up";
	else if(i<=50) direction = "down";
      	else if(i<=75) direction = "right";
	else if(i<=100) direction = "left";

	if(aggressive==false){
	if(worldX>26*gp.tileSize) direction = "left";
	if(worldX<20*gp.tileSize) direction = "right";
	if(worldY>30*gp.tileSize) direction = "up";
	if(worldY<20*gp.tileSize) direction = "down";}
	counter=0;
	}
	
    }

    public void speak(){
	super.speak();
    }
}

