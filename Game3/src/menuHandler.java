import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class menuHandler {
	List<Menu<String>> menus;
	Menu<Weapon> weaponMenu;
	
	public menuHandler(){
	}
	public void update(Input input){
		
		int selected;
		
		if (input.isKeyPressed(Input.KEY_1) ||input.isKeyPressed(Input.KEY_NUMPAD1)){
			selected=0;
			weaponMenu.moveSelecter(selected);
			
		}
		if (input.isKeyPressed(Input.KEY_2) ||input.isKeyPressed(Input.KEY_NUMPAD2)){
			selected=1;
			weaponMenu.moveSelecter(selected);
		}
		if (input.isKeyPressed(Input.KEY_3) ||input.isKeyPressed(Input.KEY_NUMPAD3)){
			selected=2;
			weaponMenu.moveSelecter(selected);
		}
		if (input.isKeyPressed(Input.KEY_4) ||input.isKeyPressed(Input.KEY_NUMPAD4)){
			selected=3;
			weaponMenu.moveSelecter(selected);
		}
	
	}
	public void initialize(float cameraX, float cameraY) throws SlickException{
		for (int i=0; i<menus.size();i++){
			menus.get(i).initialize(cameraX, cameraY);
		}
		weaponMenu.initialize(cameraX, cameraY);
	}
	public void render(GameContainer gc, Graphics g, float cameraX, float cameraY){
		for (int i=0; i<menus.size();i++){
			if (menus.get(i).isVisible){
				menus.get(i).render(gc,g, cameraX, cameraY);
			}
		}
		if (weaponMenu.isVisible){
			weaponMenu.render(gc, g, cameraX, cameraY);
		}
	}
}
