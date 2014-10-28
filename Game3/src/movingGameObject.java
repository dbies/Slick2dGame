import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;



public class movingGameObject extends visibleGameObject{
	///////////////////
	//START VARIABLES//
	///////////////////
	
	float speedX, speedY, maximumHeight, deltaY;
	float health, damage;
	//int duckingHeight;
	//List<aiState> aiList;
	String leftFile, rightFile,leftDuckingFile,rightDuckingFile;
	String aiFile;
	List<aiState> sources;
	List<aiState> destinations;
	aiState currentState;
	SpriteSheet leftSS, rightSS, leftDuckingSS,rightDuckingSS;
	boolean isDamageTaken;
	boolean isDucking;
	Animation leftAnimation, rightAnimation,leftDuckingAnimation, rightDuckingAnimation;
	
	
	enum objectState {Jumping, JumpingLeft, JumpingRight, Falling, Standing, Walking, Attacking, Dead};
	objectState objState;
	//Keep at 0. Regards to speed when falling
	float gravityChange = 0;
	//List<String> possActions;//{Jumping, JumpingLeft, JumpingRight, Falling, Standing, Walking, Attacking, Dead};
	aiHandler artIntHandler;
	int ID; //to use bitwise operator on  Direction,isVisible,isAnchored,isDucking
	//0 left 1 right
	
	///////////////////
	///END VARIABLES///
	///////////////////
	
	
	public movingGameObject(){
	
	}
	
	public movingGameObject(String rightDuckFile,String leftDuckFile,boolean isAnch, int ssd, float xPos, float yPos, int ht, int wdth, String LeftFile, String RightFile, String standingFile, float spdX, float spdY, float maxHt, float hlth, float dmg) throws SlickException{
//add ducking
		isAnchored=isAnch;
		spriteSheetDuration=ssd;
		x=xPos;
		y=yPos;
		height=ht;
		//duckingHeight=height/2;
		width=wdth;
		file=standingFile;
		leftFile=LeftFile;
		rightFile=RightFile;
		//duckingFile=duckFile;
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
		objectPolygon=new Polygon(new float[]{x-100,y,x+width,y,x+width,y+height,x-100,y+height});
	}
	
	public void initialize() throws SlickException{
		sources=new ArrayList<aiState>();
		destinations=new ArrayList<aiState>();
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
		//artIntHandler=new aiHandler(aiList);
		isDucking=false;
		isDamageTaken=false;
		objDirection=objectDirection.Left;
		fillMap();
		calculateID();
	}
	public void calculateID(){
		String id = "";
		if (objDirection==objectDirection.Left)
			id+="0";
		else
			id+="1";
		if (isVisible)
			id+="1";
		else
			id+="0";
		if (isAnchored)
			id+="1";
		else
			id+="0";
		if (isDucking)
			id+="1";
		else 
			id+="0";
		ID=Integer.parseInt(id);
	}
	public void onDamageTaken(float damage){
		isDamageTaken=true;
		calculateID();
	}
	public void onNotVisible(){
		isVisible=false;
		calculateID();
	}
	public void onVisible(){
		isVisible=true;
		calculateID();
	}
	public void fillMap(){
		List<aiState> aiList=new ArrayList<aiState>();
		aiState walkingState=new aiState("Walking", 1);
		aiState huntingState=new aiState("Hunting",0);
		aiState attackingState=new aiState("Attacking",0);
		
		try{
			Scanner scanner=new Scanner(new File(aiFile));
			
			int numberOfVertices;
			numberOfVertices=scanner.nextInt();
			for (int i=0;i<numberOfVertices;i++){ 
				String nextName=scanner.next();
				switch(nextName){
					case "W":aiList.add(walkingState);break;//aiList.add(new aiState("Walking", scanner.nextInt()));break;
					case "H": aiList.add(huntingState);break;//aiList.add(new aiState("Hunting", scanner.nextInt()));break;
					case "A":aiList.add(attackingState);break;// aiList.add(new aiState("Attacking", scanner.nextInt()));break;
				}	
			}
			
			String first;
			String second;
			//int firstLikelihood,secondLikelihood;
	
			while(scanner.hasNext()){
				first=scanner.next();
				//firstLikelihood=scanner.nextInt();
				second=scanner.next();
				//secondLikelihood=scanner.nextInt();
				switch(first){
				case "W": sources.add(walkingState);break;//aState=new aiState("Walking",firstLikelihood);sources.add(aState);break;
				case "H": sources.add(huntingState);break;//aState=new aiState("Hunting",firstLikelihood);sources.add(aState);break;
				case "A": sources.add(attackingState);break;//aState=new aiState("Attacking",firstLikelihood);sources.add(aState);break;
			}	
				switch(second){
					case "W":destinations.add(walkingState);break;// aState=new aiState("Walking",firstLikelihood);destinations.add(aState);break;
					case "H":destinations.add(huntingState);break;// aState=new aiState("Hunting",firstLikelihood);destinations.add(aState);break;
					case "A":destinations.add(attackingState);break;// aState=new aiState("Attacking",firstLikelihood);destinations.add(aState);break;
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		artIntHandler=new aiHandler(aiList);
		currentState=aiList.get(0);//first time first aistate in sources is current state
	
		for (int i=0;i<sources.size();i++){
			artIntHandler.addEdge(sources.get(i), destinations.get(i));
		}
		
	
	}
	public void onDuck(){
		height=height/2;
		isDucking=true;
		String id=Integer.toString(ID);
		id.toCharArray()[3]='1';
		ID=Integer.valueOf(id);
	}
	public void onStanding(){
		height=height*2;
		isDucking=false;
		String id=Integer.toString(ID);
		id.toCharArray()[3]='0';
		ID=Integer.valueOf(id);
	}
	public void updateObject(GameContainer gc, int delta, float gravity){

	}
	protected void goRight(){
		x+=speedX;
		objectPolygon.setX(x);
	}
	protected void goLeft(){
		x-=speedX;
		objectPolygon.setX(x);
	}
	public void Hit(float dmg){

		health-=damage;
		if (health<=0){
			health=0;
			objState=objectState.Dead;
		}

	}
	public float getDamage(){
		return damage;
	}

	public void applyGravity(float gravity,BlockMap map) throws SlickException{
		if(!isAnchored){
			y+=(gravityChange + gravity);
			objectPolygon.setY(y);

			if (collision(map)){
				y-=(gravityChange + gravity);
				objectPolygon.setY(y);
				objState=objectState.Standing;
				gravityChange = 0;
			} else {
				gravityChange+=.082;
			}
		}
	}

	protected void jump(float gravity, BlockMap map) throws SlickException{//do i need blockmap? 
		if(objState==objectState.Jumping){

			y-=speedY;
			objectPolygon.setY(y);
			deltaY-=speedY;

			if (collision(map)){
				y+=speedY;
				objectPolygon.setY(y);
				objState=objectState.Falling;
				deltaY=0;
			}

			if (deltaY<=-maximumHeight){
				objState=objectState.Falling;
				deltaY=0;
			}
		}

		applyGravity(gravity, map);

	}



}
