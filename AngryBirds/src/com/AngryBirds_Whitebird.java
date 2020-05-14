package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Whitebird extends AngryBirds_Bird{

	private BufferedImage[] image=new BufferedImage[5];
	BufferedImage[] present=new BufferedImage[2]; 
	private double newVx;		//蛋的vx
	private double newVy;		//蛋的vy
	private double newx;		//蛋的x
	private double newy;		//蛋的y
	private int changecnt=2;
	private boolean collide=false;
	
	public AngryBirds_Whitebird(int x, int y) throws IOException {
		super(x, y);
		// TODO Auto-generated constructor stub
		for(int i=0;i<5;i++) {
			image[i] = ImageIO.read(new File("picture/birdwhite"+i+".png"));
		}
		present[0] = image[0];
		this.width = present[0].getWidth();
		this.height = present[0].getHeight();
		present[1]=null;
		newx=newy=0;
	}

	@Override
	public void special() {
		// TODO Auto-generated method stub
		if(y > 813 - height - 1)
		{
			vx *= 0.9;
		}
		vx *= 0.995;
		vy += 0.08;
		newy += newVy;
		if(changecnt>=5) {
			this.present[1]=null;
			if(this.present[0]==null) {
				reSet();
			}
		}
		else if(collide||newy>=740)
		{
			newVy = 0;
			this.present[1]=image[changecnt];
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changecnt++;
		}
	}

	@Override
	protected void reSet() {
		// TODO Auto-generated method stub
		this.present[0] = null;
		if(this.present[1]==null) {
			this.fly = false;
			this.chosen = false;
		}
	}
	
	@Override
	protected void changeitself() {
		present[1]=image[1];
		newx=x;newy=y;
		newVx=0;newVy=5;
	}
	
	@Override
	public boolean isCollide(AngryBirds_Pig pig)
	{
		if( distance(pig) < 50)
		{
			return true;
		}
		if(present[1]!=null) {
		if(distance(pig,1)<100) {
			collide=true;
			return true;
		}}
		
		return false;
	}
	
	double distance(AngryBirds_Pig pig,int i) {
		return Math.sqrt(
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX() + present[1].getWidth() / 2)) * 
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX() + present[1].getWidth() / 2)) +
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY() + present[1].getHeight() / 2)) * 
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY() + present[1].getHeight() / 2))
				);
	}
	
	public double getnewX() {
		return newx;
	}
	
	public double getnewY() {
		return newy;
	}
}
