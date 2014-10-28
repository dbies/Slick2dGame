import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

	

public class Enemy extends movingGameObject{
	//int ID; //to use bitwise operator on  0,isVisible,isAnchored,isDamageTaken
	int level;
	String movementType;
	String characterType;
	objectDirection lastDirectionSeen;

	
	
	
	public Enemy(boolean isAnch, int ssd, float xPos, float yPos, int ht, int wdth, String LeftFile, String RightFile, String standingFile, float spdX, float spdY, float maxHt, int hlth, int dmg) throws SlickException{
		isAnchored=isAnch;
		spriteSheetDuration=ssd;
		x=xPos;
		y=yPos;
		height=ht;
		width=wdth;
		file=standingFile;
		leftFile=LeftFile;
		rightFile=RightFile;
		speedX=spdX;
		speedY=spdY;
		maximumHeight=maxHt;
		health=hlth;
		damage=dmg;
		standingSS=new SpriteSheet(file, width, height);
		leftSS=new SpriteSheet(leftFile, width, height);
		rightSS=new SpriteSheet(rightFile, width, height);
		animation=new Animation(standingSS, spriteSheetDuration);
		leftAnimation=new Animation(leftSS, spriteSheetDuration);
		rightAnimation=new Animation(rightSS, spriteSheetDuration);
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
		lastDirectionSeen=null;
		isDamageTaken=false;
		String id = "0";
		if (isVisible)
			id+="1";
		else
			id+="0";
		if (isAnchored)
			id+="1";
		else
			id+="0";
		if (isDamageTaken)
			id+="1";
		else 
			id+="0";
		ID=Integer.parseInt(id);
	
	}
	public Enemy()  {
		// TODO Auto-generated constructor stub
		lastDirectionSeen=null;
		isDamageTaken=false;
	}

	

	private boolean tooFar(float playerX){
		if (playerX-x>500 ||playerX-x<-500){
			objState=objectState.Standing;
			return true;
		}
		objState=objectState.Attacking;
		return false;
	}
	public boolean viewCollision(BlockMap map, Polygon viewPolygon) throws SlickException//do i need to pass blockmap?
	{
		for (int i=0; i<BlockMap.entities.size();i++){
			Block entity1=(Block) BlockMap.entities.get(i);
			if (entity1.poly.intersects(viewPolygon)){
				return true;		
			}
		}
		return false;
	}
	
