package com;

import java.awt.CardLayout;					// card panel layout
import java.awt.Image;						// image controller
import java.awt.Point;						// for drawing points
import java.awt.Toolkit;					// toolkit
import java.awt.event.MouseEvent;			// for mouse event
import java.awt.event.MouseListener;		// for mouse event listener
import java.io.File;						// for file IO
import java.io.IOException;					// for IO exception
import java.net.MalformedURLException;		// for URL wrong format exception
import java.net.URL;						// URL
import javax.swing.JFrame;					// JFrame
import javax.swing.JPanel;					// JPanel

public class AngryBirds_Windows extends JFrame{
	/**** private variable ****/	
	private JPanel backPanel;				// the back panel
	private CardLayout layout;				// card layout
	private AngryBirds_Lodin p_1;			// loading class
	private AngryBirds_Chosen p_2;			// chosen level class
	private AngryBirds_Plays p_3;			// play class
	private AngryBirds_Victory p_4;			// victory class
	private JPanel p_5;						// 
	private AngryBirds_Mouse mouse;			// mouse
	/**************************/

	// Construct Function
	public AngryBirds_Windows() throws IOException{
		super("Angry Birds");				// set title
		layout = new CardLayout();			// set card layout
		backPanel = new JPanel(layout);		// set panel with layout

		p_1 = new AngryBirds_Lodin(false);	// run = false
		p_2 = new AngryBirds_Chosen(false);	
		// p_3 = new AngryBirds_Plays(false);
		p_4 = new AngryBirds_Victory(false);
		p_5 = new JPanel();

		// add page into card layout in order
		backPanel.add(p_1,"p1");			// first load
		backPanel.add(p_2,"p2");			// second chose level
		// backPanel.add(p_3,"p3");			// third play
		backPanel.add(p_4,"p4");			// then victory
		backPanel.add(p_5,"blank");			// then blank panel

		this.getContentPane().add(backPanel);	// add backPanel into ContentPane
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1600, 900);
		this.setResizable(false);				// set resizable is true
		this.setLocationRelativeTo(null);		// absolute position
		this.setVisible(true);

		// set Mouse cursor
		URL classURL = this.getClass().getResource("");
		Image imageCursor = Toolkit.getDefaultToolkit().getImage(classURL);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageCursor,new Point(0,0),"cursor"));
	}

	public void action() throws InterruptedException, IOException {
		p_1.run = true;
		String flag = p_1.action();			// flag is the next choice
		int level = 1;
		while(true){
			if(flag == "p_1"){
				// loading page
				layout.show(backPanel, "p1");
				p_1.run = true;
				flag = p_1.action();
			}
			else if(flag == "p_2"){
				// choose page
				/**/ // can add new level choose here
				layout.show(backPanel, "p2");
				p_2.run = true;
				AngryBirds_Choice choice = p_2.action();
				flag = choice.flag;
				level = choice.level;
			}
			else if(flag == "p_3"){
				// play page
				// add argument to record level here
				p_3 = new AngryBirds_Plays(false,level);
				p_3.run = true;
				backPanel.add(p_3,"p3");
				layout.show(backPanel, "p3");
				flag = p_3.action();				// add argument here /**/
				layout.show(backPanel, "blank");
				backPanel.remove(p_3);
				// p_3 = new AngryBirds_Plays(false);	// refresh here when done
				// backPanel.add(p_3,"p3");
			}
			else if(flag == "p_4"){
				// victory page
				layout.show(backPanel, "p4");
				p_4.run = true;
				flag = p_4.action();
			}
			Thread.sleep(1);							// sleep one second
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException{
		AngryBirds_Windows window = new AngryBirds_Windows();
		window.action();							///game start
	}
}
