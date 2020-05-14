package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Blackbird extends AngryBirds_Bird{

	private BufferedImage[] image=new BufferedImage[5];
	private int changecnt=0;
	private int dist=50;
	
	public AngryBirds_Blackbird(int x, int y) throws IOException {
		super(x, y);
		// TODO Auto-generated constructor stub
		
		image[0] = ImageIO.read(new File("picture/birdblack0.png"));
		for(int i=1;i<5;i++) {
			image[i]=ImageIO.read(new File("picture/birdblack"+i+".png"));
		}
		present= image[0];
		this.width = image[0].getWidth();
		this.height = image[0].getHeight();
	}

	@Override
	public void special() {
		// TODO Auto-generated method stub
		if(changecnt>=3) {
			special=false;
			vx=0;vy=0;
		}
		if(y > 813 - height - 1){
			vx *= 0.9;
		}
		vx *= 0.995;
		vy += 0.08;
		present=image[changecnt];
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		width=present.getWidth();
		height=present.getHeight();
		changecnt++;
		System.out.println(changecnt);
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
		this.width=image[1].getWidth();
		this.height=image[1].getHeight();
		present=image[1];
		changecnt++;
		dist=150;
	}
	
	@Override
	public boolean isCollide(AngryBirds_Pig pig)
	{
		if( distance(pig) < dist)
		{
			return true;
		}
		
		return false;
	}
}
