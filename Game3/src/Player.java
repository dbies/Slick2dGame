
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;



public class Player extends movingGameObject {
	///////////////////
	//START VARIABLES//
	///////////////////
	
	// Keep at 0 for declaration of variable. Do not change.
	float jumpChange = 0;
	int jumpBoost=0;
	int boostMax=20;
	PlayerState currentActionState;
	//StateManager stateManager;
	///////////////////
	///END VARIABLES///
	///////////////////
	
	public Player(String leftDuckFile,String rightDuckFile,boolean isAnch, int ssd, float xPos, float yPos, int ht, int wdth, String LeftFile, String RightFile, String standingFile, float spdX, float spdY, float maxHt, float hlth, float dmg) throws SlickException{
		isAnchored=isAnch;
		spriteSheetDuration=ssd;
		x=xPos;
		y=yPos;
		height=ht;
		width=wdth;
		file=standingFile;
		leftFile=LeftFile;
		rightFile=RightFile;
		leftDuckingFile=leftDuckFile;
		rightDuckingFile=rightDuckFile;
		speedX=spdX;
		speedY=spdY;
		maximumHeight=maxHt;
		health=hlth;
		damage=dmg;
		standingSS=new SpriteSheet(file, width, height);
		leftSS=new SpriteSheet(leftFile, width, height);
		rightSS=new SpriteSheet(rightFile, width, height);
		leftDuckingSS=new SpriteSheet(leftDuckingFile, width, height/2);//duckingHeight);
		rightDuckingSS=new SpriteSheet(rightDuckingFile, width, height/2);
		animation=new Animation(standingSS, spriteSheetDuration);
		leftAnimation=new Animation(leftSS, spriteSheetDuration);
		rightAnimation=new Animation(rightSS, spriteSheetDuration);
		leftDuckingAnimation=new Animation(leftDuckingSS, spriteSheetDuration);
		rightDuckingAnimation=new Animation(rightDuckingSS,spriteSheetDuration);
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
		//stateManager=new StateManager();
		
		}
	public Player(){
	
	}
	public void Initialize() throws SlickException{
		standingSS=new SpriteSheet(file, width, height);
		leftSS=new SpriteSheet(leftFile, width, height);
		rightSS=new SpriteSheet(rightFile, width, height);
		leftDuckingSS=new SpriteSheet(leftDuckingFile, width, height/2);//duckingHeight);
		rightDuckingSS=new SpriteSheet(rightDuckingFile, width, height/2);
		animation=new Animation(standingSS, spriteSheetDuration);
		leftAnimation=new Animation(leftSS, spriteSheetDuration);
		rightAnimation=new Animation(rightSS, spriteSheetDuration);
		leftDuckingAnimation=new Animation(leftDuckingSS, spriteSheetDuration);
		rightDuckingAnimation=new Animation(rightDuckingSS,spriteSheetDuration);
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
	//	stateManager=new StateManager();
		//stateManager.initialize(this,StateManager.standing);
		isDucking=false;
		isDamageTaken=false;
		objDirection=objectDirection.Left;
		currentActionState=new PlayerState();
		currentActionState=PlayerState.standing;
		calculateID();
	}

	public void changeState(PlayerState state){
		currentActionState.onExit(this);
		currentActionState=state;
		currentActionState.onEnter(this);
	}
	public void handleInput(Input input){
		//stateManager.handleInput(this, input);
		currentActionState.handleInput(this, input);
	}
	//public void changeState(PlayerState state){
	//	playerState=state;
	//	playerState.onEnter(this);
	//}
	public void updatePlayer(GameContainer gc, int delta, BlockMap map, Input input,float gravity) throws SlickException{
	//	stateManager.update(this,map,gravity);
		//currentActionState.update(this, map, gravity);
		if (objState!=objectState.Dead){

			//Check for Moving Left
			if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)){
				objDirection=objectDirection.Left;
				goLeft();
				if (collision(map)){
					goRight();
				}
			}

			//Check for Moving Right
			if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)){
				objDirection=objectDirection.Right;
				goRight();
				if (collision(map)){
					goLeft();
				}
			}

			//Check for Jump Action (Standing Still)
			if (input.isKeyDown(Input.KEY_SPACE) &&  objState!=objectState.Jumping &&objState!=objectState.Falling){
				objState=objectState.Jumping;
				jumpChange = 0;
			}

			if (input.isKeyDown(Input.KEY_ADD)) {
				System.out.println(health);
				health+=10;
			}
			
			if (input.isKeyDown(Input.KEY_SUBTRACT)) {
				System.out.println(health);
				health-=10;
			}
			
			if (input.isKeyPressed(Input.KEY_H)) {
				y=0;
				objectPolygon.setY(y);
			}
			
			//Jump Action
			if(objState==objectState.Jumping){
				y-=(speedY - jumpChange);
				jumpChange+=.1;
				objectPolygon.setY(y);
				deltaY-=speedY;

				if (collision(map)){
					y+=(speedY+ jumpChange);
					objectPolygon.setY(y);
					objState=objectState.Falling;
					deltaY=0;
				}

				if (deltaY<=-maximumHeight){
					objState=objectState.Falling;
					deltaY=0;
				}
			}
			//End Jump Action
		
			applyGravity(gravity, map);
			
		}
	
	}
	public void goLeft(BlockMap map,float gravity) throws SlickException{
		objDirection=objectDirection.Left;
		x-=speedX;
		objectPolygon.setX(x);
		if (collision(map)){
			goRight(map,gravity);
		}
	}
	public void goRight(BlockMap map,float gravity) throws SlickException{
		objDirection=objectDirection.Right;
		x+=speedX;
		objectPolygon.setX(x);
		if (collision(map)){
			goLeft(map,gravity);
		}
	}
	public void itemCollision(){
		
	}

	public void renderPlayer(GameContainer gc, Graphics g){
		int result;
//		System.out.println(ID);
		if ((result=ID & 0101) ==0101){
			leftDuckingAnimation.draw(x,y);
		}
		else if ((result=ID & 1101) ==1101){
			rightDuckingAnimation.draw(x,y);
		}
		else if ((result=ID & 0100) ==0100){
				leftAnimation.draw(x,y);			
		} 
		else if ((result=ID & 1100) ==1100){
			rightAnimation.draw(x,y);
		} 
		
	}

	public float getHealth() {
		return health;
	}
	public int getDirection(){
		if (objDirection==objectDirection.Left){
			return 0;
		}
		else 
			return 1;
	}
	public void duck() {
		
		
	}

}
