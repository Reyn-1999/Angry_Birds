package com;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

public abstract class AngryBirds_Bird implements Runnable{
	
    BufferedImage present; 							//鸟图片
	
	protected double x;								//位置x
	protected double y;								//位置y
	protected int width;							//鸟宽度
	protected int height;							//鸟高度
	protected double vx;							//横向速度
	protected double vy;							//纵向速度
	protected boolean drag;							//被拖拽
	protected boolean fly;							//在空中
	protected boolean chosen;					    //被选择
	protected boolean special;						//鸟的技能
	protected AudioClip sound;						//鸟叫声
	protected double initX;							//初始位置x
	protected double initY;							//初始位置y
	
	
	/**初始化位置*/
	public AngryBirds_Bird(int x, int y) throws IOException
	{
		this.vx = vy = 0;
		this.x = x;
		this.y = y;
		this.drag = false;
		this.fly = false;
		this.chosen = false;
		this.special = false;
		this.initX = x;
		this.initY = y;
	}
	
	/**移动到弹弓上*/
	public void moveTo(double x, double y)
	{
		this.x = x - width / 2 ;
		this.y = y - height / 2;
	}
	/**移动到初始位置*/
	public void moveToInit()
	{
		this.x = initX;
		this.y = initY;
	}
	/**
	 * 
	 * 某只鸟是否被选中
	 * 
	 * */
	public boolean isChosen(int x, int y)
	{
		int dx = (int) (x - this.x);
		int dy = (int) (y - this.y);
		return (dx > 0 && dx < width) && (dy > 0 && dy < height);
	}
	
	/**持续飞翔*/
	public void run()
	{
		while(this.chosen)
		{
			this.move();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**物理飞翔*/
	public void move()
	{	
		if(fly)
		{
			if(special)
			{	
				special();
			}
			else
			{
				if(y > 813 - height - 1)
				{
					vx *= 0.9;
				}
				
				vx *= 0.995;
				vy += 0.08;
			}
		}
		
		if(y > 813 - height || y < 0)
		{
			if(y > 813 - height)
			{
				y = 813 - height;
			}
			
			if(y < 0)
			{
				y = 0;
			}
			
			vy *= -0.5;
		}
		
		if(Math.abs(vx) <= 0.1 && Math.abs(vy) <= 0.08)
		{
			vx = vy = 0;
			
			//fly = false;
			if(fly)
			{
				reSet();
			}
		}
			
		x += vx;
		y += vy;
		
		//System.out.println("vx:"+vx+"vy"+vy);
	}
	
	/**撞到猪*/
	public boolean isCollide(AngryBirds_Pig pig)
	{
		if( distance(pig) < 50)
		{
			return true;
		}
		
		return false;
	}
	
	/**和猪的距离*/
	public double distance(AngryBirds_Pig pig)
	{
		/**sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))*/
		return Math.sqrt(
				((pig.getX() + pig.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) * 
				((pig.getX() + pig.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) +
				((pig.getY() + pig.getHeight() / 2) - (this.getY() + this.getHeight() / 2)) * 
				((pig.getY() + pig.getHeight() / 2) - (this.getY() + this.getHeight() / 2))
				);
	}
	
	/**发射时鸟叫声*/
	public void birdFly() throws MalformedURLException
	{
		this.sound = Applet.newAudioClip(new File("music/birdflying.wav").toURI().toURL());
		sound.play();
	}

	abstract public void special();
	abstract protected void reSet();
	protected abstract void changeitself();

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

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public boolean isDragged() {
		return drag;
	}

	public void setDragged(boolean dragged) {
		this.drag = dragged;
	}

	public boolean isFly() {
		return fly;
	}

	public void setFly(boolean isfly) {
		this.fly = isfly;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chonsen) {
		this.chosen = chonsen;
	}

	public double getInitX(){
		return initX;
	}

	public double getInitY(){
		return initY;
	}
}

