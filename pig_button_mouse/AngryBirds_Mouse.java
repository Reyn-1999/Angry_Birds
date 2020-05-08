package wang.zhi.yuan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Mouse extends Thread{
	
	private int x;								//xy position of top left corner
	private int y;								
	private BufferedImage[] image;				//buffer image of mouse
	private BufferedImage present;				//present image of mouse

	AngryBirds_Mouse() throws IOException
	{
		//initialize at top left corner
		x = 0;
		y = 0;
		image = new BufferedImage[2];
		image[0] = ImageIO.read(new File("src/picture/mouse1.png"));    //loose
		image[1] = ImageIO.read(new File("src/picture/mouse3.png"));    //press
		
		present = image[0];    //initialize to loose
	}
	
	/**move mouse*/
	public void moveto(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**set to loose*/
	public void mouseLoose()
	{
		present = image[0];
	}
	
	/**set to press*/
	public void mousePress()
	{
		present = image[1];
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage[] getImage() {
		return image;
	}

	public void setImage(BufferedImage[] image) {
		this.image = image;
	}

	public BufferedImage getPresent() {
		return present;
	}

	public void setPresent(BufferedImage present) {
		this.present = present;
	}
}

