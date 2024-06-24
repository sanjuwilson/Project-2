package snake;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame{
	GameHub gb;
	Frame(){	
		gb=new GameHub();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(gb);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
