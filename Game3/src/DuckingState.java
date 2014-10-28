import org.newdawn.slick.Input;


public class DuckingState extends PlayerState {
	//int chargeTime;
	
	public DuckingState(){
		//chargeTime=0;
	}
	public void handleInput(Player player, Input input){
		//input
		if ( !input.isKeyDown(Input.KEY_DOWN) || !input.isKeyDown(Input.KEY_S)){
			player.changeState(PlayerState.standing);
		}
	}
	public void update(Player player,BlockMap map,float gravity){
		//chargeTime++;
		//chargelogic
		player.duck();
	}
	public void onEnter(Player player){
		
	}
public void onExit(Player player){
		
	}
}
