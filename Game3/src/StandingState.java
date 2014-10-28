import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class StandingState extends PlayerState{

	public StandingState(){
		
	}
	public void handleInput(Player player, Input input){
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
			player.changeState(PlayerState.ducking);
		
		}
		else if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
			player.changeState(PlayerState.walkingLeft);
		}

		//Check for Moving Right
		else if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
			player.changeState(PlayerState.walkingRight);
		}
	}
	public void update(Player player, BlockMap map,float gravity) throws SlickException{
		player.applyGravity(gravity, map);
	}
	public void onEnter(Player player){
		
	}
	public void onExit(Player player){
		
	}
	public void print(){
	
	}
}