	public void updateEnemy(GameContainer c, float playerX, float playerY, BlockMap map, float gravity, int playerHeight, int playerWidth, Polygon playerPolygon) throws SlickException{
		if (currentState.name=="Hunting"){	
			searchForPlayer(map, gravity, playerPolygon);
		}		
		else if (currentState.name=="Walking"){
			//meander(map);
		}
		
		if (checkIfPlayerInView(playerX,playerY,map, playerHeight,playerWidth)){
			if(currentState.name!="Hunting"){
				artIntHandler.increaseLikelihood(currentState, "Hunting");
			}
			if (playerX<x){
				lastDirectionSeen=objectDirection.Left;
			}
			else{
				lastDirectionSeen=objectDirection.Right;
			}
		}
		else {
			Random rand=new Random();
			int nextRand=rand.nextInt();
			if (nextRand>0 && nextRand<  1000000){ //0.00046 chance
				artIntHandler.resetLikelihood(currentState, "Hunting");
				artIntHandler.resetLikelihood(currentState, "Attacking");
				artIntHandler.resetLikelihood(currentState, "Walking");
				artIntHandler.increaseLikelihood(currentState, "Walking");
			}

		}
		//System.out.println(currentState.name);
		jump(gravity, map);
		aiState nextState=artIntHandler.getNext(currentState);
		currentState=nextState;
	}
	public void meander(BlockMap map) throws SlickException{
		Random randGen=new Random();
		double direction=randGen.nextDouble();
		 if (direction>.10000000000 && direction <.11111111111){
			 goLeft();
			 if(collision(map)){
				goLeft();
				if(objState!=objectState.Jumping && objState!=objectState.Falling){
					objState=objectState.Jumping;
				}
			}
		 }
	
			
			else {
				goRight();
				if(collision(map)){
					goLeft();
					if(objState!=objectState.Jumping && objState!=objectState.Falling){
						objState=objectState.Jumping;
					}
				}
		}	
	}
	public void searchForPlayer(BlockMap map, float gravity, Polygon playerPolygon) throws SlickException{
		if (lastDirectionSeen==null){
			artIntHandler.increaseLikelihood(currentState, "Walking");
		}
		else{
			if (lastDirectionSeen==objDirection.Left){
				goLeft();
				if(collision(map)){
					goRight();
					if(objState!=objectState.Jumping && objState!=objectState.Falling){
						objState=objectState.Jumping;
					}
				}
				//if (objectPolygon.intersects(playerPolygon)){
				//	goRight();
				//	goRight();
				//}
			}
			else if(lastDirectionSeen==objDirection.Right){
				goRight();
				if(collision(map)){
					goLeft();
					if(objState!=objectState.Jumping && objState!=objectState.Falling){
						objState=objectState.Jumping;
					}
				}
				//if (objectPolygon.intersects(playerPolygon)){
			//		goLeft();
				//	goLeft();
				//}
			}
		}
	}
	public void followPlayer(float playerX,BlockMap map, float gravity) throws SlickException{
		if (playerX<x){
			objDirection=objectDirection.Left;
				goLeft();
			
		if (collision(map)){
			goRight();
					if(objState!=objectState.Jumping && objState!=objectState.Falling){
				objState=objectState.Jumping;
			}
		}
	}
	else if (playerX>x){
		objDirection=objectDirection.Right;//right
		goRight();
		if(collision(map)){
			goLeft();
			if(objState!=objectState.Jumping && objState!=objectState.Falling){
				objState=objectState.Jumping;
			}
			
		}
	}
	}
	public boolean checkIfPlayerInView(float playerX,float playerY,BlockMap map, int playerHeight, int playerWidth) throws SlickException{
		Polygon viewPolygon = new Polygon();
		int sourceX=(int) x;
		int sourceY=(int) y;
		int destinationX=(int) playerX;
		int destinationY=(int) playerY;
		

			if (sourceX>=destinationX){ //if the enemy to right of player
				if (sourceY<=destinationY){ //enemy is above player
					viewPolygon=new Polygon(new float[]{playerX,playerY,playerX+playerWidth,playerY,x,y,x+width,y,x+width,y+height/2,x,y+height/2,playerX+playerWidth,playerY+playerHeight/2, playerX,playerY+playerHeight/2});
				}
				else if (sourceY>destinationY){ //if enemy below player
					viewPolygon=new Polygon(new float[]{playerX,playerY,playerX+playerWidth,playerY,x,y,x+width,y,x+width,y+height/2,x,y+height/2,playerX+playerWidth,playerY+playerHeight/2, playerX,playerY+playerHeight/2});
					
				}
				
			}
			else if (sourceX<destinationX){ //enemy to the left of player
				if (sourceY<=destinationY){ //enemy above
					viewPolygon=new Polygon(new float[]{x,y,x+width,y,playerX, playerY,playerX+playerWidth,playerY,playerX+playerWidth,playerY+playerHeight/2,playerX,playerY+playerHeight/2,x+width, y+height/2,x,y+height/2});
				}
				else if (sourceY>destinationY){//enemy below
					viewPolygon=new Polygon(new float[]{x,y,x+width,y,playerX, playerY,playerX+playerWidth,playerY,playerX+playerWidth,playerY+playerHeight/2,playerX,playerY+playerHeight/2,x+width, y+height/2,x,y+height/2});
				}
			}
		if (viewCollision(map,viewPolygon)){
			return false;
		}
		return true;
	}
	
	public void renderEnemy(GameContainer gc, Graphics g) throws SlickException{
		if (objState==objectState.Standing){
			animation.draw(x,y);
		}
		else if (objDirection==objectDirection.Left)
			leftAnimation.draw(x,y);
		else
			rightAnimation.draw(x,y);
	}
}


 