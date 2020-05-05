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
	private BufferedImage backgroundImage;			// background image
	private AngryBirds_Button button;				// refresh button
	private AngryBirds_Bird	bird;					// bird object
	private AngryBirds_Redbird redBird;				// red bird object
	private AngryBirds_Yellowbird yellowBird;		// yellow bird object
	private AngryBirds_Pig[] pig;					// pig object array
	private AngryBirds_Mouse mouse;					// mouse object
	private BufferedImage[] branch;					// branch (the image is divided into two parts)
	private Graphics2D graph;						// the paiting graph object
	private float width;							// the width for Basci Stoke Function (draw line)
	private double distance;						// the distance between the bird and the branch's centre
	private int centreX, centreY;					// the x and y coordinates of the branch's centre
	private int pigInitNum;							// the initial number of pigs
	private int pigNowNum;							// the present number of pigs
	private int score;								// the current score
	/**************************/
	public boolean run;								// mark if runnable now	
	public String choice;							// mark the current choice

	// Constrctor Function = Set Game Level
	public AngryBirds_Plays(boolean run) throws IOException{
		/**/
		setBirdConf();
		setPigConf();
		this.button = new AngryBirds_Button(1450,0);				
		this.mouse = new AngryBirds_Mouse();
		this.backgroundImage = ImageIO.read(new File("src/picture/background2.png"));
		this.branch = new BufferedImage[2];
		this.branch[0] = new ImageIO.read(new File("src/picture/branch1.png"));
		this.branch[1] = new ImageIO.read(new File("src/picture/branch2.png"));
		this.width = 5.0f;
		this.centreX = 200;
		this.cnetreY = 630;
		this.score = 0;
		this.run = run;
		this.choice = "p_2";		// if win, then return to choose 
	}
	
	// set birds configuration
	public void setBirdConf(){
		this.redBird = new AngryBirds_Redbird(105,770);
		this.yellowBird = new AngryBirds_Yellowbird(35,760);
		this.bird = yellowBird; 	// ????
	}

	// set pigs configuration
	public void setPigConf(){
		this.pigInitNum = 4;
		this.pigNowNum = this.pigInitNum;
		this.pig = new AngryBirds_Pig[pigInitNum];
		for(int i = 0; i < pigInitNum; ++i){
			this.pig[i] = new AngryBirds_Pig(800 + i * 200, 300 + i * 100);
		}
	}

	/*	
		set paint component function, this function is like
		main() function in the javax.swing.JPanel.
	 	every time when refresh record these things:
	 	1. whether the bird hitd the pig;
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
		g.drwaImage(button.getButtonImage(), button.getX(), button.getY(),
			button.getWeight(), button.getHeight(), null);

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
		// bird can get throught from the two parts
		g.drawImage(branch[0], 169, 598, null);
		/**/
		// present is currnt image
		g.drawImage(redBird.present, (int)redBird.getX(), (int)redBird.getY(),
			redBird.getWidth(), redBird.getHeight(), null);
		g.drawImage(yellowBird.present, (int)yellowBird.getX(), (int)yellowBird.getY(),
			yellowBird.getWidth(), yellowBird.getHeight(), null);
		g.drawImage(branch[1], 200, 610, null);

		// draw the instruct line when dragging bird
		if(bird.isChosen() == true && bird.isFly() == false){
			/***/ // why two lines?
			this.graph.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 179, 636);
			this.graph.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 226, 639);
		}
	}

	private void drawPig(Graphics g){
		// judge pig's number and draw the pig's image
		for(int i = 0; i < pigInitNum; ++i){
			// exited virable is changed in the pig's thread
			if(pig[i].isExited()){
				g.drawImage(pig[i].getPresent(),(int)pig[i].getX(), (int)pig[i].getY(),
					pig[i].getPresent().getWidth(), pig[i].getPresent().getHeight(), null);
			}
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
					distance = Math.sqrt((e.getX()-centreX)*(e.getX()-centreX)+(e.getY()-cnetreY)*(e.getY()-centreY));	// set between 60-120
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
					}else{
						distance = distance;
					}
					if(distance < 60){
						distance = 60;
					}else{
						distance = distance;
					}
					// control the wodth into appropriate range (5-10)
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
				if(bird.fly && bird.vy > 0){
					/***/ // why vy > 0
					bird.special = true;
					/**/
					if(bird == yellowBird){
						// set speed into double speed
						((AngryBirds_Yellowbird) bird).changeFast();
						((AngryBirds_Yellowbird) bird).setNewVx(bird.vx * 2);
						((AngryBirds_Yellowbird) bird).setNewvy(bird.vy * 2);
					}
				}

				/**/
				// set the chosen bird on the branch
				if(redBird.isChosen(e.getX(), e.getY())){
					redBird.setChosen(true);
					yellowBird.setChosen(true);
					bird = redBird;
					bird.moveTo(140, 700);					// set the bird onto the bird
					new Thread(bird).start();
				}

				if(yellowBird.isChosen(e.getX(), e.getY())){
					redBird.setChosen(false);
					yellowBird.setChosen(true);
					bird = yellowBird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}

				// set refreshment if click the button
				if(button.isChosen(e.getX(), e.getY())){
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
		this.addMouseotionListener(mml);	// mouse move listener
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
}










