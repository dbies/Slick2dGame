import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Menu<T> {
	//String menuName;
	String menuFileName;
	String selectionFileName;
	boolean isVisible;
	boolean isSelecterVisible;
	List<MenuItem<T>> choices;
	SpriteSheet spriteSheet;
	int selected;
	float selectedLocation;
	
	public void moveSelecter(int sel){
		isSelecterVisible=true;
		selected=sel;
		selectedLocation=choices.get(selected).getX();
		choices.get(selected).doAction();
	}

	public void initialize(float cameraX, float cameraY) throws SlickException{
		selected=1;
		selectedLocation=10;
		spriteSheet=new SpriteSheet(selectionFileName,32,32);
		for(int i=0; i<choices.size(); i++){
			choices.get(i).initialize(cameraX, cameraY);
		}
		
	}
	public void render(GameContainer gc, Graphics g, float cameraX, float cameraY){
			for (int i=0; i<choices.size();i++){
				choices.get(i).render(gc, g, cameraX, cameraY);
			}
			if (isSelecterVisible){
				spriteSheet.draw(choices.get(selected).x+cameraX, choices.get(selected).getY()+cameraY);
			}
			
	}
	
}
