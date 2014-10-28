import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;


public class Weapon {//non shooting weapon
	
	String name;
	float damage;
	int width, height;
	String leftSSFileName, rightSSFileName;
	int level;
	float x,y;
	boolean isVisible;
	boolean isEquipped;
	SpriteSheet leftSS, rightSS;
	Animation leftAnimation, rightAnimation;
	Polygon objectPolygon;
	
	
	
	public Weapon(){
		
	}
	public Weapon(int wt, int ht, String leftFile, String rightFile, float dmg) throws SlickException{
		width=wt;
		height=ht;
		damage=dmg;
		leftSSFileName=leftFile;
		rightSSFileName=rightFile;
		leftSS=new SpriteSheet(leftSSFileName, width, height);
		rightSS=new SpriteSheet(rightSSFileName, width, height);
		//animations
		leftAnimation=new Animation(leftSS,150);
		rightAnimation=new Animation(rightSS, 150);
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
		
	}
	public void copy(Weapon weapon){//can i equate animations?
		name=weapon.name;
		damage=weapon.damage;
		width=weapon.width;
		height=weapon.height;
		leftSSFileName=weapon.leftSSFileName;
		rightSSFileName=weapon.rightSSFileName;
		leftAnimation=weapon.leftAnimation;
		rightAnimation=weapon.rightAnimation;
		objectPolygon=weapon.objectPolygon;
	}
	public void initialize() throws SlickException{
		leftSS=new SpriteSheet(leftSSFileName, width, height);
		rightSS=new SpriteSheet(rightSSFileName, width, height);
		leftAnimation=new Animation(leftSS,150);
		rightAnimation=new Animation(rightSS, 150);
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
		
	}
	public void update(){
		objectPolygon.setX(x);
		objectPolygon.setY(y);
	}
	public void attack(){
		
	}
	public void renderWeapon(GameContainer gc, Graphics g){
		leftAnimation.draw(x,y);
	}
	public void onPickUp(){
		isVisible=false;
		isEquipped=true;
		
	}

}
