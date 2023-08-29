package snakeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;


//body of the snake game.
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener
{
	//declaring the requirements for the program:
	//screen dimensions
	
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=100;
	
	
	// creating arrays to hold coordinates for body parts of snake
	
	final int[]x = new int[GAME_UNITS];
	final int[]y = new int[GAME_UNITS];

	//important variables
	int bodyParts=6;
	int applesEaten=0;
	int appleX;
	int appleY;
	
	//snake goes right when game is launched
	char direction='R';
	
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
	}
	
	public void startGame()
	{
		
		newApple();
		running=true;
		timer= new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) 
	{
		
		
		if(running)
		{
			//populate the apple on the screen randomly
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//draw head and body of snake
			
			for(int i=0;i<bodyParts;i++)
			{
				if(i==0)
				{
					g.setColor(Color.yellow);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					
				}
				
				else
				{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//display score
			g.setColor(Color.white);	
			g.setFont(new Font("Ink Free",Font.BOLD,29));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " +applesEaten*10, (SCREEN_WIDTH-metrics.stringWidth("Score: " +applesEaten*10))/2, g.getFont().getSize());
		}
		
		else
		{
			gameOver(g);
		}
	
		
	}
	public void newApple() 
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		 
	}
	
	public void move() 
	{
		for(int i=bodyParts; i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
			
		}
		
		switch(direction)
		{
			case 'U':
				y[0]=y[0]-UNIT_SIZE;
				break;
				
			case 'D':
				y[0]=y[0]+UNIT_SIZE;
				break;
				
			case 'L':
				x[0]= x[0]-UNIT_SIZE;	
				break;
		
				
			case 'R':
				x[0] = x[0]+UNIT_SIZE;	
				break;
		}
		
		
	}
	
	public void checkApple()
	{
		if((x[0]==appleX) && (y[0]==appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	public void checkCollisions() 
	{
		
		
		//if head collides with body, game over
		for(int i=bodyParts; i>0;i--)
		{
			if((x[0]==x[i] && (y[0]==y[i])))
			{
				running = false;
			}
		}
		
		// if head touches any of the borders game is over
		
		//left
		if(x[0]<0)
		{
			running = false;
		}
		
		//right
		if(x[0] > SCREEN_WIDTH)
		{
			running = false;
		}
		
		//top
		if(y[0]<0)
		{
			running = false;
		}
		
		
		
		//bottom
		if(y[0] > SCREEN_HEIGHT)
		{
			running = false;
		}
		
		// stop the timer
		
		if(!running)
		{
			timer.stop();
		}
		
		
		
	}
	
	public void gameOver(Graphics g)
	{
		//Game over message
		g.setColor(Color.red);	
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER!!", (SCREEN_WIDTH-metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		//display score
		g.setColor(Color.white);	
		g.setFont(new Font("Ink Free",Font.BOLD,29));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " +applesEaten*10, (SCREEN_WIDTH-metrics2.stringWidth("Score: " +applesEaten*10))/2, g.getFont().getSize());
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
			
		}
		
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				{
					direction='L';
				}
				break;
				
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction='R';
				}
				break;
			
			case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction='U';
				}
				break;	
			
			
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction='D';
				}
				break;	
			}
			
		}
	}


	
}
