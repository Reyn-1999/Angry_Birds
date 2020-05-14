package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Yellowbird extends AngryBirds_Bird{

	private BufferedImage[] image;				//图片buffer
	private double newVx;						//加速后vx
	private double newVy;						//加速后vy
	
	public AngryBirds_Yellowbird(int x, int y) throws IOException {
		super(x, y);
		// TODO Auto-generated constructor stub
		image = new BufferedImage[2];
		for(int i = 0; i < 2; i++)
		{
			this.image[i] = ImageIO.read(new File("picture/birdyellow" + i + ".png"));
		}
		present = image[0];
		this.width = present.getWidth();
		this.height = present.getHeight();
		newVx = 0;
		newVy = 0;
	}

	public double getNewVx() {
		return newVx;
	}

	public void setNewVx(double newVx) {
		this.newVx = newVx;
	}

	public double getNewVy() {
		return newVy;
	}

	public void setNewVy(double newVy) {
		this.newVy = newVy;
	}
	@Override
	public void special() {
		// TODO Auto-generated method stub
		vx = newVx;
		vy = newVy;
		
		if(y > 813 - height - 1) //上升阶段
		{
			special = false;
		}
	}
	
	@Override
	protected void reSet() {
		// TODO Auto-generated method stub
		this.present = null;
		this.fly = false;
		this.chosen = false;
	}
	
	@Override
	protected void changeitself() {
		changeFast();
		setNewVx(vx * 2);
		setNewVy(vy * 2);
	}
	
	public void changeFast()
	{
		present = image[1];
	}
	
	public void changeSlow()
	{
		present = image[0];
	}
}
