import java.util.Stack;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class StateManager {
	Stack<PlayerState> playerStateStack;
	static StandingState standing;
	static DuckingState ducking; //if charge time need to create new state when change to it
	static JumpingState jumping;
	static WalkingLeft walkingLeft;
	static WalkingRight walkingRight;
	
	public StateManager(){
		playerStateStack=new Stack<PlayerState>();
	}
	public void initialize(Player player, PlayerState state){
		playerStateStack.push(state);
		//playerStateStack.peek().onEnter(player);
	}
	public void update(Player player, BlockMap map, float gravity) throws SlickException {
		//PlayerState currentState=
		//playerStateStack.peek().update(player, map, gravity);
		playerStateStack.peek().print();
		//System.out.println("meow" );
		//currentState.update(player,map,gravity);
	}
	public void handleInput(Player player, Input input){
		playerStateStack.peek().handleInput(player, input);
	}
	public void addState(PlayerState state, Player player){
		playerStateStack.peek().onExit(player);
		playerStateStack.push(state);
		playerStateStack.peek().onEnter(player);
	}
	public void removeState(PlayerState state, Player player){
		playerStateStack.pop();
	}
}
