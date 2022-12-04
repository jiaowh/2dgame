package object;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;

import main.GamePanel;

public class SuperObject{

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision;
    public int worldX, worldY;
    public Rectangle solidArea= new Rectangle(0,0,48,48);//whole tile is solid -> can set individually in object class later
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    
    public void draw(Graphics2D g2, GamePanel gp){
	
	int screenX = worldX - gp.player.worldX + gp.player.screenX;
	int screenY = worldY - gp.player.worldY + gp.player.screenY;
	int rightOffset=gp.screenWidth - gp.player.screenX;
	int bottomOffset = gp.screenHeight - gp.player.screenY;
	   if(gp.player.screenX>gp.player.worldX){
		screenX = worldX;
	    }
	    if(gp.player.screenY>gp.player.worldY){
		screenY = worldY;
	    }
	   
	    if(rightOffset>gp.worldWidth - gp.player.worldX){
		screenX = gp.screenWidth - (gp.worldWidth - worldX);
	    }
	    
	    if(bottomOffset>gp.worldHeight - gp.player.worldY){
		screenY = gp.screenHeight - (gp.worldHeight - worldY);}
        if(worldX + gp.tileSize >gp.player.worldX - gp.player.screenX &&
	   worldX - gp.tileSize <gp.player.worldX + gp.player.screenX &&
	   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
	   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);}
	else if(gp.player.screenX>gp.player.worldX||gp.player.screenY>gp.player.worldY||rightOffset>gp.worldWidth - gp.player.worldX||bottomOffset>gp.worldHeight - gp.player.worldY ){
	g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	    }

    }
}
