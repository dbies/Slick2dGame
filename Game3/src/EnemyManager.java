import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class EnemyManager {
	String enemyFileName;
	List<Enemy> enemiesInLevel;
	
	public EnemyManager(){
		enemiesInLevel=new ArrayList<Enemy>();
	}
	public void Initialize(String fileName){
		enemyFileName=fileName;
	}
	public void getEnemies(int curLevel) throws SlickException {
		Enemy current=new Enemy();
		Gson gson=new Gson();
		InputStream in;
		JsonReader reader;
		try{
			in=new FileInputStream(enemyFileName);
			reader=new JsonReader(new InputStreamReader(in, "UTF-8"));
			reader.beginArray();
			while(reader.hasNext()){
				if((current=gson.fromJson(reader, Enemy.class)).level==curLevel){
					enemiesInLevel.add(current);
				}
			}
			reader.endArray();
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		for(int i=0;i<enemiesInLevel.size();i++){
			enemiesInLevel.get(i).initialize();
		}
	}
	public void checkForDamage(Polygon weaponPolygon, float damage){
		for (int i=0; i<enemiesInLevel.size();i++){
			int result;
			if ((result=enemiesInLevel.get(i).ID & 0100) ==0100){
				if (enemiesInLevel.get(i).objectPolygon.intersects(weaponPolygon)){
					enemiesInLevel.get(i).onDamageTaken(damage);
					System.out.println("Damaged");
				}
			}
		}
	}

	public void update(GameContainer gc, Camera camera, float playerX, float playerY, BlockMap map, float gravity, int playerHeight,int playerWidth, Polygon playerPolygon) throws SlickException{
		for(int i=0;i<enemiesInLevel.size();i++){
			if (enemiesInLevel.get(i).x>camera.cameraX-gc.getWidth() && enemiesInLevel.get(i).x<camera.cameraX+2*gc.getWidth()  && enemiesInLevel.get(i).y>camera.getY()-gc.getHeight() && enemiesInLevel.get(i).y<camera.getY()+2*gc.getHeight()){
				//enemiesInLevel.get(i).isVisible=true;
				enemiesInLevel.get(i).onVisible();
				enemiesInLevel.get(i).updateEnemy(gc, playerX, playerY, map, gravity,playerHeight,playerWidth, playerPolygon);
			}
		}
	}
	public void renderEnemies(GameContainer gc, Graphics g) throws SlickException{
		for (int i=0; i<enemiesInLevel.size();i++){
			int result;
			if ((result=enemiesInLevel.get(i).ID & 0100) ==0100){
				enemiesInLevel.get(i).renderEnemy(gc, g);
			}
		}
	}
	
}
