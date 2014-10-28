import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class WalkingLeft extends PlayerState{

		public WalkingLeft(){
			
		}
		public void handleInput(Player player, Input input){
			if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
				player.changeState(PlayerState.ducking);
			}
			//else if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
		//		player.stateManager.addState(StateManager.walkingLeft, player);
			//}

			//Check for Moving Right
			else if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
				player.changeState(PlayerState.walkingRight);
			}
		}
		public void update(Player player,BlockMap map,float gravity) throws SlickException{
			player.goLeft(map,gravity);
			player.applyGravity(gravity, map);
		}
		public void onEnter(Player player){
			
		}
		public void onExit(Player player){
		
		}
	}


