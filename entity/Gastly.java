package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.util.Random;

public class Gastly  extends Entity{
    public Gastly(GamePanel gp){
	super(gp);
	direction = "down";
	speed = 2;
	collision = false;
	maxLife = 3;
	life = maxLife;

	getnpcImage();
	setDialogue();

    }
     public void getnpcImage(){
	try{

	    up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_up_1.png"));
	    up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_up_2.png"));
	    down1  = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_down_1.png"));
	    down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_down_2.png"));
	    left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_left_1.png"));
	    left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_left_2.png"));
	    right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_right_1.png"));
	    right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/gastly_right_2.png"));
	}catch(IOException e){
	    e.printStackTrace();
	}
    }

    public void setDialogue(){
	dialogue[0] = "you can see me?!";
	dialogue[1] = "maybe...\ncould it be?...";
	dialogue[2] = "お前はもう死んでいる";

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

	if(worldX>14*gp.tileSize) direction = "left";
	if(worldX<6*gp.tileSize) direction = "right";
	if(worldY>28*gp.tileSize) direction = "up";
	if(worldY<20*gp.tileSize) direction = "down";
	counter=0;
	}
	
    }

    public void speak(){
	super.speak();
    }
}
