import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class WeaponManager {
	String weaponFileName;
	List<Weapon> weaponsInInventory;
	List<Weapon> weaponsInMenu;
	List<Weapon> weaponsInLevel;
	Weapon equippedWeapon;
	
	public WeaponManager(){
		weaponsInInventory=new ArrayList<Weapon>();
		weaponsInLevel=new ArrayList<Weapon>();
	}
	public void Initialize(String fileName){
		weaponFileName=fileName;
		equippedWeapon=null;
		
	}
	public void getWeapons(int curLevel) throws SlickException{
		
		Weapon cur=new Weapon();
		Gson gson=new Gson();
		
		InputStream in;
		JsonReader reader;
		try {
			in = new FileInputStream(weaponFileName);
			reader=new JsonReader(new InputStreamReader(in, "UTF-8"));
			reader.beginArray();
			while(reader.hasNext()){
				if ((cur=gson.fromJson(reader, Weapon.class)).level==curLevel){
					weaponsInLevel.add(cur);			
				}
			}
			reader.endArray();
			reader.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		for (int i=0; i<weaponsInLevel.size();i++){
			weaponsInLevel.get(i).initialize();
		}
		
	}
	
	public void update(GameContainer gc, Camera camera){
		for (int i=0; i<weaponsInLevel.size();i++){
			if (weaponsInLevel.get(i).x>camera.cameraX && weaponsInLevel.get(i).x<camera.cameraX+gc.getWidth()  && weaponsInLevel.get(i).y>camera.getY() && weaponsInLevel.get(i).y<camera.getY()+gc.getHeight()){
				weaponsInLevel.get(i).isVisible=true;
			}
			weaponsInLevel.get(i).update();
		}
	
	}
	public void checkForPickUp(Polygon playerPolygon){
		for (int i=0; i<weaponsInLevel.size();i++){
			if (weaponsInLevel.get(i).isVisible){
				if (playerPolygon.intersects(weaponsInLevel.get(i).objectPolygon)){
					weaponsInLevel.get(i).onPickUp();
					weaponsInInventory.add(weaponsInLevel.get(i));
					checkInventory();
				}
			}
		}
	}
	public void checkInventory(){
		if (weaponsInInventory.size()==1){
			equippedWeapon=weaponsInInventory.get(0);
		}
	}
	public void renderWeapons(GameContainer gc, Graphics g, Camera camera, float x, float y, int direction, int width){
		for (int i=0; i<weaponsInLevel.size();i++){
			if (weaponsInLevel.get(i).isVisible && !weaponsInLevel.get(i).isEquipped){
				weaponsInLevel.get(i).renderWeapon(gc, g);
			}
		}
		if (weaponsInInventory.size()!=0){
		switch (direction){
		case 0:equippedWeapon.leftAnimation.draw(x-equippedWeapon.width, y);break;
		case 1:equippedWeapon.rightAnimation.draw(x+width, y); break;
		default: equippedWeapon.rightAnimation.draw(x+width, y);
		}
		}
	}
	
	public List<Weapon> getInventory(){
		return weaponsInInventory;
	}
}
