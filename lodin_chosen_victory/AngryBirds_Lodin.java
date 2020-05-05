package wang.zhi.yuan;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;					// For painting
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;	// For mouse op
import java.awt.image.BufferedImage;		// For creating and changing images
import java.io.File;						// For File IO
import java.io.IOException;					// For Exception in IO procedure
import java.net.MalformedURLException;		// For URL format wrong exception

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AngryBirds_Lodin extends JPanel{
	
	private BufferedImage background;		//background image
	private BufferedImage button;			//button image
	private int height;						//the height of button image
	private int weight;						//the width of button image
	private AngryBirds_Mouse mouse;			//mouse
	boolean run;							//if running
	AudioClip sound;						//sound
	
	AngryBirds_Lodin(boolean run) throws IOException
	{
		background = ImageIO.read(new File("src/picture/background.png"));
		button = ImageIO.read(new File("src/picture/startbutton.png"));
		this.height = button.getHeight();
		this.weight = button.getWidth();
		this.run = run;
		this.sound = Applet.newAudioClip(new File("src/music/backmusic.wav").toURI().toURL());
		this.sound.loop();
		this.mouse = new AngryBirds_Mouse();
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		g.drawImage(button, 600, 550, null);
		g.drawImage(mouse.getPresent(), mouse.getX(), mouse.getY(), null);
	}
	
	public String action() throws MalformedURLException
	{	
		MouseListener ma = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX() > 670 && e.getX() < 850 && e.getY() > 660 && e.getY() < 740)
				{
					run =  false;
					sound.stop();
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
		
		return "p_2";
	}
}



