import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class PlayerState {
	
	static StandingState standing;
	static DuckingState ducking; //if charge time need to create new state when change to it
	static JumpingState jumping;
	static WalkingLeft walkingLeft;
	static WalkingRight walkingRight;
	
	public PlayerState(){
		
	}
	public void update(Player player, BlockMap map,float gravity)throws SlickException {
	}
	public void handleInput(Player player, Input input){
		
	}
	public void onExit(Player player){
		
	}
	public void onEnter(Player player){
	
	}
	public void print(){
		
	}
}
