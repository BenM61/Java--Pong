package pack;

public class Ball extends GameObj{
	private int velX;
	public static final int VELOCITY = 8;
	public static final int WIDTH = 18;
	public static final int HEIGHT = WIDTH;
	
	Ball(){
		resetBall(ID.P1);
		w = WIDTH;
		h = HEIGHT;
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
		stayInScreenYAxis();
	}
	
	public void deflection(double fractionPlaceOnPlayer) { //Changes ball speed and direction after hitting a player
		double angleRadians = (fractionPlaceOnPlayer - 0.5) * Math.PI; //now 0 goes to -0.5*PI and 1 goes to 0.5*PI 
			velY = (int) Math.round(VELOCITY * Math.sin(angleRadians));
			velX *= -1; 
	}
	
	private void stayInScreenYAxis() { //Changes ball speed and direction after hitting a wall
		int min = 0;
		int max = Game.WINDOW_HEIGHT - h;
		if (y > max || y < min) {
			this.velY *= -1;
		}
	}

	void resetBall(ID loser) {
		x = Game.WINDOW_WIDTH / 2;
		y = (Game.WINDOW_HEIGHT - Player.HEIGHT) / 2;
		velY = 0;
		if (loser == ID.P1) {
			velX = VELOCITY;
		}
		else {
			velX = -VELOCITY;
		}
	}
}
