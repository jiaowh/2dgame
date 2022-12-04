package main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.awt.BasicStroke;
import java.io.InputStream;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;

import object.Heart;
import entity.Player;
import entity.Tentacool;


public class Shooting{

    Key key;

    GamePanel gp;
    Graphics2D g2;
    BufferedImage heart_full, heart_half, heart_empty, playeri, enemy;
    int my_x;
    public static final int MY_Y = 400;
    int player_width,player_height;
    int counter;
    
    //variables for enemies
    int enemy_width, enemy_height;
    int n;
    int a[];
    int enemy_x[];
    int enemy_y[];
    int enemy_move[];
    int enemy_alive[];
    
    //variables for player's missiles
    int my_missile_x, my_missile_y;
    int missile_flag;
    
    //variables for enemies' missiles
    int e_missile_flag[];
    int e_missile_x[];
    int e_missile_y[];
    int e_missile_move[];
    
    
    public Shooting(GamePanel gp){
	this.gp = gp;
	this.key = gp.keyH;
	Player player = gp.player;
	Tentacool tentacool = new Tentacool(gp);
	
	Heart heart = new Heart();

	heart_full = heart.image;
	heart_half = heart.image2;
	heart_empty = heart.image3;
	my_x = 250;

	playeri = player.up1;
	
	player_width = gp.tileSize;
	player_height= gp.tileSize;
	
	missile_flag = 0;
	
	/*** initialize enemies' info ***/
	enemy = tentacool.down1;
	enemy_width = gp.tileSize;
	enemy_height = gp.tileSize;
	
	
	n = 14; //number of enemies
	enemy_x = new int[n];
	enemy_y = new int[n];
	enemy_move = new int[n];
	enemy_alive = new int[n];
	a = new int[n];
	int distance = 40;
	
	e_missile_flag = new int[n];
	e_missile_x = new int[n];
	e_missile_y = new int[n];
	e_missile_move = new int[n];
	
	// place enemies in 7x2
	for (int i = 0; i < 7; i++) {
	    enemy_x[i] = (enemy_width + distance) * i + 50;
	    enemy_y[i] = 50;
	}
	for (int i = 7; i < n; i++) {
	    enemy_x[i] = (enemy_width + distance) * (i - 5) + 50;
	    enemy_y[i] = 100;
	}
	for (int i = 0; i < n; i++) {
	    enemy_alive[i] = 1; //all alive
	    enemy_move[i] = -10; //moves to left
	}
	
	for (int i = 0; i < n; i++) {
	    e_missile_flag[i] = 0;
	    e_missile_x[i] = 0;
	    e_missile_y[i] = 0;
	    e_missile_move[i] = 7 + n%3;
	}

	
			
    }


