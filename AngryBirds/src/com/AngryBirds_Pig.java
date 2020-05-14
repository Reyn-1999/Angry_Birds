package com;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;

public class AngryBirds_Pig extends Thread{
	
	private BufferedImage[] image; 					//image buffer of pig
	private double x;								//x position of pig image
	private double y;								//y position of pig image
	private double width;							//width of pig image
	private double height;							//height of pig image
	private boolean alived;							//if pig is killed
	private boolean existed;							//if pig image exists	
	private BufferedImage present;					//present image in buffer
	private AudioClip sound;						//music effect when pig is killed
	
	AngryBirds_Pig() throws IOException
	{
		Init(0,0);
	}
	
	AngryBirds_Pig(int x, int y) throws IOException
	{
		Init(x,y);
	}
	
	public void Init(int x, int y) throws IOException
	{
		//initialization
		
		//load images
		this.image = new BufferedImage[5];
		this.image[0] = ImageIO.read(new File("picture/pig.png"));
		for(int i = 1; i < 5; i++)
		{
			this.image[i] = ImageIO.read(new File("picture/气泡" + i +".png"));
		}
		
		//set basic information
		this.width = image[0].getWidth();
		this.height = image[0].getHeight();
		this.x = x;
		this.y = y;
		this.present = image[0];    //show pig.png at the beginning
		this.alived = true;
		this.existed = true;
	}
	
	//play sound effect while the pig is dead
	private void pigdeathMusic()
	{
		try {
			this.sound = Applet.newAudioClip(new File("music/pigdeath2.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sound.play();
	}
	
	//while the pig is just killed
	//show bubble in sequence
	public void death()
	{
		for(int i = 1 ; i < 5; i++)
		{
			present = image[i];
			try {
				Thread.sleep(100);  //set interval = 100ms
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		present = null;			    //set present image to null
		existed = false;				//set pig to dead after showing bubbles
	}
	
	public void run()
	{
		while(true)
		{
			if(!alived)
			{
				//play death music & set to dead (show bubbles)
				pigdeathMusic();
				death();
				
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//tool functions

	public BufferedImage[] getImage() {
		return image;
	}

	public void setImage(BufferedImage[] image) {
		this.image = image;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public BufferedImage getPresent() {
		return present;
	}

	public void setPresent(BufferedImage present) {
		this.present = present;
	}

	public boolean isAlived() {
		return alived;
	}

	public void setAlived(boolean alived) {
		this.alived = alived;
	}

	public boolean isExisted() {
		return existed;
	}

	public void setExisted(boolean exited) {
		this.existed = existed;
	}
	
	
}

