package entity;

import main.GamePanel;
import main.Key;
import java.awt.Graphics2D;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;

public class Player extends Entity{

 
    Key keyH;
    public final int screenX, screenY;
    public int hasKey = 0;
    

    public Player(GamePanel gp, Key keyH){
	super(gp);//pass gp to entity

	this.keyH=keyH;

	screenX = gp.screenWidth/2 - gp.tileSize/2;
	screenY = gp.screenHeight/2 - gp.tileSize/2;

	solidArea = new Rectangle(0, 0, 48, 48); //x,y,width,height
	solidAreaDefaultX = solidArea.x;
	solidAreaDefaultY = solidArea.y;

	attackArea = new Rectangle(0,0,48,48);
	
	setDefaultValues();
	getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize*22;
	worldY = gp.tileSize*23;

	speed = 5;
	direction = "down";
	level = 1;
	atk = 1;
	def = 1;
	exp = 0;
	nextExp = 5;

	maxLife = 6;
	life = maxLife;
    }

    public void getPlayerImage(){
	try{

	    up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/up_1.png"));
	    up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/up_2.png"));
	    down1  = ImageIO.read(getClass().getResourceAsStream("/res/player/down_1.png"));
	    down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/down_2.png"));
	    left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/left_1.png"));
	    left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/left_2.png"));
	    right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/right_1.png"));
	    right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/right_2.png"));
	    aup1 = ImageIO.read(getClass().getResourceAsStream("/res/player/aup_1.png"));
	    aup2 = ImageIO.read(getClass().getResourceAsStream("/res/player/aup_2.png"));
	    adown1  = ImageIO.read(getClass().getResourceAsStream("/res/player/adown_1.png"));
	    adown2 = ImageIO.read(getClass().getResourceAsStream("/res/player/adown_2.png"));
	    aleft1 = ImageIO.read(getClass().getResourceAsStream("/res/player/aleft_1.png"));
	    aleft2 = ImageIO.read(getClass().getResourceAsStream("/res/player/aleft_2.png"));
	    aright1 = ImageIO.read(getClass().getResourceAsStream("/res/player/aright_1.png"));
	    aright2 = ImageIO.read(getClass().getResourceAsStream("/res/player/aright_2.png"));
	}catch(IOException e){
	    e.printStackTrace();
	}
    }


    
    public void update(){

	if(attacking==true){
	    attack();
	    
	}

	else if(keyH.up == true ||keyH.down==true || keyH.left==true||keyH.right == true){
	    
	    
	    if(keyH.up == true){
		direction = "up";
		
	    }
	    else if(keyH.down == true){
		direction = "down";
		
	    }
	    else  if(keyH.left == true){
		direction = "left";
		
	    }
	    
	    else if(keyH.right == true){
		direction = "right";
		
	    }
	    
	    
	    collisionOn = false;
	    gp.cChecker.checkTile(this);
	    
	    int ObjIndex = gp.cChecker.checkObject(this, true);
	    //ObjInteract(ObjIndex);
	    
	    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	    contactDamage(npcIndex);
	    //npcInteract(npcIndex);
	    
	    
	    if(collisionOn == false){
		switch(direction){
		case "up":
		    worldY -= speed;
		    break;
		case "down":
		    worldY += speed;
		    break;
		case "left":
		    worldX -= speed;
		    break;
		case "right":
		    worldX += speed;
		    break;
		    
		}
	    }
	    

	    spriteCounter++;
	    if(spriteCounter>10){
		if(spriteNum == 1){
		    spriteNum = 2;
		}
		else if(spriteNum ==2){
		    spriteNum = 1;}
		
		spriteCounter = 0;}
	}
	//happens without key input
	if(invincible==true){
	    
	    invincibleCounter ++;
	    if(invincibleCounter>60){
		invincible=false;
		invincibleCounter=0;
	    }
	}
    }

    public void attack(){

	spriteCounter++;

	if(spriteCounter<=5){
	    spriteNum=1;
	}
	if(spriteCounter>5&&spriteCounter<25){
	    spriteNum=2;

	    //save current data
	    int currentWorldX = worldX;
	    int currentWorldY = worldY;
	    int solidAreaWidth = solidArea.width;
	    int solidAreaHeight = solidArea.height;

	    //adjust for attackarea
	    switch(direction){
	    case "up":
		worldY -= attackArea.height;
		break;
	    case "down":
		worldY += attackArea.height;
		break;
	    case "left":
		worldX -= attackArea.width;
		break;
	    case "right":
		worldX += attackArea.width;
		break;
	    }
	    solidArea.width=attackArea.width;
	    solidArea.height = attackArea.height;

	    int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
	    damageMonster(npcIndex);

	    worldX = currentWorldX;
	    worldY = currentWorldY;
	    solidArea.width = solidAreaWidth;
	    solidArea.height = solidAreaHeight;
	    
	}
        if(spriteCounter>25){
	    spriteNum=1;
	    spriteCounter = 0;
	    attacking=false;
	  
	}

    }

