package pack;

public class Player extends GameObj{
	
	private ID id;
	public static final int WIDTH = 18;
	public static final int HEIGHT = 90;
	public static final int VELOCITY = 8;
	public static final int ADDITIONAL_OFFSET = 20; //so the players won't touch the sides of the screen
	
	Player(ID id){
		if (id != ID.ERROR) {
			this.id = id;
		}
		w = WIDTH;
		h = HEIGHT;
		resetPlayer();
	}
	
	@Override
	public void tick() {
		y += velY;
		y = stayInScreenXAxis(y, 0, Game.WINDOW_HEIGHT - h);
	}
	
	private int stayInScreenXAxis(int var, int min, int max) {
		if (var > max) {
			var = max;
		}
		else if (var < min) {
			var = min;
		}
		return var;
	}
	
	void resetPlayer() {
		y = Game.WINDOW_HEIGHT / 2 - HEIGHT;
		if (id == ID.P1) { //P1
			x = ADDITIONAL_OFFSET;
		}
		else{ //P2
			x = Game.WINDOW_WIDTH - w - ADDITIONAL_OFFSET;
		}
	}

	
}
