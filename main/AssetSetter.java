package main;
import object.Key;
import object.Door;
import object.Chest;
import object.Excalinyan;
import entity.Tentacool;
import entity.Gastly;
import entity.Magikarp;
import entity.Slowbro;
import entity.Omanyte;

public class AssetSetter{
    GamePanel gp;
    public AssetSetter(GamePanel gp){
	this.gp = gp;
    }

    public void setObject(){
	gp.obj[0] = new Key();
	gp.obj[0].worldX = 43*gp.tileSize;
	gp.obj[0].worldY = 3*gp.tileSize;
	
	gp.obj[1] = new Key();
	gp.obj[1].worldX = 5*gp.tileSize;
	gp.obj[1].worldY = 39*gp.tileSize;

	gp.obj[2] = new Door();
	gp.obj[2].worldX = 19*gp.tileSize;
	gp.obj[2].worldY = 33*gp.tileSize;

       	gp.obj[3] = new Chest();
	gp.obj[3].worldX = 45*gp.tileSize;
	gp.obj[3].worldY = 38*gp.tileSize;

	gp.obj[4] = new Excalinyan();
	gp.obj[4].worldX = 11*gp.tileSize;
	gp.obj[4].worldY = 23*gp.tileSize;
	
    }

    public void setNPC(){
	gp.npc[0] = new Tentacool(gp);
	gp.npc[0].worldX = gp.tileSize*23;
	gp.npc[0].worldY = gp.tileSize*17;

	gp.npc[1] = new Gastly(gp);
	gp.npc[1].worldX = gp.tileSize*10;
	gp.npc[1].worldY = gp.tileSize*24;

	gp.npc[2]= new Magikarp(gp);
	gp.npc[2].worldX = gp.tileSize*26;
	gp.npc[2].worldY = gp.tileSize*12;

	gp.npc[3]= new Slowbro(gp);
	gp.npc[3].worldX = gp.tileSize*23;
	gp.npc[3].worldY = gp.tileSize*26;

	gp.npc[4]= new Omanyte(gp);
	gp.npc[4].worldX = gp.tileSize*10;
	gp.npc[4].worldY = gp.tileSize*26;

	gp.npc[5]= new Omanyte(gp);
	gp.npc[5].worldX = gp.tileSize*40;
	gp.npc[5].worldY = gp.tileSize*40;
	gp.npc[5].aggressive = true;

	gp.npc[6]= new Slowbro(gp);
	gp.npc[6].worldX = gp.tileSize*39;
	gp.npc[6].worldY = gp.tileSize*41;
	gp.npc[6].aggressive = true;

	gp.npc[7]= new Tentacool(gp);
	gp.npc[7].worldX = gp.tileSize*38;
	gp.npc[7].worldY = gp.tileSize*42;
	gp.npc[7].aggressive = true;

	gp.npc[8]= new Tentacool(gp);
	gp.npc[8].worldX = gp.tileSize*37;
	gp.npc[8].worldY = gp.tileSize*43;
	gp.npc[8].aggressive = true;

	gp.npc[9]= new Omanyte(gp);
	gp.npc[9].worldX = gp.tileSize*36;
	gp.npc[9].worldY = gp.tileSize*44;
	gp.npc[9].aggressive = true;
	

    }
}
