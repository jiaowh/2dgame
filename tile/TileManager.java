package tile;

import main.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;


public class TileManager{

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
	this.gp = gp;
	tile = new Tile[50];
	mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
	getTileImage();
	loadMap("/res/maps/world01.txt");
    }

    public void getTileImage(){

	    loadTile(0,"tree_1.png", true);
	    loadTile(1,"tree_1.png", true);
	    loadTile(2,"tree_1.png", true);
	    loadTile(3,"tree_1.png", true);
	    loadTile(4,"tree_1.png", true);
	    loadTile(5,"tree_1.png", true);
	    loadTile(6,"tree_1.png", true);
	    loadTile(7,"tree_1.png", true);
	    loadTile(8,"tree_1.png", true);
	    loadTile(9,"tree_1.png", true);
	    loadTile(10,"tree_1.png", true);
	    loadTile(11,"tree2.png", true);
	    loadTile(12,"tree_3.png", true);
	    loadTile(13,"tree4.png", true);
	    loadTile(14, "grass1.png", false); 
	    loadTile(15, "grass2.png", false);
	    loadTile(16,"water.png", false);
	    loadTile(17, "water1.png", false);
	    loadTile(18, "water2.png", true);
	    loadTile(19, "water3.png", true);
	    loadTile(20, "water4.png", true);
	    loadTile(21, "water5.png", true);
	    loadTile(22, "water6.png", true);
	    loadTile(23, "water7.png", true);
	    loadTile(24, "water8.png", true);
	    loadTile(25, "water9.png", true);
	    loadTile(26, "carpet.png", false);
	    loadTile(27, "dirt.png", false);
	    loadTile(28, "wall.png", true);
	    loadTile(29, "water10.png", true);
	    loadTile(30, "water11.png", true);
	    loadTile(31, "water12.png", true);
	    loadTile(32, "water13.png", true);
	    loadTile(33,"road1.png", false);
	    loadTile(34,"road2.png", false);
	    loadTile(35,"road3.png", false);
	    loadTile(36,"road4.png", false);
	    loadTile(37,"road5.png", false);
	    loadTile(38,"road6.png", false);
	    loadTile(39,"road7.png", false);
	    loadTile(40,"road8.png", false);
	    loadTile(41,"road9.png", false);
	    loadTile(42,"road10.png", false);
	    loadTile(43,"road11.png", false);
	    loadTile(44,"road12.png", false);
	    

	
    }

    public void loadTile(int index, String name, boolean collision){
	try{
	    tile[index]= new Tile();
	    tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+name));
	    BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[index].image.getType());
	    Graphics2D g2 = scaledImage.createGraphics();
	    g2.drawImage(tile[index].image,0,0,gp.tileSize, gp.tileSize, null);
	    g2.dispose();
	    tile[index].image = scaledImage;
	    tile[index].collision = collision;
	}catch(IOException e){
	    e.printStackTrace();
	   
	}
    }



    public void loadMap(String filepath){
	try{
	    InputStream is = getClass().getResourceAsStream(filepath);
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));

	    int col = 0;
	    int row = 0;
	    while(col<gp.maxWorldCol && row<gp.maxWorldRow){
		String line = br.readLine();
		while(col<gp.maxWorldCol){
		    String numbers[] = line.split(" ");//splits string around " "
		    int num = Integer.parseInt(numbers[col]);
		    mapTileNum[col][row] = num;
		    col++;
		}
		if(col == gp.maxWorldCol){
		    col = 0;
		    row++;
		}
		
	    }
	    br.close();
	}catch(Exception e){

	}

    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
	int worldRow = 0;


	while(worldCol<gp.maxWorldCol && worldRow < gp.maxWorldRow){

	    int tileNum = mapTileNum[worldCol][worldRow];

	    int worldX = worldCol * gp.tileSize;
	    int worldY = worldRow * gp.tileSize;
	    int screenX = worldX - gp.player.worldX + gp.player.screenX;
	    int screenY = worldY - gp.player.worldY + gp.player.screenY;

	    //stop moving camera
	    if(gp.player.screenX>gp.player.worldX){
		screenX = worldX;
	    }
	    if(gp.player.screenY>gp.player.worldY){
		screenY = worldY;
	    }
	    int rightOffset=gp.screenWidth - gp.player.screenX;
	    if(rightOffset>gp.worldWidth - gp.player.worldX){
		screenX = gp.screenWidth - (gp.worldWidth - worldX);
	    }
	     int bottomOffset=gp.screenHeight - gp.player.screenY;
	    if(bottomOffset>gp.worldHeight - gp.player.worldY){
		screenY = gp.screenHeight - (gp.worldHeight - worldY);
	    }
	    

	    
	    
	    if(worldX + gp.tileSize >gp.player.worldX - gp.player.screenX &&
	       worldX - gp.tileSize <gp.player.worldX + gp.player.screenX &&
	       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
	       worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
		g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);}

	    else if(gp.player.screenX>gp.player.worldX||gp.player.screenY>gp.player.worldY||rightOffset>gp.worldWidth - gp.player.worldX||bottomOffset>gp.worldHeight - gp.player.worldY ){
	g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	    }
	    worldCol++;
	   
	    if(worldCol==gp.maxWorldCol){
		worldCol = 0;
        
		worldRow++;
        
	    }
	    
	}
    }
}
