import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;


public class MenuItem <T>{
//	String name;
	float x, y;
	int width, height;
	String action;
	String fileName;
	boolean isSelected;
	T menuObject;
	Polygon objectPolygon;
	SpriteSheet spriteSheet;
	boolean isFilled;
	
	
	public MenuItem(){
		isFilled=false;
		//if (menuObject.getClass() ==Weapon.class){ //?????????
		//	menuObject=null;
		//}
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void initialize(float cameraX, float cameraY) throws SlickException{
		objectPolygon=new Polygon(new float[]{x,y,x+width,y,x+width,y+height,x,y+height});
		spriteSheet=new SpriteSheet(fileName, width, height);
		x=cameraX+x;
		y=cameraY+y;
	}
	public void doAction(){
		isSelected=true;
		if (menuObject!=null){
			if (menuObject.getClass()==Weapon.class){
				
			}
		}
	}
	public void render(GameContainer gc, Graphics g, float cameraX, float cameraY){
		spriteSheet.draw(x+cameraX,y+cameraY);
	}
	
}
