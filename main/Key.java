package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

public class Key implements KeyListener{
    GamePanel gp;
  
   
  
    public boolean up, down, left, right;
    public Key(GamePanel gp){
	this.gp = gp;

    }
   
    
    public void keyTyped(KeyEvent e){

    }

    public void keyPressed(KeyEvent e){
	int code = e.getKeyCode();

	if(gp.gameState == gp.playState){
	    if(code==KeyEvent.VK_W){
		up = true;
	    }
	    if(code==KeyEvent.VK_S){
		down = true;
	    }
	    if(code==KeyEvent.VK_A){
		left=true;
	    }
	    if(code==KeyEvent.VK_D){
		right=true;
	    }
	    if(code==KeyEvent.VK_P){
	        gp.gameState = gp.pauseState;
	    
	    }
	    if(code==KeyEvent.VK_I){
		gp.gameState=gp.statusState;
	    }
	    if(code == KeyEvent.VK_ENTER){
		int npcIndex = gp.cChecker.checkEntity(gp.player, gp.npc);
		gp.player.npcInteract(npcIndex);
		int ObjIndex = gp.cChecker.checkObject(gp.player, true);
		gp.player.ObjInteract(ObjIndex);
        
	    }
	    if(code == KeyEvent.VK_J){
		gp.player.attacking = true;
	    }
	}
	else if(gp.gameState == gp.pauseState){
	    if(code==KeyEvent.VK_P){
		gp.gameState = gp.playState;
	    }
	}
	else if(gp.gameState == gp.dialogueState){
	    if(code==KeyEvent.VK_ENTER){
		gp.gameState = gp.playState;
	    }
	    
	}
	else if(gp.gameState == gp.titleState){
	    if(code==KeyEvent.VK_W){
	        gp.ui.commandNum--;
		if(gp.ui.commandNum < 0){
		    gp.ui.commandNum = 2;
		}
	    }
	    if(code==KeyEvent.VK_S){
	        gp.ui.commandNum++;
		if( gp.ui.commandNum >2){
		    gp.ui.commandNum = 0;
		}
	    }
	    if(code==KeyEvent.VK_ENTER){
		if( gp.ui.commandNum==0){
		    gp.gameState = gp.playState;
		}
		if( gp.ui.commandNum==2){
		    System.exit(0);
		}
	    }
	    
	}
	else if(gp.gameState==gp.statusState){
	    if(code==KeyEvent.VK_I){
		gp.gameState=gp.playState;
	    }
	}
	else if(gp.gameState == gp.shootingState){
	    if(code==KeyEvent.VK_J){
	        gp.player.attacking=true;
	    }
	    if(code==KeyEvent.VK_A){
		left=true;
	    }
	    if(code==KeyEvent.VK_D){
		right=true;
	    }
	}
    }

    public void keyReleased(KeyEvent e){
	int code = e.getKeyCode();
	if(code==KeyEvent.VK_W){
	    up = false;
	}
	if(code==KeyEvent.VK_S){
	    down = false;
	}
	if(code==KeyEvent.VK_A){
	    left=false;
	}
	if(code==KeyEvent.VK_D){
	    right=false;
	}

	
    }
}
