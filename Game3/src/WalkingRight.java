import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class WalkingRight extends PlayerState{

		public WalkingRight(){
			
		}
		public void handleInput(Player player, Input input){
			if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
				player.changeState(PlayerState.ducking);
			}
			else if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
				player.changeState(PlayerState.walkingLeft);
			}

		}
		public void update(Player player,BlockMap map,float gravity) throws SlickException {
			player.goRight(map,gravity);
			player.applyGravity(gravity, map);
		}
		public void onEnter(Player player){
		
		}
		public void onExit(Player player){
		}
	}


