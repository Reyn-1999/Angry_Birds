package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Button {
	
	private BufferedImage buttonImage; 			//current button image
	private int x;                              //xy position of top left corner
	private int y;
	private int width;
	private int height;
	
	public BufferedImage getButtonImage() {
		return buttonImage;
	}

	public void setButtonImage(BufferedImage buttonImage) {
		this.buttonImage = buttonImage;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	AngryBirds_Button(int x, int y) throws IOException
	{
		//default use for restarting game
		this.buttonImage = ImageIO.read(new File("picture/refresh.png"));
		this.x = x;
		this.y = y;
		this.width = buttonImage.getWidth();
		this.height = buttonImage.getHeight();
	}
	
	public boolean isChosen(int x, int y)
	{
		//xy in range of image
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
}
