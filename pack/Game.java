package pack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable {
	
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 600;
	public static final int GAME_OVER_SCORE = 10; //after a player reaches it the game ends
	private Thread thread;
	private boolean running = false;
	private Player p1 = new Player(ID.P1);
	private Player p2 = new Player(ID.P2);
	private Ball ball = new Ball();
	private final List<GameObj> gameObjList; //a list that contains (in this order): P1, P2, Ball
	private final HUD hud;
	public boolean isGameOver = false;
	//private ID winnerID;

	
	Game(){
		new Window("Pong 2.0", WINDOW_WIDTH, WINDOW_HEIGHT, this);
		gameObjList = new ArrayList<>();
		gameObjList.add(p1);
		gameObjList.add(p2);
		gameObjList.add(ball);
		this.addKeyListener(new KeyInput(gameObjList));
		hud = new HUD(this);
		
	}
	
	public final synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public final synchronized void stop() {
		try{
			thread.join();
			running = false;
		}
		catch (Exception e) {
		}
	}
	
	@Override
	public final void run() {
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 90.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis(); //not necessary 
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1){
                tick();
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){ //just to show fps, counts until 1sec passed
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();		
	}

	public final void tick() {
		if (!isGameOver) {
			for (GameObj a : gameObjList) {
				a.tick();
			}
			hit(); //player hits the ball
			point(); //someone got a point
			hud.tick();
			checkWinner();
		}
	}
	
	public final void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		//draw the board
		g.setColor(Color.black);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		if (!isGameOver) {
			for (GameObj a : gameObjList) {
				a.render(g);
			}
		}
		hud.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	private void point() {
		Rectangle leftWall = new Rectangle(0,0,1,WINDOW_HEIGHT);
		Rectangle rightWall = new Rectangle(WINDOW_WIDTH - 1,0,1,WINDOW_HEIGHT);
		boolean p1Scored = ball.getBounds().intersects(rightWall);
		boolean p2Scored = ball.getBounds().intersects(leftWall);
		if (p1Scored || p2Scored) {
			if (p1Scored) {
				hud.upP1Score();
				restartScreen(ID.P2);
			}
			if (p2Scored){ //practically else
				hud.upP2Score();
				restartScreen(ID.P1);
			}
		}
	}
	
	private String checkGameOver() {
		if (hud.getP1Score() == GAME_OVER_SCORE) {
			isGameOver = true;
			return ID.P1.getName();
		}
		if (hud.getP2Score() == GAME_OVER_SCORE) {
			isGameOver = true;
			return ID.P2.getName();
		}
		else {
			return ID.ERROR.getName();
		}
	}
	
	private void checkWinner() {
		String winnerName = checkGameOver();
		hud.setWinnerName(winnerName);
	}
	
	
	private void restartScreen(ID loser) {
		for (int i = 0; i < gameObjList.size(); i++) {
			GameObj currObj = gameObjList.get(i);
			if (i < 2) { //currObj is a player
				Player p = (Player)currObj;
				p.resetPlayer();
			}
			else {
				Ball b = (Ball)currObj;
				b.resetBall(loser);
			}
		}
	}
	
	private void hit() {
		ID playerWhoHit = isHitting();
		if (playerWhoHit == ID.ERROR) {}
		else {
			double loc = findHitLocation(playerWhoHit);
			ball.deflection(loc);
		}
	}
	
	private ID isHitting() { //if the ball hits a player- returns the player's ID, else- returns error 
							 //the ball can't hit both players at once
		if (p1.getBounds().intersects(ball.getBounds())) {
			 return ID.P1;
		 }
		 else if (p2.getBounds().intersects(ball.getBounds())) {
			 return ID.P2;
		 }
		 else {
			 return ID.ERROR;
		 }
	}
	
	private double findHitLocation(ID playerId) { //return a double between 0 and 1 that indicates the place of intersection on the player
		Player curr;
		if (playerId == ID.P1) {
			curr = p1;
		}
		else {
			curr = p2;
		}
		int bottomOfBall = ball.getY() + ball.getHeight(); 
		int bottomOfCurr = curr.getY() + curr.getHeight();
		if (curr.getY() > ball.getY()) //the ball is part over/ part on the player
			return 0;
		if (bottomOfCurr < bottomOfBall) { //the ball is part under/ part on the player
			return 1;
		}
		int pixelDiff = ball.getY() - curr.getY();
		pixelDiff += ball.getHeight() / 2; //the intersections are with the middle of the ball
		return (double)pixelDiff / curr.getHeight(); 
	}
	
	public static void main(String[] args) {
		Game game = new Game();
	}

}
