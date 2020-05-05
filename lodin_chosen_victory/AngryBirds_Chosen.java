package wang.zhi.yuan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class AngryBirds_Chosen extends JPanel{
	
	private BufferedImage background;		//background image
	private AngryBirds_Mouse mouse;			//mouse	
	boolean run;							//if running
	AngryBirds_Choice mychoice;
	
	AngryBirds_Chosen(boolean run) throws IOException
	{
		background = ImageIO.read(new File("src/picture/chosen1.png"));
		this.run = run;
		this.mouse = new AngryBirds_Mouse();
		mychoice = new AngryBirds_Choice();
		mychoice.p3 = "p_3";
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		g.drawImage(mouse.getPresent(), mouse.getX(), mouse.getY(), null);
	}
	
	public AngryBirds_Choice action() throws MalformedURLException
	{	
		MouseListener ma = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getPoint());
				if(e.getX() > 260 && e.getX() < 360 && e.getY() > 250 && e.getY() < 350)
				{
					mychoice.level = 1;
					run =  false;
				}
				if(e.getX() > 420 && e.getX() < 520 && e.getY() > 250 && e.getY() < 350)
				{
					mychoice.level = 2;
					run =  false;
				}
				if(e.getX() > 580 && e.getX() < 680 && e.getY() > 250 && e.getY() < 350)
				{
					mychoice.level = 3;
					run =  false;
				}
				if(e.getX() > 740 && e.getX() < 840 && e.getY() > 250 && e.getY() < 350)
				{
					mychoice.level = 4;
					run =  false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mouse.mousePress();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouse.mouseLoose();
			}
		};
		
		MouseMotionListener mb = new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				mouse.moveto(e.getX(),e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouse.moveto(e.getX(),e.getY());
			}
			
		};
		
		addMouseListener(ma);
		addMouseMotionListener(mb);
		
		while(run)
		{
			repaint();							//repaint
			try {
				Thread.sleep(10);				//set the sleep time of thread
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		return mychoice;
	}
}