    public void draw(Graphics2D g2){
	this.g2 = g2;


	g2.drawImage(playeri,my_x,400,gp.tileSize, gp.tileSize, null);
	//draw enemies
	for (int i = 0; i < n; i++) {
	    if (enemy_alive[i] == 1) {
		g2.drawImage(enemy, enemy_x[i], enemy_y[i],gp.tileSize, gp.tileSize, null);
	    }
	}
	        
	//draw players missiles
	if (missile_flag == 1) {
	    g2.setColor(Color.black);
	    g2.fillRect(my_missile_x, my_missile_y, 2, 5);
	    g2.fillRect(my_missile_x-20, my_missile_y, 2, 5);
	    g2.fillRect(my_missile_x+20, my_missile_y, 2, 5);
	    g2.fillRect(my_missile_x-40, my_missile_y, 2, 5);
	    g2.fillRect(my_missile_x+40, my_missile_y, 2, 5);
	    
	}
	//draw enemies' missiles
	for(int i = 0; i < n; i++){
	    if (e_missile_flag[i] == 1) {
		g2.setColor(Color.black);
		g2.fillRect(e_missile_x[i], e_missile_y[i], 2, 5);
	    }
	}
		
    }
    private void updateEnemiesPosition(){
			//update enemies' position
	        
			for (int i = 0; i < n; i++) {
				enemy_x[i] += enemy_move[i];
				if ((enemy_x[i] < 0) || (enemy_x[i] > (gp.screenWidth - enemy_width))) {
					enemy_move[i] = -enemy_move[i];
				}
			}
		}
    public void activateMyMissile(){
			//shoot a missile
			if(missile_flag == 0){
				my_missile_x = my_x + player_width / 2;
				my_missile_y = MY_Y; //MY_Y=400
				missile_flag = 1;
			}
			gp.player.attacking=false;
		}
    private void updateMyMissile(){
		    //update missile position if alive
			if (missile_flag == 1) {
				my_missile_y -= 15;
				if (0 > my_missile_y) {
					missile_flag = 0;
				}
			}
		}
    private void activateEnemiesMissile(){
			//update enemies' missile position if alive
			for(int i = 0; i < n; i++){
				if (e_missile_flag[i] == 0) {
					e_missile_x[i] = enemy_x[i] + enemy_width/2;
					a[i] = e_missile_x[i];
					e_missile_y[i] = enemy_y[i];
					e_missile_flag[i] = 1;
				}
			}
		}
    private void updateEnemiesMissile(){
			//update enemies' missile position if alive
	        

			for(int i = 0; i < n; i++){
				if (e_missile_flag[i] == 1) {
					e_missile_y[i] += e_missile_move[i];
					if (e_missile_y[i] > gp.screenHeight) {
						e_missile_flag[i] = 0;
					}
				}
			}
		}
    private void checkHitToEnemy(){
			for(int i = 0; i < n; i++){
				if(missile_flag == 1 && enemy_alive[i] == 1){
					if(
					   (my_missile_x > enemy_x[i] &&
						my_missile_x < (enemy_x[i] + enemy_width) &&
						my_missile_y > enemy_y[i] &&
					    my_missile_y < (enemy_y[i] + enemy_height))
					   ||(my_missile_x+20 > enemy_x[i] &&
						my_missile_x+20 < (enemy_x[i] + enemy_width) &&
						my_missile_y > enemy_y[i] &&
					    my_missile_y < (enemy_y[i] + enemy_height))
					   ||(my_missile_x-20 > enemy_x[i] &&
						my_missile_x-20< (enemy_x[i] + enemy_width) &&
						my_missile_y > enemy_y[i] &&
					    my_missile_y < (enemy_y[i] + enemy_height))
					     ||(my_missile_x+40 > enemy_x[i] &&
						my_missile_x+40 < (enemy_x[i] + enemy_width) &&
						my_missile_y > enemy_y[i] &&
					    my_missile_y < (enemy_y[i] + enemy_height))
					   ||(my_missile_x-40 > enemy_x[i] &&
						my_missile_x-40< (enemy_x[i] + enemy_width) &&
						my_missile_y > enemy_y[i] &&
					    my_missile_y < (enemy_y[i] + enemy_height))
					   
					){
						//hit
						missile_flag = 0;
						enemy_alive[i] = 0;
					}
				}
			}
		}
    private boolean checkHitToPlayer(){
			for(int i = 0; i < n; i++){
				if(e_missile_flag[i] == 1){
					if(
						e_missile_x[i] > my_x &&
						e_missile_x[i] < (my_x + player_width) &&
						e_missile_y[i] > MY_Y &&
						e_missile_y[i] < (MY_Y + player_height)
					){
						e_missile_flag[i] = 0;
						return true;
					}
				}
			}
			return false;
		}
    private boolean checkClear(){
			int cnt = 0;
			for(int i = 0; i < n; i++){
				if(enemy_alive[i] == 0) cnt++;
			}
			return (n == cnt);
		}
    public void update(){
	if(gp.player.attacking==true){activateMyMissile();}
	if(key.left==true){my_x --;}
	if(key.right==true){my_x ++;}
	if(counter>3){
	updateEnemiesPosition();
	
	updateMyMissile();
	updateEnemiesMissile();
	
	activateEnemiesMissile();
	
	if(checkHitToPlayer()){
	    gp.player.life-=1;
	    if(gp.player.life==0){
		System.out.println("===== Game Over =====");
		System.exit(0);}
	}
	checkHitToEnemy();
	if(checkClear()){
	    gp.gameState=gp.playState;
	}
	counter = 0;}
	counter++;
	
    }
        
    
  


}

