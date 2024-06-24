package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameHub extends JPanel implements ActionListener {
    Random random;
    static final int WIDTH = 700;
    static final int HEIGHT =700;
    static final int DELAY =80;
    static final int UNITS =35;
    static final int UNITS_SIZE = (WIDTH * HEIGHT) / (UNITS * UNITS);
    static final int Ext_ptdly=6000;
    Timer timerctd;
    int timedelay=1000;
    Timer Ext_timer;
    int apx;
    JPanel contenthold=new JPanel();
    boolean starttimer=true;
    JLabel label=new JLabel();
    JLabel score=new JLabel();
    int starttime=5;
    int Ext_counter;
    int totalpts;
    int apy;
    int appleseaten = 0;
    boolean running = false;
    int body = 4;
    int barrierlen = 0;
    int ext_X;
    int ext_Y;
    int[] x = new int[UNITS_SIZE];
    int[] y = new int[UNITS_SIZE];
    int[] bx = new int[UNITS_SIZE]; 
    int[] by = new int[UNITS_SIZE]; 
    Timer timer;
    char dir = 'R';
    boolean done=false;

    
    GameHub() {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        random=new Random();
        this.setFocusable(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        contenthold.setLayout(new BoxLayout(contenthold,BoxLayout.Y_AXIS));
        contenthold.setOpaque(false);
        this.add(contenthold);
        this.setBackground(Color.BLACK);
        label.setForeground(Color.white);
        label.setPreferredSize(new Dimension(50,20));
        label.setFont(new Font("Arial Black",Font.BOLD,20));
        this.addKeyListener(new Key());
       
        start();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        super.paintComponent(g); 
        draw(g);
    }
    public void draw(Graphics g) {
    	Graphics2D gd = (Graphics2D) g;
    	for(int i=0;i<HEIGHT/UNITS;i++) {
    		gd.drawLine(i*UNITS,0,i*UNITS,HEIGHT);
    		gd.drawLine(0, i*UNITS, WIDTH,i*UNITS);
    		
    	}
    	gd.setColor(Color.GREEN); 
    	gd.fillOval(apx,apy,UNITS,UNITS);
    	
    	for(int i=0;i<body;i++) {
    		if(i==0) {
    			gd.setColor(Color.BLUE);
    			gd.fill3DRect(x[i],y[i],UNITS,UNITS,true);
    		}
    		else {
    			gd.setColor(Color.RED);
    			gd.fill3DRect(x[i],y[i],UNITS,UNITS,true);
    		}
    	}
    	for(int i=0;i<barrierlen;i++) {
    		gd.setColor(Color.ORANGE);
    		gd.fill3DRect(bx[i],by[i],UNITS,UNITS,true);;
    	}
    	if(done) {
    		gd.setColor(Color.PINK);
        	gd.fillOval(ext_X,ext_Y,UNITS,UNITS);
    	}
    	
    } 
    public void start() {
    	running=true;
    	pointCalc();
    	createBarrier();
    	newApple();
    	timer=new Timer(DELAY,this);
    	timer.start();
    	
    }
    public void checkCollisions() {
    	for(int i=body;i>0;i--) {
    		if(x[0]==x[i]&& y[0]==y[i]) {
    			running=false;
    		}
    	}
    	if(x[0]<0) {
    		running=false;
    	}
    	else if(x[0]>WIDTH-UNITS) {
    		running=false;
    	}
    	else if(y[0]>HEIGHT-UNITS) {
    		running=false;
    	}
    	else if(y[0]<0) {
    		running=false;
    	}
    	for(int i=0;i<barrierlen;i++) {
    		if(x[0]==bx[i]&&y[0]==by[i]) {
    			running=false;
    		}
    		
    	}
    	
    }
    public void createBarrier() {
    	for(int i=0;i<barrierlen;i++) {
    		bx[i]=random.nextInt(HEIGHT/UNITS)*UNITS;
    		by[i]=random.nextInt(HEIGHT/UNITS)*UNITS;
    	}
    }
    public void newApple() {
    	random=new Random();
    	boolean overlap;
    	
    		do {
    			apx=random.nextInt(HEIGHT/UNITS)*UNITS;
        	    apy=random.nextInt(HEIGHT/UNITS)*UNITS;
        	    overlap=false;
    			for(int i=0;i<barrierlen;i++) {
    				if(apx==bx[i] && apy==by[i]) {
    					overlap=true;
    					break;
    				}
    			}	
    		}while((overlap));
    }
    
    public void extraPoints() {
    	if(appleseaten%6==0) {
    		ext_X=random.nextInt(HEIGHT/UNITS)*UNITS;
    		ext_Y=random.nextInt(HEIGHT/UNITS)*UNITS;
    		done=true;
    		starttimer=true;
    		if (Ext_timer != null) {
                Ext_timer.stop();
            }
    		if(timerctd!=null) {
    			timerctd.stop();
    		}
    		Ext_timer=new Timer(Ext_ptdly,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(done) {
						done=false;
						Ext_timer.stop();
						
					}
					
				}
    			
    		});
    		timerctd=new Timer(timedelay,new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					revalidate();
					repaint();
					if(!(starttimer)) {
						label.setVisible(false);
					}
					else {
						label.setVisible(true);
					    label.setText(""+starttime);
					    revalidate();
					    repaint();
					    contenthold.add(label);
						starttime--;
					}
					
					if(starttime==-1) {
						timerctd.stop();
						done=false;
						Ext_timer.stop();
						label.setVisible(false);
						starttime=5;
						
					}
					
				}
    			
    		});
    		label.setText("" + starttime);
 	        label.setVisible(true);
 	        revalidate();
 	        repaint();
 	        contenthold.add(label);
 	        updateCountdownLabel();
    		
    		 
    		
		    
		    
            timerctd.start();
    		Ext_timer.start();
    	}
    	
    	
    }
    public void pointCalc() {
    	totalpts=appleseaten+Ext_counter;
    	score.setText("Score: "+totalpts);
    	score.setForeground(Color.ORANGE);
    	score.setPreferredSize(new Dimension(95,60));
    	score.setFont(new Font("Cambria",Font.ITALIC,25));
    	score.setVisible(true);
    	contenthold.add(score);
    }
    public void checkApple() {
    	if(x[0]==apx && y[0]==apy) {
    		body++;
    		appleseaten++;
    		pointCalc();
    		newApple();
    		extraPoints();
    		}
    	}
    public void checkExtraPoints() {
    	if(x[0]==ext_X && y[0]==ext_Y && done) {
    	    if(starttime==5) {
    	    	Ext_counter+=18;
    	    }
    	    else if(starttime==4) {
    	    	Ext_counter+=14;
    	    }
    	    else if(starttime==3) {
    	    	Ext_counter+=9;
    	    }
    	    else if(starttime==2) {
    	    	Ext_counter+=6;
    	    }
    	    else if(starttime==1) {
    	    	Ext_counter+=3;
    	    }
    	    else if(starttime==0) {
    	    	Ext_counter+=1;
    	    }
    		
    		pointCalc();
    		extraPoints();
    		done=false;
    		starttimer=false;   
    		starttime=5;
    		}
    }
    private void updateCountdownLabel() {
        if (starttime >= 0) {
            label.setText("" + starttime);
            starttime--;
            revalidate();
            repaint();
        }
    }

    public void move() {
    	for(int i=body;i>0;i--) {
    		y[i]=y[i-1];
    		x[i]=x[i-1];
    	}
    	switch(dir) {
    	case 'R':
    		x[0]=x[0]+UNITS;
    		break;
    	case 'L':
    		x[0]=x[0]-UNITS;
    		break;
    	case 'U':
    		y[0]=y[0]-UNITS;
    		break;
    	case 'D':
    		y[0]=y[0]+UNITS;
    		break;
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(running) {
    		move();
    		checkCollisions();
    		checkApple();
    		checkExtraPoints();
    		
    		
    	}
    	
    	repaint();
    }


    public class Key extends KeyAdapter {
    	@Override
    	public void keyPressed(KeyEvent e) {
    		switch(e.getKeyCode()) {
    		case KeyEvent.VK_LEFT:
    			if(dir!='R') {
    				dir='L';
    			}
    			break;
    		case KeyEvent.VK_RIGHT:
    			if(dir!='L') {
    				dir='R';
    			}
    			break;
    		case KeyEvent.VK_UP:
    			if(dir!='D') {
    				dir='U';
    			}
    			break;
    		case KeyEvent.VK_DOWN:
    			if(dir!='U') {
    				dir='D';
    			}
    			break;
    		}
    	}
    }

   

    
}
