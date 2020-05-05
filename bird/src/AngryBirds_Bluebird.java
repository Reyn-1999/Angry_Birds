package wang.zhi.yuan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AngryBirds_Bluebird extends AngryBirds_Bird{
	
	BufferedImage[] present=new BufferedImage[3]; 	

	private BufferedImage[] image;				//图片buffer
	private double[] newVx=new double[3];		//分裂后vx
	private double[] newVy=new double[3];		//分裂后vy
	private double[] newx=new double[3];		//分裂后x
	private double[] newy=new double[3];		//分裂后y
	private boolean haschanged=false;
	
	public AngryBirds_Bluebird(int x, int y) throws IOException {
		super(x, y);
		// TODO Auto-generated constructor stub
		image = new BufferedImage[2];
		for(int i = 0; i < 2; i++)
		{
			this.image[i] = ImageIO.read(new File("src/picture/birdblue" + i + ".png"));
		}
		present[0] = image[0];
		present[1]=null;
		present[2]=null;
		this.width = present[0].getWidth();
		this.height = present[0].getHeight();
		newVx[0] =newVx[1]=newVx[2]= 0;
		newVy[0] =newVy[1]=newVy[2]= 0;
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
		for(int i=1;i<3;i++) {
			if(newy[i] > 813 - height - 1)
			{
				newVx[i] *= 0.9;
			}
			newVx[i] *= 0.995;
			newVy[i] += 0.08;
			if(newy[i] > 813 - height || newy[i] < 0)
			{
				if(newy[i] > 813 - height)
				{
					newy[i] = 813 - height;
				}
				
				if(newy[i] < 0)
				{
					newy[i] = 0;
				}
				
				newVy[i] *= -0.5;
			}
			if(Math.abs(newVx[i]) <= 0.1 && Math.abs(newVy[i]) <= 0.08)
			{
				newVx[i] = newVy[i] = 0;
				if(fly)
				{
					reSet(i);
					reSet();
				}
			}
			newx[i] += newVx[i];
			newy[i] += newVy[i];
		}
	}
	
	protected void reSet(int i) {
		// TODO Auto-generated method stub
		this.present[i] = null;
	}
	
	@Override
	protected void reSet() {
		this.present[0]=null;
		if(present[1]==null&&present[2]==null) {
			this.fly=false;
			this.chosen=false;
		}
	}
	
	@Override
	protected void changeitself() {
		if(haschanged)return;
		present[0]=image[1];
		present[1]=image[1];
		present[2]=image[1];
		this.width=present[1].getWidth();
		this.height = present[1].getHeight();
		this.newVx[1]=vx*1.3;
		this.newVy[1]=vy*0.9;
		this.newVx[2]=vx*0.7;
		this.newVy[2]=vy*1.1;
		this.newx[1]=x;
		this.newy[1]=y;
		this.newx[2]=x;
		this.newy[2]=y;
		haschanged=true;
	}
	
	public double getnewX(int i) {
		return newx[i];
	}
	
	public double getnewY(int i) {
		return newy[i];
	}
	
	@Override
	public double distence(AngryBirds_Pig pig)
	{
		/**sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))*/
		double dis1= Math.sqrt(
				((pig.getX() + pig.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) * 
				((pig.getX() + pig.getWidth() / 2) - (this.getX() + this.getWidth() / 2)) +
				((pig.getY() + pig.getHeight() / 2) - (this.getY() + this.getHeight() / 2)) * 
				((pig.getY() + pig.getHeight() / 2) - (this.getY() + this.getHeight() / 2))
				);
		double dis2=Math.sqrt(
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX(1) + this.getWidth() / 2)) * 
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX(1) + this.getWidth() / 2)) +
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY(1) + this.getHeight() / 2)) * 
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY(1) + this.getHeight() / 2))
				);
		double dis3=Math.sqrt(
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX(2) + this.getWidth() / 2)) * 
				((pig.getX() + pig.getWidth() / 2) - (this.getnewX(2) + this.getWidth() / 2)) +
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY(2) + this.getHeight() / 2)) * 
				((pig.getY() + pig.getHeight() / 2) - (this.getnewY(2) + this.getHeight() / 2))
				);
		return Math.min(Math.min(dis1,dis2),dis3);
	}
}
