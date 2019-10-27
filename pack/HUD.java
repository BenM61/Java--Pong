package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class HUD {
	private Game game;
	private int P1Score; 
	private int P2Score;
	private int halfWidth = Game.WINDOW_WIDTH / 2;
	private int halfHeight = Game.WINDOW_HEIGHT / 2;
	private String winnerName;
	
	HUD(Game game){
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		//this.g = g;
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.white);
		//drawing the line at the middle
		float[] dashingPattern1 = {10f, 6f};
		Stroke stroke1 = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
		g2d.setStroke(stroke1);
		g2d.drawLine(Game.WINDOW_WIDTH / 2, 0, halfWidth, Game.WINDOW_HEIGHT);
		//drawing score
		int fontSize = 96;
		g.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		g.drawString(String.valueOf(P1Score) + "    " + String.valueOf(P2Score), halfWidth - 120, 100);
		//drawing the result of the game
		if (game.isGameOver) {
			fontSize = 84;
			g.setFont(new Font("Tahoma", Font.BOLD, fontSize));
			g.drawString( "Game Over", halfWidth - 250, halfHeight);
			fontSize = 72;
			g.setColor(Color.CYAN);
			g.setFont(new Font("Tahoma", Font.BOLD, fontSize));
			g.drawString(winnerName + " WON" , 400 , halfHeight + 100);	
		}
	}

	public int getP1Score() {
		return P1Score;
	}

	public int getP2Score() {
		return P2Score;
	}

	void upP1Score() {
		P1Score++;
	}

	void upP2Score() {
		P2Score++;
	}

	void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
}
