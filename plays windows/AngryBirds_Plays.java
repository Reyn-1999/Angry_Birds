package wang.zhi.yuan;

import java.awt.BasicStroke;				// For describing lines' features
import java.awt.Color;						// For describing color (RGB)
import java.awt.Font;						// For describing font
import java.awt.Graphics;					// For painting
import java.awt.Graphics2D;					// For painting 2D
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;	// For mouse op
import java.awt.image.BufferedImage;		// For creating and changing images
import java.io.File;						// For File IO
import java.io.IOException;					// For Exception in IO procedure
import java.net.MalformedURLException;		// For URL format wrong exception
import javax.imageio.ImageIO;				// For Image IO
import javax.swing.JPanel;					// JPanel

public class AngryBirds_Plays extends JPanel{

	/**** private variable ****/
	private int level;								// game level
	private BufferedImage backgroundImage;			// background image
	private AngryBirds_Button button;				// refresh button
	private AngryBirds_Bird	bird;					// bird object
	private AngryBirds_Redbird[] redBird;			// red bird object
	private int redBirdNum;							// red bird num 			
	private AngryBirds_Yellowbird[] yellowBird;		// yellow bird object
	private int yellowBirdNum;						// yellow bird num
	private AngryBirds_Bluebird[] blueBird;			// blue bird object
	private int blueBirdNum;						// blue bird num
	private AngryBirds_Blackbird[] blackBird; 		// black bird object
	private int blackBirdNum;						// black bird num
	private AngryBirds_Whitebird[] whiteBird; 		// white bird object
	private int whiteBirdNum;						// white bird num
	private AngryBirds_Pig[] pig;					// pig object array
	private AngryBirds_Mouse mouse;					// mouse object
	private BufferedImage[] branch;					// branch (the image is divided into two parts)
	private Graphics2D graph;						// the painting graph object
	private float width;							// the width for Basic Stroke Function (draw line)
	private double distance;						// the distance between the bird and the branch's centre
	private int centreX, centreY;							// the x and y coordinates of the branch's centre
	private int pigInitNum;							// the initial number of pigs
	private int pigNowNum;							// the present number of pigs
	private int score;								// the current score
	/**************************/
	public boolean run;								// mark if runnable now	
	public String choice;							// mark the current choice

	// Constrctor Function = Set Game Level
	public AngryBirds_Plays(boolean run) throws IOException{
		/**/
		this.level = 1;
		this.redBirdNum = 0;
		this.yellowBirdNum = 0;
		this.blueBirdNum = 0;
		this.blackBirdNum = 0;
		this.whiteBirdNum = 0;
		setBirdConf();
		setPigConf();
		this.button = new AngryBirds_Button(1450,0);				
		this.mouse = new AngryBirds_Mouse();
		this.backgroundImage = ImageIO.read(new File("src/picture/background2.png"));
		this.branch = new BufferedImage[2];
		this.branch[0] = ImageIO.read(new File("src/picture/branch1.png"));
		this.branch[1] = ImageIO.read(new File("src/picture/branch2.png"));
		this.width = 5.0f;
		this.centreX = 200;
		this.centreY = 630;
		this.score = 0;
		this.run = run;
		this.choice = "p_2";		// if win, then return to choose 
	}
	
	// set birds configuration
	public void setBirdConf() throws IOException{
		if(this.level == 1){
			this.redBirdNum = 1;
			this.redBird = new AngryBirds_Redbird[this.redBirdNum];
			this.redBird[0] = new AngryBirds_Redbird(130,770);			//initialize birds' positions
			this.yellowBirdNum = 1;
			this.yellowBird = new AngryBirds_Yellowbird[this.yellowBirdNum];
			this.yellowBird[0] = new AngryBirds_Yellowbird(90,760);
			this.blueBirdNum = 1;
			this.blueBird = new AngryBirds_Bluebird[this.blueBirdNum];
			this.blueBird[0] = new AngryBirds_Bluebird(50,770);
			this.blackBirdNum = 1;
			this.blackBird = new AngryBirds_Blackbird[this.blackBirdNum];
			this.blackBird[0] = new AngryBirds_Blackbird(5,750);
			this.whiteBirdNum = 1;
			this.whiteBird = new AngryBirds_Whitebird[this.whiteBirdNum];
			this.whiteBird[0] = new AngryBirds_Whitebird(5,650);
			this.button = new AngryBirds_Button(1450,0);
			this.bird = yellowBird[0]; 	// ????
		}
	}

	// set pigs configuration
	public void setPigConf() throws IOException{
		if(this.level == 1){
			this.pigInitNum = 1;
			this.pigNowNum = this.pigInitNum;
			this.pig = new AngryBirds_Pig[pigInitNum];
			for(int i = 0; i < pigInitNum; ++i){
				this.pig[i] = new AngryBirds_Pig(800 + i * 200, 300 + i * 100);
			}
		}
	}