    public void contactDamage(int i){
	if(i != 999&&gp.npc[i].aggressive == true){
	    if(invincible == false){
		life --;
		invincible = true;
	    }
	}
    }

    public void damageMonster(int i){
	if(i != 999&&gp.npc[i].aggressive == true){
	    if(gp.npc[i].invincible == false){
		gp.npc[i].life -= atk;
		gp.npc[i].invincible = true;
		gp.npc[i].damageReaction();

		if(gp.npc[i].life<=0){
		    gp.npc[i].dying = true;
		    exp+=2;
		    checkLevelUp();
		}
	    }
	}

    }
    public void checkLevelUp(){
	if(exp>=nextExp){
	    level++;
	    exp-=nextExp;
	    nextExp+=4;
	    maxLife ++;
	    atk++;
	    def++;
	    life=maxLife;
	    gp.ui.addMessage("Level Up!");
	    
	}

    }

    public void ObjInteract(int i){
	if(i != 999){
	    String objectName = gp.obj[i].name;
	    switch(objectName){
	    case "Key":
		gp.ui.currentDialogue = "you picked up a key";
		gp.gameState = gp.dialogueState;
		hasKey++;
		gp.obj[i] = null;
        
		break;
	    case "Door":
		if(hasKey>0){
		    gp.ui.currentDialogue = "you used a key";
		    gp.gameState = gp.dialogueState;
		    gp.obj[i] = null;
		    hasKey--;
		}
		else{
	        gp.ui.currentDialogue = "you need a key";
		gp.gameState = gp.dialogueState;
		}
		break;
	    case "Chest":
		if(hasKey>0){
		  
		    gp.obj[i] = null;
		    hasKey--;
		   
		    gp.gameState = gp.shootingState;
		}
		else{
	        gp.ui.currentDialogue = "you need a key";
		gp.gameState = gp.dialogueState;
		}
		break;
	    case "Excalinyan":
	        gp.ui.currentDialogue = "伝説の剣\nエクスカリュバー";
		gp.gameState = gp.dialogueState;
		}
	    }
	}
    
    public void npcInteract(int i){
	if(i!=999){
	    gp.gameState = gp.dialogueState;
	    gp.npc[i].speak();
	    
	}
	
    }
    public void draw(Graphics2D g2){

	BufferedImage image = null;

	int x = screenX;
	int y = screenY;

	if(screenX>worldX){
	    x = worldX;
	}
	if(screenY>worldY){
	    y = worldY;
	}
	int rightOffset=gp.screenWidth - screenX;
	if(rightOffset>gp.worldWidth - worldX){
	    x  = gp.screenWidth - (gp.worldWidth - worldX);
	}
	int bottomOffset=gp.screenHeight -screenY;
	if(bottomOffset>gp.worldHeight -worldY){
	    y  = gp.screenHeight - (gp.worldHeight - worldY);
	}

	int tmpScreenX = x;
	int tmpScreenY= y;
	
	switch(direction){
	case "up":
	    if(attacking==false){
		if(spriteNum==1){image = up1;}
		else if(spriteNum==2) {image = up2;}}
	    else if(attacking==true){
		tmpScreenY=y-gp.tileSize;
		if(spriteNum==1){image = aup1;}
		else if(spriteNum==2) {image = aup2;}}
	    break;
	case "down":
	     if(attacking==false){
		 if(spriteNum==1){image = down1;}
		 else if(spriteNum==2) {image = down2;}}
	     else if(attacking==true){
		 if(spriteNum==1){image = adown1;}
		 else if(spriteNum==2) {image = adown2;}}
	     break;
        case "left":
	     if(attacking==false){
		 if(spriteNum==1){image = left1;}
		 else if(spriteNum==2){image = left2;}}
	     else if(attacking==true){
		  tmpScreenX = x-gp.tileSize;
		 if(spriteNum==1){image = aleft1;}
		 else if(spriteNum==2){image = aleft2;}}
	     break;
	case "right":
	     if(attacking==false){
		 if(spriteNum==1){image = right1;}
		 else  if(spriteNum==2){image = right2;}}
	     else if(attacking==true){
		 if(spriteNum==1){image = aright1;}
		 else  if(spriteNum==2){image = aright2;}}
	    break;
	}
	
	
	
	if(invincible==true){
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));//sets transparency of graphics2d
	}
	if(attacking==false){
	    g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
	if(attacking==true){
	    g2.drawImage(image, tmpScreenX, tmpScreenY, gp.tileSize*2, gp.tileSize*2, null);
	}
	
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	
    }
}
