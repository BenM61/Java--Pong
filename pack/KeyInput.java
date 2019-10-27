package pack;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class KeyInput extends KeyAdapter{
	private List<GameObj> list;
	private static final int DEAFULT_VELOCITY = Player.VELOCITY;
	private boolean UP = false;
	private boolean DOWN = false;
	private boolean W = false;
	private boolean S = false;
	public KeyInput(List<GameObj> list) {
		this.list = list;
	}
	@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			Player player1 = (Player) list.get(0);
			Player player2 = (Player) list.get(1);
			//key events for player 1
			if (key == KeyEvent.VK_W) {
				W = true;
				player1.setVelY(-DEAFULT_VELOCITY);
			}
			if (key == KeyEvent.VK_S) {
				S = true;
				player1.setVelY(DEAFULT_VELOCITY);
			}
	
			//key events for player 2
			if (key == KeyEvent.VK_UP) {
	            UP = true;
	            player2.setVelY(-DEAFULT_VELOCITY);
			}
			if (key == KeyEvent.VK_DOWN) {
				DOWN = true;
				player2.setVelY(DEAFULT_VELOCITY);
			}
	}
	
	@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			Player player1 = (Player) list.get(0);
			Player player2 = (Player) list.get(1);
			//key events for player 1
			if (key == KeyEvent.VK_W) {
	            W = false;
	            if (S) {
	            	player1.setVelY(DEAFULT_VELOCITY);
	            }
	            else {
	            	player1.setVelY(0);
	            }
	        }
			if (key == KeyEvent.VK_S) {
				 S = false;
	            if (W) {
	            	player1.setVelY(-DEAFULT_VELOCITY);
	            }
	            else {
	            	player1.setVelY(0);
	            }
			}
			//key events for player 2
			if (key == KeyEvent.VK_UP) {
				UP = false;
				if (DOWN) {
					player2.setVelY(DEAFULT_VELOCITY);
				}
				else{
					player2.setVelY(0);
				}
			}
			if (key == KeyEvent.VK_DOWN) {
				DOWN = false;
				if (UP) {
					player2.setVelY(-DEAFULT_VELOCITY);
				}
				else {
					player2.setVelY(0);
				}
			}
	}
}