	/*	
		set paint component function, this function is like
		main() function in the javax.swing.JPanel.
	 	every time when refresh record these things:
	 	1. whether the bird hit the pig;
	 	2. the location coordinates of the bird;
	 	then draw all the components.
	*/
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		score = (pigInitNum - pigNowNum) * 10;
		pigNowNum = 0;

		// check pigs and birds to set 
		for(int i = 0; i < pigInitNum; ++i){
			if(bird.isCollide(pig[i])){
				pig[i].setAlived(false);	// bird collides pig[i]
			}
			else{
				pigNowNum++;				// bird doesn't collide pig[i]
			}
		}
		if(pigNowNum == 0){
			run = false;					// Game over
		}
		// can add cases here, if birds are used up, then game over

		// judge if the bird is flying
		if(bird.getX() > 200 && bird.isDrug() == false && bird.isChosen() == true){
			bird.setFly(true);
		}

		// set line's color and width
		// arguments are x, y, height, width,(all are int type) pointer
		this.graph = (Graphics2D)g;
		this.graph.setStroke(new BasicStroke(width));
		this.graph.setColor(new Color(48,22,8));
		g.drawImage(backgroundImage,0,0,1600,900,null);
		g.drawImage(button.getButtonImage(), button.getX(), button.getY(), 
				button.getWeight(), button.getHeight(),null);

		// draw the birds
		drawBird(g);

		// draw the pigs
		drawPig(g);

		// draw the score
		drawScore(g);

