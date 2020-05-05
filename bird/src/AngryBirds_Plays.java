package wang.zhi.yuan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AngryBirds_Plays extends JPanel{
	
	private BufferedImage background;		//鑳屾櫙鍥剧墖
	private AngryBirds_Button button; 		//鎸夐挳鍥剧墖
	private AngryBirds_Bird bird;			//当前鸟
	private AngryBirds_Redbird redbird; 	//红鸟一只			
	private AngryBirds_Yellowbird yellowbird;//黄鸟一只
	private AngryBirds_Bluebird bluebird;	//蓝鸟一只
	private AngryBirds_Blackbird blackbird; //黑鸟一只
	private AngryBirds_Whitebird whitebird; //白鸟一只
	private AngryBirds_Pig[] pig;			//鍔犺浇鐚�
	private AngryBirds_Mouse mouse;			//鍔犺浇榧犳爣
	private BufferedImage[] branch;			//鏍戞灊鍒嗚В鍥剧墖
	private Graphics2D gra;					//缁樼敾鍥剧墖瀵硅薄
	private float width;					//鐢荤嚎鐨勫搴�
	private double distence;				//灏忛笩绂讳腑蹇冭窛绂�
	private int centerX;                    //寮瑰紦涓績X鍧愭爣
	private int centerY;					//寮瑰紦涓績Y鍧愭爣
	private final int pigcount = 4; 		//鐚殑鍒濆鏁伴噺
	int pigcountpre;						//褰撳墠鐚殑鏁伴噺
	int score;								//寰楀垎
	boolean run;
	String choice;
	
	public AngryBirds_Plays(boolean run) throws IOException
	{
		//this.bird = new AngryBirds_Bird();
		this.redbird = new AngryBirds_Redbird(130,770);			//初始化bird位置
		this.yellowbird = new AngryBirds_Yellowbird(90,760);
		this.bluebird=new AngryBirds_Bluebird(50,770);
		this.blackbird=new AngryBirds_Blackbird(5,750);
		this.whitebird=new AngryBirds_Whitebird(5,650);
		this.button = new AngryBirds_Button(1450,0);
		
		this.pig = new AngryBirds_Pig[pigcount];
		for(int i = 0; i < pigcount; i++)
		{
			this.pig[i] = new AngryBirds_Pig(800 + i * 200, 300 + i * 100);
		}
		
		this.mouse = new AngryBirds_Mouse();					//鏋勯�犻紶鏍�
		this.background = ImageIO.read(new File("src/picture/background2.png")); 				//璁剧疆鑳屾櫙鍥剧墖
		this.branch = new BufferedImage[2];
		this.branch[0] = ImageIO.read(new File("src/picture/branch1.png"));
		this.branch[1] = ImageIO.read(new File("src/picture/branch2.png"));
		this.width = 5.0f;
		
		this.centerX = 200;
		this.centerY = 630;
		this.score = 0;
		this.pigcountpre = pigcount;
		bird = yellowbird;
		this.run = run;
		this.choice = "p_2";
	}
	
	
	/**璁剧疆閲嶇粯鐨勭晫闈紝绯荤粺鑷姩璋冪敤锛屽埛鏂伴『搴忔瀯閫犲浘灞傞噸鍙犳晥鏋�
	 * 
	 * 姣忔鍒锋柊鍒ゆ柇鐚拰楦熺殑浣嶇疆鏄惁纰版挒
	 * 浠ュ強楦熺殑浣嶇疆淇℃伅
	 * 鍏堢敾鑳屾櫙鎸夐挳浠ュ強鍥炬爣
	 * 鏈�鍚庣敾
	 * */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		score = (pigcount - pigcountpre) * 100;
		pigcountpre = pigcount;
		
		for(int i = 0; i < pigcount; i++)
		{
			if(bird.isCollide(pig[i]))
			{
				pig[i].setAlived(false);
			}	
		}
		
		for(int i = 0; i < pigcount; i++)
		{
			if(pig[i].isAlived() == false)
			{
				pigcountpre--;
			}
		}	
		
		if(pigcountpre == 0)
		{
			//System.exit(0);
			run = false;
		}

		if(bird.getX() > 200 && !bird.isDrug() && bird.chosen)
		{
			bird.setFly(true);
		}
		
		/**璁剧疆鐢荤嚎棰滆壊浠ュ強瀹藉害*/
		this.gra = (Graphics2D)g;
		this.gra.setStroke(new BasicStroke(width));
		this.gra.setColor(new Color(48,22,8));
		g.drawImage(background, 0, 0, 1600, 900, null);							//鑳屾櫙鍥剧墖鎷変几鍒板睆骞曡瀹氬ぇ灏�
		g.drawImage(button.getButtonImage(), button.getX(), button.getY(), button.getWeight(), button.getHeight(),null);
		
		/**灏嗘爲鏋濇埅鍥惧紑鍐嶈繘琛屾嫾鎺ワ紝鏋勯�犲浘褰㈠眰鍙犻『搴忥紝灏忛笩鍙互浠庝袱涓爲鏋濅腑闂寸粡杩�*/
		g.drawImage(branch[1], 200, 610, null);
		g.drawImage(redbird.present, (int)redbird.getX(), (int)redbird.getY(), null);
		g.drawImage(yellowbird.present, (int)yellowbird.getX(), (int)yellowbird.getY(), null);
		g.drawImage(bluebird.present[0],(int)bluebird.getX(),(int)bluebird.getY(),null);
		g.drawImage(bluebird.present[1],(int)bluebird.getnewX(1),(int)bluebird.getnewY(1),null);
		g.drawImage(bluebird.present[2],(int)bluebird.getnewX(2),(int)bluebird.getnewY(2),null);
		g.drawImage(blackbird.present, (int)blackbird.getX(),(int)blackbird.getY(), null);
		g.drawImage(whitebird.present[0], (int)whitebird.getX(),(int)whitebird.getY(), null);
		g.drawImage(whitebird.present[1], (int)whitebird.getnewX(),(int)whitebird.getnewY(), null);
		g.drawImage(branch[0], 169, 598, null);
		
		/**鐢诲嚭涓ゆ潯绾�*/
		if(bird.isChosen() && !bird.isFly())
		{
			this.gra.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 179, 636);
			this.gra.drawLine((int)bird.getX(), (int)bird.getY() + bird.getHeight()/2, 226, 639);	
		}

		/**缁樺埗鐚殑鍥剧墖
		 * 
		 * 鍒ゆ柇鐚槸鍚﹁繕瀛樺湪锛屼笉瀛樺湪涓嶉噸缁樺浘鐗�
		 * 
		 * */
		for(int i = 0; i < pigcount; i++)
		{
			if(pig[i].isExited())
			{
				g.drawImage(pig[i].getPresent(), (int)pig[i].getX(), (int)pig[i].getY(), null);
			}
		}
		
		/**缁樺埗寰楀垎*/
		score(g);

		g.drawImage(mouse.getPresent(), mouse.getX(), mouse.getY(), null);			//鐢诲嚭榧犳爣褰撳墠鐘舵��
	}
	
	private void score(Graphics g)
	{
		Font f = new Font("", Font.BOLD, 30);
		g.setFont(f);
		g.setColor(Color.BLUE);
		g.drawString("count:" + pigcountpre, 20, 30);
		g.drawString("score:" + score , 20, 60);
	}

	/**璁剧疆榧犳爣浜嬩欢鐩戝惉锛屽寘鎷紶鏍囨嫋鍔ㄥ拰榧犳爣鐐瑰嚮浜嬩欢*/
	public String action()
	{
		MouseMotionListener ma = new MouseMotionListener() {						//榧犳爣鎷栧姩浜嬩欢

			@Override
			public void mouseDragged(MouseEvent e) {		
				// TODO Auto-generated method stub
				if(bird.isDrug())
				{
					distence = Math.sqrt((e.getX() - centerX)*(e.getX() - centerX) + (e.getY() - centerY)*(e.getY() - centerY)); //璁剧疆60 - 120
					if(distence > 160)
					{
						bird.moveTo(160 / distence * (e.getX() - centerX) + centerX, 160 / distence * (e.getY() - centerY) + centerY);
						//System.out.println("1");
					}
					else
					{
						bird.moveTo(e.getX(), e.getY());
					}
					
					/**
					 * 璁剧疆璺濈涓哄悎鐞嗙殑鑼冨洿
					 */
					distence = distence > 120 ? 120 : distence;
					distence = distence < 60 ? 60 : distence;
					
					/**纭繚缁樼敾鏃剁殑绾跨殑瀹藉害鍦ㄥ悎鐞嗚寖鍥村唴*/
					width =  (float) (600.0/distence); // 5 - 10;
				}
				
				mouse.moveto(e.getX(), e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouse.moveto(e.getX(), e.getY());
			}		
		};
		
		MouseListener mb = new MouseListener(){   			//榧犳爣鐐瑰嚮浜嬩欢

			@Override
			/**鐐瑰嚮鍒锋柊鐣岄潰*/
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(bird.fly && bird.vy > -10)
				{
					if(bird==yellowbird) {
						if(bird.vy>=0) {		//黄鸟只能俯冲
							bird.special = true;
							bird.changeitself();
						}
					}
					else {						//其他鸟随时可以发动技能
						bird.special = true;
						bird.changeitself();
					}
				}
				
				if(redbird.isChosen(e.getX(), e.getY()))
				{
					redbird.setChosen(true);
					yellowbird.setChosen(false);
					bluebird.setChosen(false);
					blackbird.setChosen(false);
					whitebird.setChosen(false);
					bird = redbird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}
				
				if(yellowbird.isChosen(e.getX(), e.getY()))
				{
					yellowbird.setChosen(true);
					redbird.setChosen(false);
					bluebird.setChosen(false);
					blackbird.setChosen(false);
					whitebird.setChosen(false);
					bird = yellowbird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}
				
				if(bluebird.isChosen(e.getX(), e.getY()))
				{
					bluebird.setChosen(true);
					redbird.setChosen(false);
					yellowbird.setChosen(false);
					blackbird.setChosen(false);
					whitebird.setChosen(false);
					bird = bluebird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}
				if(blackbird.isChosen(e.getX(),e.getY())) {
					blackbird.setChosen(true);
					bluebird.setChosen(false);
					redbird.setChosen(false);
					yellowbird.setChosen(false);
					whitebird.setChosen(false);
					bird = blackbird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}
				if(whitebird.isChosen(e.getX(),e.getY())) {
					whitebird.setChosen(true);
					blackbird.setChosen(false);
					bluebird.setChosen(false);
					redbird.setChosen(false);
					yellowbird.setChosen(false);
					bird = whitebird;
					bird.moveTo(140, 700);
					new Thread(bird).start();
				}
				
				if(button.isChonse(e.getX(),e.getY()))
				{
					try {
						reFresh();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mouse.mousePress();
				
				if(bird.isChosen(e.getX(), e.getY()))
				{
					//System.out.println(Bird.isChosen());
					if(bird.isChosen())
					{
						bird.setDrug(true);
					}
				}
			}

			@Override
			/**
			 * 鏉惧紑鏃惰缃笩鐨勫垵濮嬮�熷害 鎾斁楦熼缈旂殑闊充箰
			 * */
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mouse.mouseLoose();
				if(bird.isDrug())
				{
					bird.setVx((centerX - bird.getX())/13.0);
					bird.setVy((centerY - bird.getY())/13.0);
					
					bird.setDrug(false);
					//Bird.setUnmove(false);
					try {
						bird.birdFly();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		};
		
		/**娣诲姞榧犳爣浜嬩欢鐩戝惉*/
		this.addMouseMotionListener(ma);    //榧犳爣绉诲姩浜嬩欢鐩戝惉
		this.addMouseListener(mb); 			//榧犳爣浜嬩欢鐩戝惉
		
		/**鍚姩绾跨▼*/
		

		//mouse.start();
		for(int i = 0; i < pigcount; i++)
		{
			//pig[i].start();
			new Thread(pig[i]).start();
		}
		
		/**鏃犻檺寰幆涓�鐩村埛鏂板睆骞曪紝鏋勯�犲浘褰㈣繍鍔�*/
		while(run)
		{
			repaint();						//閲嶇粯Jpanel鐣岄潰
			try {
				Thread.sleep(10);			//璁剧疆绾跨▼浼戠湢鏃堕棿
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return choice;
	}
	
	private void reFresh() throws IOException
	{
		this.choice = "p_3";
		run = false;
	}
}
