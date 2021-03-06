package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Redbird extends AngryBirds_Bird{

	private BufferedImage image;
	
	public AngryBirds_Redbird(int x, int y) throws IOException {
		super(x, y);
		// TODO Auto-generated constructor stub
		
		image = ImageIO.read(new File("picture/birdred.png"));
		present = image;
		this.width = present.getWidth();
		this.height = present.getHeight();
	}

	@Override
	public void special() {
		// TODO Auto-generated method stub
		special = false;
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
		special=false;
	}
}