		// draw the mouse
		g.drawImage(mouse.getPresent(), mouse.getX(), mouse.getY(), 
			mouse.getPresent().getWidth(), mouse.getPresent().getHeight(), null);
	}

	private void drawBird(Graphics g){
		// divide the branch into two parts for graph layer setting
		// bird can get through from the two parts
		g.drawImage(branch[0], 169, 598, null);
		/**/
		// present is current image
		for(int i = 0; i < this.redBirdNum; ++i)
			g.drawImage(redBird[i].present, (int)redBird[i].getX(), (int)redBird[i].getY(), redBird[i].getWidth(), redBird[i].getHeight(), null);
		for(int i = 0; i < this.yellowBirdNum; ++i)
			g.drawImage(yellowBird[i].present, (int)yellowBird[i].getX(), (int)yellowBird[i].getY(), yellowBird[i].getWidth(), yellowBird[i].getHeight(), null);
		for(int i = 0; i < this.blueBirdNum; ++i){
			g.drawImage(blueBird[i].present[0],(int)blueBird[i].getX(),(int)blueBird[i].getY(),null);
			g.drawImage(blueBird[i].present[1],(int)blueBird[i].getnewX(1),(int)blueBird[i].getnewY(1),null);
			g.drawImage(blueBird[i].present[2],(int)blueBird[i].getnewX(2),(int)blueBird[i].getnewY(2),null);
		}
		for(int i = 0; i < this.blackBirdNum; ++i)
			g.drawImage(blackBird[i].present, (int)blackBird[i].getX(),(int)blackBird[i].getY(), null);
		for(int i = 0; i < this.whiteBirdNum; ++i){
			g.drawImage(whiteBird[i].present[0], (int)whiteBird[i].getX(),(int)whiteBird[i].getY(), null);
			g.drawImage(whiteBird[i].present[1], (int)whiteBird[i].getnewX(),(int)whiteBird[i].getnewY(), null);
		}
		g.drawImage(branch[1], 200, 610, null);

		// draw the instruct line when dragging bird
		if(bird.isChosen() == true && bird.isFly() == false){
			/***/ 
			this.graph.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 179, 636);
			this.graph.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 226, 639);
		}
	}

	private void drawPig(Graphics g){
		// judge pig's number and draw the pig's image
		boolean tmp = true;
		for(int i = 0; i < pigInitNum; ++i){
			// exited variable is changed in the pig's thread
			if(pig[i].isExited()){
				tmp = false;
				g.drawImage(pig[i].getPresent(),(int)pig[i].getX(), (int)pig[i].getY(),
					pig[i].getPresent().getWidth(), pig[i].getPresent().getHeight(), null);
			}
		}
		if(tmp == true){
			this.choice = "p_4";
			run = false;
		}
	}

	private void drawScore(Graphics g){
		// "" is default font type
		Font font = new Font("", Font.BOLD, 31);
		g.setFont(font);
		g.setColor(Color.ORANGE);
		g.drawString("Number:" + pigNowNum, 20, 30);
		g.drawString("Score:" + score, 20, 60);
	}

	// set mouse listener action
	// such as move mouse and click
	public String action(){
		// mouse move event listener
		MouseMotionListener mml = new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e){
				if(bird.isDrug()){
					distance = Math.sqrt((e.getX()-centreX)*(e.getX()-centreX)+(e.getY()-centreY)*(e.getY()-centreY));	// set between 60-120
					if(distance >= 160){
						// control the max drag length 
						bird.moveTo(160/distance*(e.getX()-centreX)+centreX, 160/distance*(e.getY()-centreY)+centreY);
					}
					else{
						bird.moveTo(e.getX(), e.getY());
					}
					// control the distance into appropriate range (60-120)
					if(distance > 120){
						distance = 120;
					}
					if(distance < 60){
						distance = 60;
					}
					// control the width into appropriate range (5-10)
					width = (float) (600.0f/distance);
				}
				// move mouse
				mouse.moveto(e.getX(), e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e){
				// move mouse
				mouse.moveto(e.getX(), e.getY());
			}
		};

		// mouse click event listener
		MouseListener ml = new MouseListener(){
			@Override
			// click to refresh windows
			public void mouseClicked(MouseEvent e){
				// vy is the speed for v axis
				boolean tmp = false;
				if(bird.fly && bird.vy > -10)
				{
					tmp = true;
					for(int i = 0; i < yellowBirdNum; ++i){
						if(bird == yellowBird[i]) {
							if(bird.vy < 0) {		//黄鸟只能俯冲
								tmp = false;
								break;
							}
						}
					}
					if(tmp == true){
						bird.special = true;
						bird.changeitself();
					}
				}

				for(int i = 0; i < redBirdNum; ++i){
					if(redBird[i].isChosen(e.getX(), e.getY())){
						setBirdChosen();
						redBird[i].setChosen(true);
						bird = redBird[i];
						bird.moveTo(140, 700);
						new Thread(bird).start();
					}
				}
				
				for(int i = 0; i < yellowBirdNum; ++i){
					if(yellowBird[i].isChosen(e.getX(), e.getY())){
						setBirdChosen();
						yellowBird[i].setChosen(true);
						bird = yellowBird[i];
						bird.moveTo(140, 700);
						new Thread(bird).start();
					}
				}
				
				for(int i = 0; i < blueBirdNum; ++i){
					if(blueBird[i].isChosen(e.getX(), e.getY())){
						setBirdChosen();
						blueBird[i].setChosen(true);
						bird = blueBird[i];
						bird.moveTo(140, 700);
						new Thread(bird).start();
					}
				}
				
				for(int i = 0; i < blackBirdNum; ++i){
					if(blackBird[i].isChosen(e.getX(),e.getY())) {
						setBirdChosen();
						blackBird[i].setChosen(true);
						bird = blackBird[i];
						bird.moveTo(140, 700);
						new Thread(bird).start();
					}
				}
				
				for(int i = 0; i < whiteBirdNum; ++i){
					if(whiteBird[i].isChosen(e.getX(),e.getY())) {
						setBirdChosen();
						whiteBird[i].setChosen(true);
						bird = whiteBird[i];
						bird.moveTo(140, 700);
						new Thread(bird).start();
					}
				}
				

				// set refreshment if click the button
				if(button.isChonse(e.getX(), e.getY())){
					try{
						refresh();
					} catch (IOException e1){
						System.out.println("button chosen IOException");
						e1.printStackTrace();
					}
				}
			}

			/***/ //???
			@Override
			public void mouseEntered(MouseEvent e){
			}

			@Override
			public void mouseExited(MouseEvent e){
			}

			@Override
			public void mousePressed(MouseEvent e){
				// mouse presssed
				mouse.mousePress();			// change the mouse picture
				if(bird.isChosen(e.getX(), e.getY())){
					if(bird.isChosen()){
						bird.setDrug(true);	// this bird is dragged by the mouse
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e){
				// set the birds initial speed and play the bird's flying music
				mouse.mouseLoose();			// change the mouse picture
				if(bird.isDrug()){
					/**/
					bird.setVx((centreX - bird.getX())/13.0);
					bird.setVy((centreY - bird.getY())/13.0);
					bird.setDrug(false);
					try{
						bird.birdFly();
					} catch (MalformedURLException e1){
						e1.printStackTrace();
					}
				}
			}
		};

		// add mouse listener
		this.addMouseMotionListener(mml);	// mouse move listener
		this.addMouseListener(ml);			// mouse listener

		// start the thread
		for(int i = 0; i < pigInitNum; ++i){
			new Thread(pig[i]).start();
		}

		// keep the window refresh to construct graph motion
		while(run){
			repaint();				// repaint Jpanel
			try{
				Thread.sleep(10);	// set thread sleep time
			} catch(InterruptedException e1) {
				System.out.println("play InterruptedException");
				e1.printStackTrace();
			}
		}
		return choice;
	}
	
	private void refresh() throws IOException{
		this.choice = "p_3";
		run = false;
	}
	private void setBirdChosen(){
		for(int i = 0; i < this.redBirdNum; ++i)
			redBird[i].setChosen(false);
		for(int i = 0; i < this.yellowBirdNum; ++i)
			yellowBird[i].setChosen(false);
		for(int i = 0; i < this.blueBirdNum; ++i)
			blueBird[i].setChosen(false);
		for(int i = 0; i < this.blackBirdNum; ++i)
			blackBird[i].setChosen(false);
		for(int i = 0; i < this.whiteBirdNum; ++i)
			whiteBird[i].setChosen(false);
	}
}
